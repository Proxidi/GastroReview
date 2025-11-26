package WebSiters.GastroReview.graphql;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import WebSiters.GastroReview.dto.NotificationRequest;
import WebSiters.GastroReview.dto.NotificationResponse;
import WebSiters.GastroReview.service.NotificationService;
import jakarta.validation.Valid;

@Controller
public class NotificationGraphql {
    @Autowired
    private NotificationService service;

    @QueryMapping
    public List<NotificationResponse> findAllNotifications() {
        return service.listar();
    }

    @QueryMapping
    public NotificationResponse findNotificationById(@Argument Long id) {
        return service.obtener(id);
    }

    @QueryMapping
    public List<NotificationResponse> findNotificationsByUser(@Argument String userId) {
        return service.listarPorUsuario(UUID.fromString(userId));
    }

    @MutationMapping
    public NotificationResponse createNotification(@Valid @Argument NotificationRequest req) {
        return service.crear(req);
    }

    @MutationMapping
    public NotificationResponse updateNotification(@Argument Long id, @Valid @Argument NotificationRequest req) {
        return service.actualizar(id, req);
    }

    @MutationMapping
    public Boolean deleteNotification(@Argument Long id) {
        service.eliminar(id);
        return true;
    }

    @MutationMapping
    public NotificationResponse markNotificationAsRead(@Argument Long id) {
        return service.marcarComoLeida(id);
    }
}
