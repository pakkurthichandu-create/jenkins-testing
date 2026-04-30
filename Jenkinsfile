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
    }

    post {
        always {
            cleanWs()
        }
    }
}
