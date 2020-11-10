This SpringBoot application has an Organizations POST API which adds Organizations to the
Organizations Table. Once this Spring Boot Application is started you can access the h2 database and
the API through swagger using the following information.

url: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:five9
username: sa
password: 

Swagger can be accessed using the url: http://localhost:8080/swagger-ui.html

Overview of your assignment:
* Clone this repository & make sure you can build it locally
* Review the objectives below.
* Create a separate Merge Request for each objective. Follow best practices regarding your commit messages,
MR description, branch strategy, etc. It is acceptable for one MR to depend on another, but this should be minimized
and be clear by means of the branch selected and its commit history.
* Each objective should include additions or changes to tests as appropriate. Feel free to include additional comments,
documentation, or anything else that you think would improve the overall quality of this project.

Objectives:
1. In the OrganizationsService make changes to the service to include the check for null or empty organization name.
Note that `testSaveAllOrganizationsInvalidOrgName()` checks whether the name is valid or not.
1. Create Add Customers API to add Customers to a given organization
1. Create List Customers API in a given organization
1. Create Update Customers API in a given organization
