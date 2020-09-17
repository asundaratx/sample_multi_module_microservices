This is a multi-module Spring Boot project that
contains 2 REST microservices for users and accounts that 
can be accessed via a third microservice API-Gateway. It also 
contains a eurekaserver and a config-server. Zipkin server,
Zookeeper, and Kafka need to be started with docker, as well as
scripts run to create topics. 
The
microservices are configured to work with one another with Feign.
Default users and accounts are created on startup and
the gateway is configured to use jjwt to create a token and
add it to response header on login. On other requests, the gateway
validates this token prior to allowing the request to go through.

This will be updated to also include Vault for secrets.

The final application architecture should resemble:
![this diagram](https://raw.githubusercontent.com/asundaratx/sample_multi_module_microservices/master/blob/image.jpg)
