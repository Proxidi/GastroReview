package WebSiters.GastroReview.graphql;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import WebSiters.GastroReview.dto.DishRequest;
import WebSiters.GastroReview.dto.DishResponse;
import WebSiters.GastroReview.service.DishService;
import jakarta.validation.Valid;

@Controller
public class DishGraphql {
    @Autowired
    private DishService service;

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<DishResponse> findAllDishes() {
        return service.listar();
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public DishResponse findDishById(@Argument String id) {
        return service.obtener(UUID.fromString(id));
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<DishResponse> findDishesByRestaurant(@Argument String restaurantId) {
        return service.listarPorRestaurante(UUID.fromString(restaurantId));
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public DishResponse createDish(@Valid @Argument DishRequest req) {
        return service.crear(req);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public DishResponse updateDish(@Argument String id, @Valid @Argument DishRequest req) {
        return service.actualizar(UUID.fromString(id), req);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean deleteDish(@Argument String id) {
        service.eliminar(UUID.fromString(id));
        return true;
    }
}
