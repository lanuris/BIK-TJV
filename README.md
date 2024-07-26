# BIK-TJV
FIT CTU - BIK-TJV (Java Technology)

## Course overview
The aim of the course is to provide knowledge and skills needed for the development of smaller and larger information systems. Students will get acquainted with general theoretical concepts and will be able to apply these concepts using libraries and tools from the ecosystem of the Java programming language. After completing the course students will be able to participate in the development of software systems on the Java platform.

## Civil Freelancers
A web application where Civil Engineers can share their projects and find coworkers.

## Quickstart

- Install Docker [(Click here if you need to know how)](https://docs.docker.com/get-docker/)
- Clone the entire repo or download `compose.yml`
- Run the command `docker compose up` in the terminal
- Allow cookies in your browser (the `JWT token` is saved there)
- The backend will be available at `localhost:8081`
  * See the [Request Examples](#request-examples) section
- The frontend will be available at `localhost:8085`
  * Register a user or use the following credentials: `username: admin@admin.com`, `password: admin`


## Task Description
Semester project is a client–server application working with at least 3 domain types that implements full CRUD (operations create, read, update, delete) over all of them.

###  Server part
It is a three-layer application (persistence layer, business/application layer, and presentation layer (REST API)):

- uses Spring Framework and written in Java programming language (hard requirement; another JVM language may be approved: ask the tutor)
- uses object-relational mapping (ORM) in persistence layer
  * the data tier must use a relational database system that uses a persistent data storage and is capable of serving concurrent requests (a DB server)
    * neither in-memory DB nor an embedded DB (e.g., SQLite) is not an option. `(Implemented solution using PostgreSQL database)` 
    * while meeting these criteria, the RDBMS is the student’s choice
  * at least three entities with at least one many-to-many association (implies at least four tables in the database)
  * implements complete CRUD over all entities and also over the M:N association
  * at least one complex query (besides CRUD or working with M:N association; thus multiple tables should be involved) in ORM (using JPQL)
- contains clearly separated business layer
  * implements all data operations enabled by persistence layer (using delegation)
  * uses transactions in a reasonable way
- contains clearly separated layer of RESTful web service (REST API)
  * exposes all business operations and supports all entities
  * follows web standards (designing and implementing standardized REST API is important here)
    * does not return HTTP status 500 for invalid requests (i.e., the service is bug-free, status 500 is used only for server problems, e.g., DB connection)
  * complete and machine-readable API documentation (e.g., OpenAPI): all endpoints, operations, and data formats
- contains automated tests
  * three different types of tests taught in this course; loss of up to 4 pts for missing type of test or for test of poor quality
- uses smart build (Maven, Gradle); automated tests should be run and evaluated within the build system
- is developed using the git versioning system
  * Gitlab FIT repository (gitlab.fit.cvut.cz/<username>/<server_repo>) should be used for both development and submitting

### Client part
It is an application written in any programming/scripting language with the following features:

- any user interface (web, GUI, interactive console application)
- uses the REST API of the server part as the backend
- implements a complex business logic operation over the server part
  * the business operation is a single action that is composed of multiple data operations (CRUD); the client issues these data operations by multiple calls on the server part
- is versioned
  * Gitlab FIT repository (gitlab.fit.cvut.cz/<username>/<client_repo>) should be used for both development and submitting


## Request Examples

### Authentication:
`curl --location 'http://localhost:8081/auth/signin' \
--header 'Content-Type: application/json' \
--data-raw '{
"email" : "admin@admin.com",
"password" : "admin"
}'`

### Registration:
`curl --location 'http://localhost:8081/auth/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
"name" : "SomeName",
"surname": "SomeSurname",
"email" : "SomeMail@SomeDomain.com",
"password": "somePassword"
}'`

### Get All Engineers (Admin role only):
`curl --location 'http://localhost:8081/engineer/' \
--header 'Authorization: Bearer "addJWTtoken"'`

### Get first Project: 
`curl --location 'http://localhost:8081/project/1' \
--header 'Authorization: Bearer "addJWTtoken"'`


## Connection Configuration

### Connection Configuration

The backend application requires a PostgreSQL database to function.  
Here are the default parameters and the variables that can be used to override these values:

| Property       | Default Value    | Variable              |
|----------------|------------------|-----------------------|
| url            | `localhost:5432` | `DATA_SOURCE_URL`     |
| database name  | `dbtjv`          | `DATA_SOURCE_DB`      |
| user           | `root`           | `DATA_SOURCE_USERNAME`|
| password       | `root`           | `DATA_SOURCE_PASSWORD`|

In the `compose.yml` file, for the purpose of running with `docker compose`, the value of `DATA_SOURCE_URL` is set to `db:5432`, which corresponds to the address of the running PostgreSQL container.

### Frontend

The frontend application requires the backend to function. 
Here are the default parameter and the variable that can be used to override the value:

| Property       | Default Value     | Variable              |
|----------------|-------------------|-----------------------|
| url            | `localhost:8081/` | `back_end_url`        |

In the `compose.yml` file, for the purpose of running with `docker compose`, the value of `back_end_url` is set to `backend:8081`, which corresponds to the address of the running backend container.

## Documentation

The latest JavaDoc documentation can be found [here]().
## Backend Log Locations

### Console Appender (`STDOUT`):

- This appender outputs logs to the console.
- The log message format is specified using the pattern `%d{yyyy-MM-dd HH:mm:ss} %highlight(%-5level) %logger{36} - %msg%n`.
- `STDOUT` is attached for levels from `INFO`.
- The log message format is specified using the pattern: Date and time, Log level with highlighting, Logger class name, Log message content.

### File Appender (`FILE`):

- Saves logs to the file `logs/app.log`.
- Logs are saved to rolling files, with each day having a separate file (`logs/app-yyyy-MM-dd.i.log`).
- The maximum size of a single file is **10 MB** (`<maxFileSize>`).
- It retains the history of the last **30 files** (`<maxHistory>`).
- The total capacity of the files is limited to **1 GB** (`<totalSizeCap>`).
- `FILE` is attached for levels [`DEBUG`, `INFO`, `WARN`, `ERROR`].
- The log message format is specified using the pattern: Date and time, Logger class name, Log message content.

### Error File Appender (`ERROR_FILE`):

- Only saves logs with the `ERROR` level to the file `logs/errors.log`.
- Like the `FILE` appender, it saves to rolling files with a daily format.
- It contains a filter (`<filter>`) to filter messages based on the `ERROR` level.
- It uses the same log message format as `FILE`.
