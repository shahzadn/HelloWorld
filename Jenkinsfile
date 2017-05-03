"#!groovy"
node {

    stage('Download code') {
            echo('INFO: checking out SCM')
            milestone()
            checkout scm
            sh 'git submodule update --init'
    }

    stage('Environment'){

        def file = '/home/jenkins/android-sdk-linux/tools/android'
        echo('INFO: Check Android SDK')
        if (fileExists(file)) {
            echo('INFO: Android SDK already exists Updating...')
            env.ANDROID_HOME = '/home/jenkins/android-sdk-linux/';
            sh 'mv licenses.zip /home/jenkins/android-sdk-linux/'
            sh 'unzip /home/jenkins/android-sdk-linux/licenses.zip -d /home/jenkins/android-sdk-linux/'
            sh '(while sleep 1; do echo "y"; done) | /home/jenkins/android-sdk-linux/tools/android update sdk --no-ui --filter build-tools-24.0.2,android-24,extra-android-m2repository'
            sh 'touch /home/jenkins/.android/repositories.cfg'
        } else {
            echo('Setup Android SDK')
            sh 'curl --fail --output android-sdk.tgz http://dl.google.com/android/android-sdk_r24.4.1-linux.tgz'
            sh 'tar -xvf android-sdk.tgz'
            sh 'rm -rf /home/jenkins/android-sdk-linux'
            sh 'mv android-sdk-linux /home/jenkins/'
            env.ANDROID_HOME = '/home/jenkins/android-sdk-linux/'

            sh 'mv licenses.zip /home/jenkins/android-sdk-linux/'
            sh 'unzip /home/jenkins/android-sdk-linux/licenses.zip -d /home/jenkins/android-sdk-linux/'
            sh '(while sleep 1; do echo "y"; done) | /home/jenkins/android-sdk-linux/tools/android update sdk --no-ui --filter build-tools-24.0.2,android-24,extra-android-m2repository'
            sh 'touch /home/jenkins/.android/repositories.cfg'
        }
    }

  stage('Stage Build'){
      echo("INFO: Building ${env.BRANCH_NAME}")
      sh "chmod +x gradlew"
      sh "./gradlew clean build"
  }

  stage('Stage Archive'){

      //tell Jenkins to archive the apks
      archiveArtifacts artifacts: 'app/build/outputs/apk/*.apk', fingerprint: true
  }

}
