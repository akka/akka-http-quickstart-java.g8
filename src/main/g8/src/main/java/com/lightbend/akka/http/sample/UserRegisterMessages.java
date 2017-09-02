public interface UserRegitryMessages {

    static class GetUsers implements Serializable{
    }

    static class ActionPerformed implements Serializable{
        private final String description;

        public ActionPerformed(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    static class CreateUser  implements Serializable{
        private final User user;

        public CreateUser(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    static class GetUser implements Serializable{
        private final String name;

        public CreateUser(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    static class DeleteUser implements Serializable{
        private final String name;

        public DeleteUser(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}