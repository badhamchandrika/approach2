@Library('jenkins-shared-lib-v2') _
import io.jenkins.plugins.adobe.cloudmanager.action.CloudManagerBuildAction;

//small jenkins parameter validation check
<<<<<<<< HEAD:jenkins/Jenkinsfile-cicd-nonprod.groovy
if (JENKINS_PIPELINE_ID != 'ci' && JENKINS_PIPELINE_ID != 'dev' && JENKINS_PIPELINE_ID != 'qa' ) error("JENKINS_PIPELINE_ID string parameter in jenkins needs to be either 'code_quality', 'hotfix_code_quality', 'hotfix_staging', 'dev', 'qa' or 'staging'")

========
if (JENKINS_PIPELINE_ID != 'staging') error("JENKINS_PIPELINE_ID string parameter in jenkins needs to be either 'code_quality', 'hotfix_code_quality', 'hotfix_staging', 'dev', 'qa' or 'staging'")
>>>>>>>> main:jenkins/Jenkinsfile-cicd-prod.groovy

// GLOBAL STATIC Variables
EMAIL_RECIPIENTS = 'dlakshmipathy@loyalty.com,snarala@loyalty.com'
L1_REPO_NAME = 'aem-airmiles-web'
MICROSOFT_TEAMS_PROJECT_WEBHOOK = 'https://loyaltyone.webhook.office.com/webhookb2/49f5de54-58a8-4b58-b0cd-a8c8dc3f18ab@a9f9458b-a469-48c4-9a71-806e262869bf/JenkinsCI/c72c6228a8df485dae95fb74b1ee718b/0472fcc8-6fdf-4e27-b43c-ff67e3529ebc'
<<<<<<<< HEAD:jenkins/Jenkinsfile-cicd-nonprod.groovy
L1_REPO_TRUNK='develop'
ADOBE_REPO_TRUNK='develop'
========
L1_REPO_TRUNK='main'
ADOBE_REPO_TRUNK='main'
>>>>>>>> main:jenkins/Jenkinsfile-cicd-prod.groovy
ADOBE_REPO_ID=58628

// GLOBAL Variables Scoped To The File
isDeployable = false
isReleasable = false
releaseVersion = null;
isNewRelease = false;
notificationStrPattern = "Jenkins Pipeline:\t${env.JOB_NAME}\nWorkflow:\t\t%s\nStage:\t\t\t%s\nStage Status:\t\t%s\nVersion:\t\t%s\nLast Commit Author:\t%s\nBuild Url:\t(${env.BUILD_URL})"


// Local Variables Scoped To The Pipeline
def JENKINS_PIPELINES = [
<<<<<<<< HEAD:jenkins/Jenkinsfile-cicd-nonprod.groovy
        ci:        [adobePipelineId: '9236774',  mutex: "${L1_REPO_NAME}-ci",          deployStageName: 'Adobe Code Quality Scan'],
        dev:                 [adobePipelineId: '7479542',  mutex: "ams-dev",         deployStageName: 'Adobe Dev Deployment'],
        qa:                  [adobePipelineId: '9454786',  mutex: "ams-qa",          deployStageName: 'Adobe QA Deployment'],
========
        staging:             [adobePipelineId: '58806',    mutex: "ams-prod",        deployStageName: 'Adobe Staging Deployment'],
>>>>>>>> main:jenkins/Jenkinsfile-cicd-prod.groovy
]
def adobePipelineId = JENKINS_PIPELINES[JENKINS_PIPELINE_ID]["adobePipelineId"]
def deploymentStageName = JENKINS_PIPELINES[JENKINS_PIPELINE_ID]["deployStageName"]
def mutex = JENKINS_PIPELINES[JENKINS_PIPELINE_ID]["mutex"]
def isWebhookTrigger = params.RELEASE_VERSION == '' || params.RELEASE_VERSION == null
def hotfixBranchPattern = /^hotfix_.+$/
def isHotfixBranch = env.GITHUB_BRANCH_NAME ==~ hotfixBranchPattern


pipeline {
    agent { label 'aws-ec2' }

    stages {
        // Gracefully Skip Automated Tagged Commits By Jenkins That Trigger This Pipeline
        stage('Skip Automated Jenkins User Webhook Triggers') {
            when { expression { isJenkinsCommit() && isWebhookTrigger  } }
            steps {
                script {
                    currentBuild.getRawBuild().getExecutor().interrupt(Result.SUCCESS)
                    sleep(1)   // Interrupt is not blocking and does not take effect immediately.
                }
            }
        }

        stage ("Identifying Workflow"){

            steps   {

                script {
                    println "DEBUG: \n\tisWebhookTrigger: ${isWebhookTrigger}"
                    println "DEBUG: \n\tisHotfixBranch: ${isHotfixBranch}"

                    // pr, main, tagged, hotfix
                    entrypoint = getPipelineTriggeredBy(isWebhookTrigger, isHotfixBranch)

                    stage ("${entrypointStageName}") {
                        // determines the next stages in the workflow to execute based on the tag, environment and entrypoint
                        // The environment and tag are global variables
                        println "DEBUG: \n\tpipelineTriggeredBy:${entrypoint}"
                        //pr, ci, dev, qa, staging/prod
                        runWorkflow(entrypoint)
                    }
                }
            }

            post {

                success {

                    script {
                        notifyParties(String.format(
                                notificationStrPattern,
                                automationStatus,
                                env.STAGE_NAME,
                                "Done",
                                releaseVersion,
                                getLastCommitAuthor()
                        ), '#330000')
                    }
                }

                failure {

                    script {

                        notifyParties(String.format(
<<<<<<<< HEAD:jenkins/Jenkinsfile-cicd-nonprod.groovy
                                notificationStrPattern,
                                automationStatus,
                                env.STAGE_NAME,
                                "Error: Could not properly identify the workflow for this pipeline. i.e. automated/manual flow for develop, hotfix, tagged",
                                releaseVersion,
                                getLastCommitAuthor()
                        ), '#FF0000')
                    }
                }
            }
        }

        //***** NOTE: Team Dynamite hasn't implemented the L1 Sonarqube server yet.
        stage('L1 SonarQube Build') {
            agent {
                dockerfile {
                    //check whether maven is part of this docker image or not?
                    filename 'Maven_Dockerfile'
                    dir 'jenkins'
                    label 'aws-ec2'
                    args '-u root:root'
                }
            }
            when {
                expression { JENKINS_PIPELINE_ID == 'ci' || JENKINS_PIPELINE_ID == 'hotfix_ci'}
            }
            steps {
                notifyParties(String.format(
                        notificationStrPattern,
                        automationStatus,
                        env.STAGE_NAME,
                        "Started...",
                        releaseVersion,
                        getLastCommitAuthor()
                ),'#00FF00')
                //print "Pending Approval..."
                script{
                    sh """
                     #add maven related commands
                      cd core
                      mvn clean install
                     """
                    // Here we stash the important files which will be used in the scan stage.
                    // We do this so that build and scan steps can work independent of each other
                    // in different agents.
                    stash includes : 'core/target/**', name: 'build'
                    stash includes : 'core/src/**', name: 'src'
                }
            }
            post {
                success {
                    script {
                        notifyParties(String.format(
========
>>>>>>>> main:jenkins/Jenkinsfile-cicd-prod.groovy
                                notificationStrPattern,
                                automationStatus,
                                env.STAGE_NAME,
                                "Error: Could not properly identify the workflow for this pipeline. i.e. automated/manual flow for main, hotfix, tagged",
                                releaseVersion,
                                getLastCommitAuthor()
                        ), '#FF0000')
                    }
                }
            }
        }
<<<<<<<< HEAD:jenkins/Jenkinsfile-cicd-nonprod.groovy
        stage('L1 SonarQube Scan') {
            agent {
                docker {
                    //check whether maven is part of this docker image or not?
                    image '277983268692.dkr.ecr.us-east-1.amazonaws.com/ubuntu-22-slim'
                    args '-u root:root'
                }
            }
            when {
                expression { JENKINS_PIPELINE_ID == 'ci' || JENKINS_PIPELINE_ID == 'hotfix_ci'}
            }
            steps {
                notifyParties(String.format(
                        notificationStrPattern,
                        automationStatus,
                        env.STAGE_NAME,
                        "Scan Started...",
                        releaseVersion,
                        getLastCommitAuthor()
                ), '#00FF00')

                //print "Pending Approval..."
                script {
                    script {
                        unstash 'build'
                        unstash 'src'
                        sonarqube.checkQualityGate()
                    }
                }
            }
            post {
                success {
                    script {
                        notifyParties(String.format(
                                notificationStrPattern,
                                automationStatus,
                                env.STAGE_NAME,
                                "L1 SonarQube Scan Completed!",
                                releaseVersion,
                                getLastCommitAuthor()
                        ), '#00994c')
                    }
                }
                failure {
                    script {
                        notifyParties(String.format(
                                notificationStrPattern,
                                automationStatus,
                                env.STAGE_NAME,
                                "Error: L1 SonarQube Scan Failed",
                                releaseVersion,
                                getLastCommitAuthor()
                        ), '#FF0000')
                    }
                }
                cleanup {
                    script {
                        sh """find . -user root -name \'*\' | xargs chmod ugo+rw"""
                    }
                    deleteDir()
                }
            }
        }

        //***** NOTE: ATS Team needs to provide their checkmarx team name before this can be implemented.
        stage('L1 Checkmarx Scan') {
            when {
                expression { JENKINS_PIPELINE_ID == 'ci' || JENKINS_PIPELINE_ID == 'hotfix_ci'}
            }
            steps {

                notifyParties(String.format(
                        notificationStrPattern,
                        automationStatus,
                        env.STAGE_NAME,
                        "Started...",
                        releaseVersion,
                        getLastCommitAuthor()
                ), '#00FF00')

                script {

                    checkmarx.scan(excludeFolders: 'jenkins')
                }
            }
            post {
                success {
                    script {

                        notifyParties(String.format(
                                notificationStrPattern,
                                automationStatus,
                                env.STAGE_NAME,
                                "L1 Checkmarx Scan Completed!",
                                releaseVersion,
                                getLastCommitAuthor()
                        ), '#00994c')
                    }

                }

                failure {
                    script {

                        notifyParties(String.format(
                                notificationStrPattern,
                                automationStatus,
                                env.STAGE_NAME,
                                "Error: L1 Checkmarx Scan Failed",
                                releaseVersion,
                                getLastCommitAuthor()
                        ), '#FF0000')
                    }

                }
            }
        }

========
>>>>>>>> main:jenkins/Jenkinsfile-cicd-prod.groovy
        stage("Increment Release") {
            when { expression { isReleasable } }
            agent {
                docker {
                    image '277983268692.dkr.ecr.us-east-1.amazonaws.com/ubuntu-22-slim'
                    args '-u root:root'
                }
            }
            steps {

                script {

                    notifyParties(String.format(
                            notificationStrPattern,
                            automationStatus,
                            env.STAGE_NAME,
                            "Started...",
                            releaseVersion,
                            getLastCommitAuthor()
                    ), '#00FF00')

                    semanticRelease()
                    sh "chown -R 1000:1000 ${workspace} && ls -l ${workspace}"
                }
            }
            post {

                success {

                    script {

                        notifyParties(String.format(
                                notificationStrPattern,
                                automationStatus,
                                env.STAGE_NAME,
                                "Release Incremented To ${releaseVersion}",
                                releaseVersion,
                                getLastCommitAuthor()
                        ), '#00994c')
                    }
                }

                failure {

                    script {

                        notifyParties(String.format(
                                notificationStrPattern,
                                automationStatus,
                                env.STAGE_NAME,
                                "Error: Release Failed To Increment ${releaseVersion} To The Next Release Version.",
                                releaseVersion,
                                getLastCommitAuthor()
                        ), '#FF0000')
                    }
                }
                cleanup {

                    script {

                        sh """
                            chown -R 1000:1000 ${workspace} && ls -l ${workspace}
                        """
                    }
                }
                always {

                    println "debug releaseVersion: " + releaseVersion
                    println "debug JENKINS_PIPELINE_ID: " + JENKINS_PIPELINE_ID
                    println "debug isNewRelease: " + isNewRelease
                    println "debug isDeployable: " + isDeployable
                }
            }
        }

        stage('Waiting In Global Pipeline Queue') {
            agent {
                dockerfile {
                    label 'aws-ec2'
                    dir 'jenkins'
                    filename 'Jenkins_To_Adobe_Dockerfile'
                    args '-u root:root'
                }
            }
            when {
                expression { isDeployable }
            }
            steps {

                script {

                    notifyParties(String.format(
                            notificationStrPattern,
                            automationStatus,
                            env.STAGE_NAME,
                            "Waiting...",
                            releaseVersion,
                            getLastCommitAuthor()
                    ), '#C0C0C0')

                    // Setup Adobe AIO CLI Credentials
                    installAioCli()

                    // Wait in a global Jenkins build queue for your intended deployment. Only one Jenkins pipeline execution build, regardless of the number of pipelines, can sync to Adobe Github & execute the CQ, Dev, QA, Stage, Prod Environments
                    //***** NOTE: Add milestone to lock if necessary
                    lock(resource: mutex , variable: 'mutex') {

                        waiting = true
                        while(waiting){
                            stage('Wait For Impropper/Manual Deployments To Complete'){

                                //Not as safe as Jenkins Lock. This is used to attempt to wait for a pipeline that was executed without implementing the Jenkins lock functionality
                                // If the intended adobe pipeline is already in use by another person that did not wait in the pipeline queue, try to wait for the adobe pipeline to finish instead of crashing the Jenkins build
                                pollBeforeAdobePipelineExec(adobePipelineId)
                            }

                            stage('Push L1 Github Tag to Adobe Github Branch'){

                                notifyParties(String.format(
                                        notificationStrPattern,
                                        automationStatus,
                                        env.STAGE_NAME,
                                        "Starting...",
                                        releaseVersion,
                                        getLastCommitAuthor()
                                ), '#00FF00')

                                withCredentials([usernamePassword(credentialsId: 'adobe-github-credentials', usernameVariable: 'adobe_git_user', passwordVariable: 'adobe_git_password')]) {
                                    jenkinsUtils.maskExecute([adobe_git_password]) {
                                        // Push L1 Github Tag To Be Deployed Into An Adobe Branch With The Same Name As The Tag
                                        sh """
                                            git config --global --add safe.directory ${WORKSPACE}

                                            # create remote connection to adobe github repo.
                                            # ***** NOTE: This uses my personal gsmith credentials. When provided with service account credentials, I'll update the pipeline.
                                            if [ "\$( git remote | grep 'adobeSync' )" != "adobeSync" ]; then
                                                git remote add adobeSync https://${adobe_git_user}:${adobe_git_password}@git.cloudmanager.adobe.com/loyaltyone/${L1_REPO_NAME}/
                                            fi

                                            # push L1 tag to Adobe Github Branch
                                            git checkout ${L1_REPO_TRUNK} 
                                            git checkout -b ${releaseVersion} || (git checkout ${L1_REPO_TRUNK} && git branch -D ${releaseVersion} && git checkout -b ${releaseVersion})
                                            git push adobeSync refs/heads/${releaseVersion}

                                        """

                                        // Sync Latest Tags To Adobe Environment
                                        sh """
                                            git config --global --add safe.directory ${WORKSPACE}
                                            git checkout ${L1_REPO_TRUNK}
                                            git pull adobeSync ${ADOBE_REPO_TRUNK} || git push adobeSync ${ADOBE_REPO_TRUNK} #pull 1st if branch exists, otherwise push 1st
                                            git push adobeSync ${ADOBE_REPO_TRUNK}
                                            git push adobeSync ${ADOBE_REPO_TRUNK} --tags || echo "Already Exists"
                                        """
                                    }

                                }

                                notifyParties(String.format(
                                        notificationStrPattern,
                                        automationStatus,
                                        env.STAGE_NAME,
                                        "L1 to Adobe Github Repo Sync Complete!",
                                        releaseVersion,
                                        getLastCommitAuthor()
                                ), '#00994c')
                            }

                            stage("${deploymentStageName}") {

                                notifyParties(String.format(
                                        notificationStrPattern,
                                        automationStatus,
                                        env.STAGE_NAME,
                                        "Starting...",
                                        releaseVersion,
                                        getLastCommitAuthor()
                                ), '#00FF00')

                                // Don't fail if a user improperly (manually) triggered an AMS pipeline, which would bypass the jenkins mutex, wait for the AMS pipeline to be free to use instead
                                try {

                                    sh """
                                       # configure AMS pipeline to use project repo name
                                       aio cloudmanager:pipeline:update ${adobePipelineId} -p 2007 --branch=${releaseVersion} --repositoryId=${ADOBE_REPO_ID}
                                       printenv
                                    """
                                    acmStartPipeline(
                                            aioProject: 'L1 Jenkins and AMS Cloud Manager Integration',
                                            program: '2007',
                                            pipeline:"${adobePipelineId}"
                                    )
                                    acmPollPipeline(
                                            recurrencePeriod: 30000,
                                            quiet: true
                                    )

                                    /*
                                        1. Sometimes acmStartPipeline or acmPollPipeline from the Jenkins Adobe Cloud Manager plugin doesn't work properly. This is an issue on the Adobe side. When it fails, it doesn't throw an Exception.Hense the need to have checkAdobePipelineStatus inside the

                                        2. When the Jenkins plugin is working properly, there is a chance acmPollPipeline will throw an Exception if an AMS pipeline has a failed or cancelled build.

                                        To accomodate for the 2 above usecases, checkAdobePipelineStatus() is invoked in both the try and catch blocks. Otherwise there will be an endless loop to start a pipeline.

                                        checkAdoptionPipelineStatus() is meant as a backup in-case acmPollPipeline fails in usecase #1.
                                    */
                                    checkAdobePipelineStatus(adobePipelineId)
                                } catch (Exception e) {

                                    checkAdobePipelineStatus(adobePipelineId)
                                }
                            }
                        }

                        /*if (JENKINS_PIPELINE_ID != 'ci' && JENKINS_PIPELINE_ID != 'staging' && JENKINS_PIPELINE_ID != 'hotfix_ci' && JENKINS_PIPELINE_ID != 'qa') {
                            stage('Allow Others To Use Adobe Pipeline') {
                                notifyParties(String.format(
                                        notificationStrPattern,
                                        automationStatus,
                                        env.STAGE_NAME,
                                        "Waiting On Manual Approval...",
                                        releaseVersion,
                                        getLastCommitAuthor()
                                ), '#FFCCCC')

                                timeout(time: 1, unit: 'DAYS') {
                                    input(
                                            message: 'Are you finished with the environment?',
                                            submitter: "L1-AEM-Jenkins-DM",
                                            ok: 'Approved!'
                                    )
                                }

                                notifyParties(String.format(
                                        notificationStrPattern,
                                        automationStatus,
                                        env.STAGE_NAME,
                                        "Approval Accepted...",
                                        releaseVersion,
                                        getLastCommitAuthor()
                                ), '#00994c')
                            }
                        }*/
                    }
                }
            }
            post {
                success {

                    script {
                        def msg = "${JENKINS_PIPELINE_ID} Deployment Using Version '${releaseVersion}' Went Successfully"
                        echo "$msg"
                    }
                }
                failure {

                    script {
                        def msg = "${JENKINS_PIPELINE_ID} Deployment Using Version '${releaseVersion}' Failed"
                        echo "$msg"
                    }
                }
                cleanup {
                    // Hotfix Deployments require the adobe branch to live so that the custom AMS tags can be pulled back into the L1 hotfix branch
                    script {

                        if (JENKINS_PIPELINE_ID != 'hotfix_staging'){
                            sh """
                                git config --global --add safe.directory ${WORKSPACE}
                                set +x
                                git push adobeSync --delete refs/heads/${releaseVersion} || echo ""
                                set -x
                            """
                        }
                        sh """
                                git config --global --add safe.directory ${WORKSPACE}
                                chown -R 1000:1000 ${workspace} && ls -l ${workspace}
                                set +x
                                git remote remove adobeSync || echo ""
                                set -x
                        """
                    }
                }
            }
        }

        stage('Backup Custom Tags From AMS Repo To L1 Repo'){
            when {

                expression { JENKINS_PIPELINE_ID == 'staging' || JENKINS_PIPELINE_ID == 'hotfix_staging'}
            }

            steps {
                script {

                    lock(resource: mutex , variable: 'mutex') {

                        withCredentials([usernamePassword(credentialsId: 'adobe-github-credentials', usernameVariable: 'adobe_git_user', passwordVariable: 'adobe_git_password')]) {
                            jenkinsUtils.maskExecute([adobe_git_password]) {
                                sshagent(credentials: ['jenkins-ssh-key'], ignoreMissing: false) {
                                    def gitL1HotfixBranchName = env.GIT_BRANCH.split('/')[1]
                                    sh """
                                        git config --global --add safe.directory ${WORKSPACE}
                                        echo "Syncing Adobe Tags to develop"

                                        # create remote connection to adobe github repo.
                                        # ***** NOTE: This uses my personal gsmith credentials. When provided with service account credentials, I'll update the pipeline.

                                        if [ "\$( git remote | grep 'adobeSync' )" != "adobeSync" ]; then
                                            git remote add adobeSync https://${adobe_git_user}:${adobe_git_password}@git.cloudmanager.adobe.com/loyaltyone/${L1_REPO_NAME}/
                                        fi
                                        
                                        # Sync custom tags from hotfix branch instead of main if the deployment is a hotfix deployment
                                        if [ "${JENKINS_PIPELINE_ID}" == "hotfix_staging" ]; then
                                            git pull adobeSync refs/heads/${releaseVersion} --tags
                                            git branch | cat
                                            git push origin HEAD:${gitL1HotfixBranchName} --tags 
                                        else
                                            git pull adobeSync ${ADOBE_REPO_TRUNK} --tags
                                            git push origin ${L1_REPO_TRUNK} --tags 
                                        fi
                                    """
                                }
                            }
                        }
                    }
                }
            }

            post {
                success {
                    script {

                        notifyParties(String.format(
                                notificationStrPattern,
                                automationStatus,
                                env.STAGE_NAME,
                                "Successfully Backed Adobe Custom Tags To L1 Github With ${releaseVersion} Changes.",
                                releaseVersion,
                                getLastCommitAuthor()
                        ), '#00994c')
                    }
                }

                failure {
                    script {

                        notifyParties(String.format(
                                notificationStrPattern,
                                automationStatus,
                                env.STAGE_NAME,
                                "Error: Adobe Custom Tag Backup To L1 Github Failed.",
                                releaseVersion,
                                getLastCommitAuthor()
                        ), '#FF0000')
                    }
                }

                cleanup {
                    script {
                        if (JENKINS_PIPELINE_ID == 'hotfix_staging'){
                            sh """git push adobeSync --delete refs/heads/${releaseVersion} || echo "" """
                        }
                        sh """
                            set +x
                            git remote | cat
                            git remote remove adobeSync || echo ""
                            set -x
                        """
                    }
                }
            }
        }

    }
    post {

        success {
            script {

                //ci jenkins pipeline should trigger dev deployment after completion
<<<<<<<< HEAD:jenkins/Jenkinsfile-cicd-nonprod.groovy
                if (JENKINS_PIPELINE_ID == 'ci' && !isJenkinsCommit()) {
========
                if (JENKINS_PIPELINE_ID == 'staging' && !isJenkinsCommit()) {

>>>>>>>> main:jenkins/Jenkinsfile-cicd-prod.groovy

                    notifyParties(String.format(
                            notificationStrPattern,
                            automationStatus,
                            env.STAGE_NAME,
<<<<<<<< HEAD:jenkins/Jenkinsfile-cicd-nonprod.groovy
                            "Triggering Jenkins Dev Deployment Pipeline(cd-dev)",
========
                            "Triggering Jenkins Staging Deployment Pipeline(cd-stage-prod)",
>>>>>>>> main:jenkins/Jenkinsfile-cicd-prod.groovy
                            releaseVersion,
                            getLastCommitAuthor()
                    ), '#00994c')

                    //trigger cd-dev
<<<<<<<< HEAD:jenkins/Jenkinsfile-cicd-nonprod.groovy
                    build job:"aem-projects/${L1_REPO_NAME}/cd-dev", parameters: [
                            string(name: 'JENKINS_PIPELINE_ID', value: 'dev'),
                            string(name: 'RELEASE_VERSION', value: releaseVersion),
                    ],
                            wait: false
                } else if (JENKINS_PIPELINE_ID == 'dev') {
========
                    build job:"${L1_REPO_NAME}/cd-stage-prod", parameters: [
                            string(name: 'JENKINS_PIPELINE_ID', value: 'staging'),
                            string(name: 'RELEASE_VERSION', value: releaseVersion),
                    ],
                            wait: false
                } else if (JENKINS_PIPELINE_ID == 'hotfix_ci') {
>>>>>>>> main:jenkins/Jenkinsfile-cicd-prod.groovy

                    notifyParties(String.format(
                            notificationStrPattern,
                            automationStatus,
                            env.STAGE_NAME,
<<<<<<<< HEAD:jenkins/Jenkinsfile-cicd-nonprod.groovy
                            "Triggering Jenkins QA Deployment Pipeline(cd-qa)",
========
                            "Triggering Jenkins Hotfix Staging Deployment Pipeline(hotfix-cd)",
>>>>>>>> main:jenkins/Jenkinsfile-cicd-prod.groovy
                            releaseVersion,
                            getLastCommitAuthor()
                    ), '#00994c')

<<<<<<<< HEAD:jenkins/Jenkinsfile-cicd-nonprod.groovy
                    //trigger cd-qa
                    build job:"aem-projects/${L1_REPO_NAME}/cd-qa", parameters: [
                            string(name: 'JENKINS_PIPELINE_ID', value: 'qa'),
========
                    //trigger hotfix deployment
                    build job:"${L1_REPO_NAME}/hotfix/hotfix-staging", parameters: [
                            string(name: 'JENKINS_PIPELINE_ID', value: 'hotfix_staging'),
>>>>>>>> main:jenkins/Jenkinsfile-cicd-prod.groovy
                            string(name: 'RELEASE_VERSION', value: releaseVersion),
                    ],
                            wait: false
                }
            }
        }

        failure {
            script {

                notifyParties(String.format(
                        notificationStrPattern,
                        automationStatus,
                        env.STAGE_NAME,
                        "Something Went Wrong! Check the logs.",
                        releaseVersion,
                        getLastCommitAuthor()
                ), '#FF0000')
            }
        }

        cleanup {
            deleteDir()
            dir("${workspace}@tmp") {
                deleteDir()
            }
            dir("${workspace}@script") {
                deleteDir()
            }
        }
    }
}

def semanticRelease(){

    // Release a new version to be deployed to the staging environment
    println "Attempting to retrieve release version..."
    //(releaseVersion, isNewRelease) = releaseUtils.semanticRelease()
    def pkgJson = readJSON file: 'package.json'
    oldReleaseVersion = pkgJson.version
    withCredentials([string(credentialsId: 'github-personal-access-token-jenkins', variable: 'GH_TOKEN')]) {
        sh """
            git config --global --add safe.directory ${WORKSPACE}
            npm run release
        """
    }
    pkgJson = readJSON file: 'package.json'
    releaseVersion = pkgJson.version
    if (oldReleaseVersion != releaseVersion) {
        isNewRelease = true
        isDeployable = true
        println "TAGGED SEMANTIC VERSION --> " + releaseVersion
        //return [releaseVersion, isNewRelease]
    } else {
        println "NO NEW RELEASES TO PUBLISH"
        println "** BONUS OPTIONAL: Add Option To Dynamically Ammend Commit and Rerun semanticRelease() If Possible Semantic Release Syntax On The Commit Was Poorly Formatted. **"
        //return [null, isNewRelease]
    }
}

Boolean isJenkinsCommit() {

    println "Is Jenkins Commit WAS CALLED"
    def committerEmail = sh(script: """
        git log -1 --pretty=format:'%ae'
    """, returnStdout: true)?.trim()
    return (committerEmail == "${env.GIT_COMMITTER_EMAIL}")
}
def pushL1prodReleaseTag(){

    def pkgJson = readJSON file: 'package.json'
    releaseVersion = "prod_rel_" + pkgJson.version + "_" + new Date().format('yyyyMMddHHmmss').toString()
    // push pre-release tag to L1 Github
    pushTag(releaseVersion)
}
def pushTag(String releaseVersion){

    //gitUtils.pushTag(releaseVersion)
    sshagent(credentials: ['jenkins-ssh-key'], ignoreMissing: false) {
        sh "git tag -a -m ${releaseVersion} ${releaseVersion} && git push origin ${releaseVersion}"
    }
}

def installAioCli(){

    def clientSecret='None', sslPrivateKey='None'
    awsUtils.withDeployer('nonprod', 'amrpint') {
        clientSecret = sh(returnStdout: true, script:"aws secretsmanager get-secret-value --secret-id adobe-ats-client-secret --query SecretString --output text --region us-east-1").trim()
        sslPrivateKey = sh(returnStdout: true, script:"aws secretsmanager get-secret-value --secret-id adobe-ats-ssl-private-key --query SecretString --output text --region us-east-1").trim()
    }
    def aioConfig = """{
        \\"client_id\\": \\"1af8be6b37174a53926c5190167312ab\\",
        \\"client_secret\\": \\"${clientSecret}\\",
        \\"technical_account_id\\": \\"DB6B1E226286AB510A495CC8@techacct.adobe.com\\",
        \\"ims_org_id\\": \\"6A3DF65A5832D31C0A495C35@AdobeOrg\\",
        \\"meta_scopes\\": [
          \\"ent_cloudmgr_sdk\\"
        ]
    }"""

    sh """
        set +x
        git config --global --add safe.directory ${workspace}
        echo "${aioConfig}" > config.json
        echo "${sslPrivateKey}" > adobe_ats_team_private.key
        set -x
        aio config:set ims.contexts.aio-cli-plugin-cloudmanager config.json --file --json
        aio config:set ims.contexts.aio-cli-plugin-cloudmanager.private_key adobe_ats_team_private.key --file
    """
}

def pollBeforeAdobePipelineExec(String adobePipelineId) {

    // Don't fail if a user improperly (manually) triggered a pipeline, which would bypass the mutex, wait for the AMS pipeline to be free to use instead
    def currentStatus = sh(returnStdout: true, script:"""aio cloudmanager:pipeline:list-executions ${adobePipelineId} -p 2007 | head -3 | tail -1 | awk '{print \$8}'""").trim()
    println "Current Adobe Pipeline Status: " + currentStatus
    if (currentStatus == "RUNNING"){
        println "Wait a minute! Someone either manually triggered a nonprod deployment pipeline or a Jenkins pipeline is not using a the right Jenkins lock."
        println "Wait a minute! Someone Skipped The Queue! RUDE!\nWaiting..."
    } else {
        println "Clear To Proceed Using The Pipeline In Adobe..."
    }
    while(currentStatus == "RUNNING"){
        sleep 30
        currentStatus = sh(returnStdout: true, script:"aio cloudmanager:pipeline:list-executions ${adobePipelineId} -p 2007 | head -3 | tail -1 | awk '{print \$8}'").trim()
        println "The Adobe Pipeline That I'm In QUEUE For's Current Status Is: " + currentStatus + ".\nWaiting..."
    }

}

def checkAdobePipelineStatus(String adobePipelineId) {

    // Check Status Of Executed Pipeline
    status = sh(returnStdout: true, script:"""aio cloudmanager:pipeline:list-executions ${adobePipelineId} -p 2007 | head -3 | tail -1 | awk '{print \$8}'""").trim()
    println "The Pipeline STATUS is '${status}'"
    def subject = "${env.STAGE_NAME} - ${releaseVersion}"
    if (status == 'FAILED'){

        notifyParties(String.format(
                notificationStrPattern,
                automationStatus,
                env.STAGE_NAME,
                "Deployment Failed...please read build logs in Adobe Cloud Manager Console",
                releaseVersion,
                getLastCommitAuthor()
        ), '#FF0000')

        error "Jenkins Pipeline failed, please read logs in Adobe Cloud Manager..."
    } else if (status == 'CANCELLED'){

        notifyParties(String.format(
                notificationStrPattern,
                automationStatus,
                env.STAGE_NAME,
                "Deployment Cancelled From Adobe Cloud Manager Console...",
                releaseVersion,
                getLastCommitAuthor()
        ), '#C0C0C0')

        error "Jenkins Pipeline was cancelled"
    } else if (status == 'FINISHED'){

        waiting = false
        notifyParties(String.format(
                notificationStrPattern,
                automationStatus,
                env.STAGE_NAME,
                "Done",
                releaseVersion,
                getLastCommitAuthor()
        ), '#00994C')
    } else {

        // pipeline is busy, wait
        notifyParties(String.format(
                notificationStrPattern,
                automationStatus,
                env.STAGE_NAME,
                "Someone interrupted your deployment to run theirs outside of the expected queue...waiting 30 seconds",
                releaseVersion,
                getLastCommitAuthor()
        ), '#C0C0C0')

        sleep 30
    }
}

@NonCPS
def getPipelineTriggeredBy(Boolean isWebhookTrigger, Boolean isHotfixBranch){

    def entrypoint = ''
    def isManualTrigger = !isWebhookTrigger //more readable
    def upstream = currentBuild.rawBuild.getCause(hudson.model.Cause$UpstreamCause) //returns non serializable object
    def hasUpstream = upstream?.shortDescription != null
    println "DEBUG: \n\thasUpstream: ${hasUpstream}"

    if (isWebhookTrigger && !isHotfixBranch) {

        entrypointStageName = "Using Automated CICD Workflow"
        automationStatus = "Automated Trigger"
        entrypoint = "develop" //change from develop to ci
        //also need to identify all the usecases properly
        //maybe rename entrypoint to pipelineTriggerdBy
    }
    else if (isWebhookTrigger && isHotfixBranch && hasUpstream) {

        entrypointStageName = "Using Automated Hotfix CICD Workflow"
        automationStatus = "Automated Trigger"
        entrypoint = "hotfix"
    }
    else if (isWebhookTrigger && isHotfixBranch && !hasUpstream) {

        entrypointStageName = "Using Manual Tagged Hotfix CICD Workflow"
        automationStatus = "Manual Trigger"
        entrypoint = "hotfix"
    }
    else if (isManualTrigger && hasUpstream) {

        entrypointStageName = "Using Automated CICD Workflow"
        automationStatus = "Automated Trigger"
        entrypoint = "tagged"
    }
    else if (isManualTrigger && !hasUpstream) {

        entrypointStageName = "Using Manual Tagged Parameter Entrypoint"
        automationStatus = "Manual Trigger"
        entrypoint = "tagged"
    }
    return entrypoint
}

def runWorkflow(String entrypoint){
    if (entrypoint == 'develop'){

        // push L1 pre-release tag to Github
        pushL1prodReleaseTag()
        println "Using Newly Created Pre-Release Version -- " + releaseVersion
<<<<<<<< HEAD:jenkins/Jenkinsfile-cicd-nonprod.groovy

========
>>>>>>>> main:jenkins/Jenkinsfile-cicd-prod.groovy
        // Flag stages for the future
        if (JENKINS_PIPELINE_ID != 'staging') {
            isDeployable = true
        } else {
            isReleasable = true
        }
    } else if (entrypoint == 'hotfix') {

        // push L1 hotfix tag to Github
        if (JENKINS_PIPELINE_ID == 'hotfix_ci') {
            pushL1HotfixTag()
            isDeployable = true
            println "Using Newly Created Hotfix Version -- " + releaseVersion
        } else {
            error("You can only trigger a hotfix flow if you need to fix a production issue quickly. Otherwise, use the main branch for to use the main CI/CD flow.")
        }
    } else if (entrypoint == 'tagged') {

        releaseVersion = params.RELEASE_VERSION
        println "Using Existing Release Version -- " + releaseVersion

        if (JENKINS_PIPELINE_ID != 'staging') {

            //all tags can be deployed to any nonprod environments
            isDeployable = true
        } else {

            def prodReleasePattern = /^prod_rel_\d+\.\d+\.\d+_\d{14}$/
            def releasePattern = /^\d+\.\d+\.\d+$/
            def hotfixPattern = /^hotfix_.+_\d+\.\d+\.\d+_\d{14}$/

            // release tags are by definition released, and therefore can be deployed to production enviroonments
            if (releaseVersion ==~ releasePattern) {

                isDeployable = true
                // pre-release tags need to prepared for release before they can be deployed to a production environment
<<<<<<<< HEAD:jenkins/Jenkinsfile-cicd-nonprod.groovy
            } else if (releaseVersion ==~ preReleasePattern) {
========
            } else if (releaseVersion ==~ prodReleasePattern) {
>>>>>>>> main:jenkins/Jenkinsfile-cicd-prod.groovy

                isReleasable = true
                // hotfix tags can be deployed to production environments
            } else if (releaseVersion ==~ hotfixPattern) {

                isDeployable = true
            } else {

                println "releaseVersion: " + releaseVersion
                error("Unrecognised Pattern For Pre-Release, Release or Hotfix Versions. This tag cannot be deployed into a production environment.")
            }
        }
    }
}

def getLastCommitAuthor(){
    withCredentials([string(credentialsId: 'github-personal-access-token-jenkins', variable: 'GH_TOKEN')]) {
        return sh(returnStdout: true, script:"""
            git config --global --add safe.directory ${WORKSPACE}
            git log -1 --pretty=format:'%an'
        """).trim()
    }
}

def notifyParties(String msg, msgColor='#330000') {
    switch(JENKINS_PIPELINE_ID){
        case "pr":
        case "ci":
        case "dev":
        case "qa":
            println msg
            office365ConnectorSend (
                    color: msgColor,
                    factDefinitions: [[name: "Details", template: '```' + msg]],
                    webhookUrl: MICROSOFT_TEAMS_PROJECT_WEBHOOK
            )
            break
        case "hotfix_ci":
        case "hotfix_staging":
        case "staging":
            println msg
            office365ConnectorSend (
                    color: msgColor,
                    factDefinitions: [[name: "Details", template: '```' + msg]],
                    webhookUrl: MICROSOFT_TEAMS_PROJECT_WEBHOOK
            )
            mail (
                    subject: "${env.STAGE_NAME} - ${releaseVersion}",
                    body: """${msg}""",
                    to: "${EMAIL_RECIPIENTS}"
            )
            break
        default:
            println msg
            break
    }
<<<<<<<< HEAD:jenkins/Jenkinsfile-cicd-nonprod.groovy
}
//testing
========
}
>>>>>>>> main:jenkins/Jenkinsfile-cicd-prod.groovy
