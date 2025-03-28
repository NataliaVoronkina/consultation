# HeliosX Consultation Service

This project simulates a medical consultation system that allows customers to start consultations,
receive and answer medical questions, and receive a treatment decision.

---

## Features

- Create and store customer profiles.
- Start a consultation using a unique customer ID.
- Fetch a list of consultation questions.
- Submit answers to questions.
- Get a treatment decision based on answers.

---

## Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring MVC**
- **Hibernate / JPA**
- **H2 (In-Memory Database)**
- **Lombok**
- **JUnit 5**
- **Jackson** (for JSON serialization)

---

## Installation & Setup

### Clone the Repository

```sh
git clone https://github.com/NataliaVoronkina/consultation.git
```

## Getting Started

- Build  `./gradlew clean build`
- Run it `./gradlew bootRun`
- The Consultation Service will start on port 8080 by default `localhost:8080`

## Usage

1. Create a Customer

```sh
curl -X POST http://localhost:8080/api/v1/customer/create/{customerUid}
```

2. Start Consultation

```sh
curl -X POST http://localhost:8080/api/v1/consultation/create \
  -H "Content-Type: application/json" \
  -d '"customerUid"'
```

3. Get Questions

```sh
curl -X GET http://localhost:8080/api/v1/consultation/{consultationUid}/questions
```

4. Submit Answers

```sh
curl -X POST http://localhost:8080/api/v1/consultation/{consultationUid}/answers \
  -H "Content-Type: application/json" \
  -d '[{
    "consultationUid": "fill consultationUid here",
    "questionId": 1,
    "answerText": "Yes"
  }]'
```

5. Get Decision

```sh
curl -X GET http://localhost:8080/api/v1/consultation/decision/{consultationUid}
```

## Running Tests

`./gradlew test`

## Future Improvements

1. An example of possible system design of the MedExpress application for Principality of Genovia can be found in the
   file System design.png
   The good idea is to use microservice architecture and event driven design for better scalability and reliability of
   the system.
2. Introduce multiple consultation types for different conditions, each with its own predefined set of questions.
3. Support multiple answer types for questions, including multiple choice selections and free text input.
4. Implement a Doctor Service to simulate more realistic consultation decision logic based on patient answers and
   condition specific criteria.
