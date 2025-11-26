package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.FavoriteReviewRequest;
import WebSiters.GastroReview.dto.FavoriteReviewResponse;
import WebSiters.GastroReview.service.FavoriteReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/favorite-reviews")
@Tag(name = "Favorite Reviews", description = "API de gestión de reseñas favoritas de usuarios")
public class FavoriteReviewController {

    private final FavoriteReviewService service;

    public FavoriteReviewController(FavoriteReviewService service) {
        this.service = service;
    }

    @GetMapping
    public List<FavoriteReviewResponse> listar() {
        return service.listar();
    }

    @GetMapping("/user/{userId}")
    public List<FavoriteReviewResponse> listarPorUsuario(@PathVariable UUID userId) {
        return service.listarPorUsuario(userId);
    }

    @GetMapping("/review/{reviewId}")
    public List<FavoriteReviewResponse> listarPorReview(@PathVariable UUID reviewId) {
        return service.listarPorReview(reviewId);
    }

    @GetMapping("/user/{userId}/review/{reviewId}")
    public FavoriteReviewResponse obtener(@PathVariable UUID userId,
                                           @PathVariable UUID reviewId) {
        return service.obtener(userId, reviewId);
    }

    @GetMapping("/user/{userId}/review/{reviewId}/exists")
    public boolean verificarFavorito(@PathVariable UUID userId,
                                     @PathVariable UUID reviewId) {
        return service.verificarFavorito(userId, reviewId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FavoriteReviewResponse crear(@Valid @RequestBody FavoriteReviewRequest request) {
        return service.crear(request);
    }

    @DeleteMapping("/user/{userId}/review/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable UUID userId,
                         @PathVariable UUID reviewId) {
        service.eliminar(userId, reviewId);
    }
}