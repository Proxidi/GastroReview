package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.NotificationRequest;
import WebSiters.GastroReview.dto.NotificationResponse;
import WebSiters.GastroReview.service.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
@Tag(name = "Notifications", description = "API de gestión de notificaciones de usuarios")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping
    public List<NotificationResponse> listar() {
        return service.listar();
    }

    @GetMapping("/user/{userId}")
    public List<NotificationResponse> listarPorUsuario(@PathVariable UUID userId) {
        return service.listarPorUsuario(userId);
    }

    @GetMapping("/user/{userId}/unread")
    public List<NotificationResponse> listarNoLeidasPorUsuario(@PathVariable UUID userId) {
        return service.listarNoLeidasPorUsuario(userId);
    }

    @GetMapping("/user/{userId}/read")
    public List<NotificationResponse> listarLeidasPorUsuario(@PathVariable UUID userId) {
        return service.listarLeidasPorUsuario(userId);
    }

    @GetMapping("/user/{userId}/unread/count")
    public long contarNoLeidas(@PathVariable UUID userId) {
        return service.contarNoLeidas(userId);
    }

    @GetMapping("/type/{type}")
    public List<NotificationResponse> listarPorTipo(@PathVariable String type) {
        return service.listarPorTipo(type);
    }

    @GetMapping("/{id}")
    public NotificationResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationResponse crear(@Valid @RequestBody NotificationRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public NotificationResponse actualizar(@PathVariable Long id,
                                            @Valid @RequestBody NotificationRequest request) {
        return service.actualizar(id, request);
    }

    @PatchMapping("/{id}/read")
    public NotificationResponse marcarComoLeida(@PathVariable Long id) {
        return service.marcarComoLeida(id);
    }

    @PatchMapping("/user/{userId}/read-all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void marcarTodasComoLeidas(@PathVariable UUID userId) {
        service.marcarTodasComoLeidas(userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}