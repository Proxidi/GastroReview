package WebSiters.GastroReview.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Entity mapping the "text_analysis" table.
 *
 * Schema:
 *  id          BIGSERIAL PRIMARY KEY
 *  review_id   UUID NOT NULL REFERENCES reviews(id) ON DELETE CASCADE
 *  sentiment   TEXT CHECK (sentiment IN ('negative','neutral','positive','mixed'))
 *  score       NUMERIC(5,4)
 *  spam_prob   NUMERIC(5,4)
 *  language    TEXT
 *  metadata    JSONB
 *  created_at  TIMESTAMPTZ NOT NULL DEFAULT now()
 */
@Entity
@Table(name = "text_analysis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TextAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // BIGSERIAL -> IDENTITY
    private Long id;

    /**
     * Many-to-one relation to Review.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "review_id", nullable = false)
    @NotNull
    private Review review;

    /**
     * Sentiment: allowed values are validated in service/controller.
     */
    @Column(name = "sentiment")
    private String sentiment;

    /**
     * Score and spam_prob stored as BigDecimal to preserve NUMERIC precision.
     */
    @Column(name = "score", precision = 9, scale = 4)
    private BigDecimal score;

    @Column(name = "spam_prob", precision = 9, scale = 4)
    private BigDecimal spamProb;

    @Column(name = "language")
    private String language;

    /**
     * Metadata as JSONB.
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "jsonb")
    private JsonNode metadata;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = OffsetDateTime.now();
    }
}
