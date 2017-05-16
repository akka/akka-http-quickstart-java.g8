package com.example.routes;

import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import org.junit.Before;
import org.junit.Test;

public class SimpleRoutesTest extends JUnitRouteTest {

  private TestRoute simpleRoutes;

  @Before
  public void initClass() {
    SimpleRoutes simpleRoutes = new SimpleRoutes();
    this.simpleRoutes = testRoute(simpleRoutes.simpleRoutes());
  }

  @Test
  public void testHello() {
    simpleRoutes.run(HttpRequest.GET("/hello"))
      .assertStatusCode(StatusCodes.OK)
      .assertEntity("<html><body><h1>Say hello to akka-http</h1></body></html>");
  }

  @Test
  public void testHandleOnlyGET() {
    simpleRoutes.run(HttpRequest.POST("/hello"))
      .assertStatusCode(StatusCodes.METHOD_NOT_ALLOWED);
  }

}
