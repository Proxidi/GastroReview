package WebSiters.GastroReview.graphql;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import WebSiters.GastroReview.dto.FavoriteReviewRequest;
import WebSiters.GastroReview.dto.FavoriteReviewResponse;
import WebSiters.GastroReview.service.FavoriteReviewService;
import jakarta.validation.Valid;

@Controller
public class FavoriteReviewGraphql {
    @Autowired
    private FavoriteReviewService service;

    @QueryMapping
    public List<FavoriteReviewResponse> findAllFavoriteReviews() {
        return service.listar();
    }

    @QueryMapping
    public List<FavoriteReviewResponse> findFavoriteReviewsByUser(@Argument String userId) {
        return service.listarPorUsuario(UUID.fromString(userId));
    }

    @MutationMapping
    public FavoriteReviewResponse createFavoriteReview(@Valid @Argument FavoriteReviewRequest req) {
        return service.crear(req);
    }

    @MutationMapping
    public Boolean deleteFavoriteReview(@Argument String userId, @Argument String reviewId) {
        service.eliminar(UUID.fromString(userId), UUID.fromString(reviewId));
        return true;
    }
}
