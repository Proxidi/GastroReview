package WebSiters.GastroReview.graphql;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import WebSiters.GastroReview.dto.ReviewRequest;
import WebSiters.GastroReview.dto.ReviewResponse;
import WebSiters.GastroReview.service.ReviewService;
import jakarta.validation.Valid;

@Controller
public class ReviewGraphql {
    @Autowired
    private ReviewService service;

    @QueryMapping
    public List<ReviewResponse> findAllReviews() {
        return service.listar();
    }

    @QueryMapping
    public ReviewResponse findReviewById(@Argument String id) {
        return service.obtener(UUID.fromString(id));
    }

    @QueryMapping
    public List<ReviewResponse> findReviewsByRestaurant(@Argument String restaurantId) {
        return service.listarPorRestaurante(UUID.fromString(restaurantId));
    }

    @QueryMapping
    public List<ReviewResponse> findReviewsByUser(@Argument String userId) {
        return service.listarPorUsuario(UUID.fromString(userId));
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ReviewResponse createReview(@Valid @Argument ReviewRequest req) {
        return service.crear(req);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ReviewResponse updateReview(@Argument String id, @Valid @Argument ReviewRequest req) {
        return service.actualizar(UUID.fromString(id), req);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Boolean deleteReview(@Argument String id) {
        service.eliminar(UUID.fromString(id));
        return true;
    }
}
