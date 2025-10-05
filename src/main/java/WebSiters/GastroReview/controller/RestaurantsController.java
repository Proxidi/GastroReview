package WebSiters.GastroReview.controller;

import jakarta.validation.Valid;
import WebSiters.GastroReview.dto.RestaurantRequest;
import WebSiters.GastroReview.dto.RestaurantResponse;
import WebSiters.GastroReview.service.RestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantsController {

    private final RestaurantService service;

    public RestaurantsController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping
    public Page<RestaurantResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{id}")
    public RestaurantResponse get(@PathVariable("id") UUID id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantResponse create(@Valid @RequestBody RestaurantRequest in) {
        return service.create(in);
    }

    @PutMapping("/{id}")
    public RestaurantResponse update(@PathVariable("id") UUID id,
                                     @Valid @RequestBody RestaurantRequest in) {
        return service.update(id, in);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") UUID id) {
        service.delete(id);
    }
}
