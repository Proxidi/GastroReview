package WebSiters.GastroReview.graphql;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import WebSiters.GastroReview.dto.ReviewImageRequest;
import WebSiters.GastroReview.dto.ReviewImageResponse;
import WebSiters.GastroReview.service.ReviewImageService;
import jakarta.validation.Valid;

@Controller
public class ReviewImageGraphql {
    @Autowired
    private ReviewImageService service;

    @QueryMapping
    public List<ReviewImageResponse> findAllReviewImages() {
        return service.listar();
    }

    @QueryMapping
    public ReviewImageResponse findReviewImageById(@Argument Long id) {
        return service.obtener(id);
    }

    @QueryMapping
    public List<ReviewImageResponse> findReviewImagesByReview(@Argument String reviewId) {
        return service.listarPorReview(UUID.fromString(reviewId));
    }

    @MutationMapping
    public ReviewImageResponse createReviewImage(@Valid @Argument ReviewImageRequest req) {
        return service.crear(req);
    }

    @MutationMapping
    public ReviewImageResponse updateReviewImage(@Argument Long id, @Valid @Argument ReviewImageRequest req) {
        return service.actualizar(id, req);
    }

    @MutationMapping
    public Boolean deleteReviewImage(@Argument Long id) {
        service.eliminar(id);
        return true;
    }
}
