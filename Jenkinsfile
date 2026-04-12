def gv

pipeline {
    agent any
    
    tools {
        maven "Maven"
    }

 stages{

    stage('Build jar') {
        steps {
            script{
            echo "Building the application..."
            sh 'mvn package'
            }
                
        
        }
    }
    stage('Build Image') {
        steps {
            script{
                echo "building the docker image..."
                withCredentials([usernamePassword(credentialsId: 'nitinkdocker18', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
        
                sh """
                docker build -t nitinkdocker18/demo-app:3.0 .
                echo \$PASSWORD | docker login -u \$USERNAME --password-stdin
                docker push nitinkdocker18/demo-app:3.0
                """
         
                }
            }
        }
    }
    
    stage('Test') {
        steps {
            script{
                gv.testApp()
            }
        
        }
    }
    stage('Deploy') {
        
        steps{
            script{
            
            echo 'deploying the application...'
            
            }
        }
    }
 }
}