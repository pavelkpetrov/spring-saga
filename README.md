#Spring Demo to show saga pattern, feign client, elastik log, circuit braker pattern

Eureka server url : http://localhost:8761/
Gateway service zuul gateway example with filter with successfull saga commit:  http://localhost:8080/order-service/transactional/doOrder
Gateway service zuul gateway example with filter with failed saga commit:  http://localhost:8080/order-service/transactional/doOrderFailed
Order service and agregator pattern:  http://localhost:8081
Stock service :  http://localhost:8082
Rabbit MQ server http://localhost:15672
user guest
password guest
Kibana dashboard http://localhost:5601
in the management tab create new filter as logstash-*

How to test:
Run docker compose:
cd elk
docker-compose up -d

run Eureka service         
   - mvn spring-boot:run -pl eureka-service
run Zuul gateway service            
   - mvn spring-boot:run -pl gateway-service
run order service     
   -  mvn spring-boot:run -pl order-service
run stock service     
   -  mvn spring-boot:run -pl stock-service
 