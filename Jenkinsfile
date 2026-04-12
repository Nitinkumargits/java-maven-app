def gv

pipeline {
    agent any
    
    tools {
        maven "maven-3.6"
    }
    parameters {
        choice(name: "VERSION", choices: ["1.0.0", "1.0.1", "1.0.2"], description: "Select version to deploy")
        booleanParam(name: "executeTests", defaultValue: true, description: "Execute tests before deployment")
    }

 stages{

    stage('init') {
        steps {
            script{
                gv = load 'script.groovy'
            }
        
        }
    }
    stage('Build') {
        steps {
            script{
                gv.buildJar()
                gv.buildImage()
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
        input{
            message "Select environment to deploy to :"
            ok "Done"
            parameters {
                choice(name: "ENVIRONMENT", choices: ["dev", "staging", "production"], description: "Select environment to deploy to")
            }
        }

        steps{
            script{
                gv.deployApp()
                echo "Deploying version ${params.VERSION} to ${params.ENVIRONMENT} environment"
            }
        }
    }
 }
}