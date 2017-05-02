"#!groovy"
node {

    def currentPath = pwd()

    env.ANDROID_HOME = currentPath + '/../.android-sdk'

    stage 'Environment'
    sh 'java -version'

    stage 'Check Android SDK'
    if (fileExists(env.ANDROID_HOME)) {
        echo 'Android SDK already exists'
    } else {
            stage 'Setup Android SDK'
            sh 'curl --fail --output android-sdk.tgz http://dl.google.com/android/android-sdk_r24.4.1-linux.tgz'
            sh 'tar -xvf android-sdk.tgz'
            sh 'mv android-sdk-linux "$ANDROID_HOME"'
    }

  // Mark the code checkout 'stage'....
  stage 'Stage Checkout'

  // Checkout code from repository and update any submodules
  checkout scm
  sh 'git submodule update --init'  

  stage 'Stage Build'

  //branch name from Jenkins environment variables
  echo "My branch is: ${env.BRANCH_NAME}"

  //def flavor = flavor(env.BRANCH_NAME)
  //echo "Building flavor ${flavor}"
  echo "Building ${env.BRANCH_NAME}"

  //build your gradle flavor, passes the current build number as a parameter to gradle
  //sh "./gradlew clean assemble${flavor}Debug -PBUILD_NUMBER=${env.BUILD_NUMBER}"

  sh "chmod +x gradlew"
  sh "./gradlew build"

  stage 'Stage Archive'
  //tell Jenkins to archive the apks
  archiveArtifacts artifacts: 'app/build/outputs/apk/*.apk', fingerprint: true

  stage 'Stage Upload To Fabric'
  //sh "./gradlew crashlyticsUploadDistribution${flavor}Debug  -PBUILD_NUMBER=${env.BUILD_NUMBER}"
}

// Pulls the android flavor out of the branch name the branch is prepended with /QA_
@NonCPS
def flavor(branchName) {
  def matcher = (env.BRANCH_NAME =~ /QA_([a-z_]+)/)
  assert matcher.matches()
  matcher[0][1]
}