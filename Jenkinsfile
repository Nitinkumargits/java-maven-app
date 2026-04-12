

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
    stage('Build') {
        steps {
            echo "Building version ${params.VERSION}"
        
        }
    }
    stage('Test') {
        when {
            expression {
                params.executeTests
            }
        }
        steps {
            echo "Running tests for version ${params.VERSION}"
        
        }
    }
    stage('Deploy') {
        steps {
            echo "Deploying the application"
            echo "Deploying version ${params.VERSION}"
        
        }
    }
 }
}