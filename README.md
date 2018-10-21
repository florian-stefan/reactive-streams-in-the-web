# Reactive Streams in the Web

This repository contains the example applications for the talk "Reactive Streams in the Web". The corresponding slides
can be found [here](https://www.slideshare.net/florianstefan/reactive-streams-in-the-web). The example applications are
all based on the Spring framework and implemented as Maven modules. The Maven module `widget-server` contains a simple
HTTP server that serves widgets after a given delay. The other four Maven modules contain each a different example for
building an application that serves a web page that consists of multiple widgets. All example applications can be run
as Docker containers by executing the command `docker-compose up -d` in the root folder. After building and starting
the containers using Docker Compose, the example applications can be accessed with the following links:

* http://localhost:8080/widgets (example using server-side rendering)
* http://localhost:8081/widgets (example using AJAX requests)
* https://localhost:8443/widgets (example using AJAX requests and HTTP/2)
* http://localhost:8082/widgets (example using progressive HTML rendering)
