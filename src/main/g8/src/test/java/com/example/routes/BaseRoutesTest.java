package com.example.routes;

import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import org.junit.Before;
import org.junit.Test;

public class BaseRoutesTest extends JUnitRouteTest {

  private TestRoute baseRoutes;

  @Before
  public void initClass() {
    baseRoutes = testRoute(BaseRoutes.baseRoutes());
  }

  @Test
  public void testGetTopSlash() {
    baseRoutes.run(HttpRequest.GET("/"))
      .assertStatusCode(StatusCodes.OK)
      .assertEntity("Server up and running");
  }

  @Test
  public void testPostTopSlash() {
    baseRoutes.run(HttpRequest.POST("/"))
      .assertStatusCode(StatusCodes.OK)
      .assertEntity("Server up and running");
  }
}
