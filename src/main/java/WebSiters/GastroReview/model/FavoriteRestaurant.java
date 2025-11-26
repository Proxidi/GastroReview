package WebSiters.GastroReview.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "favorite_restaurants")
@IdClass(FavoriteRestaurant.FavoriteRestaurantId.class)
public class FavoriteRestaurant implements Serializable {

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "user_id")
    private UUID userId;

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "restaurant_id")
    private UUID restaurantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", insertable = false, updatable = false)
    private Restaurant restaurant;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    public FavoriteRestaurant() {}

    public FavoriteRestaurant(UUID userId, UUID restaurantId, Users user, 
                             Restaurant restaurant, OffsetDateTime createdAt) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.user = user;
        this.restaurant = restaurant;
        this.createdAt = createdAt;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteRestaurant that = (FavoriteRestaurant) o;
        return Objects.equals(userId, that.userId) && 
               Objects.equals(restaurantId, that.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, restaurantId);
    }

    // Clase ID para clave compuesta
    public static class FavoriteRestaurantId implements Serializable {
        private UUID userId;
        private UUID restaurantId;

        public FavoriteRestaurantId() {}

        public FavoriteRestaurantId(UUID userId, UUID restaurantId) {
            this.userId = userId;
            this.restaurantId = restaurantId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FavoriteRestaurantId that = (FavoriteRestaurantId) o;
            return Objects.equals(userId, that.userId) &&
                   Objects.equals(restaurantId, that.restaurantId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, restaurantId);
        }
    }
}