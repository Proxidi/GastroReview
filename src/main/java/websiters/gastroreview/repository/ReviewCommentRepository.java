package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.ReviewComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for ReviewComment entity.
 */
public interface ReviewCommentRepository extends JpaRepository<ReviewComment, java.util.UUID> {

    Page<ReviewComment> findByReview_Id(UUID reviewId, Pageable pageable);
    List<ReviewComment> findByReview_Id(UUID reviewId);

    Page<ReviewComment> findByAuthor_Id(UUID authorId, Pageable pageable);
    List<ReviewComment> findByAuthor_Id(UUID authorId);
}
