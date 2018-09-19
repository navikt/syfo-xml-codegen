#!/usr/bin/env groovy

pipeline {
    agent any

    environment {
        APPLICATION_NAME = 'syfo-xml-codegen'
        ZONE = 'fss'
        DOCKER_SLUG = 'integrasjon'
    }

    stages {
        stage('initialize') {
            steps {
                init action: 'default'
                sh './gradlew clean'
                script {
                    env.APPLICATION_VERSION = sh(script: './gradlew -q printVersion', returnStdout: true).trim()
                }
                init action: 'updateStatus'
            }
        }
        stage('build') {
            steps {
                sh './gradlew build -x test'
            }
        }
        stage('run tests (unit & intergration)') {
            steps {
                sh './gradlew test'
                slackStatus status: 'passed'
            }
        }
        stage('deploy to preprod') {
            steps {
                sh './gradlew publish'
            }
        }
    }
    post {
        always {
            postProcess action: 'always'
            //junit '**/build/test-results/test/*.xml'
            archiveArtifacts artifacts: '**/build/libs/*', allowEmptyArchive: true
        }
        success {
            postProcess action: 'success'
        }
        failure {
            postProcess action: 'failure'
        }
    }
}
