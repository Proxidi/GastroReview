package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.DishResponse;
import WebSiters.GastroReview.dto.RestaurantResponse;
import WebSiters.GastroReview.dto.ReviewResponse;
import WebSiters.GastroReview.dto.UserResponse;
import WebSiters.GastroReview.service.DishService;
import WebSiters.GastroReview.service.RestaurantService;
import WebSiters.GastroReview.service.ReviewService;
import WebSiters.GastroReview.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class HomeController {

    private final DishService dishService;
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;
    private final UserService userService;

    public HomeController(DishService dishService, RestaurantService restaurantService,
                         ReviewService reviewService, UserService userService) {
        this.dishService = dishService;
        this.restaurantService = restaurantService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> home() {
        List<DishResponse> dishes = dishService.listar();
        List<RestaurantResponse> restaurants = restaurantService.listar();
        List<ReviewResponse> reviews = reviewService.listar();
        List<UserResponse> users = userService.listar();

        return ResponseEntity.ok(Map.of(
            "message", "Bienvenido a GastroReview API",
            "version", "1.0.0",
            "data", Map.of(
                "dishes", dishes,
                "restaurants", restaurants,
                "reviews", reviews,
                "users", users,
                "totalDishes", dishes.size(),
                "totalRestaurants", restaurants.size(),
                "totalReviews", reviews.size(),
                "totalUsers", users.size()
            ),
            "endpoints", Map.of(
                "graphql", "/graphql",
                "graphiql", "/graphiql",
                "swagger", "/swagger-ui.html",
                "api-docs", "/v3/api-docs"
            )
        ));
    }

    @GetMapping("/Dish")
    public List<DishResponse> verTodosLosPlatos() {
        return dishService.listar();
    }

    @GetMapping("/Reviews")
    public List<ReviewResponse> verTodasLasReviews() {
        return reviewService.listar();
    }
}
