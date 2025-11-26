package WebSiters.GastroReview.dto;

import java.util.UUID;

public class UserResponse {
    private UUID id;
    private String email;

    public UserResponse() {}

    public UserResponse(UUID id, String email) {
        this.id = id;
        this.email = email;
    }

    public static UserResponseBuilder builder() {
        return new UserResponseBuilder();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static class UserResponseBuilder {
        private UUID id;
        private String email;

        UserResponseBuilder() {}

        public UserResponseBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public UserResponseBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserResponse build() {
            return new UserResponse(id, email);
        }
    }
}
