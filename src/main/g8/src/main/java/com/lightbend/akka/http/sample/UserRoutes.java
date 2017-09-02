package com.lightbend.akka.http.sample;

import akka.http.javadsl.server.Route;

import akka.dispatch.*;
import akka.util.Timout;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import java.util.concurrent.CompletableFuture;
import static akka.http.javadsl.server.Directives.*;
import static akka.http.javadsl.unmarshalling.StringUnmarshallers.INTEGER;


/**
 * Routes can be defined in separated classes like shown in here
 */
//#user-routes-class
public class UserRoutes {
    //#user-routes-class

    LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);


    // other dependencies that UserRoutes use
        final ActorRef userRegistryActor;

    // Required by the `ask` (?) method below
    final Timeout timeout = new Timeout(Duration.parse("5 seconds")) // usually we'd obtain the timeout from the system's configuration

    /**
     * This method creates one route (of possibly many more that will be part of your Web App)
     */
    //#all-routes
    //#users-get-post
    //#users-get-delete
    public Route simpleRoutes() {
       return route(
               pathPrefix("users", () ->
                concat(
                        //#users-get-delete
                    pathEnd(() ->
                        concat(
                            get(() -> {
                                Future<Users> users = Patters.ask(userRegistryActor, GetUsers, timeout).mapTo(Users.class);
                                complete(users, Jackson.<Users>marshaller());
                            }),
                            post(() -> {
                                entity(Jackson.unmarshaller(User.class), user -> {
                                            Future<ActionPerformed> userCreated = Patters.ask(userRegistryActor, new CreateUser(user), timeout);
                                            onSuccess(userCreated, performed -> {
                                                log.info("Created user [{}]: {}", user.name, performed.description);
                                                complete((StatusCodes.Created, performed, Jackson.<ActionPerformed>marshaller()))
                                            });
                                });
                            })
                        )),
        //#users-get-post
        //#users-get-delete
                    path(PathMatchers.Segment, name ->
                         concat(
                             get(() -> {
                                         //#retrieve-user-info
                                   Future<Optional<User>> maybeUser = Patters.ask(userRegistryActor, new GetUser(name), timeout).mapTo(Optional<User>.class);

                                rejectEmptyResponse (() ->
                                                     complete(maybeUser, Jackson.<User>marshaller())
                                )
                                         //#retrieve-user-info
                                    s }),
                             delete(() -> {
                                 //#users-delete-logic
                                 Future<ActionPerformed> userDeleted =
                                         Patters.ask(userRegistryActor, new DeleteUser(name)).mapTo[ActionPerformed]
                                 onSuccess(userDeleted, performed -> {
                                             log.info("Deleted user [{}]: {}", name, performed.description)
                                             complete((StatusCodes.OK, performed, Jackson.<ActionPerformed>marshaller()))
                                         }
                                         //#users-delete-logic
                                 )
                             })
                        )
                        // return a constant string with a certain content type
                  )))
        //#users-get-delete
        //#all-routes
    }
}

/**
//#user-routes-class
trait UserRoutes extends JsonSupport {
  //#user-routes-class

  // we leave these abstract, since they will be provided by the App
  implicit def system: ActorSystem

  lazy val log = Logging(system, classOf[UserRoutes])

  // other dependencies that UserRoutes use
  def userRegistryActor: ActorRef

  // Required by the `ask` (?) method below
  implicit lazy val timeout = Timeout(5.seconds) // usually we'd obtain the timeout from the system's configuration

  //#all-routes
  //#users-get-post
  //#users-get-delete
  lazy val userRoutes =
    pathPrefix("users") {
      concat(
        //#users-get-delete
        pathEnd {
          concat(
            get {
              val users: Future[Users] =
                (userRegistryActor ? GetUsers).mapTo[Users]
              complete(users)
            },
            post {
              entity(as[User]) { user =>
                val userCreated: Future[ActionPerformed] =
                  (userRegistryActor ? CreateUser(user)).mapTo[ActionPerformed]
                onSuccess(userCreated) { performed =>
                  log.info("Created user [{}]: {}", user.name, performed.description)
                  complete((StatusCodes.Created, performed))
                }
              }
            }
          )
        },
        //#users-get-post
        //#users-get-delete
        path(Segment) { name =>
          concat(
            get {
              //#retrieve-user-info
              val maybeUser: Future[Option[User]] =
                (userRegistryActor ? GetUser(name)).mapTo[Option[User]]
              rejectEmptyResponse {
                complete(maybeUser)
              }
              //#retrieve-user-info
            },
            delete {
              //#users-delete-logic
              val userDeleted: Future[ActionPerformed] =
                (userRegistryActor ? DeleteUser(name)).mapTo[ActionPerformed]
              onSuccess(userDeleted) { performed =>
                log.info("Deleted user [{}]: {}", name, performed.description)
                complete((StatusCodes.OK, performed))
              }
              //#users-delete-logic
            }
          )
        }
      )
      //#users-get-delete
    }
  //#all-routes
}
**/