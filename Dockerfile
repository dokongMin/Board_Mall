FROM openjdk:11
COPY /build/libs/*.jar /build/libs/app.jar
ENTRYPOINT ["java","-jar","app.jar"]
#ARG JAR_FILE=*.jar
#COPY ${JAR_FILE} app.jar
#COPY build/libs/*.jar /build/libs/*.jar
#COPY /build/libs/board-0.0.1-SNAPSHOT.jar /build/libs/board-0.0.1-SNAPSHOT.jar
# ./ 경로로도 가능할 듯. -> 전체를 복사하고 dockerignore 를 통해 제외하도록 함.
#ENTRYPOINT ["java","-jar","/build/libs/board-0.0.1-SNAPSHOT.jar"]
#CMD ["./gradlew", "clean", "build"]


#ARG JAR_FILE_PATH=build/libs/scheduling-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE_PATH} app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]