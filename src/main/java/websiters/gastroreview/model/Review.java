package WebSiters.GastroReview.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Review entity mapping the "reviews" table.
 * Now uses ManyToOne relations for user, restaurant and (nullable) dish.
 */
@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    /**
     * Many-to-one relation to Users (author).
     * Cascade is not used because we don't want to create/update users from reviews.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private Users user;

    /**
     * Many-to-one relation to Restaurant.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    /**
     * Optional many-to-one relation to Dish.
     * ON DELETE SET NULL semantics are handled by the DB; here we keep the relation nullable.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "has_audio", nullable = false)
    private boolean hasAudio = false;

    @Column(name = "has_image", nullable = false)
    private boolean hasImage = false;

    @Column(name = "published_at", nullable = false)
    private OffsetDateTime publishedAt;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
        if (publishedAt == null) publishedAt = OffsetDateTime.now();
    }
}
