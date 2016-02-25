# myRetail RESTful service
This is the code written for myRetail service assignment. The service is deployed in Openshift cloud. 

# Frameworks
1. JDK 8
2. Maven 3.x
3. Spring Boot (REST, HATEOS, data, actuator)
4. Swagger 2.0
5. slf4j and logback

#Usage Instructions
  myRetail service end points:
  1. http://myretailapi-parepu.rhcloud.com/v1/product GET
  2. http://myretailapi-parepu.rhcloud.com:80/v1/product/{productId} GET, PUT

  Swagger 2 Documentation:
    http://myretailapi-parepu.rhcloud.com/v2/api-docs?group=product
  
  Spring Boot actuator resource:
    http://myretailapi-parepu.rhcloud.com/actuator



#TODO:
1. Add unit and integration tests
2. Add caching and better profiling
3. Add Netflix OSS features
4. Add Security
