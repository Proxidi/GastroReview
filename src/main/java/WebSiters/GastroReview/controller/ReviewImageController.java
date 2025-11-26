package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.ReviewImageRequest;
import WebSiters.GastroReview.dto.ReviewImageResponse;
import WebSiters.GastroReview.service.ReviewImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/review-images")
@Tag(name = "Review Images", description = "API de gestión de imágenes de reseñas")
public class ReviewImageController {

    private final ReviewImageService service;

    public ReviewImageController(ReviewImageService service) {
        this.service = service;
    }

    @GetMapping
    public List<ReviewImageResponse> listar() {
        return service.listar();
    }

    @GetMapping("/review/{reviewId}")
    public List<ReviewImageResponse> listarPorReview(@PathVariable UUID reviewId) {
        return service.listarPorReview(reviewId);
    }

    @GetMapping("/{id}")
    public ReviewImageResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewImageResponse crear(@Valid @RequestBody ReviewImageRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public ReviewImageResponse actualizar(@PathVariable Long id,
                                           @Valid @RequestBody ReviewImageRequest request) {
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