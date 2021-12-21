# People_Management
Rest API to management of people and your phones

## **Requirements:**
- Spring Boot
- Java 8
- Maven
- JPA
- Hibernate
- Lombok
- JUnit
- Mockito
- Rest Assured
- Swagger
- Heroku

---
## **How to access?**

- Swagger:
  - https://peopleapi-h.herokuapp.com/swagger-ui/index.html
  ##
- API:
  - https://peopleapi-h.herokuapp.com/v1/person

---
## **Example of insertion (POST):**
```json
{
    "firstName": "Name",
    "lastName": "Last",
    "cpf": "70632820772",
    "birthDate": "01-01-1980",
    "phones":[
        {
            "type": "COMMERCIAL",
            "number": "22462348"
        },
        {
            "type": "HOME",
            "number": "25354736"
        },
        {
            "type": "MOBILE",
            "number": "999364512"
        }
    ]
}
```
---

## **And also:**

_Unit testing of Repository, DTO, Service and Controller Layers_

_Development profile_

_H2 database_

_Handled and customized exceptions_

_Validation fields and returned messages (including CPF)_
