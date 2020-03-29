# twitter-summarize

API used to get all tweets and them summarize from some criterias.
The criterias used right are:
- Idiom of a text from a hashtag
- By Followers
- By Date

## Runing local with docker

in this folder use mvn clean package to build the app and then use docker build -t twitter-summarize to build the image and docker run to run.

## Runing Jar

Use mvn clean package tu build the project and then java -jar inside the target folder twitter-summarize-0.0.1-SNAPSHOT.jar.

## API Documantion
it's possible to see the endpoints and example after run the project and acess that endpoint:
http://localhost:8081/swagger-ui.html

This apis runs a service to summary all new hashtags when receives a new message from sqs. however it's possible to force a summaryzing using an endpoint described in the swagger endpoint.
