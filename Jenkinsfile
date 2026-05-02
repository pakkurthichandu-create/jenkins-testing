pipeline {
    agent any
    
    stages {
        stage('Build & Test') {
            steps {
                sh "docker build -t chandu4440/jenkins-practice:v${BUILD_NUMBER} ."
                sh "docker tag chandu4440/jenkins-practice:v${BUILD_NUMBER} chandu4440/jenkins-practice:${env.BRANCH_NAME}-latest"
            }
        }

        stage('Deploy') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-hub-creds', 
                    passwordVariable: 'PASS', 
                    usernameVariable: 'USER'
                )]) {

                    sh "echo \$PASS | docker login -u \$USER --password-stdin"

                    sh "docker push chandu4440/jenkins-practice:v${BUILD_NUMBER}"
                    sh "docker push chandu4440/jenkins-practice:${env.BRANCH_NAME}-latest"

                    sh "docker rmi chandu4440/jenkins-practice:v${BUILD_NUMBER}"
                    sh "docker rmi chandu4440/jenkins-practice:${env.BRANCH_NAME}-latest"
                }
            }
        }

        stage('Update Manifest Repo') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'github-token', 
                    passwordVariable: 'GIT_TOKEN', 
                    usernameVariable: 'GIT_USER'
                )]) {
                    sh """
                        git clone https://${GIT_USER}:${GIT_TOKEN}@github.com/pakkurthichandu-create/manifest-for-kubenetes.git
                        cd manifest-for-kubenetes

                        git config user.email "jenkins@example.com"
                        git config user.name "Jenkins CI"

                        sed -i 's|image: chandu4440/jenkins-practice:.*|image: chandu4440/jenkins-practice:v${BUILD_NUMBER}|' dev/deployment.yaml

                        git add dev/deployment.yaml
                        git commit -m "Update image to v${BUILD_NUMBER} for ${env.BRANCH_NAME} [skip ci]"
                        git push https://${GIT_USER}:${GIT_TOKEN}@github.com/pakkurthichandu-create/manifest-for-kubenetes.git main

                        cd ..
                        rm -rf manifest-for-kubenetes
                    """
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}