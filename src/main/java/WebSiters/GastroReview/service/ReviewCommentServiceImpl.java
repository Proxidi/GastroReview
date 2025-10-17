package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.ReviewCommentRequest;
import WebSiters.GastroReview.dto.ReviewCommentResponse;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.model.Review;
import WebSiters.GastroReview.model.ReviewComment;
import WebSiters.GastroReview.model.Users;
import WebSiters.GastroReview.repository.ReviewCommentRepository;
import WebSiters.GastroReview.repository.ReviewRepository;
import WebSiters.GastroReview.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementation of ReviewCommentService.
 */
@Service
@RequiredArgsConstructor
public class ReviewCommentServiceImpl implements ReviewCommentService {

    private final ReviewCommentRepository repo;
    private final ReviewRepository reviewRepo;
    private final UsersRepository usersRepo;

    @Override
    public Page<ReviewCommentResponse> findAll(Pageable pageable) {
        return repo.findAll(pageable).map(Mappers::toDto);
    }

    @Override
    public Page<ReviewCommentResponse> findByReviewId(UUID reviewId, Pageable pageable) {
        return repo.findByReview_Id(reviewId, pageable).map(Mappers::toDto);
    }

    @Override
    public Page<ReviewCommentResponse> findByAuthorId(UUID authorId, Pageable pageable) {
        return repo.findByAuthor_Id(authorId, pageable).map(Mappers::toDto);
    }

    @Override
    public ReviewCommentResponse findById(UUID id) {
        ReviewComment c = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found: " + id));
        return Mappers.toDto(c);
    }

    @Override
    @Transactional
    public ReviewCommentResponse create(ReviewCommentRequest req) {
        // Resolve review
        Review review = reviewRepo.findById(req.getReviewId())
                .orElseThrow(() -> new EntityNotFoundException("Review not found: " + req.getReviewId()));

        // Resolve author
        Users author = usersRepo.findById(req.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + req.getAuthorId()));

        // Build entity
        ReviewComment toSave = Mappers.toEntity(req, review, author);
        try {
            ReviewComment saved = repo.save(toSave);
            return Mappers.toDto(saved);
        } catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }

    @Override
    @Transactional
    public ReviewCommentResponse update(UUID id, ReviewCommentRequest req) {
        ReviewComment existing = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found: " + id));

        // Forbid changing review or author via update
        if (req.getReviewId() != null && !req.getReviewId().equals(existing.getReview().getId())) {
            throw new IllegalArgumentException("Changing review is not allowed");
        }
        if (req.getAuthorId() != null && !req.getAuthorId().equals(existing.getAuthor().getId())) {
            throw new IllegalArgumentException("Changing author is not allowed");
        }

        // Apply other updates
        Mappers.updateEntityFromRequest(req, existing);

        try {
            ReviewComment saved = repo.save(existing);
            return Mappers.toDto(saved);
        } catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Comment not found: " + id);
        }
        repo.deleteById(id);
    }
}
