<div align="center">

# ☁️ Cloud-Native DevOps Platform

### Spring Boot • Docker • Jenkins • Kubernetes • Minikube

A production-style DevOps project demonstrating how multiple Spring Boot microservices are **built, containerized, pushed to Docker Hub, and deployed to Kubernetes through Jenkins CI/CD**.

<br/>

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=springboot)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED?style=for-the-badge&logo=docker)
![Jenkins](https://img.shields.io/badge/Jenkins-CI%2FCD-D24939?style=for-the-badge&logo=jenkins)
![Kubernetes](https://img.shields.io/badge/Kubernetes-Orchestration-326CE5?style=for-the-badge&logo=kubernetes)
![Minikube](https://img.shields.io/badge/Minikube-Local%20Cluster-1E88E5?style=for-the-badge&logo=kubernetes)

</div>

---

## 🚀 Project Overview

This project implements a small **cloud-native microservices platform** with an end-to-end DevOps workflow.

The platform contains three independent Spring Boot services:

| Service | Purpose | Port |
|---|---|---:|
| 👤 **User Service** | User-related REST APIs | `8081` |
| 📦 **Order Service** | Order-related REST APIs | `8082` |
| 🔔 **Notification Service** | Notification-related REST APIs | `8083` |

### CI/CD workflow

```text
Developer
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
   ├── Docker Login
   ├── Docker Push
   │
   ▼
Docker Hub
   │
   ▼
Kubernetes / Minikube
   │
   ├── User Service
   ├── Order Service
   └── Notification Service
```

---

## 🏗️ Architecture

```text
                         ┌─────────────────────┐
                         │       GitHub        │
                         │  Source Repository  │
                         └──────────┬──────────┘
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │      Jenkins       │
                         │       CI/CD        │
                         └──────────┬──────────┘
                                    │
                    ┌───────────────┼───────────────┐
                    │               │               │
                    ▼               ▼               ▼
              Maven Build     Docker Build     Docker Push
                    │               │               │
                    └───────────────┼───────────────┘
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │     Docker Hub      │
                         │ Container Registry  │
                         └──────────┬──────────┘
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │ Kubernetes /         │
                         │ Minikube Cluster     │
                         └──────────┬──────────┘
                                    │
              ┌─────────────────────┼─────────────────────┐
              │                     │                     │
              ▼                     ▼                     ▼
        User Service         Order Service        Notification
        NodePort 30081       NodePort 30082       NodePort 30083
```

---

## 📁 Repository Structure

```text
cloud-native-devops-platform/
│
├── .gitignore
├── README.md
├── Jenkinsfile
├── Dockerfile.jenkins
├── compose.yaml
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
└── notification-service/
    ├── src/
    ├── pom.xml
    ├── mvnw
    └── Dockerfile
```

> **Note:** `jenkins-kubeconfig.yaml` is intentionally excluded from GitHub because it contains Kubernetes authentication material.

---

## 🧩 Microservices

### 👤 User Service

Spring Boot application responsible for user-related functionality.

- Maven project
- Dockerized
- Kubernetes Deployment
- Kubernetes NodePort Service
- Exposed through NodePort `30081`

### 📦 Order Service

Spring Boot application responsible for order-related functionality.

- Maven project
- Dockerized
- Kubernetes Deployment
- Kubernetes NodePort Service
- Exposed through NodePort `30082`

### 🔔 Notification Service

Spring Boot application responsible for notification functionality.

- Maven project
- Dockerized
- Kubernetes Deployment
- Kubernetes NodePort Service
- Exposed through NodePort `30083`

---

## 🐳 Docker

Each microservice has its own Docker image.

```bash
docker build -t user-service:1.0 user-service/user-service
docker build -t order-service:1.0 order-service/order-service
docker build -t notification-service:1.0 notification-service
```

Images are tagged for Docker Hub:

```text
<dockerhub-user>/user-service:1.0
<dockerhub-user>/order-service:1.0
<dockerhub-user>/notification-service:1.0
```

---

## 🔄 Jenkins CI/CD Pipeline

The Jenkins pipeline automates the application delivery process.

### Pipeline stages

```text
┌──────────┐
│ Checkout │
└────┬─────┘
     ▼
┌───────────────┐
│ Maven Build   │
└────┬──────────┘
     ▼
┌───────────────┐
│ Docker Build  │
└────┬──────────┘
     ▼
┌───────────────┐
│ Docker Push   │
└────┬──────────┘
     ▼
┌─────────────────────┐
│ Kubernetes Deploy   │
└─────────────────────┘
```

Jenkins uses the repository `Jenkinsfile` to execute the pipeline.

The project also includes `Dockerfile.jenkins`, which creates a Jenkins image containing Docker CLI and `kubectl`.

---

## ☸️ Kubernetes Deployment

The Kubernetes manifests are stored under:

```text
k8s/
```

Apply the deployments:

```bash
kubectl apply -f k8s/user-deployment.yaml
kubectl apply -f k8s/user-service.yaml

kubectl apply -f k8s/order-deployment.yaml
kubectl apply -f k8s/order-service.yaml

kubectl apply -f k8s/notification-deployment.yaml
kubectl apply -f k8s/notification-service.yaml
```

Check deployments:

```bash
kubectl get deployments
```

Check pods:

```bash
kubectl get pods
```

Check services:

```bash
kubectl get services
```

---

## 🖥️ Minikube

Start the local Kubernetes cluster:

```bash
minikube start --driver=docker
```

Check cluster status:

```bash
minikube status
```

Check nodes:

```bash
kubectl get nodes
```

Get the Minikube IP:

```bash
minikube ip
```

Open a NodePort service:

```bash
minikube service user-service
```

or:

```bash
minikube service order-service
```

or:

```bash
minikube service notification-service
```

---

## 🔐 Jenkins → Kubernetes Integration

Jenkins is configured to communicate with the Minikube Kubernetes API using a dedicated kubeconfig.

The kubeconfig is copied into the Jenkins container and used with:

```bash
kubectl --kubeconfig /var/jenkins_home/jenkins-kubeconfig.yaml get deployments
```

Example verification:

```bash
kubectl --kubeconfig /var/jenkins_home/jenkins-kubeconfig.yaml get services
```

### Security

The following file must **never** be committed to GitHub:

```text
jenkins-kubeconfig.yaml
```

It is included in `.gitignore`.

---

## 📦 Docker Hub Images

The pipeline publishes the following images:

| Image | Tag |
|---|---|
| `rakeshs53350/user-service` | `1.0` |
| `rakeshs53350/order-service` | `1.0` |
| `rakeshs53350/notification-service` | `1.0` |

---

## 🛠️ Technology Stack

| Category | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot |
| Build | Maven |
| Containerization | Docker |
| CI/CD | Jenkins |
| Registry | Docker Hub |
| Orchestration | Kubernetes |
| Local Kubernetes | Minikube |
| Source Control | Git / GitHub |
| Configuration | YAML |
| Platform | Windows + Docker Desktop |

---

## 🎯 DevOps Skills Demonstrated

This project demonstrates practical experience with:

- ✅ Git and GitHub
- ✅ Git branching and commits
- ✅ Maven builds
- ✅ Spring Boot microservices
- ✅ Docker image creation
- ✅ Docker Hub
- ✅ Jenkins pipelines
- ✅ Jenkins credentials
- ✅ CI/CD automation
- ✅ Kubernetes Deployments
- ✅ Kubernetes Services
- ✅ NodePort
- ✅ Minikube
- ✅ `kubectl`
- ✅ Jenkins-to-Kubernetes integration
- ✅ Containerized Jenkins
- ✅ Kubernetes configuration management

---

## 🧪 Useful Commands

### Git

```bash
git status
git add .
git commit -m "message"
git push origin master
```

### Docker

```bash
docker ps
docker images
docker build -t <image>:<tag> .
docker push <image>:<tag>
```

### Kubernetes

```bash
kubectl get nodes
kubectl get pods
kubectl get deployments
kubectl get services
kubectl describe pod <pod-name>
kubectl logs <pod-name>
```

### Minikube

```bash
minikube status
minikube ip
minikube service list
```

---

## 🚧 Future Enhancements

Planned improvements include:

- [ ] Add automated unit tests to the Jenkins pipeline
- [ ] Add SonarQube code-quality analysis
- [ ] Add Trivy container security scanning
- [ ] Add Kubernetes rolling deployments
- [ ] Add Ingress
- [ ] Add Helm charts
- [ ] Add Prometheus and Grafana monitoring
- [ ] Add GitHub webhook-based automatic builds
- [ ] Add AWS EKS deployment
- [ ] Add Terraform infrastructure provisioning

---

## 👨‍💻 Author

**Rakesh**

Cloud / DevOps Engineer

### Core focus

`AWS` • `Docker` • `Kubernetes` • `Jenkins` • `Terraform` • `CI/CD` • `Linux` • `Git`

---

<div align="center">

### ⭐ If you find this project useful, consider giving it a star!

**Built as a hands-on Cloud-Native DevOps portfolio project.**

</div>
