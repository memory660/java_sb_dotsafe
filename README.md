# TUTORIAL

POST http://localhost:8080/api/tutorials
```json
{
"title": "title1",
"description": "description1",
"level": "1"  
}
```

```json
{
"title": "title2",
"description": "description2",
"level": "2"
}
```

// mettre en base de données published = true, pour: title2  
// mettre en base de données created_at = 2022-04-10 10:09:10  

```
GET http://localhost:8080/api/tutorials/1
GET http://localhost:8080/api/tutorials/published
```

# DOTSAFE

```
GET http://localhost:8080/api/contributions
GET http://localhost:8080/api/users
GET http://localhost:8080/api/users/1/contributions
```

POST http://localhost:8080/api/auth/signup

```
{
"username": "user1",
"email": "user1@gmail.com",
"password": "22222222"
}
```

POST http://localhost:8080/api/auth/signin

```
{
    "username": "user1",
    "password": "22222222"
}
```

GET http://localhost:8080/api/users/1/contributions
```
Bearer ................................
```

# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.acme.api-tutorial' is invalid and this project uses 'com.acme.apitutorial' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.7/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.7/maven-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.6.7/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.7/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)



