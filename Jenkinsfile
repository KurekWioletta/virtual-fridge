pipeline {
    agent {
        node {
            label 'master'
        }
    }
    environment {
        IDENTIFIER = "${env.BRANCH_NAME == "main" ? "main" : "develop"}"
        PORT = "${env.BRANCH_NAME == "main" ? 6010 : 16010}"
    }

    stages {
        stage('Remove old docker image') {
            steps {
                script {
                    try {
                        sh 'docker stop virtualfridgeapi_${IDENTIFIER}'
                        sh 'docker container rm virtualfridgeapi_${IDENTIFIER}'
                    }
                    catch(all) {
                        print 'No docker containers ran previously'
                    }
                }
            }
        }
        stage('Build VirtualFridge container') {
            steps {
                sh 'docker build -f Dockerfile --tag virtualfridgeapi_${IDENTIFIER}:1.0 .'
            }
        }
        stage('Run VirtualFridge container') {
            steps {
                sh 'docker run --publish ${PORT}:8080 --detach --name virtualfridgeapi_${IDENTIFIER} virtualfridgeapi_${IDENTIFIER}:1.0'
            }
        }
    }
}