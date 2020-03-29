# twitter-info
Use to show tweets group by some criterias. 
Currently it's possible two recive three criterias to get group info:
- By Date
- By language from hashtag text
- By followers.

## Runing local with docker

in this folder use mvn clean package to build the app and then use docker build -t twitter-info . to build the image and docker run to run.

## Runing Jar

Use mvn clean package tu build the project and then java -jar inside the target folder twitter-info-0.0.1-SNAPSHOT.jar.

## API Documantion
it's possible to see the endpoints and example after run the project and acess that endpoint:
http://localhost:8082/swagger-ui.html

there is 3 path params currently supported.

- ByDate
- ByFollowers
- ByHashTagAndLanguage
