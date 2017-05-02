"#!groovy"
node {

    def currentPath = '/tmp/'
    //echo "Current Path ${currentPath}"
    def file = currentPath + 'android-sdk-linux/android'

	echo "ANDROID_HOME=${file}"
	
    stage 'Environment'
    // sh 'java -version'

    stage 'Check Android SDK'
    if (fileExists(file)) {
        echo 'Android SDK already exists'
        env.ANDROID_HOME = '/tmp/android-sdk-linux/';
    } else {
            stage 'Setup Android SDK'
            sh 'curl --fail --output android-sdk.tgz http://dl.google.com/android/android-sdk_r24.4.1-linux.tgz'
            sh 'tar -xvf android-sdk.tgz'
            sh 'rm -rf /tmp/android-sdk-linux'
            sh 'mv android-sdk-linux /tmp/'
            env.ANDROID_HOME = '/tmp/android-sdk-linux/';
			sh 'cd /tmp/android-sdk-linux/tools'
			def currentUrl = pwd()
			echo "CurrentPath ${currentUrl}"
//			# install all sdk packages
//			sh './android update sdk --no-ui'

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
  sh "./gradlew clean build"

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