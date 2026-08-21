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

The Jenkins job should have the Docker Hub credential:

dockerhub-creds

Docker Hub Credential

Create the Jenkins credential:

Credentials
  → System
    → Global credentials
      → Add Credentials

Use:

Kind: Username with password
ID: dockerhub-creds
Username: <Docker Hub username>
Password: <Docker Hub access token/password>

For production usage, prefer a Docker Hub access token instead of a normal account password.

Running the Pipeline

After configuring the Jenkins job:

Open Jenkins.

Open cloud-native-devops-pipeline.

Click Build Now.

Open the build.

Open Console Output.

A successful pipeline should progress through:

Checkout
Build User Service
Build Order Service
Build Notification Service
Docker Build
Docker Push
Deploy to Kubernetes
Verify Kubernetes Deployment

The final message is expected to indicate successful completion of the CI/CD flow.

Useful Kubernetes Commands

Check all resources:

kubectl get all

Check pods:

kubectl get pods -o wide

Check deployments:

kubectl get deployments

Check services:

kubectl get services

Describe a pod:

kubectl describe pod <pod-name>

View pod logs:

kubectl logs <pod-name>

Restart a deployment:

kubectl rollout restart deployment user-service

Check rollout status:

kubectl rollout status deployment/user-service

Delete the application resources:

kubectl delete -f k8s/

Troubleshooting

Jenkins is not accessible

Check the container:

docker ps -a --filter "name=jenkins"

Check Jenkins logs:

docker logs jenkins --tail 100

Docker command is not available inside Jenkins

Check:

docker exec jenkins docker --version

If Docker is missing, make sure Jenkins is running from the custom image:

jenkins-with-docker

and that the Docker socket is mounted:

-v /var/run/docker.sock:/var/run/docker.sock

kubectl has no context inside Jenkins

Check:

docker exec jenkins kubectl config get-contexts

Use the Jenkins kubeconfig explicitly:

docker exec jenkins kubectl `
  --kubeconfig /var/jenkins_home/jenkins-kubeconfig.yaml `
  get nodes

Jenkins cannot connect to Kubernetes

Check the kubeconfig:

docker exec jenkins kubectl `
  --kubeconfig /var/jenkins_home/jenkins-kubeconfig.yaml `
  cluster-info

Make sure the kubeconfig is accessible inside the container and that Minikube is running.

Check Minikube

minikube status

If stopped:

minikube start --driver=docker

Pipeline fails during Git checkout

Verify the repository:

https://github.com/RS-cloud-intellipaat/cloud-native-devops-platform.git

and ensure the Jenkins job is configured to use the master branch and Jenkinsfile.

Important Image/Minikube Note

The Kubernetes deployment manifests currently use local image names such as:

user-service:1.0
order-service:1.0
notification-service:1.0

The User Service deployment currently uses:

imagePullPolicy: Never

while the Order and Notification deployments use:

imagePullPolicy: IfNotPresent

This is suitable for a local Minikube workflow where the images are available to the Kubernetes runtime.

If the project is moved to a remote Kubernetes cluster, the deployment manifests should be updated to use registry-qualified image names, for example:

rakeshs53350/user-service:1.0
rakeshs53350/order-service:1.0
rakeshs53350/notification-service:1.0

and the cluster must be able to pull those images from Docker Hub.

Security Notes

Do not commit any of the following to Git:

Docker Hub passwords

Docker Hub access tokens

Jenkins credentials

Kubernetes private keys

Personal kubeconfig files

Cloud provider access keys

API tokens

The Jenkins kubeconfig used for local development should be treated as a credential and should not be committed to the repository.

DevOps Skills Demonstrated

This project demonstrates practical experience with:

Git and GitHub

Git branching and commits

Maven builds

Spring Boot microservices

Docker image creation

Docker image tagging

Docker Hub

Jenkins

Jenkins Declarative Pipeline

Jenkins credentials

Docker-in-Jenkins

Kubernetes Deployments

Kubernetes Services

NodePort

Minikube

kubectl

Kubernetes rollout verification

CI/CD automation

CI/CD Flow Summary

Developer
   │
   ▼
GitHub Repository
   │
   ▼
Jenkins
   │
   ├── Checkout source
   │
   ├── Maven package
   │      ├── User Service
   │      ├── Order Service
   │      └── Notification Service
   │
   ├── Build Docker images
   │
   ├── Login to Docker Hub
   │
   ├── Push Docker images
   │
   ├── Apply Kubernetes manifests
   │
   └── Verify deployments/pods/services
   │
   ▼
Minikube / Kubernetes
   │
   ├── User Service
   ├── Order Service
   └── Notification Service

Project Status

The repository currently contains the microservices, Docker configuration, Kubernetes manifests, Jenkins pipeline, and local Minikube deployment workflow.

The Jenkins pipeline has been configured to perform:

Maven Build → Docker Build → Docker Push → Kubernetes Deploy → Verification

Repository

GitHub:

https://github.com/RS-cloud-intellipaat/cloud-native-devops-platform

Author

RS-cloud-intellipaat

Cloud-native DevOps project demonstrating containerization, CI/CD automation, and Kubernetes deployment.
