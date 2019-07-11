FROM openjdk:8-jre-alpine
RUN mkdir /app
COPY ./target/*.jar /app/demo.jar
WORKDIR /app
EXPOSE 8080
CMD ["java","-jar","demo.jar"]