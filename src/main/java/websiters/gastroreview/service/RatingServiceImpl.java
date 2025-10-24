package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.RatingRequest;
import WebSiters.GastroReview.dto.RatingResponse;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.model.Rating;
import WebSiters.GastroReview.model.Review;
import WebSiters.GastroReview.model.Users;
import WebSiters.GastroReview.repository.RatingRepository;
import WebSiters.GastroReview.repository.ReviewRepository;
import WebSiters.GastroReview.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementation of RatingService.
 *
 * Responsibilities:
 * - Resolve referenced entities (Review, User).
 * - Prevent duplicate ratings (unique review + user).
 * - Orchestrate create/update/delete operations in transactions.
 * - Throw:
 *    - EntityNotFoundException when referenced entities or target rating do not exist
 *    - IllegalStateException for duplicates (mapped by controller to 409)
 *    - IllegalArgumentException for business rule violations (mapped to 400)
 *    - DataIntegrityViolationException is propagated for DB-level issues
 */
@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository repo;
    private final ReviewRepository reviewRepo;
    private final UsersRepository usersRepo;

    @Override
    public Page<RatingResponse> findAll(Pageable pageable) {
        return repo.findAll(pageable).map(Mappers::toDto);
    }

    @Override
    public Page<RatingResponse> findByReviewId(java.util.UUID reviewId, Pageable pageable) {
        return repo.findByReview_Id(reviewId, pageable).map(Mappers::toDto);
    }

    @Override
    public Page<RatingResponse> findByUserId(java.util.UUID userId, Pageable pageable) {
        return repo.findByUser_Id(userId, pageable).map(Mappers::toDto);
    }

    @Override
    public RatingResponse findById(Long id) {
        Rating r = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rating not found: " + id));
        return Mappers.toDto(r);
    }

    @Override
    @Transactional
    public RatingResponse create(RatingRequest req) {
        // Resolve review
        Review review = reviewRepo.findById(req.getReviewId())
                .orElseThrow(() -> new EntityNotFoundException("Review not found: " + req.getReviewId()));

        // Resolve user
        Users user = usersRepo.findById(req.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + req.getUserId()));

        // Defensive duplicate check: if a rating already exists for this (review,user) -> conflict
        Optional<Rating> existing = repo.findByReview_IdAndUser_Id(req.getReviewId(), req.getUserId());
        if (existing.isPresent()) {
            throw new IllegalStateException("User already rated this review");
        }

        // Build and save
        Rating toSave = Mappers.toEntity(req, review, user);
        try {
            Rating saved = repo.save(toSave);
            return Mappers.toDto(saved);
        } catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }

    @Override
    @Transactional
    public RatingResponse update(Long id, RatingRequest req) {
        Rating existing = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rating not found: " + id));

        // Forbid changing the review or user via update
        if (req.getReviewId() != null && !req.getReviewId().equals(existing.getReview().getId())) {
            throw new IllegalArgumentException("Changing review is not allowed");
        }
        if (req.getUserId() != null && !req.getUserId().equals(existing.getUser().getId())) {
            throw new IllegalArgumentException("Changing user is not allowed");
        }

        // Apply mutable fields (stars and points)
        Mappers.updateEntityFromRequest(req, existing);

        try {
            Rating saved = repo.save(existing);
            return Mappers.toDto(saved);
        } catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Rating not found: " + id);
        }
        repo.deleteById(id);
    }
}
