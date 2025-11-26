package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.RestaurantImageRequest;
import WebSiters.GastroReview.dto.RestaurantImageResponse;
import WebSiters.GastroReview.service.RestaurantImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/restaurant-images")
@Tag(name = "Restaurant Images", description = "API de gestión de imágenes de restaurantes")
public class RestaurantImageController {

    private final RestaurantImageService service;

    public RestaurantImageController(RestaurantImageService service) {
        this.service = service;
    }

    @GetMapping
    public List<RestaurantImageResponse> listar() {
        return service.listar();
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<RestaurantImageResponse> listarPorRestaurante(@PathVariable UUID restaurantId) {
        return service.listarPorRestaurante(restaurantId);
    }

    @GetMapping("/user/{uploadedBy}")
    public List<RestaurantImageResponse> listarPorUsuario(@PathVariable UUID uploadedBy) {
        return service.listarPorUsuario(uploadedBy);
    }

    @GetMapping("/{id}")
    public RestaurantImageResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantImageResponse crear(@Valid @RequestBody RestaurantImageRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public RestaurantImageResponse actualizar(@PathVariable Long id,
                                               @Valid @RequestBody RestaurantImageRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @DeleteMapping("/restaurant/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarPorRestaurante(@PathVariable UUID restaurantId) {
        service.eliminarPorRestaurante(restaurantId);
    }
}