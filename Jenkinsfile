
pipeline {
    agent any
    tools{
        maven 'maven_3_9_1'
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/develop']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Tintin227/medico-auth-service']]])
                sh 'mvn clean install'
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    sh 'docker build -t medico-auth-service/medico-auth-service-0.0.1-SNAPSHOT .'
                }
            }
        }
//         stage('Push image to Hub'){
//             steps{
//                 script{
//                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
//                    sh 'docker login -u javatechie -p ${dockerhubpwd}'
//
// }
//                    sh 'docker push medico-auth-service/medico-auth-service-0.0.1-SNAPSHOT'
//                 }
//             }
//         }
//         stage('Deploy to k8s'){
//             steps{
//                 script{
//                     kubernetesDeploy (configs: 'deploymentservice.yaml',kubeconfigId: 'k8sconfigpwd')
//                 }
//             }
//         }
    }
}
