# QuizQuest- A Distributed QuizApp

This is a distributed quiz game application that utilizes Kafka, Springboot, Maven and Docker.

## Prerequisites

Make sure you have the following tools installed on your machine:

- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

## Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/quiz-game-distributed.git

2. Navigate to the project directory:

    ```bash
    cd quiz-game-distributed

## Maven Package
Package each service using:
    
    ```bash
    mvn clean pacakage

## Docker Compose
Start the application with Docker Compose:

    ```bash
    docker-compose up -d

This command will start Kafka, the backend services, and the frontend.

Wait for at least a minute to allow Kafka to set up and the services to connect.

## Accessing the Application

- The frontend can be accessed at [http://localhost:3000](http://localhost:3000).
- The backend (master service) can be accessed at [http://localhost:8080](http://localhost:8080).

## Stopping the Application

To stop the application, run:

```bash
docker-compose down
