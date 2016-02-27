# pictureserver
A HTTP Picture Server

## Run
````
$ mvn jetty:run
````

or without logging:

````
$ mvn jetty:run -P without-logging
````

## Use

````
$ curl -X POST localhost:8080/ --data-binary @picture.png
````
## Benchmark
````
$ ab -p picture.png -n 20000 http://localhost:8080/ 
````
handled ~1300 requests per second.
