package WebSiters.GastroReview.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Entity mapping the "restaurant_schedules" table.
 * Uses a ManyToOne relation to Restaurant (restaurant_id FK).
 */
@Entity
@Table(
    name = "restaurant_schedules",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_restaurant_schedules_rest_weekday_times",
        columnNames = {"restaurant_id", "weekday", "open_time", "close_time"}
    )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // BIGSERIAL -> IDENTITY
    private Long id;

    /**
     * Many-to-one relationship to Restaurant.
     * Use LAZY fetching to avoid eager loads on lists; controller maps to DTOs.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    /**
     * Weekday 0..6 (database CHECK enforces it, but we annotate for bean validation too).
     */
    @Min(0)
    @Max(6)
    @Column(name = "weekday", nullable = false)
    private int weekday;

    /**
     * TIME columns -> use LocalTime.
     */
    @Column(name = "open_time", nullable = false)
    @NotNull
    private LocalTime openTime;

    @Column(name = "close_time", nullable = false)
    @NotNull
    private LocalTime closeTime;

    @Column(name = "special", nullable = false)
    private boolean special = false;

    /**
     * Business validation: openTime must be strictly before closeTime.
     * This runs before insert/update to catch mistakes early.
     */
    @PrePersist
    @PreUpdate
    public void validateTimes() {
        if (openTime == null || closeTime == null) return;
        if (!openTime.isBefore(closeTime)) {
            throw new IllegalArgumentException("openTime must be strictly before closeTime");
        }
    }
}
