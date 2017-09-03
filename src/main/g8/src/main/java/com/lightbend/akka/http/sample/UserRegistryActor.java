package com.lightbend.akka.http.sample;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;

import java.util.*;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;


public class UserRegistryActor extends AbstractActor {

  LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

  //#user-case-classes
  public static class User {
    private final String name;
    private final int age;
    private final String countryOfResidence;

    public User(String name, int age, String countryOfResidence) {
      this.name = name;
      this.age = age;
      this.countryOfResidence = countryOfResidence;
    }

    public String getName() {
      return name;
    }

    public int getAge() {
      return age;
    }

    public String getCountryOfResidence() {
      return countryOfResidence;
    }
  }

  public static class Users{
    private final List<User> users;

    public Users(List<User> users) {
      this.users = users;
    }

    public List<User> getUsers() {
      return users;
    }
  }
//#user-case-classes


  static Props props() {
    return Props.create(UserRegistryActor.class, new UserRegistryActor());
  }

  private final List<User> users =new ArrayList<>();

  @Override
  public Receive createReceive(){
    return receiveBuilder()
            .match(UserRegistryMessages.GetUsers.class, getUsers -> getSender().tell(new Users(users),getSelf()))
            .match(UserRegistryMessages.CreateUser.class, createUser -> {
              users.add(createUser.getUser());
              getSender().tell(new UserRegistryMessages.ActionPerformed(
                      String.format("User %s created.",createUser.getUser().getName())),getSelf());
            })
            .match(UserRegistryMessages.GetUser.class, getUser -> {
              getSender().tell(users.stream()
                      .filter(user -> user.getName().equals(getUser.getName()))
                      .findFirst(),getSelf());
            })
            .match(UserRegistryMessages.DeleteUser.class, deleteUser -> {
              users.removeIf(user -> user.getName().equals(deleteUser.getName()));
              getSender().tell(new UserRegistryMessages.ActionPerformed(String.format("User %s deleted.",deleteUser.getName())),
                      getSelf());

            }).matchAny(o -> log.info("received unknown message"))
            .build();
  }
}
