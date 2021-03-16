ARG BASE_IMAGE=openjdk:15.0.2-slim
FROM ${BASE_IMAGE}

COPY build/libs/*-all.jar ./app.jar

EXPOSE 8080
CMD java ${JAVA_OPTS} -jar app.jar
