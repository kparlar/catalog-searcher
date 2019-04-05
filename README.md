# catalog-searcher
Dummy Catalog Searcher Project For Spring Boot Application

This is an example project for implementing a given use cases.
I have given a name Catalog Searcher, as it search for artist names from Itunes library and author names from google books library.

This project aims is using the Netflix Hystrix Curcuit Breaker Pattern to make the microservices resillient in response time manner. The Circuit Breaker is handled in each service end-points and set to maximum 1 second response time. So If the third party services could not respond back in 1 second, this pattern breaks the connection and returns an empty response. 

Catalog-Searcher is designed in a way that according to the search-term , it responses back to desired output. If you are searching album, then album records are gathered from Itunes API. In the Itunes Search API pages, service has been called with limited output by predefined value which has been defined in Catalog-Searcher API. Later on according to the response, Catalog-Searcher API orders the result, according to title and also limits the result with given predefined configured limit size which is default 5.

If you are searching books, then book records are gathered from Google Book Volume API. In this api, max-result is 40 but Catalog-Searcher limits the result according to the predefined parameter. There is also sorting available in Google API, like "newest" or  "relevance", but service is not called with this parameter and let Google returns according to its sorting mechanism. According to the response Catalog-Searcher orders them according to book title.

Also there is one more search available, it is search for all. This search goes to both services independently, and gather results and order them according to title, and return maximum 5 books and maximum 5 albums, totally 10 result. As here the aim is returning in 1 second from each services independently. So the challenging part here is calling these services with threshold of 1 second response time. Each has 1 second circuit break definition, so if they hit 1 second, they return empty list. With java.util.concurrent.Future api, Catalog-Searcher calls these service in asynchronous way, and waits their responses. In Each Service Hystrix command api is used, 
com.netflix.hystrix.contrib.javanica.command.AsyncResult api. With this api, response will be returned in Future api. So after calling these two 
services independently, wait for each reply, and gather their data in one result set and order according to their title.

Have fun.

# Tech Stack Card

## Back-End
* Java Spring Boot 1.5.9.RELEASE
* Netflix Hystrix for Curcuit Breaker Pattern : hystrix-javanica, spring-cloud-services-starter-circuit-breaker
* Netflix Hystrix Dashboard used to display metrics: spring-cloud-starter-hystrix-dashboard
* Metrics Spring for Timed Metrics : metrics-spring
* Unit-Test:  Junit 4
* Swagger for API documentation
* Itunes end-point for artist search : https://itunes.apple.com/search
* Google Books end-point for author search: https://www.googleapis.com/books/v1/volumes
* Performance-Test : JMeter 3.2

## Api End Points
Swagger annotation is used in this project.
You can check it from this link, http://localhost:8011/swagger-ui.html#/
Also here is the screenshot for swagger. There are also additional end-points added by additional libraries i use for metrics.
![Swagger](img/swagger_1.PNG?raw=true "Swagger All Api")
![Swagger](img/swagger_2.PNG?raw=true "Swagger Search Api for Books and Albums")

## Postman Collections 
Postman Collections are in the postman folder. There is a screenshot below.

#### Postman All Search
![Postman All Search](img/postman-search-all.PNG?raw=true "Postman All Search")

#### Postman Album Search
![Postman Album Search](img/postman-search-album.PNG?raw=true "Postman Album Search")

#### Postman Book Search
![Postman Book Search](img/postman-search-book.PNG?raw=true "Postman Book Search")

## How To Run
After git clone, you can directly run from your ide as any spring-boot project. When the application has been started
you can use the postman request to get data.

If you want to run jar, first you have to clone the project from repository, (please don't forget to checkout develop branch, cause this code has not been merged to master branch yet.) inside the catalog-searcher folder  go to services\service-catalog-searcher folder. Inside service-catalog-searcher folder run first
**mvn package** (run this under service-catalog-searcher folder)
this will build a jar called **catalog-searcher-0.0.1-SNAPSHOT.jar** under target folder. 
Than go to target folder and you can run the application with  **java -jar catalog-searcher-0.0.1-SNAPSHOT.jar** command.

when the service is on, it will host on port 8011.
For API calls please take a look to Postman Section
 
 ## Code Quality
 There is no issues left to resolve.
 
 #### Sonarlint
 ![SONAR](img/sonarlint.PNG?raw=true "SONAR")
 
 ## About The Application ( Metrics - Tunning - Performance)
 
 At this topic, there will be metrics of this application. You can easily reach the app metrics from this 
 url. 
 http://localhost:8011/metrics.
 I have defined a kp-search-service name metric with @Timed annotation using metrics-spring library here is the output of this metrics
 
 #### Timed Metrics Code
 ![Timed Metric](img/metric_timed.PNG?raw=true "Timed Metric")
 
 #### Timed Metrics
 ![Metrics](img/metrics.PNG?raw=true "Metrics")
 
 #### Health Check
 
 Health Check of this application will be reached from this url.
 
 ![Health Check](img/health_check.PNG?raw=true "Health Check")
 
 
 #### Hytrix Monitor
 Applications in complex distributed architectures have dozens of dependencies, each of which will inevitably fail at some point. If the host application is not isolated from these external failures, it risks being taken down with them.  
 Hystrix is designed to do the following:
 
 * Give protection from and control over latency and failure from dependencies accessed (typically over the network) via third-party client libraries.
 * Stop cascading failures in a complex distributed system.
 * Fail fast and rapidly recover.
 * Fallback and gracefully degrade when possible.
 * Enable near real-time monitoring, alerting, and operational control.
 
 So for SearchControl class i have added CircuitBreaker pattern for this kind of failure. To measure the number of succes , short-circuit, Bad-request, timeout , reject and failures, I have added Hytrix Dashboard to the api. This Dashboard will be seperated but it is added inside the project as this is a test project.
 After you run the catalog-searcher ( microservice app), open in browser http://localhost:8011/hystrix
 this will open the page given below
 
 
 ![Hystrix Dashboard](img/hystrix-dashboard-1.PNG?raw=true "Hystrix Dashboard")
 
 in the hostname:port text , write  http://localhost:8011/hystrix.stream
 
 ![Hystrix Dashboard](img/hystrix-dashboard-2.PNG?raw=true "Hystrix Dashboard")
 
 Now, lets looks at the dashboard and take some test on it.
 First start with All Search. 
 we have to call the api with these parameters.
 type=all 
 term=test
 
 so end point has to be look like this.
 http://localhost:8011/api/app/catalog-searcher/v1/search?type=all&term=test&
 
 I have ran this on Jmeter for Performance test to see the result. Here is the Performance test results. I have ran "all" search five times and now i am going to give its result.
 
 ## First Run 
 
 #### Search All - First Run - Hystrix Dashboard
 ![Search All - First Run - Hystrix Dashboard](img/hystrix-search-all-first-run.PNG?raw=true "Search All - First Run - Hystrix Dashboard")
 
 After a cold-start, Jmeter tests which is configured to call Catalog-Searcher API calling with search all parameter with a hundred times.
 
 In the dashboard it is clearly seen that there is 200 request, 100 for Google API, 100 for Itunes API. For Google API call, in getSearchGoogle image, there is 5 success, 82 timeout which is cause of 1 second break, and 13 failure, which will be cause of reject in Google side. And for these 100 request 41 successfully respond back with success in reliable fallback, here i expect this should be 82. 
 
 For Itunes API call, this api is much more successfull than Google, 32 success response and 68 timeout, and in fall back 25 success reliable fallback response. 
 
 Here the Request paramters for JMeter
 #### Search All - First Run - Jmeter - Parameters
 ![Search All - First Run - Jmeter - Parameters](img/jmeter-search-all.PNG?raw=true "Search All - First Run - Jmeter - Parameters")
  
 
 To check Jmeter according to these metrics, there is no error reported, so each 100 request successfully return response. Min response time is 755 ms, and max response time is 1343ms. Here the assignment metrics has to be 1 but what i did here is given the 1 threshold to each service calls, and not restrict the Catalog-Searcher API to return in one second, if i add a restriction to the whole service to return 1 second, each api return empty responses. But we can restrict.
 
 So going back to the Jmeter metric throughput for these 100 request is 49.7 in a second, which is fine, for cold start. 
 Here are the Jmeter reports of first Run.
 #### Search All - First Run - Jmeter - Aggregate Report
 ![Search All - First Run - Jmeter - Aggregate Report](img/hystrix-search-all-first-run-jmeter-aggregate-report.PNG?raw=true "Search All - First Run - Jmeter - Aggregate Report")
   
  #### Search All - First Run - Jmeter - Album and Book Response
 Also lets take a look at the responses too, there are some empty responses although service respond back with 200 OK, this is because of our architecture on Circuit Breaker pattern.
 But this response contains all data all and books together, order by title, and 5 data each.
 ![Search All - First Run - Jmeter - Album and Book Response](img/hystrix-search-all-first-run-data-all.PNG?raw=true "Search All - First Run - Jmeter - Album and Book Response")
 
 #### Search All - First Run - Jmeter - Book Response Only
 Also sometimes Itunes API responses have been broken so there is only book responses.
 ![Search All - First Run - Jmeter - Book Response Only](img/hystrix-search-all-first-run-data-book-only.PNG?raw=true "Search All - First Run - Jmeter - Book Response Only")
 
  #### Search All - First Run - Jmeter - Empty Response
  Also sometimes Google API and Itunes API responses have been broken so there is empty response return.
  ![Search All - First Run - Jmeter - Empty Response](img/hystrix-search-all-first-run-data-nothing.PNG?raw=true "Search All - First Run - Jmeter - Empty Response")
  
 ## Second Run
 
 #### Search All - Second Run - Hystrix Dashboard
   ![Search All - Second Run - Hystrix Dashboard](img/hystrix-search-all-second-run.PNG?raw=true "Search All - Second Run - Hystrix Dashboard")
   
   For second 100 request run, in the dashboard we have second 100 request for both of services.
   
   For Google side : 10 success results have been returned, 32 of 100 have been short-circuit, 21 timeout and 37 has returned as failure. And for Google Fallback , 90 of the fallbacks have been successfull return empty response, which was i expected.
   
   For Itunes side: 91 success results which is  really nice, 9 of 100 has been short-circuited and these have been returned as empty response by Fallback method. 
   
   For JMeter side
  #### Search All - Second Run - Jmeter - Aggregate Report
    
   Again no errors, but this time min response time is decrease to 4 miliseconds. This is amazing. And Max response time has not been increased it is also cool. Throughput has been increased a little 51.3 per second. So this is what i expect after cold-start, cause each of the classes have been already loaded to jvm, and this time Java run faster than before. Given below is the Jmeter metrics for second run.    
   ![Album - First Run - Jmeter - Aggregate Report](img/hystrix-search-all-second-run-jmeter-aggregate-report.PNG?raw=true "Album - First Run - Jmeter - Aggregate Report")
   
   So after second run, our service warmed up and response much more better than the first time. Lets look at the next one.
   ## Third Run
   
   #### Search All - Third Run - Hystrix Dashboard
   ![Search All - Third Run - Hystrix Dashboard](img/hystrix-search-all-third-run.PNG?raw=true "v")
     
   In this metrics, Google is not good but Itunes becomes much better. 98/100 success result
   Lets take a look to Jmeter metrics.
     
  #### Search All - Third Run - Jmeter - Aggregate Report
   Again no errors, min and max response times are same, so everything is perfect. There is something not well reported on Throughput value but i think this is Jmeter bug.
   
   ![Search All - Third Run - Jmeter - Aggregate Report](img/hystrix-search-all-third-run-jmeter-aggregate-report.PNG?raw=true "Search All - Third Run - Jmeter - Aggregate Report")
   ## Fifth Run (Last Run)   
   After fifth call, here are the metrics given below.
   ![Search All - Fifteenth Run - Dashboard](img/hystrix-search-all-fifth-run.PNG?raw=true "Search All - Fifteenth Run - Dashboard")
        
   Itunes API respond back with %100 success, but Google API respond back %100 failure. 
   So i suggest to increase the Google Response time circuit break configuration, as Google is not good at 1 second responses.
   
  
 
 ## Back-End UnitTest
 Completed 17 Unit Tests, and this covers %80 class coverage, %78 line coverage. This will be leverage but these tests will show how i handle test cases.
 The important classes are covered with test, there are some left for setting and getting variable methods. So these will be eliminated for now. 
 
 ![Back-End Unit Test Coverage](img/unit-test.PNG?raw=true "Unit Test Coverage")

 ## Performance-Tests with Jmeter
 I have added also some performance test under folder tests/jmeter. You can easily open this with Jmeter but you need some plugins added to your Jmeter configuration.
 Jmeter Details:
 Version: 3.2
 Plugins: 
 * Additional Plugins for Graphs Generator Listener
 * https://jmeter-plugins.org/?search=jpgc-cmd
 * https://jmeter-plugins.org/?search=jpgc-synthesis
 * https://jmeter-plugins.org/wiki/MergeResults/
 * https://jmeter-plugins.org/?search=jpgc-graphs-basic
 * https://jmeter-plugins.org/?search=jpgc-graphs-additional
 * https://jmeter-plugins.org/?search=jpgc-graphs-dist
 * https://jmeter-plugins.org/?search=jpgc-graphs-vs

I have added catalog-searcher-test.jmx under tests folder so you can run your own tests too. And 
 ## Run With Docker Images
 There is also docker images for both jmeter and spring-boot application you can easily run both with docker.
 Dockerfile in the first relative path runs the spring-boot application
 Dockerfile under the Jmeter folder runs the jmeter.  
 #### Docker - Catalog-Searcher
 This docker file contains only building the spring-boot jar and running it. Thats all
 #### Docker - Jmeter
 This docker files do these
 - installs required tool for images
 - set some environment variables
 - creates temp folders for copy required files
 - downloads the Jmeter 5.1
 - and run launcher.sh under jmeter folder \
You can easily find the outputs under jmeter/output folder. There will be two files created :
 - catalog-searcher-...-log file 
 - catalog-searcher-...-jtl file
 #### Hot To Run Docker
 First run build-docker.sh  and later  run this command
 > docker-compose up
 
   


