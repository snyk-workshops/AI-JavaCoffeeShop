# Hint 3

Something like this:

```
FROM maven:3-openjdk-11 as build
RUN mkdir /usr/src/project
COPY . /usr/src/project
WORKDIR /usr/src/project
RUN mvn package -DskipTests

FROM eclipse-temurin:11-jre
COPY --from=build /usr/src/project/target/JavaCoffeeShop.jar /project/
WORKDIR /project
ENTRYPOINT java -jar JavaCoffeeShop.jar
```

![](burp.png)
