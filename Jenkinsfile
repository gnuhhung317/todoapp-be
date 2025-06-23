pipeline {
    agent any
    environment {
        DOCKER_HUB_CREDENTIALS = credentials('docker-hub-credentials')
        CONFIG_REPO = 'https://github.com/gnuhhung317/todo-config.git'
        CONFIG_REPO_CREDENTIALS = credentials('git-credentials')
    }
    stages {
        stage('Check Tag') {
            steps {
                script {
                    if (!env.TAG_NAME) {
                        error "No tag found for this commit. Pipeline stopped."
                    }
                    echo "Tag found: ${env.TAG_NAME}"
                }
            }
        }
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    def tag = env.TAG_NAME
                    sh "docker build -t gnuhhung317/spring-boot-app:${tag} ."
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    def tag = env.TAG_NAME
                    sh "echo $DOCKER_HUB_CREDENTIALS_PSW | docker login -u $DOCKER_HUB_CREDENTIALS_USR --password-stdin"
                    sh "docker push gnuhhung317/spring-boot-app:${tag}"
                }
            }
        }
        stage('Update Config Repo') {
            steps {
                script {
                    def tag = env.TAG_NAME
                    dir('config-repo') {
                        git url: "${CONFIG_REPO}", credentialsId: 'git-credentials', branch: 'main'
                        sh 'git config user.email "jenkins@example.com"'
                        sh 'git config user.name "Jenkins"'
                        sh "sed -i 's|tag: \".*\"|tag: \"${tag}\"|g' spring-boot-values.yaml"
                        sh "git add spring-boot-values.yaml"
                        sh "git diff --staged --quiet || git commit -m 'Update spring-boot-app image version to ${tag}'"
                        withCredentials([usernamePassword(credentialsId: 'git-credentials', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD')]) {
                            sh "git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/gnuhhung317/todo-config.git main"
                        }
                    }
                }
            }
        }
    }
}