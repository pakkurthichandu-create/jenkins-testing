pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                // 'checkout scm' is required for Multibranch pipelines to pick up the correct branch
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                sh "docker build -t chandu4440/jenkins-practice:v${BUILD_NUMBER} ."
                // Tagging with branch name to avoid overwriting 'latest' from other branches
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
                    withCredentials([usernamePassword(credentialsId: 'github-token', 
                                                passwordVariable: 'GIT_TOKEN', 
                                                usernameVariable: 'GIT_USER')]) {
                        sh """
                            # 1. Clone the Manifest Repository
                            git clone https://${GIT_USER}:${GIT_TOKEN}@github.com/pakkurthichandu-create/manifest-for-kubenetes.git
                            cd manifest-for-kubenetes

                            # 2. Configure Git
                            git config user.email "jenkins@example.com"
                            git config user.name "Jenkins CI"

                            # 3. Update the image version in dev/deployment.yaml
                            # Note: You might want to update a different folder (e.g., qa/) if branch is 'qa'
                            sed -i 's|image: chandu4440/jenkins-practice:.*|image: chandu4440/jenkins-practice:v${BUILD_NUMBER}|' dev/deployment.yaml

                            # 4. Push back to GitHub
                            git add dev/deployment.yaml
                            git commit -m "Update image to v${BUILD_NUMBER} for ${env.BRANCH_NAME} [skip ci]"
                            git push https://${GIT_USER}:${GIT_TOKEN}@github.com/pakkurthichandu-create/manifest-for-kubenetes.git main
                            
                            # 5. Clean up
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
