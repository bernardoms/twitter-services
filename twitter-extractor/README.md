# twitter-extract
API used for given a hashtag, get all latest tweets using this hashtag and save in a database.

## Runing local with docker

in this folder use mvn clean package to build the app and then use docker build -t twitter-extractor to build the image and docker run to run.

## Runing Jar

Use mvn clean package tu build the project and then java -jar inside the target folder twitter-extractor-0.0.1-SNAPSHOT.jar.

## API Documantion
it's possible to see the endpoints and example after run the project and acess that endpoint:
http://localhost:8080/swagger-ui.html
