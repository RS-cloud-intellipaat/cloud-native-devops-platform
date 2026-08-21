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
        withCredentials([usernamePassword(
            credentialsId: 'dockerhub-creds',
            usernameVariable: 'DOCKER_USER',
            passwordVariable: 'DOCKER_PASSWORD'
        )]) {

            sh 'echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USER" --password-stdin'

            // Remove existing local Docker Hub tags
            sh 'docker image rm $DOCKER_USER/user-service:1.0 || true'
            sh 'docker image rm $DOCKER_USER/order-service:1.0 || true'
            sh 'docker image rm $DOCKER_USER/notification-service:1.0 || true'

            // Create Docker Hub tags
            sh 'docker tag user-service:1.0 $DOCKER_USER/user-service:1.0'
            sh 'docker tag order-service:1.0 $DOCKER_USER/order-service:1.0'
            sh 'docker tag notification-service:1.0 $DOCKER_USER/notification-service:1.0'

            // Push images
            sh 'docker push $DOCKER_USER/user-service:1.0'
            sh 'docker push $DOCKER_USER/order-service:1.0'
            sh 'docker push $DOCKER_USER/notification-service:1.0'
        }
    }
}
        }
    }

    post {
        success {
            echo 'Maven builds and Docker builds completed successfully!'
        }

        failure {
            echo 'Pipeline failed!'
        }
    }
}