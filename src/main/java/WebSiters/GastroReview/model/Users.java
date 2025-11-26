package WebSiters.GastroReview.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.*;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(name = "hash_password", nullable = false)
    private String hashPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role = Role.USER;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    @PrimaryKeyJoinColumn
    private UserProfile profile;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Restaurant> restaurants = new ArrayList<>();

    // Constructors
    public Users() {}

    public Users(UUID id, String email, String hashPassword, Role role, UserProfile profile, List<Restaurant> restaurants) {
        this.id = id;
        this.email = email;
        this.hashPassword = hashPassword;
        this.role = role != null ? role : Role.USER;
        this.profile = profile;
        this.restaurants = restaurants != null ? restaurants : new ArrayList<>();
    }

    // Getters and Setters
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

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    // Builder
    public static UsersBuilder builder() {
        return new UsersBuilder();
    }

    public static class UsersBuilder {
        private UUID id;
        private String email;
        private String hashPassword;
        private Role role = Role.USER;
        private UserProfile profile;
        private List<Restaurant> restaurants = new ArrayList<>();

        UsersBuilder() {}

        public UsersBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public UsersBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UsersBuilder hashPassword(String hashPassword) {
            this.hashPassword = hashPassword;
            return this;
        }

        public UsersBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public UsersBuilder profile(UserProfile profile) {
            this.profile = profile;
            return this;
        }

        public UsersBuilder restaurants(List<Restaurant> restaurants) {
            this.restaurants = restaurants;
            return this;
        }

        public Users build() {
            return new Users(id, email, hashPassword, role, profile, restaurants);
        }
    }

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
    }
}
