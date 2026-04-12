#!/usr/bin/env groovy
def gv

pipeline {
    agent any
    
    tools {
        maven "Maven"
    }

    stages {

        stage('Checkout') {
            steps {
                script {
                    echo "Checking out code..."
                    checkout scm
                }
            }
        }

        stage('init') {
            steps {
                script {
                    gv = load 'script.groovy'
                }
            }
        }
        stage('Increment Version') {
            steps {
                script {
                    echo 'Incrementing version...'

                    sh '''
                    #!/bin/bash
                    mvn build-helper:parse-version versions:set \
                    -DnewVersion=${parsedVersion.majorVersion}.${parsedVersion.minorVersion}.${parsedVersion.nextIncrementalVersion} \
                    versions:commit
                    '''
                }
            }
        }

        stage('Build Jar') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(
                        credentialsId: 'nitinkdocker18',
                        passwordVariable: 'PASSWORD',
                        usernameVariable: 'USERNAME'
                    )]) {

                        sh """
                        docker build -t nitinkdocker18/java-maven-app:${IMAGE_NAME} .
                        echo \$PASSWORD | docker login -u \$USERNAME --password-stdin
                        docker push nitinkdocker18/java-maven-app:${IMAGE_NAME}
                        """
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    gv.testApp()
                }
            }
        }

        stage('Deploy') {
            steps {
                echo "Deploying version ${env.IMAGE_NAME}"
            }
        }
    }
}