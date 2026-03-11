

steps for jenkins pipeline setup:


- Setup Jenkins server locally

- download jenkins.war from https://www.jenkins.io/download/

- run the command: java -jar jenkins.war

- open the browser and go to http://localhost:8080

- follow the instructions to unlock Jenkins using the initial admin password

- Install suggested plugins and create an admin user

- Create a new Jenkins job 

- In the Jenkins dashboard, click on "New Item"

- Enter a name for the job and select "Pipeline" as the job type, then click "OK"

- In the job configuration page, scroll down to the "Pipeline" section

- In the "Definition" dropdown, select "Pipeline script from SCM"

- In the "SCM" dropdown, select "Git"

- In the "Repository URL" field, enter the URL of your Git repository 

- Choose branch and credentials if necessary

- In the "Script Path" field, enter the path to your Jenkinsfile in the repository (e.g., "Jenkinsfile")

- Click "Save" to save the job configuration

- Trigger the Jenkins job