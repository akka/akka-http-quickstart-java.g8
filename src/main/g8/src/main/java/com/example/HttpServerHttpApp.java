package com.example;

import akka.http.javadsl.server.HttpApp;
import akka.http.javadsl.server.Route;

/**
 * Server will be started calling `WebServerHttpApp.startServer("localhost", 8080)`
 * and it will be shutdown after pressing return.
 */
class HttpServerHttpApp extends HttpApp {

  /**
   * Routes that this WebServer must handle are defined here
   */
  @Override
  protected Route route() {
    return route(
      pathEndOrSingleSlash(() -> // Listens to the top `/`
        complete("Server up and running") // Completes with some text
      ),
      path("hello", () -> // Listens to paths that are exactly `/hello`
        get(() -> // Listens only to GET requests
          complete("<html><body><h1>Say hello to akka-http</h1></body></html>") // Completes with some html
        )
      ));
  }

  public static void main(String[] args) throws Exception {
    final HttpServerHttpApp myServer = new HttpServerHttpApp();
    // This will start the server until the return key is pressed
    myServer.startServer("localhost", 8080);
  }
}
