node('master') {
  env.BUILD_IMAGE = "docker.io/openshift/jenkins-slave-maven-centos7:latest"
}
podTemplate(cloud: 'openshift', label: 'maven-sonar', containers: [
    containerTemplate(name: 'jnlp',
    image: "${env.BUILD_IMAGE}",
    args: '${computer.jnlpmac} ${computer.name}'),
    containerTemplate(name: 'maven',
    image: "${env.BUILD_IMAGE}",
    ttyEnabled: true,
    command: 'cat',
    workingDir: '/home/jenkins'
    )
  ]) {
  node('maven-sonar') {
    stage('SonarQube Scan') {
      checkout scm
      sh """
        mvn test org.sonarsource.scanner.maven:sonar-maven-plugin:3.3.0.603:sonar -f pom.xml \
        -Dsonar.login=${SONAR_TOKEN} -Dsonar.host.url=http://sonarqube:9000 \
        --batch-mode"""
    }
  }
}
