package com.example.routes;

import akka.http.javadsl.server.Route;

import static akka.http.javadsl.server.Directives.path;
import static akka.http.javadsl.server.Directives.get;
import static akka.http.javadsl.server.Directives.complete;

/**
 * Routes can be defined in separated classes like shown in here
 */
public class SimpleRoutes {

  /**
   * This method creates one route (of possibly many more that will be part of your Web App)
   */
  public Route simpleRoutes() {
    return path("hello", () -> // Listens to paths that are exactly `/hello`
      get(() -> // Listens only to GET requests
        complete("<html><body><h1>Say hello to akka-http</h1></body></html>"))); // Completes with some html page
  }
}