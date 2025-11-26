package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.ReviewRequest;
import WebSiters.GastroReview.dto.ReviewResponse;
import WebSiters.GastroReview.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
@Tag(name = "Reviews", description = "API de gestión de reseñas de restaurantes")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todas las reseñas con paginación",
               description = "Obtiene una lista paginada de todas las reseñas con opciones de ordenamiento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de reseñas obtenida exitosamente")
    })
    public Page<ReviewResponse> listar(
            @Parameter(description = "Número de página (inicia en 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo por el cual ordenar") @RequestParam(defaultValue = "publishedAt") String sortBy,
            @Parameter(description = "Dirección del ordenamiento (ASC o DESC)") @RequestParam(defaultValue = "DESC") String direction) {
        Sort sort = direction.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return service.listar(pageable);
    }

    @GetMapping("/all")
    public List<ReviewResponse> listarTodos() {
        return service.listar();
    }

    @GetMapping("/user/{userId}")
    public Page<ReviewResponse> listarPorUsuario(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "publishedAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {
        Sort sort = direction.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return service.listarPorUsuario(userId, pageable);
    }

    @GetMapping("/user/{userId}/all")
    public List<ReviewResponse> listarTodosPorUsuario(@PathVariable UUID userId) {
        return service.listarPorUsuario(userId);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public Page<ReviewResponse> listarPorRestaurante(
            @PathVariable UUID restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "publishedAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {
        Sort sort = direction.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return service.listarPorRestaurante(restaurantId, pageable);
    }

    @GetMapping("/restaurant/{restaurantId}/all")
    public List<ReviewResponse> listarTodosPorRestaurante(@PathVariable UUID restaurantId) {
        return service.listarPorRestaurante(restaurantId);
    }

    @GetMapping("/dish/{dishId}")
    public Page<ReviewResponse> listarPorPlatillo(
            @PathVariable UUID dishId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "publishedAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {
        Sort sort = direction.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return service.listarPorPlatillo(dishId, pageable);
    }

    @GetMapping("/dish/{dishId}/all")
    public List<ReviewResponse> listarTodosPorPlatillo(@PathVariable UUID dishId) {
        return service.listarPorPlatillo(dishId);
    }

    @GetMapping("/with-audio")
    public Page<ReviewResponse> listarConAudio(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "publishedAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {
        Sort sort = direction.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return service.listarConAudio(pageable);
    }

    @GetMapping("/with-audio/all")
    public List<ReviewResponse> listarTodosConAudio() {
        return service.listarConAudio();
    }

    @GetMapping("/with-image")
    public Page<ReviewResponse> listarConImagen(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "publishedAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {
        Sort sort = direction.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return service.listarConImagen(pageable);
    }

    @GetMapping("/with-image/all")
    public List<ReviewResponse> listarTodosConImagen() {
        return service.listarConImagen();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reseña por ID", description = "Obtiene una reseña específica mediante su identificador único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña encontrada"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    public ReviewResponse obtener(@Parameter(description = "ID de la reseña") @PathVariable UUID id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear nueva reseña", description = "Crea una nueva reseña para un restaurante o platillo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reseña creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ReviewResponse crear(@Valid @RequestBody ReviewRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reseña", description = "Actualiza los datos de una reseña existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    public ReviewResponse actualizar(@Parameter(description = "ID de la reseña") @PathVariable UUID id,
                                      @Valid @RequestBody ReviewRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar reseña", description = "Elimina una reseña del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reseña eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    public void eliminar(@Parameter(description = "ID de la reseña") @PathVariable UUID id) {
        service.eliminar(id);
    }
}