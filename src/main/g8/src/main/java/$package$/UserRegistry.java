package $package$;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class UserRegistry extends AbstractBehavior<UserRegistry.Command>  {

  // actor protocol
  interface Command {}

  public final static class GetUsers implements Command {
    public final ActorRef<Users> replyTo;
    public GetUsers(ActorRef<Users> replyTo) {
      this.replyTo = replyTo;
    }
  }

  public final static class CreateUser implements Command {
    public final User user;
    public final ActorRef<ActionPerformed> replyTo;
    public CreateUser(User user, ActorRef<ActionPerformed> replyTo) {
      this.user = user;
      this.replyTo = replyTo;
    }
  }

  public final static class GetUserResponse {
    public final Optional<User> maybeUser;
    public GetUserResponse(Optional<User> maybeUser) {
      this.maybeUser = maybeUser;
    }
  }

  public final static class GetUser implements Command {
    public final String name;
    public final ActorRef<GetUserResponse> replyTo;
    public GetUser(String name, ActorRef<GetUserResponse> replyTo) {
      this.name = name;
      this.replyTo = replyTo;
    }
  }


  public final static class DeleteUser implements Command {
    public final String name;
    public final ActorRef<ActionPerformed> replyTo;
    public DeleteUser(String name, ActorRef<ActionPerformed> replyTo) {
      this.name = name;
      this.replyTo = replyTo;
    }
  }


  public final static class ActionPerformed implements Command {
    public final String description;
    public ActionPerformed(String description) {
      this.description = description;
    }
  }

  //#user-case-classes
  public final static class User {
    public final String name;
    public final int age;
    public final String countryOfResidence;
    @JsonCreator
    public User(@JsonProperty("name") String name, @JsonProperty("age") int age, @JsonProperty("countryOfRecidence") String countryOfResidence) {
      this.name = name;
      this.age = age;
      this.countryOfResidence = countryOfResidence;
    }
  }

  public final static class Users{
    public final List<User> users;
    public Users(List<User> users) {
      this.users = users;
    }
  }
  //#user-case-classes

  private final List<User> users = new ArrayList<>();

  private UserRegistry(ActorContext<Command> context) {
    super(context);
  }

  public static Behavior<Command> create() {
    return Behaviors.setup(UserRegistry::new);
  }

  @Override
  public Receive<Command> createReceive() {
    return newReceiveBuilder()
        .onMessage(GetUsers.class, this::onGetUsers)
        .onMessage(CreateUser.class, this::onCreateUser)
        .onMessage(GetUser.class, this::onGetUser)
        .onMessage(DeleteUser.class, this::onDeleteUser)
        .build();
  }

  private Behavior<Command> onGetUsers(GetUsers command) {
    // We must be careful not to send out users since it is mutable
    // so for this response we need to make a defensive copy
    command.replyTo.tell(new Users(Collections.unmodifiableList(new ArrayList<>(users))));
    return this;
  }

  private Behavior<Command> onCreateUser(CreateUser command) {
    users.add(command.user);
    command.replyTo.tell(new ActionPerformed(String.format("User %s created.", command.user.name)));
    return this;
  }

  private Behavior<Command> onGetUser(GetUser command) {
    Optional<User> maybeUser = users.stream()
        .filter(user -> user.name.equals(command.name))
        .findFirst();
    command.replyTo.tell(new GetUserResponse(maybeUser));
    return this;
  }

  private Behavior<Command> onDeleteUser(DeleteUser command) {
    users.removeIf(user -> user.name.equals(command.name));
    command.replyTo.tell(new ActionPerformed(String.format("User %s deleted.", command.name)));
    return this;
  }

}
