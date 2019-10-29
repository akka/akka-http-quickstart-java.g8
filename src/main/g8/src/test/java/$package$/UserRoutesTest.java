package $package$;


//#test-top
import akka.actor.typed.ActorRef;
import akka.http.javadsl.model.*;
import akka.http.javadsl.testkit.JUnitRouteTest;
import akka.http.javadsl.testkit.TestRoute;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.StatusCodes;
import akka.actor.testkit.typed.javadsl.TestKitJunitResource;


//#set-up
public class UserRoutesTest extends JUnitRouteTest {

    @ClassRule
    public static TestKitJunitResource testkit = new TestKitJunitResource();

    //#test-top
    private static TestRoute appRoute;


    @Before
    public static void initClass() {
        ActorRef<UserRegistry.Command> userRegistry = testkit.spawn(UserRegistry.create(), "UserRegistry");
        UserRoutes userRoutes = new UserRoutes(testkit.system(), userRegistry);
        appRoute = testRoute(userRoutes.userRoutes());
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
