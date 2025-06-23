pipeline {
    agent any
    environment {
        DOCKER_HUB_CREDENTIALS = credentials('docker-hub-credentials')
        CONFIG_REPO = 'https://github.com/gnuhhung317/todo-config.git'
        CONFIG_REPO_CREDENTIALS = credentials('git-credentials')
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    def tag = env.GIT_COMMIT.take(7) 
                    sh "docker build -t gnuhhung317/spring-boot-app:${tag} ."
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    def tag = env.GIT_COMMIT.take(7)
                    sh "echo $DOCKER_HUB_CREDENTIALS_PSW | docker login -u $DOCKER_HUB_CREDENTIALS_USR --password-stdin"
                    sh "docker push gnuhhung317/spring-boot-app:${tag}"
                }
            }
        }
        stage('Update Config Repo') {
            steps {
                script {
                    def tag = env.GIT_COMMIT.take(7)
                    dir('config-repo') {
                        git url: "${CONFIG_REPO}", credentialsId: 'git-credentials'
                        sh "sed -i 's|image: gnuhhung317/spring-boot-app:.*|image: gnuhhung317/spring-boot-app:${tag}|g' be-values.yaml"
                        sh "git add be-values.yaml"
                        sh "git commit -m 'Update spring-boot-app image version to ${tag}'"
                        sh "git push origin main"
                    }
                }
            }
        }
    }
}