package WebSiters.GastroReview.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "review_audios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewAudio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "review_id", nullable = false)
    private UUID reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", insertable = false, updatable = false)
    private Review review;

    @NotBlank
    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "duration_seconds")
    private Integer durationSeconds;

    @Column(name = "transcription")
    private String transcription;
}