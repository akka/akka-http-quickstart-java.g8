package com.example.routes;

import akka.http.javadsl.server.Route;

import static akka.http.javadsl.server.Directives.complete;
import static akka.http.javadsl.server.Directives.pathEndOrSingleSlash;

/**
 * Routes can be defined in separated classes like shown in here
 */
public class BaseRoutes {

  /**
   * This route is the one that listens to the top level '/'. It can be a static method
   */
  public static Route baseRoutes() {
    return pathEndOrSingleSlash(() -> // Listens to the top `/`
      complete("Server up and running")); // Completes with some text
  }
}
