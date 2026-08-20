pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
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