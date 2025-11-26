package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.RestaurantCategoryRequest;
import WebSiters.GastroReview.dto.RestaurantCategoryResponse;
import WebSiters.GastroReview.service.RestaurantCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurant-categories-catalog")
@Tag(name = "Restaurant Categories", description = "API de catálogo de categorías de restaurantes")
public class RestaurantCategoryController {

    private final RestaurantCategoryService service;

    public RestaurantCategoryController(RestaurantCategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<RestaurantCategoryResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public RestaurantCategoryResponse obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @GetMapping("/name/{name}")
    public RestaurantCategoryResponse obtenerPorNombre(@PathVariable String name) {
        return service.obtenerPorNombre(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantCategoryResponse crear(@Valid @RequestBody RestaurantCategoryRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public RestaurantCategoryResponse actualizar(@PathVariable Integer id,
                                                    @Valid @RequestBody RestaurantCategoryRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}