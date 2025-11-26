package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.FavoriteRestaurantRequest;
import WebSiters.GastroReview.dto.FavoriteRestaurantResponse;
import WebSiters.GastroReview.service.FavoriteRestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/favorite-restaurants")
@Tag(name = "Favorite Restaurants", description = "API de gestión de restaurantes favoritos de usuarios")
public class FavoriteRestaurantController {

    private final FavoriteRestaurantService service;

    public FavoriteRestaurantController(FavoriteRestaurantService service) {
        this.service = service;
    }

    @GetMapping
    public List<FavoriteRestaurantResponse> listar() {
        return service.listar();
    }

    @GetMapping("/user/{userId}")
    public List<FavoriteRestaurantResponse> listarPorUsuario(@PathVariable UUID userId) {
        return service.listarPorUsuario(userId);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<FavoriteRestaurantResponse> listarPorRestaurante(@PathVariable UUID restaurantId) {
        return service.listarPorRestaurante(restaurantId);
    }

    @GetMapping("/user/{userId}/restaurant/{restaurantId}")
    public FavoriteRestaurantResponse obtener(@PathVariable UUID userId,
                                               @PathVariable UUID restaurantId) {
        return service.obtener(userId, restaurantId);
    }

    @GetMapping("/user/{userId}/restaurant/{restaurantId}/exists")
    public boolean verificarFavorito(@PathVariable UUID userId,
                                     @PathVariable UUID restaurantId) {
        return service.verificarFavorito(userId, restaurantId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FavoriteRestaurantResponse crear(@Valid @RequestBody FavoriteRestaurantRequest request) {
        return service.crear(request);
    }

    @DeleteMapping("/user/{userId}/restaurant/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable UUID userId,
                         @PathVariable UUID restaurantId) {
        service.eliminar(userId, restaurantId);
    }
}