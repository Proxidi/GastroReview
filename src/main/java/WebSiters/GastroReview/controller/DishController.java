package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.DishRequest;
import WebSiters.GastroReview.dto.DishResponse;
import WebSiters.GastroReview.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/dishes")
@Tag(name = "Dishes", description = "API de gestión de platillos de restaurantes")
public class DishController {

    private final DishService service;

    public DishController(DishService service) {
        this.service = service;
    }

    @GetMapping
    public Page<DishResponse> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        Sort sort = direction.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return service.listar(pageable);
    }

    @GetMapping("/all")
    public List<DishResponse> listarTodos() {
        return service.listar();
    }

    @GetMapping("/restaurant/{restaurantId}")
    public Page<DishResponse> listarPorRestaurante(
            @PathVariable UUID restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        Sort sort = direction.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return service.listarPorRestaurante(restaurantId, pageable);
    }

    @GetMapping("/restaurant/{restaurantId}/all")
    public List<DishResponse> listarTodosPorRestaurante(@PathVariable UUID restaurantId) {
        return service.listarPorRestaurante(restaurantId);
    }

    @GetMapping("/available")
    public Page<DishResponse> listarDisponibles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        Sort sort = direction.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return service.listarDisponibles(pageable);
    }

    @GetMapping("/available/all")
    public List<DishResponse> listarTodosDisponibles() {
        return service.listarDisponibles();
    }

    @GetMapping("/restaurant/{restaurantId}/available")
    public Page<DishResponse> listarDisponiblesPorRestaurante(
            @PathVariable UUID restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        Sort sort = direction.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return service.listarDisponiblesPorRestaurante(restaurantId, pageable);
    }

    @GetMapping("/restaurant/{restaurantId}/available/all")
    public List<DishResponse> listarTodosDisponiblesPorRestaurante(@PathVariable UUID restaurantId) {
        return service.listarDisponiblesPorRestaurante(restaurantId);
    }

    @GetMapping("/{id}")
    public DishResponse obtener(@PathVariable UUID id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DishResponse crear(@Valid @RequestBody DishRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public DishResponse actualizar(@PathVariable UUID id,
                                    @Valid @RequestBody DishRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable UUID id) {
        service.eliminar(id);
    }
}