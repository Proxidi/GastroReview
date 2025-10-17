package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.RestaurantAddressRequest;
import WebSiters.GastroReview.dto.RestaurantAddressResponse;
import WebSiters.GastroReview.service.RestaurantAddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/restaurant-addresses")
@Tag(name = "Restaurant Addresses", description = "CRUD operations for restaurant and address associations")
public class RestaurantAddressController {

    private final RestaurantAddressService service;

    public RestaurantAddressController(RestaurantAddressService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "List restaurant-address associations",
            description = "Retrieves all restaurant-address relationships in a paginated format.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public Page<RestaurantAddressResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{restaurantId}/{addressId}")
    @Operation(summary = "Get restaurant-address association",
            description = "Retrieves the relationship between a restaurant and an address.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Association found",
                    content = @Content(schema = @Schema(implementation = RestaurantAddressResponse.class))),
            @ApiResponse(responseCode = "404", description = "Association not found")
    })
    public RestaurantAddressResponse get(
            @Parameter(description = "Restaurant ID", required = true)
            @PathVariable UUID restaurantId,
            @Parameter(description = "Address ID", required = true)
            @PathVariable Long addressId) {
        return service.get(restaurantId, addressId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create restaurant-address association",
            description = "Creates a new association between a restaurant and an address.")
    @ApiResponse(responseCode = "201", description = "Association created successfully")
    public RestaurantAddressResponse create(
            @Parameter(description = "Restaurant-address request body", required = true)
            @RequestBody RestaurantAddressRequest in) {
        return service.create(in);
    }

    @PutMapping("/{restaurantId}/{addressId}")
    @Operation(summary = "Update restaurant-address association",
            description = "Updates an existing restaurant-address association.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Association updated successfully"),
            @ApiResponse(responseCode = "404", description = "Association not found")
    })
    public RestaurantAddressResponse update(
            @Parameter(description = "Restaurant ID", required = true)
            @PathVariable UUID restaurantId,
            @Parameter(description = "Address ID", required = true)
            @PathVariable Long addressId,
            @RequestBody RestaurantAddressRequest in) {
        return service.update(restaurantId, addressId, in);
    }

    @DeleteMapping("/{restaurantId}/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete restaurant-address association",
            description = "Deletes the association between a restaurant and an address.")
    @ApiResponse(responseCode = "204", description = "Association deleted successfully")
    public void delete(
            @Parameter(description = "Restaurant ID", required = true)
            @PathVariable UUID restaurantId,
            @Parameter(description = "Address ID", required = true)
            @PathVariable Long addressId) {
        service.delete(restaurantId, addressId);
    }
}
