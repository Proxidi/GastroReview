package WebSiters.GastroReview.graphql;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import WebSiters.GastroReview.dto.RestaurantRequest;
import WebSiters.GastroReview.dto.RestaurantResponse;
import WebSiters.GastroReview.service.RestaurantService;
import jakarta.validation.Valid;

@Controller
public class RestaurantGraphql {

    @Autowired
    private RestaurantService service;

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<RestaurantResponse> findAllRestaurants() {
        return service.listar();
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public RestaurantResponse findRestaurantById(@Argument String id) {
        if (id == null) {
            return null;
        }
        return service.obtener(UUID.fromString(id));
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public RestaurantResponse createRestaurant(@Valid @Argument RestaurantRequest req) {
        return service.crear(req);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public RestaurantResponse updateRestaurant(@Argument String id, @Valid @Argument RestaurantRequest req) {
        return service.actualizar(UUID.fromString(id), req);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean deleteRestaurant(@Argument String id) {
        service.eliminar(UUID.fromString(id));
        return true;
    }
}
