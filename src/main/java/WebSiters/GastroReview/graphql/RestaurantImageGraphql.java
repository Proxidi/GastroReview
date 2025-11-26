package WebSiters.GastroReview.graphql;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import WebSiters.GastroReview.dto.RestaurantImageRequest;
import WebSiters.GastroReview.dto.RestaurantImageResponse;
import WebSiters.GastroReview.service.RestaurantImageService;
import jakarta.validation.Valid;

@Controller
public class RestaurantImageGraphql {
    @Autowired
    private RestaurantImageService service;

    @QueryMapping
    public List<RestaurantImageResponse> findAllRestaurantImages() {
        return service.listar();
    }

    @QueryMapping
    public RestaurantImageResponse findRestaurantImageById(@Argument Long id) {
        return service.obtener(id);
    }

    @QueryMapping
    public List<RestaurantImageResponse> findRestaurantImagesByRestaurant(@Argument String restaurantId) {
        return service.listarPorRestaurante(UUID.fromString(restaurantId));
    }

    @MutationMapping
    public RestaurantImageResponse createRestaurantImage(@Valid @Argument RestaurantImageRequest req) {
        return service.crear(req);
    }

    @MutationMapping
    public RestaurantImageResponse updateRestaurantImage(@Argument Long id, @Valid @Argument RestaurantImageRequest req) {
        return service.actualizar(id, req);
    }

    @MutationMapping
    public Boolean deleteRestaurantImage(@Argument Long id) {
        service.eliminar(id);
        return true;
    }
}
