package WebSiters.GastroReview.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Entity mapping "review_comments" table.
 *
 * Schema:
 *  id           UUID PRIMARY KEY
 *  review_id    UUID NOT NULL REFERENCES reviews(id) ON DELETE CASCADE
 *  author_id    UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE
 *  parent_id    UUID REFERENCES review_comments(id) ON DELETE CASCADE
 *  content      TEXT NOT NULL
 *  published_at TIMESTAMPTZ NOT NULL DEFAULT now()
 *
 * This entity uses relations to Review, Users and to itself (parent).
 */
@Entity
@Table(name = "review_comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewComment {

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    /**
     * Many-to-one to Review (the reviewed item).
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "review_id", nullable = false)
    @NotNull
    private Review review;

    /**
     * Many-to-one to Users (author of the comment).
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull
    private Users author;

    @NotBlank
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "published_at", nullable = false)
    private OffsetDateTime publishedAt;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
        if (publishedAt == null) publishedAt = OffsetDateTime.now();
    }
}
