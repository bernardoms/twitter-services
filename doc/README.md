# Twitter-services

## Dashboard
![DashBoardWithThroughput](dashboard_with_throughput_and_response_time.png)

Using new relic in the app is possible to see a vision like the picture with the response time of the api, and the apdex(measure of the satisfaction from a configured response time).
It's possible to see the error rate and the throughput measured in RPM(request per minutes).


![DashBoardWithErrorRate](dashboard_with_error_rate.png)

With new relic is possible to see a detailed vision of error rate and the log to understand more about the error.

![DashBoardContainerInfo](dashboard_container_info.png)

Its also possible to see check info about how many instancies are running and how many resources are used.

##Logs 

![LogWithLevelInfo](logs_with_level_info.png)

In the twitter services apis is also possible to see the logs of the apis using Kibana. In this picture there is a picture
of a filter showing only logs with level info.

![LogWithLevelInfo](logs_with_level_error.png)

In this picture the filter is with log level error.

![LogArchitecture](log_architecture.png)

Log architecture used on this project. 

Filebeat gets the logs from the docker(only the containers with a specific label) 
and send to logstash. Logstash will filter and transform then and send to elasticsearch. Once logs are on elasticsearch its possible
to filter then using Kibana.


