**# ☁️ Cloud-Native DevOps Platform**



**<p align="center">**

&#x20; **<b>Spring Boot Microservices • Docker • Jenkins CI/CD • Kubernetes • Minikube</b>**

**</p>**



**<p align="center">**

&#x20; **A production-style DevOps project demonstrating how multiple Spring Boot microservices are**

&#x20; **built, containerized, pushed to Docker Hub, and deployed to Kubernetes through Jenkins.**

**</p>**



**---**



**## 🚀 Project Overview**



**This project implements a small \*\*cloud-native microservices platform\*\* and an automated CI/CD workflow.**



**The platform contains three independent Spring Boot services:**



**- 👤 \*\*User Service\*\***

**- 📦 \*\*Order Service\*\***

**- 🔔 \*\*Notification Service\*\***



**The complete delivery workflow is automated with Jenkins:**



**```text**

&#x20;                   **┌─────────────────┐**

&#x20;                   **│     GitHub      │**

&#x20;                   **│   Source Code   │**

&#x20;                   **└────────┬────────┘**

&#x20;                            **│**

&#x20;                            **▼**

&#x20;                   **┌─────────────────┐**

&#x20;                   **│     Jenkins     │**

&#x20;                   **│     CI / CD     │**

&#x20;                   **└────────┬────────┘**

&#x20;                            **│**

&#x20;             **┌──────────────┼──────────────┐**

&#x20;             **▼              ▼              ▼**

&#x20;       **Maven Build     Docker Build    Docker Push**

&#x20;             **│              │              │**

&#x20;             **└──────────────┼──────────────┘**

&#x20;                            **▼**

&#x20;                   **┌─────────────────┐**

&#x20;                   **│     Docker Hub  │**

&#x20;                   **└────────┬────────┘**

&#x20;                            **│**

&#x20;                            **▼**

&#x20;                   **┌─────────────────┐**

&#x20;                   **│ Kubernetes /    │**

&#x20;                   **│    Minikube     │**

&#x20;                   **└────────┬────────┘**

&#x20;                            **│**

&#x20;            **┌───────────────┼───────────────┐**

&#x20;            **▼               ▼               ▼**

&#x20;      **User Service    Order Service   Notification**

&#x20;                                          **Service**

**```**



**---**



**## 🧰 Technology Stack**



**| Area | Technology |**

**|---|---|**

**| Application | Java 17 |**

**| Framework | Spring Boot |**

**| Build | Maven / Maven Wrapper |**

**| Source Control | Git \& GitHub |**

**| Containerization | Docker |**

**| Container Registry | Docker Hub |**

**| CI/CD | Jenkins |**

**| Orchestration | Kubernetes |**

**| Local Cluster | Minikube |**

**| Kubernetes CLI | kubectl |**

**| Local Containers | Docker Compose |**



**---**



**## 📁 Repository Structure**



**```text**

**cloud-native-devops-platform/**

**│**

**├── 📂 k8s/**

**│   ├── user-deployment.yaml**

**│   ├── user-service.yaml**

**│   ├── order-deployment.yaml**

**│   ├── order-service.yaml**

**│   ├── notification-deployment.yaml**

**│   └── notification-service.yaml**

**│**

**├── 📂 user-service/**

**│   └── 📂 user-service/**

**│       ├── 📂 src/**

**│       ├── pom.xml**

**│       ├── mvnw**

**│       └── Dockerfile**

**│**

**├── 📂 order-service/**

**│   └── 📂 order-service/**

**│       ├── 📂 src/**

**│       ├── pom.xml**

**│       ├── mvnw**

**│       └── Dockerfile**

**│**

**├── 📂 notification-service/**

**│   ├── 📂 src/**

**│   ├── pom.xml**

**│   ├── mvnw**

**│   └── Dockerfile**

**│**

**├── 🐳 Dockerfile.jenkins**

**├── 🔄 Jenkinsfile**

**├── 🐳 compose.yaml**

**└── 📖 README.md**

**```**



**---**



**## 🧩 Microservices**



**### 👤 User Service**



**Spring Boot microservice responsible for user-related functionality.**



**- Application port: \*\*8081\*\***

**- Docker image: `user-service:1.0`**

**- Kubernetes NodePort: \*\*30081\*\***



**### 📦 Order Service**



**Spring Boot microservice responsible for order-related functionality.**



**- Application port: \*\*8082\*\***

**- Docker image: `order-service:1.0`**

**- Kubernetes NodePort: \*\*30082\*\***



**### 🔔 Notification Service**



**Spring Boot microservice responsible for notification-related functionality.**



**- Application port: \*\*8083\*\***

**- Docker image: `notification-service:1.0`**

**- Kubernetes NodePort: \*\*30083\*\***



**---**



**## 🔄 CI/CD Pipeline**



**The Jenkins pipeline follows this workflow:**



**```text**

**1. Checkout**

&#x20;     **↓**

**2. Build User Service**

&#x20;     **↓**

**3. Build Order Service**

&#x20;     **↓**

**4. Build Notification Service**

&#x20;     **↓**

**5. Docker Build**

&#x20;     **↓**

**6. Docker Push**

&#x20;     **↓**

**7. Deploy to Kubernetes**

&#x20;     **↓**

**8. Verify Kubernetes Deployment**

**```**



**### Pipeline stages**



**#### 1️⃣ Checkout**



**Jenkins cleans the workspace and checks out the source code from GitHub.**



**```groovy**

**options {**

&#x20;   **skipDefaultCheckout(true)**

**}**

**```**



**```groovy**

**deleteDir()**

**checkout scm**

**```**



**#### 2️⃣ Maven Build**



**Each microservice is built independently using its Maven Wrapper.**



**```bash**

**./mvnw clean package -DskipTests**

**```**



**#### 3️⃣ Docker Build**



**Docker images are created for all three services:**



**```bash**

**docker build -t user-service:1.0 user-service/user-service**

**docker build -t order-service:1.0 order-service/order-service**

**docker build -t notification-service:1.0 notification-service**

**```**



**#### 4️⃣ Docker Push**



**Jenkins authenticates to Docker Hub using the Jenkins credential:**



**```text**

**dockerhub-creds**

**```**



**Images are tagged and pushed as:**



**```text**

**<DOCKER\_USER>/user-service:1.0**

**<DOCKER\_USER>/order-service:1.0**

**<DOCKER\_USER>/notification-service:1.0**

**```**



**#### 5️⃣ Kubernetes Deployment**



**The Kubernetes manifests under `k8s/` are applied:**



**```bash**

**kubectl apply -f k8s/**

**```**



**#### 6️⃣ Verification**



**Jenkins verifies the resulting Kubernetes resources:**



**```bash**

**kubectl get deployments**

**kubectl get pods**

**kubectl get services**

**```**



**---**



**## 🐳 Docker**



**### Build images manually**



**```powershell**

**docker build -t user-service:1.0 user-service/user-service**

**docker build -t order-service:1.0 order-service/order-service**

**docker build -t notification-service:1.0 notification-service**

**```**



**Check images:**



**```powershell**

**docker images**

**```**



**### Docker Compose**



**The project also contains `compose.yaml` for local container-based execution.**



**Start:**



**```powershell**

**docker compose up -d**

**```**



**Check:**



**```powershell**

**docker compose ps**

**```**



**Stop:**



**```powershell**

**docker compose down**

**```**



**---**



**## ☸️ Kubernetes \& Minikube**



**### Start Minikube**



**```powershell**

**minikube start --driver=docker**

**```**



**Check status:**



**```powershell**

**minikube status**

**```**



**Check cluster:**



**```powershell**

**kubectl get nodes**

**```**



**### Deploy the platform**



**```powershell**

**kubectl apply -f k8s/**

**```**



**### Verify deployments**



**```powershell**

**kubectl get deployments**

**```**



**### Verify pods**



**```powershell**

**kubectl get pods**

**```**



**### Verify services**



**```powershell**

**kubectl get services**

**```**



**---**



**## 🌐 Kubernetes Services**



**The application services are exposed through Kubernetes `NodePort`.**



**| Service | Application Port | NodePort |**

**|---|---:|---:|**

**| User Service | 8081 | 30081 |**

**| Order Service | 8082 | 30082 |**

**| Notification Service | 8083 | 30083 |**



**Get the Minikube IP:**



**```powershell**

**minikube ip**

**```**



**Alternatively, ask Minikube for the service URL:**



**```powershell**

**minikube service user-service --url**

**minikube service order-service --url**

**minikube service notification-service --url**

**```**



**---**



**## 🏗️ Jenkins Setup**



**The project includes `Dockerfile.jenkins`, which creates a custom Jenkins image containing the tools required for the CI/CD pipeline.**



**### Build Jenkins image**



**```powershell**

**docker build -t jenkins-with-docker -f Dockerfile.jenkins .**

**```**



**### Run Jenkins**



**```powershell**

**docker run -d `**

&#x20; **--name jenkins `**

&#x20; **--network jenkins `**

&#x20; **-p 8087:8080 `**

&#x20; **-p 50000:50000 `**

&#x20; **-v jenkins\_home:/var/jenkins\_home `**

&#x20; **-v /var/run/docker.sock:/var/run/docker.sock `**

&#x20; **jenkins-with-docker**

**```**



**Open Jenkins:**



**```text**

**http://localhost:8087**

**```**



**### Verify Docker inside Jenkins**



**```powershell**

**docker exec jenkins docker --version**

**```**



**### Verify kubectl inside Jenkins**



**```powershell**

**docker exec jenkins kubectl version --client**

**```**



**---**



**## 🔐 Jenkins → Kubernetes Connectivity**



**Jenkins uses a dedicated kubeconfig:**



**```text**

**/var/jenkins\_home/jenkins-kubeconfig.yaml**

**```**



**The Kubernetes commands in the pipeline use this configuration to communicate with the Minikube cluster.**



**Test connectivity:**



**```powershell**

**docker exec jenkins kubectl `**

&#x20; **--kubeconfig /var/jenkins\_home/jenkins-kubeconfig.yaml `**

&#x20; **get nodes**

**```**



**Expected:**



**```text**

**NAME       STATUS   ROLES**

**minikube   Ready    control-plane**

**```**



**---**



**## 🔑 Jenkins Credentials**



**Create a Jenkins credential with:**



**```text**

**Kind: Username with password**

**ID:   dockerhub-creds**

**```**



**Use a \*\*Docker Hub access token\*\* rather than storing a normal account password where possible.**



**The pipeline accesses the credential securely:**



**```groovy**

**withCredentials(\[usernamePassword(**

&#x20;   **credentialsId: 'dockerhub-creds',**

&#x20;   **usernameVariable: 'DOCKER\_USER',**

&#x20;   **passwordVariable: 'DOCKER\_PASSWORD'**

**)])**

**```**



**---**



**## ⚙️ Jenkins Job Configuration**



**Create a Jenkins \*\*Pipeline\*\* job:**



**```text**

**Job Name:**

**cloud-native-devops-pipeline**

**```**



**Configure:**



**```text**

**Definition:**

**Pipeline script from SCM**



**SCM:**

**Git**



**Repository:**

**https://github.com/RS-cloud-intellipaat/cloud-native-devops-platform.git**



**Branch:**

**\*/master**



**Script Path:**

**Jenkinsfile**

**```**



**Then select:**



**```text**

**Build Now**

**```**



**and inspect:**



**```text**

**Build → Console Output**

**```**



**---**



**## 🧪 Useful Commands**



**### Git**



**```powershell**

**git status**

**git log --oneline -5**

**git add .**

**git commit -m "your message"**

**git push origin master**

**```**



**### Docker**



**```powershell**

**docker ps**

**docker ps -a**

**docker images**

**docker logs jenkins --tail 100**

**```**



**### Kubernetes**



**```powershell**

**kubectl get nodes**

**kubectl get pods**

**kubectl get deployments**

**kubectl get services**

**kubectl get all**

**```**



**### Minikube**



**```powershell**

**minikube status**

**minikube ip**

**minikube service list**

**```**



**---**



**## 🛠️ Troubleshooting**



**### Jenkins does not open**



**Check:**



**```powershell**

**docker ps -a --filter "name=jenkins"**

**```**



**View logs:**



**```powershell**

**docker logs jenkins --tail 100**

**```**



**### Docker is unavailable inside Jenkins**



**Check:**



**```powershell**

**docker exec jenkins docker --version**

**```**



**Make sure the Jenkins container was started with:**



**```text**

**-v /var/run/docker.sock:/var/run/docker.sock**

**```**



**### kubectl has no context**



**Check:**



**```powershell**

**docker exec jenkins kubectl config get-contexts**

**```**



**Use the dedicated kubeconfig:**



**```powershell**

**docker exec jenkins kubectl `**

&#x20; **--kubeconfig /var/jenkins\_home/jenkins-kubeconfig.yaml `**

&#x20; **get nodes**

**```**



**### Minikube is stopped**



**```powershell**

**minikube status**

**```**



**Start it:**



**```powershell**

**minikube start --driver=docker**

**```**



**---**



**## 🔒 Security**



**Do \*\*not\*\* commit these to GitHub:**



**- ❌ Docker Hub passwords**

**- ❌ Docker Hub access tokens**

**- ❌ Jenkins credentials**

**- ❌ Kubernetes private keys**

**- ❌ Personal kubeconfig files**

**- ❌ Cloud access keys**

**- ❌ API tokens**



**Keep development credentials outside source control.**



**---**



**## 🎯 DevOps Skills Demonstrated**



**This project demonstrates hands-on experience with:**



**- ✅ Git \& GitHub**

**- ✅ Spring Boot microservices**

**- ✅ Maven**

**- ✅ Docker**

**- ✅ Docker Hub**

**- ✅ Jenkins CI/CD**

**- ✅ Jenkins Declarative Pipeline**

**- ✅ Jenkins Credentials**

**- ✅ Docker-in-Jenkins**

**- ✅ Kubernetes**

**- ✅ Minikube**

**- ✅ kubectl**

**- ✅ Kubernetes Deployments**

**- ✅ Kubernetes Services**

**- ✅ NodePort**

**- ✅ CI/CD automation**

**- ✅ Containerized microservices**

**- ✅ Deployment verification**



**---**



**## 📊 Project Architecture at a Glance**



**```text**

&#x20;                    **GitHub**

&#x20;                       **│**

&#x20;                       **▼**

&#x20;                 **┌───────────┐**

&#x20;                 **│  Jenkins  │**

&#x20;                 **└─────┬─────┘**

&#x20;                       **│**

&#x20;         **┌─────────────┼─────────────┐**

&#x20;         **│             │             │**

&#x20;         **▼             ▼             ▼**

&#x20;      **Maven        Docker Build   Docker Hub**

&#x20;         **│             │             │**

&#x20;         **└─────────────┼─────────────┘**

&#x20;                       **│**

&#x20;                       **▼**

&#x20;                **Kubernetes**

&#x20;                 **/ Minikube**

&#x20;                       **│**

&#x20;      **┌────────────────┼────────────────┐**

&#x20;      **│                │                │**

&#x20;      **▼                ▼                ▼**

&#x20;  **👤 User          📦 Order         🔔 Notification**

&#x20;  **:8081             :8082              :8083**

&#x20;  **:30081            :30082             :30083**

**```**



**---**



**## 📌 Project Highlights**



**> \*\*End-to-end CI/CD:\*\* Source code is checked out from GitHub, built with Maven, containerized with Docker, pushed to Docker Hub, and deployed to Kubernetes through Jenkins.**



**> \*\*Containerized Microservices:\*\* Three independent Spring Boot services are packaged as Docker images.**



**> \*\*Kubernetes Deployment:\*\* Application workloads are deployed using Kubernetes Deployment and Service manifests.**



**> \*\*Local Cloud-Native Environment:\*\* Minikube provides a local Kubernetes environment for development and deployment testing.**



**> \*\*DevOps Automation:\*\* Jenkins coordinates the complete build, containerization, registry, deployment, and verification workflow.**



**---**



**## 👨‍💻 Author**



**\*\*RS-cloud-intellipaat\*\***



**GitHub repository:**



**\*\*cloud-native-devops-platform\*\***



**---**



**## ⭐ Future Enhancements**



**Possible next improvements:**



**- \[ ] Add automated unit/integration tests**

**- \[ ] Add SonarQube code-quality analysis**

**- \[ ] Add Trivy container vulnerability scanning**

**- \[ ] Add Jenkins webhook-based CI**

**- \[ ] Add Kubernetes ConfigMaps and Secrets**

**- \[ ] Add Ingress**

**- \[ ] Add Helm charts**

**- \[ ] Add Prometheus and Grafana monitoring**

**- \[ ] Add centralized logging**

**- \[ ] Deploy to AWS EKS**

**- \[ ] Add separate Dev/Staging/Production environments**



**---**



**<p align="center">**

&#x20; **<b>Built with Java • Spring Boot • Docker • Jenkins • Kubernetes</b>**

**</p>**



