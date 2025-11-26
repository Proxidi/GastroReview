package WebSiters.GastroReview.graphql;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import WebSiters.GastroReview.dto.ReviewAudioRequest;
import WebSiters.GastroReview.dto.ReviewAudioResponse;
import WebSiters.GastroReview.service.ReviewAudioService;
import jakarta.validation.Valid;

@Controller
public class ReviewAudioGraphql {
    @Autowired
    private ReviewAudioService service;

    @QueryMapping
    public List<ReviewAudioResponse> findAllReviewAudios() {
        return service.listar();
    }

    @QueryMapping
    public ReviewAudioResponse findReviewAudioById(@Argument Long id) {
        return service.obtener(id);
    }

    @QueryMapping
    public List<ReviewAudioResponse> findReviewAudiosByReview(@Argument String reviewId) {
        return service.listarPorReview(UUID.fromString(reviewId));
    }

    @MutationMapping
    public ReviewAudioResponse createReviewAudio(@Valid @Argument ReviewAudioRequest req) {
        return service.crear(req);
    }

    @MutationMapping
    public ReviewAudioResponse updateReviewAudio(@Argument Long id, @Valid @Argument ReviewAudioRequest req) {
        return service.actualizar(id, req);
    }

    @MutationMapping
    public Boolean deleteReviewAudio(@Argument Long id) {
        service.eliminar(id);
        return true;
    }
}
