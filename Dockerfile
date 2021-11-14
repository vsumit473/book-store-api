FROM openjdk:8
EXPOSE 8080
ADD target/smart-dubai-book.jar smart-dubai-book.jar
ENTRYPOINT ["java","-jar","/smart-dubai-book.jar"]