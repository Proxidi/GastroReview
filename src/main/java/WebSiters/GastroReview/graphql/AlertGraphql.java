package WebSiters.GastroReview.graphql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import WebSiters.GastroReview.dto.AlertRequest;
import WebSiters.GastroReview.dto.AlertResponse;
import WebSiters.GastroReview.service.AlertService;
import jakarta.validation.Valid;

@Controller
public class AlertGraphql {
    @Autowired
    private AlertService service;

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<AlertResponse> findAllAlerts() {
        return service.listar();
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public AlertResponse findAlertById(@Argument Long id) {
        return service.obtener(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public AlertResponse createAlert(@Valid @Argument AlertRequest req) {
        return service.crear(req);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public AlertResponse updateAlert(@Argument Long id, @Valid @Argument AlertRequest req) {
        return service.actualizar(id, req);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean deleteAlert(@Argument Long id) {
        service.eliminar(id);
        return true;
    }
}
