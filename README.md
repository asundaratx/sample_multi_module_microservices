This is a multi-module Spring Boot project that
contains 2 REST microservices for users and accounts that 
can be accessed via a third microservice API-Gateway. It also 
contains a eurekaserver and a config-server. All these 
microservices are configured to work with one another.

This will be updated to also include a Zipkin Server for tracing,
as well as Vault for secrets.

The final application architecture should resemble:
![this diagram](https://raw.githubusercontent.com/asundaratx/sample_multi_module_microservices/master/blob/image.jpg)
