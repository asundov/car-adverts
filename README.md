# Car Adverts

Car Adverts is a project designed to manage car advertisements, built with Java Spring and uses PostgreSQL as the database management system. This README will guide you through setting up the development environment, initializing the database, and starting the project.

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Project Setup](#project-setup)
3. [Database Setup](#database-setup)
    - [Running the Database Script](#running-the-database-script)
    - [Connecting to PostgreSQL with DBeaver](#connecting-to-postgresql-with-dbeaver)
    - [Opening Tables and Viewing Data](#opening-tables-and-viewing-data)
4. [Project Versions](#project-versions)
5. [Running the Project](#running-the-project)
6. [Swagger](#swagger)
7. [Authentication](#authentication)
8. [Have Fun](#have-fun)

## Prerequisites

Ensure you have the following software installed on your system:

- **Gradle 8.5**: Used for building and managing project dependencies.
- **Node.js v20.10.0**: Required for any JavaScript-related development and tooling.
- **Java 17 (OpenJDK 17)**: The project is built using Java 17.
- **Docker and Docker Compose**: Required to set up the PostgreSQL database.
- **DBeaver**: A database management tool for interacting with the PostgreSQL database.
- **IntelliJ IDEA Community Edition**: Recommended IDE for Java development.

## Project Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/asundov/car-adverts.git
   cd CarAdverts

2. Install project dependencies using Gradle:
    ```bash
   gradle build

## Database Setup
To set up the PostgreSQL database, you need to run a script that handles the creation and initialization of the database using Docker.

1. Navigate to the directory containing the script:
    ```bash
   cd .scripts
   
2. Run the recreate-db.sh script:
    ```bash
   ./recreate-db.sh

What the script does:

- Navigates to the .database directory where the Docker Compose file is located.
- Stops any running Docker containers related to the database using docker-compose down.
- Removes the existing Docker volume named car-adverts-postgres_car-adverts_data to ensure a fresh start.
- Brings up the Docker containers defined in the Docker Compose file using docker-compose up -d.
- Continuously streams the logs of the running Docker containers with docker-compose logs -f.

How to run the script:

- Make sure you have execution permissions for the script. You can grant permission using:
   ```bash
  chmod +x .scripts/recreate-db.sh

Then, execute the script from the terminal.

### Issue with "/bin/bash^M bad interpreter" due to Windows style line endings:

Example:

- root@ENTL008zApEnPFu:/mnt/c/dev/projects/oots# .scripts/recreate-db.sh
- -bash: .scripts/recreate-db.sh: /bin/bash^M: bad interpreter: No such file or directory

To fix this issue, you can convert the line endings to Unix format.

#### Using a Text Editor

- Visual Studio Code: Open the script in VSCode, and at the bottom right, you'll see "CRLF" or "LF." Click on it, and
  you can change the line endings.

- Notepad++: Open the file, go to the "Edit" menu, and select "EOL Conversion." Choose "Unix (LF)."

After fix try to execute "git add <path-to-script>". If you get this warning:

- warning: in the working copy of '.scripts/recreate-db.sh', LF will be replaced by CRLF the next time Git touches it

Execute this git command:

- git config --global core.autocrlf false

Direct edit of the script:
- Open the script:
   ```bash
   vim .scripts/recreate-db.sh

- Insert this line:
   ```bash
  :set ff=unix

- Save by executing ESC + ":wq" and Enter
- Start the script again

## Connecting to PostgreSQL with DBeaver

1. Open DBeaver.

2. Click on the Database menu and select New Database Connection.

3. Choose PostgreSQL from the list of database types.

4. Enter the connection details:
   - Host: localhost
   - Port: 1000 
   - Database: car-adverts
   - Username: car-adverts
   - Password: caradverts
   - Alternative to inserting host and database name, you can insert URL: jdbc:postgresql://localhost:1000/car-adverts

All of these data can be found inside .database/docker-compose.yaml file.

5. Click Test Connection to ensure the details are correct.

6. Once the connection is successful, click Finish.

### Opening Tables and Viewing Data

1. Once connected to the PostgreSQL database in DBeaver, expand the connection node in the Database Navigator.
2. Expand the Schemas node, then expand the schema that contains your tables.
3. Right-click on any table you want to view, and select View Data > All Rows to see the table data.

Explanation of Schema's:
- codebook - containing codebook default data, in this case codebook table of fuel types
- conf - containing tables for user handling and authentication
- core - containing core functionality tables, in this case table for car adverts

## Project Versions

This project is available in two versions depending on branch being used:

1. **Monolithic Application**:
    - main branch
    - The monolithic version of the application is a single, unified codebase where all components and functionalities are integrated into one cohesive application. This version is easier to deploy and manage as a single unit, making it suitable for smaller teams or simpler deployments.
    - This version can be found in the `monoapplication` directory of the repository.

2. **Microservices Architecture**:
    - CA-microservice-implementation branch
    - The microservices version of the application separates the core functionalities into distinct, loosely-coupled services. Each service operates independently and communicates with other services through well-defined APIs. This architecture allows for greater scalability, flexibility, and the ability to deploy services independently.
    - This version is organized in the `microservices` directory of the repository. Each microservice has its own folder and includes a dedicated README file explaining its functionality and how to run it.

### Choosing the Right Version

- **Monolithic Application**: Recommended for simple projects, quick prototyping, or when you want a single deployable unit.
- **Microservices Architecture**: Ideal for larger projects, distributed teams, or when there is a need for scalability and independent deployment of components.

## Running the Project

1. Choose application structure and checkout on chosen branch:
- Monolithic application
  ```bash
    git checkout remotes/origin/main
- Microservices architecture
  ```bash
   git checkout remotes/origin/CA-microservice-implementation

2. Open the project in IntelliJ IDEA.
3. Configure the project SDK to use Java 17:
4. Go to File > Project Structure > Project.
   - Set the Project SDK to Java 17.
5. To build and run the project:
   - Open the terminal in IntelliJ IDEA.
   - Depending on application version, run gradle bootRun to start the application in:
     - all applications in case of microservice structure, 
     - main application in case of monolithic application.

## Authentication

This application has jwt filter setup and it is **required** to be logged into the application to be able to test api calls.

#### Users
There are several users defined in database but for testing purposes we should use these two:
1. johndoe - User with admin access to all controllers and methods
2. janesmith - User with additional restrictions on controller CodebookApiControllerImpl and methods: addCarAdvert, updateCarAdvert and deleteCarAdvert

Authentication methods, apart from logout method, are accessible to everyone. For other methods, authentication must be made.

#### Swagger

When testing api calls there's Authorize button at top of api list. When clicking on it, insert name of one of mentioned users for authentication. This way every future api call will be added with Authorization header.


## Swagger

Depending on application, Swagger links are different:
1. Monolithic application:
   - http://localhost:8026/codebook/swagger-ui/index.html#/

2. Microservices structure:
    - http://localhost:8024/auth/swagger-ui/index.html#/ - authentication APIs
    - http://localhost:8025/bl/swagger-ui/index.html#/ - car adverts APIs
    - http://localhost:8026/codebook/swagger-ui/index.html#/ - codebook APIs

## 🎉 Have Fun!


Even though it's work, it can *also* be fun. Every day is an opportunity to learn something new... or to completely lose your mind over some obscure bug or a piece of documentation that doesn’t exist. 🤯

But hey, that’s the life of a developer! 
So grab your coffee ☕️(I already got mine) and let's pretend to enjoy every moment of it! 😉

Happy testing! 🚀