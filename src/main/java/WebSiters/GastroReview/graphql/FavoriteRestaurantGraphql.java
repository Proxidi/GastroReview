package WebSiters.GastroReview.graphql;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import WebSiters.GastroReview.dto.FavoriteRestaurantRequest;
import WebSiters.GastroReview.dto.FavoriteRestaurantResponse;
import WebSiters.GastroReview.service.FavoriteRestaurantService;
import jakarta.validation.Valid;

@Controller
public class FavoriteRestaurantGraphql {
    @Autowired
    private FavoriteRestaurantService service;

    @QueryMapping
    public List<FavoriteRestaurantResponse> findAllFavoriteRestaurants() {
        return service.listar();
    }

    @QueryMapping
    public List<FavoriteRestaurantResponse> findFavoriteRestaurantsByUser(@Argument String userId) {
        return service.listarPorUsuario(UUID.fromString(userId));
    }

    @MutationMapping
    public FavoriteRestaurantResponse createFavoriteRestaurant(@Valid @Argument FavoriteRestaurantRequest req) {
        return service.crear(req);
    }

    @MutationMapping
    public Boolean deleteFavoriteRestaurant(@Argument String userId, @Argument String restaurantId) {
        service.eliminar(UUID.fromString(userId), UUID.fromString(restaurantId));
        return true;
    }
}
