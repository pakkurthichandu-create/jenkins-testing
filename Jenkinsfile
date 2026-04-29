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

pipeline {
    agent any
    stages{
        stage ("Build") {
            steps {
                sh 'echo "building"'
            }
        }
        stage ("Test") {
            steps {
                sh 'echo "testing"'
            }
        }
        stage ("deploy") {
            steps {
                sh 'echo "deploying"'
            }
        }
    }
}