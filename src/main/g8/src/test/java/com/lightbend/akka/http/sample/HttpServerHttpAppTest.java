package com.lightbend.akka.http.sample;


//#test-top
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import org.junit.Before;
import org.junit.Test;
import akka.http.javadsl.marshallers.jackson.Jackson;

//#set-up
public class HttpServerHttpAppTest extends JUnitRouteTest {
  //#test-top
  private TestRoute appRoute;
  //#set-up
  @Before
  public void initClass() {
    QuickstartServer server = new QuickstartServer();
    appRoute = testRoute(server.createRoute());
  }

  //#actual-test
  @Test
  public void testNoUsers() {
    appRoute.run(HttpRequest.GET("/users"))
      .assertStatusCode(StatusCodes.OK)
            .assertMediaType("application/json")
      .assertEntity("{\"users\":[]}");
  }
  //#actual-test
  //#testing-post
  @Test
  public void testHandlePOST() {
    User user = new User("Kapi", 42, "jp")
    appRoute.run(HttpRequest.POST("/users").withEntity(Jackson.<User>marshaller(user)))
      .assertStatusCode(StatusCodes.CREATED)
    .assertMediType("application/json")
    .assertEntity("{\"description\":\"User Kapi created.\"}");
  }
  //#testing-post

  @Test
  public void testRemove() {
    appRoute.run(HttpRequest.DELETE("/users/Kapi"))
      .assertStatusCode(StatusCodes.OK)
      .assertEntity("{\"description\":\"User Kapi deleted.\"}")
            .assertMediType("application/json")

  }
  //#set-up
}
//#set-up
