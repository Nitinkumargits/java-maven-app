def buildJar() {
    echo "building the application..."
    sh 'mvn package'
} 

def testApp() {
    echo "testing the application..."
    sh 'mvn test'
}


def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'nitinkdocker18', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
        
        sh """
        docker build -t nitinkdocker18/demo-app:${params.VERSION} .
        echo \$PASSWORD | docker login -u \$USERNAME --password-stdin
        docker push nitinkdocker18/demo-app:${params.VERSION}
        """
    }
} 

def deployApp() {
    echo 'deploying the application...'
} 

return this