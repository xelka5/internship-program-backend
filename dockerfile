FROM maven:3.6.3-jdk-11 as build
LABEL maintainer="Internship Program"
COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app  
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:11
LABEL maintainer="Internship Program"
COPY --from=build /usr/src/app/target/internship-application.jar /usr/app/internship-application.jar 
EXPOSE 8080  
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=prod","-jar","/usr/app/internship-application.jar"] 