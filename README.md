# java-explore-with-me

## What is this project intended for
Explore With Me - this application gives you the opportunity to share information about interesting events and helps you find a company to participate in them. It is implemented in the form of two microservices with separate databases - one for saving and receiving statistics, the second with the main business logic.

## Instructions for deploying the project:
1. Download this repository
2. mvn clean
3. mvn package
4. docker-compose build
5. docker-compose up -d

### The application includes services:
- Main service — contains everything necessary for the operation of the product.
  - Viewing events without authorization;
  - Ability to create and manage categories;
  - Events and working with them - creation, moderation;
  - User requests to participate in the event - request, confirmation, rejection;
  - Creating and managing collections;
- Statistics service — stores the number of views and allows you to make various selections to analyze the operation of the application.

## Description of services
### The main service works on port 8080
The API of the main service is divided into three parts. The first one is public, available without registration to any network user. The second one is closed, available only to authorized users. The third one is administrative, for service administrators.

- **Public** (available to all users)
- API for working with events
- API for working with categories
- API for working with collections of events
- **Private** (available only for registered users)
- API for working with events
- API for working with requests from the current user to participate in events
- **Administrative** (available only for the project administrator)
- API for working with events
- API for working with categories
- API for working with users
  - API for working with collections of events
### The statistics service runs on port 9090:
Collects information. Firstly, about the number of user requests to the event lists and, secondly, about the number of requests for detailed information about the event. Based on this information, statistics about the operation of the application are generated.
- **Administrative** (available only for the project administrator)
  - API for working with session statistics
## swagger REST API Specification
- [Main service](https://raw.githubusercontent.com/Nuuvik/java-explore-with-me/main/ewm-main-service-spec.json)
- [Statistics service](https://raw.githubusercontent.com/Nuuvik/java-explore-with-me/main/ewm-stats-service-spec.json)