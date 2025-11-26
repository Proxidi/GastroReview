package WebSiters.GastroReview.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class RestaurantResponse {
    private UUID id;
    private String name;
    private String description;
    private String phone;
    private String email;
    private UUID ownerId;
    private OffsetDateTime createdAt;

    public RestaurantResponse() {}

    public RestaurantResponse(UUID id, String name, String description, String phone,
                            String email, UUID ownerId, OffsetDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.email = email;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
    }

    public static RestaurantResponseBuilder builder() {
        return new RestaurantResponseBuilder();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static class RestaurantResponseBuilder {
        private UUID id;
        private String name;
        private String description;
        private String phone;
        private String email;
        private UUID ownerId;
        private OffsetDateTime createdAt;

        RestaurantResponseBuilder() {}

        public RestaurantResponseBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public RestaurantResponseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public RestaurantResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public RestaurantResponseBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public RestaurantResponseBuilder email(String email) {
            this.email = email;
            return this;
        }

        public RestaurantResponseBuilder ownerId(UUID ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public RestaurantResponseBuilder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public RestaurantResponse build() {
            return new RestaurantResponse(id, name, description, phone, email, ownerId, createdAt);
        }
    }
}
