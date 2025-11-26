package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.ReviewAudioRequest;
import WebSiters.GastroReview.dto.ReviewAudioResponse;
import WebSiters.GastroReview.service.ReviewAudioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/review-audios")
@Tag(name = "Review Audios", description = "API de gestión de audios de reseñas")
public class ReviewAudioController {

    private final ReviewAudioService service;

    public ReviewAudioController(ReviewAudioService service) {
        this.service = service;
    }

    @GetMapping
    public List<ReviewAudioResponse> listar() {
        return service.listar();
    }

    @GetMapping("/review/{reviewId}")
    public List<ReviewAudioResponse> listarPorReview(@PathVariable UUID reviewId) {
        return service.listarPorReview(reviewId);
    }

    @GetMapping("/{id}")
    public ReviewAudioResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewAudioResponse crear(@Valid @RequestBody ReviewAudioRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public ReviewAudioResponse actualizar(@PathVariable Long id,
                                           @Valid @RequestBody ReviewAudioRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @DeleteMapping("/review/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarPorReview(@PathVariable UUID reviewId) {
        service.eliminarPorReview(reviewId);
    }
}