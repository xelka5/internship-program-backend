FROM maven:3.6.3-jdk-11 as build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
COPY lombok.config /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:11
COPY --from=build /usr/src/app/target/internship-application.jar /usr/app/internship-application.jar 
EXPOSE 8080  
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod","-jar","/usr/app/internship-application.jar"] 