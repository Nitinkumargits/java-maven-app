#!/usr/bin/env groovy
def gv

pipeline {
    agent any
    
    tools {
        maven "Maven"
    }

    stages {


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

                    def version = sh(
                        script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout",
                        returnStdout: true
                    ).trim()

                    echo "Current version: ${version}"

                    def newVersion = "${version.split('-')[0]}-${BUILD_NUMBER}"
                    env.IMAGE_NAME = newVersion

                    echo "New version: ${env.IMAGE_NAME}"
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
        stage('commit version update') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'Nitinkumargits', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
                        // git config here for the first time run
                        sh 'git config --global user.email "jenkins@example.com"'
                        sh 'git config --global user.name "jenkins"'

                        sh "git remote set-url origin https://${USERNAME}:${PASSWORD}@gitlab.com/${USERNAME}/java-maven-app.git"
                        sh 'git add .'
                        sh 'git commit -m "ci: version bump"'
                        sh 'git push origin HEAD:jenkins-jobs'
                    }
                }
            }
        }

    }
}


