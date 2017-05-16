package com.example;

import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import org.junit.Before;
import org.junit.Test;

public class HttpServerHttpAppTest extends JUnitRouteTest {

  private TestRoute appRoute;

  @Before
  public void initClass() {
    HttpServerHttpApp server = new HttpServerHttpApp();
    appRoute = testRoute(server.route());
  }

  @Test
  public void testHello() {
    appRoute.run(HttpRequest.GET("/hello"))
      .assertStatusCode(StatusCodes.OK)
      .assertEntity("<html><body><h1>Say hello to akka-http</h1></body></html>");
  }

  @Test
  public void testHandleOnlyGET() {
    appRoute.run(HttpRequest.POST("/hello"))
      .assertStatusCode(StatusCodes.METHOD_NOT_ALLOWED);
  }

  @Test
  public void testGetTopSlash() {
    appRoute.run(HttpRequest.GET("/"))
      .assertStatusCode(StatusCodes.OK)
      .assertEntity("Server up and running");
  }

  @Test
  public void testPostTopSlash() {
    appRoute.run(HttpRequest.POST("/"))
      .assertStatusCode(StatusCodes.OK)
      .assertEntity("Server up and running");
  }

}
