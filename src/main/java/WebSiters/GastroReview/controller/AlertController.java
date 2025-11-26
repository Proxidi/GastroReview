package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.AlertRequest;
import WebSiters.GastroReview.dto.AlertResponse;
import WebSiters.GastroReview.service.AlertService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/alerts")
@Tag(name = "Alerts", description = "API de gestión de alertas del sistema")
public class AlertController {

    private final AlertService service;

    public AlertController(AlertService service) {
        this.service = service;
    }

    @GetMapping
    public List<AlertResponse> listar() {
        return service.listar();
    }

    @GetMapping("/type/{type}")
    public List<AlertResponse> listarPorTipo(@PathVariable String type) {
        return service.listarPorTipo(type);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<AlertResponse> listarPorRestaurante(@PathVariable UUID restaurantId) {
        return service.listarPorRestaurante(restaurantId);
    }

    @GetMapping("/review/{reviewId}")
    public List<AlertResponse> listarPorReview(@PathVariable UUID reviewId) {
        return service.listarPorReview(reviewId);
    }

    @GetMapping("/{id}")
    public AlertResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlertResponse crear(@Valid @RequestBody AlertRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public AlertResponse actualizar(@PathVariable Long id,
                                     @Valid @RequestBody AlertRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}