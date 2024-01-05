# QuizQuest- A Distributed QuizApp

This is a distributed quiz game application that utilizes Kafka, Springboot, Maven and Docker.
## Instructions on how to compile and run your code
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
```


## A brief summary of what your code does

- quizService: SpringBoot application that creates API endpoints that can be used to create a quizMaster and a quiz and persist the same in the DB.
- roomService: SpringBoot application that creates a unique Room, keeps players joined linked to the room and closes the room once the game ends.
- playerService: SpringBoot application that connects a player to a room, stores player and the player's score in the DB.
- gameService: SpringBoot application that fetches the questions for a started quiz and sends each question one by one after a time interval to a websocket connected to the UI.
- scoreService: SpringBoot application that has endpoints to send score details which will create a gameEvent to update scores that will be published to a kafka topic. The listener of the topic will update the score for the player in the DB. 

## Video and Report

- The demo video has been attached with the submission. The file name is QuizQuestDemoVideo.mp4
- The report has been attached with the submission. File name is The_QuizQuest_Crew.pdf




