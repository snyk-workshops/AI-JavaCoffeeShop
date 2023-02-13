# Solution for SHIP IT Assignment

```
FROM maven:3-openjdk-11 as build
RUN mkdir /usr/src/project
COPY . /usr/src/project
WORKDIR /usr/src/project
RUN mvn package -DskipTests

FROM eclipse-temurin:11-jre
RUN mkdir /project
RUN groupadd -r brianvermeer && useradd -r -s /bin/false -g brianvermeer brianvermeer
COPY --from=build /usr/src/project/target/JavaCoffeeShop.jar /project/
RUN chown -R brianvermeer:brianvermeer /project
WORKDIR /project
USER brianvermeer
ENTRYPOINT java -jar JavaCoffeeShop.jar

```

or for alpine images

```
FROM maven:3-openjdk-11 as build
RUN mkdir /usr/src/project
COPY . /usr/src/project
WORKDIR /usr/src/project
RUN mvn package -DskipTests

FROM eclipse-temurin:11-jre-alpine
RUN mkdir /project
RUN addgroup --system brianvermeer && adduser -S -s /bin/false -G brianvermeer brianvermeer
COPY --from=build /usr/src/project/target/JavaCoffeeShop.jar /project/
RUN chown -R brianvermeer:brianvermeer /project
WORKDIR /project
USER brianvermeer
ENTRYPOINT java -jar JavaCoffeeShop.jar
```


