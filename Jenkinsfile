// node {
//     stage ("Build") {
//         sh 'echo "building"'
//     }
//     stage ("Test") {
//         sh 'echo "testing"'
//     }
//     stage ("deploy") {
//         sh 'echo "deploying"'
//     }
// }

// pipeline {
//     agent any
//     stages{
//         stage ("Build") {
//             steps {
//                 sh 'echo "building"'
//             }
//         }
//         stage ("Test") {
//             steps {
//                 sh 'echo "testing"'
//             }
//         }
//         stage ("deploy") {
//             steps {
//                 sh 'echo "deploying"'
//             }
//         }
//     }
// }


pipeline {
    agent any
    
    stages {
        stage('Build') {
            steps {
                git branch: 'main', changelog: false, poll: false, url: 'https://github.com/pakkurthichandu-create/jenkins-testing.git'
                sh './mvnw clean package'
            }
        }
        
        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }
        
        stage('Deploy') {
            steps {
                sh "docker build -t chandu4440/jenkins-practice:v${BUILD_NUMBER} ."
                sh "docker tag chandu4440/jenkins-practice:v${BUILD_NUMBER} chandu4440/jenkins-practice:latest"
                
                withCredentials([usernamePassword(
                    credentialsId: 'docker-hub-creds', 
                    passwordVariable: 'PASS', 
                    usernameVariable: 'USER'
                )]) {

                    sh "echo \$PASS | docker login -u \$USER --password-stdin"

                    sh "docker push chandu4440/jenkins-practice:v${BUILD_NUMBER}"
                    sh "docker push chandu4440/jenkins-practice:latest"
                }
            }
        }

        stage('Update Manifest Repo') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'github-token', 
                                               passwordVariable: 'GIT_TOKEN', 
                                               usernameVariable: 'GIT_USER')]) {
                    sh """
                        # 1. Clone the Manifest Repository (Using your correct repo name)
                        git clone https://${GIT_USER}:${GIT_TOKEN}@github.com/pakkurthichandu-create/manifest-for-kubenetes.git
                        cd manifest-for-kubenetes

                        # 2. Configure Git
                        git config user.email "jenkins@example.com"
                        git config user.name "Jenkins CI"

                        # 3. Update the image version in deployment.yaml
                        sed -i 's|chandu4440/jenkins-practice:v.*|chandu4440/jenkins-practice:v${BUILD_NUMBER}|' deployment.yaml

                        # 4. Push back to GitHub
                        git add deployment.yaml
                        git commit -m "Update image to v${BUILD_NUMBER} [skip ci]"
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
