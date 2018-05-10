package com.lightbend.akka.http.sample;


//#test-top

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import akka.http.javadsl.model.*;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import org.junit.Before;
import org.junit.Test;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.StatusCodes;


//#set-up
public class UserRoutesTest extends JUnitRouteTest {
    //#test-top
    private TestRoute appRoute;


    @Before
    public void initClass() {
        ActorSystem system = ActorSystem.create("helloAkkaHttpServer");
        ActorRef userRegistryActor = system.actorOf(UserRegistryActor.props(), "userRegistryActor");
        QuickstartServer server = new QuickstartServer(system, userRegistryActor);
        appRoute = testRoute(server.createRoute());
    }
    //#set-up
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
        appRoute.run(HttpRequest.POST("/users")
                .withEntity(MediaTypes.APPLICATION_JSON.toContentType(),
                        "{\"name\": \"Kapi\", \"age\": 42, \"countryOfResidence\": \"jp\"}"))
                .assertStatusCode(StatusCodes.CREATED)
                .assertMediaType("application/json")
                .assertEntity("{\"description\":\"User Kapi created.\"}");
    }
    //#testing-post

    @Test
    public void testRemove() {
        appRoute.run(HttpRequest.DELETE("/users/Kapi"))
                .assertStatusCode(StatusCodes.OK)
                .assertMediaType("application/json")
                .assertEntity("{\"description\":\"User Kapi deleted.\"}");

    }
    //#set-up
}
//#set-up
