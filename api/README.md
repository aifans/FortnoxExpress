# FortnoxExpress API Server

The project uses Maven as build tool. You can also use the IDE such like IntelliJ or Eclipse to run it easier..

*DB*

- Run the db.sql file on your MySQL

*API project*

- Open the project in IntelliJ
- Navigate and change src/main/resources/application-dev.properties
    - Replace the db settings as necessary
- The API should now be running on localhost:8070

*API doc*
- http://localhost:8070/doc.html
- Navigate to src/main/resources/application.properties
  - set knife4j.production=true to disable in production enviroment

*@WebLog*

- The API controller methods decorated by this annotation will print the detailed information of the call. It only takes effect in dev and test environments.
- You can set resources/application.properties to dev, test, prod.
