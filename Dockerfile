FROM openjdk:15
EXPOSE 9090
ADD target/spring-boot-quiz.jar spring-boot-quiz.jar
ENTRYPOINT ["java", "-jar","/spring-boot-quiz.jar"]
