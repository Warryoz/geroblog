#The image is base this dockerfile. dockerfile is use to build an image that is the container it self
FROM eclipse-temurin:17

#Metadata the maintainer name
LABEL mentainer="masafesio.10@gmail.com"

# The directory is going to be created when the container is running
WORKDIR /app

#The things we want to copy from the project to the container
# The jar from that target folder                           to this directory
                                                            # we can rename the things we copy
COPY target/springboot-blog-rest-api-0.0.1-SNAPSHOT.jar /app/springboot-docker-demo.jar

#To run a command in the container
ENTRYPOINT ["java", "-jar", "springboot-docker-demo.jar"]