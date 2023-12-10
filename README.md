# Getting Started
This is a Global Logic Challenge

### What you’ll need
+ A favorite text editor or IDE
+ JDK 8
+ Install Gradle 7.4

## Project Configurations

1. Project repository:

```
    git clone https://github.com/eilincastillo/global-logic-challenge.git
    cd global-logic-challenge
```

2. Build Gradle:

 ```
    ./gradlew build
```

## Run project

```
    ./gradlew bootRun
```
### Endpoints

To register a new user and generate a jwt:

```
    curl --location 'http://localhost:8080/sign-up' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=403412DB1BFE302430879B2732DAB1DF' \
--data-raw '{
    "name": "nombre",
    "email": "g3@gmailcom.co",
    "password": "Asrkk11daa",
    "phones": [
        {
            "number": 45188232,
            "citycode": 9,
            "countrycode": 56
        }
    ]
}'
```

To search the user information by his jwt

```
curl --location --request POST 'http://localhost:8080/login' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnM0BnbWFpbGNvbS5jbyIsImV4cCI6MTcwMjMxODY4MH0.PR6j4FK3G-uKfZqXq8gPEgv_ipyR5dEe1uK3szqW0g9Sq6uCRWoBiq33IIC18p_X5PwNyBMRk-8HZ9LQJGq_iw' \
--header 'Cookie: JSESSIONID=98A8591036452F85764703DDD173145D'
```


