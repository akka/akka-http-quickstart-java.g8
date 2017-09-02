package com.lightbend.akka.http.sample;

import akka.actor.*;
import java.util.*;


public class UserRegistryActor extends extends AbstractActor {

  //#user-case-classes
  static class User {
    private final String name;
    private final Int age;
    private final String countryOfResidence;

    public User(String name, Int age, String countryOfResidence) {
      this.name = name;
      this.age = age;
      this.countryOfResidence = countryOfResidence;
    }

    public String getName() {
      return name;
    }

    public Int getAge() {
      return age;
    }

    public String getCountryOfResidence() {
      return countryOfResidence;
    }
  }

  static class Users{
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
    return Props.create(UserRegistryActor.class, () -> new UserRegistryActor());
  }

  private final List<User> users =new ArrayList<>();

  @Override
  public void createReceive(){
    return receiveBuilder()
            .match(GetUsers.class, getUsers -> {
            getSender().tell(new Users(users.clone));
            })
            .match(CreateUser.class, createUser -> {
              users.add(createUser.getUser())
              getSender().tell(new ActionPerformed(String.format("User %s created.",createUser.getName())
            })
            .match(GetUser.class, getUser -> {
              getSender().tell(users.stream()
                      .filter(user -> user.getName()).equals(getUser.getName()))
                      .findFirst();
            })
            .match(DeleteUser.class, deleteUser -> {
              users.removeIf(user -> user.getName().equals(deleteUser.getName()));
              getSender().tell(new ActionPerformed(String.format("User %s deleted.",deleteUser.getName())

            })
            .matchAny(o -> log.info("received unknown message"))
            .build();
  }
}
