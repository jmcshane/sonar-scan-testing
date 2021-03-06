node('master') {
  env.BUILD_IMAGE = "openshift/jenkins-slave-maven-centos7:latest"
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
  ],volumes: [
    persistentVolumeClaim(claimName: 'maven-repository-2', mountPath: '/opt/openshift/mvn/repository')
  ]) {
  node('maven-sonar') {
    stage('SonarQube Scan') {
      checkout scm
      sh """
        mvn test org.sonarsource.scanner.maven:sonar-maven-plugin:3.3.0.603:sonar -f pom.xml \
        -Dsonar.login=${SONAR_TOKEN} -Dsonar.host.url=http://sonarqube:9000 \
        --batch-mode --settings=settings.xml"""
    }
  }
  parallel(
    "first_parallel" : {
      node('maven-sonar') {
        sh "echo test > /opt/openshift/mvn/repository/test.txt && sleep 300"
      }
    }, "second_parallel" : {
      node('maven-sonar') {
        sh "sleep 5 && echo test > /opt/openshift/mvn/repository/test-2.txt && sleep 300"
      }
    }, "third_parallel" : {
      node('maven-sonar') {
        sh "sleep 3 && echo test > /opt/openshift/mvn/repository/test-3.txt && sleep 300"
      }
    }
  )
}
