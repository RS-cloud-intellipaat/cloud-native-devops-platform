Cloud-Native DevOps Platform

A cloud-native microservices platform built with Spring Boot, Docker, Kubernetes, and Jenkins CI/CD.

The project demonstrates an end-to-end DevOps workflow:

Source Code
    │
    ▼
   GitHub
    │
    ▼
  Jenkins
    │
    ├── Checkout
    ├── Maven Build
    ├── Docker Build
    ├── Docker Push
    ├── Kubernetes Deploy
    └── Deployment Verification
    │
    ▼
 Minikube / Kubernetes
    │
    ├── User Service
    ├── Order Service
    └── Notification Service

Project Overview

This repository contains three Spring Boot microservices:

Service

Application Port

Kubernetes Service

User Service

8081

NodePort 30081

Order Service

8082

NodePort 30082

Notification Service

8083

NodePort 30083

The services are packaged with Maven, containerized with Docker, and deployed to Kubernetes using YAML manifests.

Technology Stack

Java 17

Spring Boot 4.0.7

Maven / Maven Wrapper

Spring Boot Actuator

Spring Web MVC

Docker

Docker Hub

Kubernetes

Minikube

kubectl

Jenkins

Jenkins Declarative Pipeline

Git / GitHub

Docker Compose

The User Service Maven configuration currently targets Java 17 and Spring Boot 4.0.7. The other services follow the same Maven/Spring Boot project pattern.

Repository Structure

cloud-native-devops-platform/
│
├── k8s/
│   ├── user-deployment.yaml
│   ├── user-service.yaml
│   ├── order-deployment.yaml
│   ├── order-service.yaml
│   ├── notification-deployment.yaml
│   └── notification-service.yaml
│
├── user-service/
│   └── user-service/
│       ├── src/
│       ├── pom.xml
│       ├── mvnw
│       └── Dockerfile
│
├── order-service/
│   └── order-service/
│       ├── src/
│       ├── pom.xml
│       ├── mvnw
│       └── Dockerfile
│
├── notification-service/
│   ├── src/
│   ├── pom.xml
│   ├── mvnw
│   └── Dockerfile
│
├── Dockerfile.jenkins
├── Jenkinsfile
├── compose.yaml
└── README.md

Microservices

1. User Service

The User Service is a Spring Boot application exposed on port 8081.

Build locally:

cd user-service/user-service
.\mvnw.cmd clean package -DskipTests

Docker image:

user-service:1.0

Kubernetes container port:

8081

2. Order Service

The Order Service is a Spring Boot application exposed on port 8082.

Build locally:

cd order-service/order-service
.\mvnw.cmd clean package -DskipTests

Docker image:

order-service:1.0

Kubernetes container port:

8082

3. Notification Service

The Notification Service is a Spring Boot application exposed on port 8083.

Build locally:

cd notification-service
.\mvnw.cmd clean package -DskipTests

Docker image:

notification-service:1.0

Kubernetes container port:

8083

Docker Images

The project builds the following images:

user-service:1.0
order-service:1.0
notification-service:1.0

For Docker Hub, the Jenkins pipeline tags the images using the Jenkins credential username:

<DOCKER_USER>/user-service:1.0
<DOCKER_USER>/order-service:1.0
<DOCKER_USER>/notification-service:1.0

Example:

rakeshs53350/user-service:1.0
rakeshs53350/order-service:1.0
rakeshs53350/notification-service:1.0

Docker Compose

The repository also contains compose.yaml for running the three services as Docker containers.

Current host-to-container mappings are:

Service

Container Port

Host Port

User Service

8081

8084

Order Service

8082

8085

Notification Service

8083

8086

Start the services:

docker compose up -d

Check running containers:

docker compose ps

Stop the services:

docker compose down

Kubernetes Deployment

Kubernetes manifests are stored in the k8s/ directory.

The repository contains:

k8s/
├── user-deployment.yaml
├── user-service.yaml
├── order-deployment.yaml
├── order-service.yaml
├── notification-deployment.yaml
└── notification-service.yaml

Apply all manifests:

kubectl apply -f k8s/

Check deployments:

kubectl get deployments

Check pods:

kubectl get pods

Check services:

kubectl get services

Minikube Setup

This project uses Minikube for local Kubernetes deployment.

Start Minikube:

minikube start --driver=docker

Check status:

minikube status

Verify the Kubernetes node:

kubectl get nodes

Expected result:

NAME       STATUS   ROLES           ...
minikube   Ready    control-plane   ...

Accessing the Kubernetes Services

The services are exposed using Kubernetes NodePort.

Current service mappings:

User Service         30081
Order Service        30082
Notification Service 30083

You can obtain the Minikube IP with:

minikube ip

Then use:

http://<MINIKUBE-IP>:30081
http://<MINIKUBE-IP>:30082
http://<MINIKUBE-IP>:30083

Alternatively, Minikube can resolve the service URL:

minikube service user-service --url
minikube service order-service --url
minikube service notification-service --url

Jenkins CI/CD Pipeline

The Jenkinsfile implements the CI/CD workflow.

Pipeline stages

1. Checkout
       ↓
2. Build User Service
       ↓
3. Build Order Service
       ↓
4. Build Notification Service
       ↓
5. Docker Build
       ↓
6. Docker Push
       ↓
7. Deploy to Kubernetes
       ↓
8. Verify Kubernetes Deployment

1. Checkout

The pipeline disables Jenkins' default checkout and explicitly cleans the workspace before checking out the source code.

options {
    skipDefaultCheckout(true)
}

The checkout stage uses:

deleteDir()
checkout scm

2. Maven Build

Each microservice is built independently using its Maven Wrapper.

Example:

./mvnw clean package -DskipTests

The pipeline builds:

user-service
order-service
notification-service

3. Docker Build

The pipeline creates Docker images:

docker build -t user-service:1.0 user-service/user-service
docker build -t order-service:1.0 order-service/order-service
docker build -t notification-service:1.0 notification-service

4. Docker Push

Jenkins uses a credential named:

dockerhub-creds

The credential should be configured in Jenkins as a username/password credential.

The pipeline logs in securely using:

docker login --password-stdin

and pushes:

<DOCKER_USER>/user-service:1.0
<DOCKER_USER>/order-service:1.0
<DOCKER_USER>/notification-service:1.0

5. Kubernetes Deployment

The pipeline applies the Kubernetes manifests:

kubectl \
  --kubeconfig /var/jenkins_home/jenkins-kubeconfig.yaml \
  apply -f k8s/

6. Deployment Verification

The pipeline verifies:

kubectl get deployments
kubectl get pods
kubectl get services

This provides a basic deployment verification step directly in the Jenkins console.

Jenkins with Docker and kubectl

The repository contains:

Dockerfile.jenkins

This creates a Jenkins image containing the tools required by the pipeline.

The custom Jenkins image provides:

Jenkins

Docker CLI

kubectl

Build the image:

docker build -t jenkins-with-docker -f Dockerfile.jenkins .

Run Jenkins using the existing Jenkins home volume:

docker run -d `
  --name jenkins `
  --network jenkins `
  -p 8087:8080 `
  -p 50000:50000 `
  -v jenkins_home:/var/jenkins_home `
  -v /var/run/docker.sock:/var/run/docker.sock `
  jenkins-with-docker

Open Jenkins:

http://localhost:8087

Verify Docker inside Jenkins:

docker exec jenkins docker --version

Verify kubectl:

docker exec jenkins kubectl version --client

Jenkins to Minikube Connectivity

The Jenkins container needs access to the Minikube Kubernetes API.

A Jenkins-specific kubeconfig is stored inside the Jenkins container:

/var/jenkins_home/jenkins-kubeconfig.yaml

The pipeline uses this kubeconfig for Kubernetes operations:

kubectl --kubeconfig /var/jenkins_home/jenkins-kubeconfig.yaml ...

Verify that Jenkins can access Kubernetes:

docker exec jenkins kubectl `
  --kubeconfig /var/jenkins_home/jenkins-kubeconfig.yaml `
  get nodes

Verify deployments:

docker exec jenkins kubectl `
  --kubeconfig /var/jenkins_home/jenkins-kubeconfig.yaml `
  get deployments

Verify services:

docker exec jenkins kubectl `
  --kubeconfig /var/jenkins_home/jenkins-kubeconfig.yaml `
  get services

Jenkins Job Configuration

Create a Jenkins Pipeline job named:

cloud-native-devops-pipeline

Configure:

Definition:
Pipeline script from SCM

SCM:
Git

Repository URL:
https://github.com/RS-cloud-intellipaat/cloud-native-devops-platform.git

Branch:
*/master

Script Path:
Jenkinsfile
