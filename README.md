# YAUS (Yet Another URL Shortener) Challenge

Basic implementation of main use cases

## How to run without gradle run task

Write into the command line from root project folder

```sh
$ clear && ./gradlew clean build
```

This command packages our application into an executable jar file com.challenge.yaus-<version>.jar that we can running with

```sh
$ clear && java -jar build/libs/com.challenge.yaus-0.0.1-SNAPSHOT.jar
```

and then, obviously, [request it from your browser](http://localhost:8080/)

## Note

Using:

- Java 8, Spring Boot, JUnit ...
- Gradle 2.7
- MVC, AJAX