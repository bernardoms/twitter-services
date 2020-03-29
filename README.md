# twitter-services

## Runing local with docker-compose

  To run the services open docker-compose.yml and then put the twitter keys into twitter-extractor service.
  For monitoring the services, put a new relic license on each service.
  After inserting the twitter api keys, just give a docker-compose build to build the images,
  and then up -d command and wait for the containers to comming up.

  For check the logs on Kibana just go to endpoint:
  http://localhost:5601/
  and create a index for the logs. Ex: filebeat*


