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
@Table(name = "favorite_reviews")
@IdClass(FavoriteReview.FavoriteReviewId.class)
public class FavoriteReview implements Serializable {

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "user_id")
    private UUID userId;

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "review_id")
    private UUID reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", insertable = false, updatable = false)
    private Review review;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    public FavoriteReview() {}

    public FavoriteReview(UUID userId, UUID reviewId, Users user, 
                         Review review, OffsetDateTime createdAt) {
        this.userId = userId;
        this.reviewId = reviewId;
        this.user = user;
        this.review = review;
        this.createdAt = createdAt;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getReviewId() {
        return reviewId;
    }

    public void setReviewId(UUID reviewId) {
        this.reviewId = reviewId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
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
        FavoriteReview that = (FavoriteReview) o;
        return Objects.equals(userId, that.userId) && 
               Objects.equals(reviewId, that.reviewId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, reviewId);
    }

    // Clase ID para clave compuesta
    public static class FavoriteReviewId implements Serializable {
        private UUID userId;
        private UUID reviewId;

        public FavoriteReviewId() {}

        public FavoriteReviewId(UUID userId, UUID reviewId) {
            this.userId = userId;
            this.reviewId = reviewId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FavoriteReviewId that = (FavoriteReviewId) o;
            return Objects.equals(userId, that.userId) &&
                   Objects.equals(reviewId, that.reviewId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, reviewId);
        }
    }
}