FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar /app2.jar
COPY target/lib /app2/lib
ENTRYPOINT ["java","-cp","app2.jar:app2/lib/*","pe.com.empresa.poc.azure.securityms.Application"]
