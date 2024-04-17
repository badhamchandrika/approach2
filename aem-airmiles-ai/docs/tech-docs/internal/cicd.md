# Jenkins Pipeline Set Up for the New Projects

# 1. Introduction
This document provides an overview of the process and the steps involved in the creation of a new project and the related Jenkins/Adobe Cloud Manager pipelines.

# 2. Adobe Cloud Manager for AMS

Cloud Manager for Adobe Experience Manager gives developers the ability to create impactful customer experiences through streamlined workflows, built upon Adobe Experience Manager’s best practices.

The CI/CD pipelines optimized for Adobe Experience Manager allow us to easily merge development workflows by simply checking in your code, which can move to production-ready.

During the build phase, the custom code updates are thoroughly tested against best practices to deliver reliable applications for your customers.

# 3. Cloud Manager Pipelines

Cloud Manager has two kinds of pipelines –
* Code Quality Pipeline
* Code Deployment Pipeline

# 4. Jenkins Pipeline

LoyaltyOne uses Jenkins CI to manage the code deployment on the AEM environment.

We use the Scripted method of Pipeline as Code to manage the various stage of the code deployment process.

Here are the various steps involved in the creation of a new project. The new project creation process is semi-automated where the skeleton of the project will be created from a base template and the corresponding Jenkins pipelines meed to be created manually from the existing template.

* Creation of GitHub Repository using Jenkins
* Creation of Adobe Git Repository & Manual sync
* Creation of Adobe Cloud Manager Pipelines
* Sync Loyalty GitHub repo and Adobe GitHub
* Creation of Jenkins Pipelines for the new project
* Creation of Microsoft teams CICD Notifications Channel and Webhook
* Jenkins Pipelines Configuration updates
* Project-specific updates in the Jenkins Pipeline Groovy file
* Pipeline testing

# 5 Creation of GitHub Repository using Jenkins

As a first step, we need to create LoyaltyOne GitHub repository for the project. We have a pipeline named “Developer Starter Kit (DSK)” which we can leverage to create the repository and the corresponding permissions.

Jenkins URL - https://jenkinstein.loyalty.com/job/_Shared/job/Developer%20Starter%20Kit%20(DSK)/

## Parameters:

APP_ID  GitHub Repository name
Platform  started-kit-nil
GITHUB_REPO_OWNER Person ID to be assigned as a owner/admin to the GitHub Repo
Rest of the parameters need to left with the default values.
Then click on the Build which will execute the pipeline job.

Once the build has successfully completed, we can see the GitHub repository got created under https://github.com/LoyaltyOne/

Go to the repository and remove the unwanted folders that are not needed for the AEM project.

# 6 Creation of Adobe Git Repository & Manual sync

Next, we need to manually create a Adobe Git repository for the project using the Adobe Experience console.

URL – https://experience.adobe.com/#/@loyaltyone/cloud-manager/repositories.html/program/2007
Adobe Git repository should be named same as that of the LoyaltyOne GitHub repository.

# 7 Creation of Adobe Cloud Manager Pipelines

Let’s create the Adobe Cloud Manager Code Quality pipelines that are needed for the new AEM project.

Navigate to the Pipelines section of the Cloud Manager and use the below direct URL.

URL – https://experience.adobe.com/#/@loyaltyone/cloud-manager/pipelines.html/program/2007
As per the individual project requirement, the necessary Code Quality pipelines need to be created. Generally it will be two Code Quality pipelines –

CI/CD Code Quality
Hotfix Code Quality
The deployment pipelines are at the program level and all the AEM projects share the same Adobe Code Deployment pipelines across the environment.

# 8 Sync Loyalty GitHub repo and Adobe GitHub

As an important steps, we need to sync the contents of the Adobe Git repository with the LoyaltyOne GitHub repository manually so that the first exection of the Jenkins pipeline won’t fail. This is an one-time activity.

Then copy the “Jenkins” directoty from the “aem-airmiles-web” project repository to the new project repository. This will copy Jenkins pipeline code to the new project and we will be updating few configuration in the next steps.

# 9 Creation of Jenkins Pipelines for the new project

As part of the “Developer Starter Kit (DSK)” pipeline execution, a Jenins job with the new project name would be created.

As these are not related to AEM projects, delete the auto-created pipelines.

Then create the new pipeline job from the other existing AEM project (say “aem-airmiles-web”).
Copy all the below pipelines from the other AEM pipeline job to the new project.

# 10 Creation of Microsoft teams CICD Notifications Channel and Webhook

Each individual project should have their own Microsoft team channel which would receive the notification from the Jenkins on the pipeline status.

If not already created, request the project team to create a channel and get the WebHook URL of the channel.

Once Webhook URL has been received, update the same on all the pipeline of the new project.

# 11 Jenkins Pipelines Configuration updates

In this step, update the “GitHub project” URL in each of the pipeline configuration. This is necessary for the GitHub to interact with the corresponding pipeline through GitHub Webhook.

# 12 Project-specific updates in the Jenkins Pipeline Groovy file

This is an important step where the project-specific configurations will be updated in the Jenkins pipeline code.

There are few important variables which differs as per the project and the same need to be changed in the pipeline code.

EMAIL_RECIPIENTS  This should contain the e-mail address to which the notifications need to be sent for the stage and production deployment
L1_REPO_NAME  LoyaltyOne GitHub Repository name
MICROSOFT_TEAMS_PROJECT_WEBHOOK  MS Teams Webhook URL obtained in step-10.
ADOBE_REPO_ID  ID of the Adobe Git Repository which can be found from the Adobe Developer console.
URL – https://developer.adobe.com/experience-cloud/cloud-manager/reference/playground/#/api/program/2007/repositories

Repository ID can be found under the “Embedded -repositories” section as depicted in the below image.

Next, the Code Quality pipelines ID need to be updated in the Jenkins Groovy code.

The Code Quality Pipeline ID can be obtained from the Cloud Manager pipeline console.

# 13 Pipeline testing

As a final step, once the project team commit/push thei code onto the GitHub repository, the Jenkins pipeline can be executed to test whether the Cloud Manager pipeline gets invoked which inturn deploys the code to the AMS AEM instances.