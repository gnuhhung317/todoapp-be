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
                         sh 'git fetch --tags --force'
                         def gitCommit = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
                         def commitTag = sh(script: "git tag --contains ${gitCommit}", returnStdout: true).trim()
                         if (commitTag == '') {
                             error "No tag found for this commit. Pipeline stopped."
                         }
                         env.COMMIT_TAG = commitTag 
                         echo "Tag found: ${commitTag}"
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
                         def tag = env.COMMIT_TAG
                         sh "docker build -t gnuhhung317/spring-boot-app:${tag} ."
                     }
                 }
             }
             stage('Push Docker Image') {
                 steps {
                     script {
                         def tag = env.COMMIT_TAG
                         sh "echo $DOCKER_HUB_CREDENTIALS_PSW | docker login -u $DOCKER_HUB_CREDENTIALS_USR --password-stdin"
                         sh "docker push gnuhhung317/spring-boot-app:${tag}"
                     }
                 }
             }
             stage('Update Config Repo') {
                 steps {
                     script {
                         def tag = env.COMMIT_TAG
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