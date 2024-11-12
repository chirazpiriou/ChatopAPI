# ChaTop API

ChaTop API is a Spring Boot application that provides a RESTful API, allowing potential tenants to contact property owners regarding properties they wish to rent. It uses JWT (JSON Web Token) authentication for security and is documented with Swagger to facilitate usage and integration.

---
## Prerequisites
Before you begin, make sure the following are installed:
- Java Development Kit JDK
- Maven 
- MySQL Server 
- IDE  Visual Studio Code or Eclipse ...
- Spring Boot
- Git

## Setup the DDB:
To create the database, follow these steps:
- Make sure that MySQL Server is running on your machine before proceeding.
- You can create the database using either MySQL Workbench for a graphical interface or MySQL CLI for command-line interaction.
- Authenticate by logging in with your MySQL username and password "root" for both
- Navigate to the following path to find the SQL script:  `Developpez-le-back-end-en-utilisant-Java-et-Spring/ressources/sql/script.sql`.
- In MySQL Workbench, go to `File > Open SQL Script`, and select the script located at the path above.
- At the beginning of the script, add the following lines to ensure that the `chatopdb` database is created:
   ```sql
   CREATE DATABASE IF NOT EXISTS chatopdb;
   USE chatopdb;

- Run the Script: Click on the lightning bolt icon (⚡) in MySQL Workbench to execute the script.
- To connect the database to the Spring Boot API, necessary configurations have been set up
## Front-End Setup
- Go to the directory where the front-end code is located.
- Install the necessary dependencies using `npm install`
- run `npm run start` to launch the front-end project, which will be accessible at http://localhost:4200.


## Installation and launch
1- Clone the git repository with the following command in a terminal
````
git clone https://github.com/chirazpiriou/ChatopAPI.git
````
2- Navigate to the project directory
````
cd ChaTop/api
````
3- Build the project and install its dependencies
````
mvn clean install  
````
4- Run the Spring Boot application:
````
mvn spring-boot:run
````

Once the server starts running locally, the application is now accessible at http://localhost:3001/. You can test its endpoints using a web browser or an API tool like Postman.

## Swagger Documentation
The Swagger documentation can be found:  http://localhost:3001/swagger-ui/index.html

