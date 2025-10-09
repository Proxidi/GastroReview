package WebSiters.GastroReview.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "id")
    private UUID id;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Users user;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "restaurant_id", nullable = false)
    private UUID restaurantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", insertable = false, updatable = false)
    private Restaurant restaurant;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "dish_id")
    private UUID dishId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id", insertable = false, updatable = false)
    private Dish dish;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "has_audio", nullable = false)
    private boolean hasAudio = false;

    @Column(name = "has_image", nullable = false)
    private boolean hasImage = false;

    @CreationTimestamp
    @Column(name = "published_at", nullable = false, updatable = false)
    private OffsetDateTime publishedAt;
}