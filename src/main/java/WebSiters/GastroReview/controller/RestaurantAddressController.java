package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.RestaurantAddressRequest;
import WebSiters.GastroReview.dto.RestaurantAddressResponse;
import WebSiters.GastroReview.service.RestaurantAddressService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/restaurant-addresses")
public class RestaurantAddressController {

    private final RestaurantAddressService service;

    public RestaurantAddressController(RestaurantAddressService service) {
        this.service = service;
    }

    @GetMapping
    public Page<RestaurantAddressResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{restaurantId}/{addressId}")
    public RestaurantAddressResponse get(@PathVariable("restaurantId") UUID restaurantId,
                                         @PathVariable("addressId") Long addressId) {
        return service.get(restaurantId, addressId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantAddressResponse create(@RequestBody RestaurantAddressRequest in) {
        return service.create(in);
    }

    @PutMapping("/{restaurantId}/{addressId}")
    public RestaurantAddressResponse update(@PathVariable("restaurantId") UUID restaurantId,
                                            @PathVariable("addressId") Long addressId,
                                            @RequestBody RestaurantAddressRequest in) {
        return service.update(restaurantId, addressId, in);
    }

    @DeleteMapping("/{restaurantId}/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restaurantId") UUID restaurantId,
                       @PathVariable("addressId") Long addressId) {
        service.delete(restaurantId, addressId);
    }
}
