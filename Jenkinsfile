pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
    }

    stages {

        stage('Checkout') {
            steps {
                deleteDir()
                checkout scm
            }
        }

        stage('Build User Service') {
            steps {
                dir('user-service/user-service') {
                    sh 'chmod +x mvnw'
                    sh './mvnw clean package -DskipTests'
                }
            }
        }

        stage('Build Order Service') {
            steps {
                dir('order-service/order-service') {
                    sh 'chmod +x mvnw'
                    sh './mvnw clean package -DskipTests'
                }
            }
        }

        stage('Build Notification Service') {
            steps {
                dir('notification-service') {
                    sh 'chmod +x mvnw'
                    sh './mvnw clean package -DskipTests'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t user-service:1.0 user-service/user-service'
                sh 'docker build -t order-service:1.0 order-service/order-service'
                sh 'docker build -t notification-service:1.0 notification-service'
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )
                ]) {

                    sh '''
                        echo "$DOCKER_PASSWORD" | docker login \
                            -u "$DOCKER_USER" \
                            --password-stdin

                        docker image rm "$DOCKER_USER/user-service:1.0" || true
                        docker image rm "$DOCKER_USER/order-service:1.0" || true
                        docker image rm "$DOCKER_USER/notification-service:1.0" || true

                        docker tag user-service:1.0 \
                            "$DOCKER_USER/user-service:1.0"

                        docker tag order-service:1.0 \
                            "$DOCKER_USER/order-service:1.0"

                        docker tag notification-service:1.0 \
                            "$DOCKER_USER/notification-service:1.0"

                        docker push "$DOCKER_USER/user-service:1.0"
                        docker push "$DOCKER_USER/order-service:1.0"
                        docker push "$DOCKER_USER/notification-service:1.0"
                    '''
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                    kubectl \
                        --kubeconfig /var/jenkins_home/jenkins-kubeconfig.yaml \
                        apply -f k8s/
                '''
            }
        }

        stage('Verify Kubernetes Deployment') {
            steps {
                sh '''
                    echo "===== DEPLOYMENTS ====="

                    kubectl \
                        --kubeconfig /var/jenkins_home/jenkins-kubeconfig.yaml \
                        get deployments

                    echo "===== PODS ====="

                    kubectl \
                        --kubeconfig /var/jenkins_home/jenkins-kubeconfig.yaml \
                        get pods

                    echo "===== SERVICES ====="

                    kubectl \
                        --kubeconfig /var/jenkins_home/jenkins-kubeconfig.yaml \
                        get services
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully: Maven → Docker Build → Docker Push → Kubernetes Deploy → Verification!'
        }

        failure {
            echo 'Pipeline failed!'
        }
    }
}