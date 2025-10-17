package WebSiters.GastroReview.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.OffsetDateTime;

/**
 * Rating entity mapping the "ratings" table.
 */
@Entity
@Table(
    name = "ratings",
    uniqueConstraints = @UniqueConstraint(name = "uq_ratings_review_user", columnNames = {"review_id", "user_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // BIGSERIAL
    private Long id;

    /**
     * Many-to-one relation to Review.
     * We expect Review entity to exist.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "review_id", nullable = false)
    @NotNull
    private Review review;

    /**
     * Many-to-one relation to Users (the user who rated).
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private Users user;

    @Min(1)
    @Max(5)
    @Column(name = "stars", nullable = false)
    private int stars;

    @Column(name = "points", nullable = false)
    private int points = 0;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    /**
     * Ensure createdAt is set on persist.
     */
    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = OffsetDateTime.now();
    }
}
