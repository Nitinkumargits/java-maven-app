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
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t nitinkdocker18/demo-app:2.0 .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push nitinkdocker18/demo-app:2.0'
    }
} 

def deployApp() {
    echo 'deploying the application...'
} 

return this