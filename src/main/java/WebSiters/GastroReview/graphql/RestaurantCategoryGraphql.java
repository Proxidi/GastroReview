package WebSiters.GastroReview.graphql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import WebSiters.GastroReview.dto.RestaurantCategoryRequest;
import WebSiters.GastroReview.dto.RestaurantCategoryResponse;
import WebSiters.GastroReview.service.RestaurantCategoryService;
import jakarta.validation.Valid;

@Controller
public class RestaurantCategoryGraphql {
    @Autowired
    private RestaurantCategoryService service;

    @QueryMapping
    public List<RestaurantCategoryResponse> findAllRestaurantCategories() {
        return service.listar();
    }

    @QueryMapping
    public RestaurantCategoryResponse findRestaurantCategoryById(@Argument Integer id) {
        return service.obtener(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public RestaurantCategoryResponse createRestaurantCategory(@Valid @Argument RestaurantCategoryRequest req) {
        return service.crear(req);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public RestaurantCategoryResponse updateRestaurantCategory(@Argument Integer id, @Valid @Argument RestaurantCategoryRequest req) {
        return service.actualizar(id, req);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean deleteRestaurantCategory(@Argument Integer id) {
        service.eliminar(id);
        return true;
    }
}
