# the first stage of our build will use a maven 3.8.6 parent image
FROM maven:3.8.6-jdk-11 AS MAVEN_BUILD

WORKDIR /build

# copy the pom and src code to the container
COPY ./ /build

# package our application code
RUN mvn clean package

# the second stage of our build will use open jdk 11
FROM openjdk:11

# copy only the artifacts we need from the first stage and discard the rest
COPY --from=MAVEN_BUILD /build/target/*.jar /courrier.jar

# port expose
EXPOSE 8080

# set the startup command to execute the jar
CMD ["java", "-jar", "/courrier.jar"]