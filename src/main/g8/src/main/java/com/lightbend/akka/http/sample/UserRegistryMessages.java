package com.lightbend.akka.http.sample;

import com.lightbend.akka.http.sample.UserRegistryActor.User;

import java.io.Serializable;

public interface UserRegistryMessages {

    class GetUsers implements Serializable {
    }

    class ActionPerformed implements Serializable {
        private final String description;

        public ActionPerformed(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    class CreateUser implements Serializable {
        private final User user;

        public CreateUser(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    class GetUser implements Serializable {
        private final String name;

        public GetUser(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    class DeleteUser implements Serializable {
        private final String name;

        public DeleteUser(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}