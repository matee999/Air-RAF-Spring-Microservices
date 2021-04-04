[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

RAF-Airlines
============

Spring Boot application built with microservice architecture with Zuul as an API gateway and Eureka for service discovery.

The system consists of 3 main services. These services include the User service for authentication and authorization. 
Flight service that is responsible for management and administration of flights and airplanes. 
And Ticket service for buying and managing tickets.

---

## Features
- User and administrator accounts
- Asynchronous communication between services
- E-mail notifications
- Search and query functionality
- Service discovery
- JWT authentication
- User tiers with sale percentages

---

## Setup
Clone this repo to your desktop and do a Maven update on all projects to get and install all the dependencies.

MySQL database was used for this project but that can be configured in application.properties file.

Apache ActiveMQ Artemis was used as a message broker: https://activemq.apache.org/components/artemis/

---

## Usage
Once the dependencies are installed, database is setup and ActiveMQ is running, you can start all the services.

---

## Notes
This application was built in parallel with https://github.com/Qwerasdzxc/RAF-Airlines-Mobile-application and https://github.com/Qwerasdzxc/RAF-Airlines-Administrator-Portal

Enjoy!
