# Formula Challenger XII - Microservices
<b>Spring Boot - Back-End Journey | AWS (19/06)</b><br>
### Compass Scholarship Program - Challenger 12
Create 3 microservices (ms-cars, ms-history, ms-races) based on the rule in this <a href="https://rentry.co/3zy2hc">document</a>.
### Kaban Board
This is activities from the project → https://github.com/users/deyvidsalvatore/projects/2
### Pre-Requisites
* Docker
* Docker Compose

### Starting (Must have Docker)
1. ```git clone https://github.com/deyvidsalvatore/Formula-Challenger-XII.git```
2. ```cd Formula-Challenger-XII/```
3. ```docker compose up -d```
- Attention: You must wait the progress, because this can take very longer.

### Architecture
* Eureka Server (eurekaserver): The server who manages the location of all microservices.
* API Gateway (apigateway): A Gateway who redirects all requests to microservices and load balancing.
* Car Microservice (ms-cars): An service who deals with CRUD registering cars.
* Race Microservice (ms-races): An service who deals with simulating races getting info from ms-cars and send the race historic to ms-history
* History Microservice (ms-history): An service who deals with message calls from ms-races to give the history of the race, writing this message in a database.
* RabbitMQ: A queue message to deal with microservices exchanging
* MongoDB: A database to save all the information.

### Postman Collection
In this repository has a file to a Postman Collection. In case of issues please follow along with this file.

### Endpoints
#### Cars Microservice (ms-cars)
* <b>GET</b> ``/api/v1/cars`` (Get All Cars)
* <b>GET</b> ``/api/v1/cars/{id}`` (Get A Car By Id)
* <b>GET</b> ``/api/v1/cars/filter?page={page}&size={size}`` (Get some cars based on your params)
* <b>POST</b> ``/api/v1/cars`` (Create a new Car)
* <b>PUT</b> ``/api/v1/cars/{id}`` (Update a existing car by ID)
* <b>DELETE</b> ``/api/v1/cars/{id}`` (Delete a existing car by ID)
* POST/PUT Usage:
    ```json
    {
        "brand": "Car Brand",
        "model": "Car Model",
        "pilot": {
            "name": "Pilot Name",
            "age": 20
        },
        "year": "2014"
    }
    ```
##### Races Microservice (ms-races)
* <b>POST</b> ``/api/v1/races`` (Simulates a new Race, but must have a track on it. The chosen cars are random)
* POST Usage:
    ```json
    {
      "name":"Track",
      "country":"Brazil",
      "date":"2020-10-12"
    }
    ```
#### History Microservice (ms-history)
* <b>GET</b> ``/api/v1/history`` (Get All Race History)
* <b>GET</b> ``/api/v1/history/{id}`` (Get a Race history from ID)

In case of issues, please an email to <a href="mailto:deyvidsantosdasilva2002@gmail.com">my email</a>.
