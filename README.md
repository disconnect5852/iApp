# iApp
try this try that, CRUD application. JPA, Vaadin, Spring

1. Checkout, and import the project to IDE, or whatever
2. exectute mvn com.github.eirslett:frontend-maven-plugin:1.7.6:install-node-and-npm -DnodeVersion="v12.14.0"   to install node dependency
3. build the project with maven
4. (optional) run IAppApplicationTests JUnit class, which will test DB operations, and fills it with some random generated data.
    That's why the default datasource URL setting (jdbc:h2:./data/db) in resources/application.properties is recommended, instead of memory stored DB
5. (optional) change resources/application.properties setting as you like
6. execute IAppApplication class to start the application
7. the application's web UI available at http://localhost:8080/ (it's not nice, and user friendly, but Vaadin also isn't too developer friendly :) )
8. DB console is available at http://localhost:8080/h2-console/ using the resources/application.properties settings

Use of application if simple:
1. open http://localhost:8080/ in web browser
2. Click on "List users" button to fill the data table (if and data in DB)
3. Click on "New user" to add new user
4. Fill name, birth date, and email(s) (click on "Add email" to add each email, only those will be added to DB which added with this button)
5. Click on "Add" button to commit

PS: it seems in java world you can choose only from bad frontend frameworks. From the bad frontend frameworks, the best should be chosen at the time for the task...
JSF, Swing is outdated, GWT is discontinued, Vaadin isn't so good too, javaFX needs runtime env to be installed(lol?), Angular is hyped for now (but GWT was hype too, until Google cut the development...)
