package WebSiters.GastroReview.controller;

import jakarta.validation.Valid;
import WebSiters.GastroReview.dto.RestaurantRequest;
import WebSiters.GastroReview.dto.RestaurantResponse;
import WebSiters.GastroReview.service.RestaurantService;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/restaurants")
@Tag(name = "Restaurants", description = "CRUD operations for Restaurants")
public class RestaurantsController {

    private final RestaurantService service;

    public RestaurantsController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "List restaurants", description = "Retrieves a paginated list of restaurants.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public Page<RestaurantResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get restaurant by ID", description = "Retrieves details of a restaurant by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurant found",
                    content = @Content(schema = @Schema(implementation = RestaurantResponse.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    public RestaurantResponse get(
            @Parameter(description = "Restaurant ID", required = true)
            @PathVariable("id") UUID id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create restaurant", description = "Registers a new restaurant in the system.")
    @ApiResponse(responseCode = "201", description = "Restaurant created successfully")
    public RestaurantResponse create(
            @Parameter(description = "Restaurant data", required = true)
            @Valid @RequestBody RestaurantRequest in) {
        return service.create(in);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update restaurant", description = "Updates the information of an existing restaurant.")
    @ApiResponse(responseCode = "200", description = "Restaurant updated successfully")
    public RestaurantResponse update(
            @Parameter(description = "Restaurant ID", required = true)
            @PathVariable("id") UUID id,
            @Valid @RequestBody RestaurantRequest in) {
        return service.update(id, in);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete restaurant", description = "Removes a restaurant by its ID.")
    @ApiResponse(responseCode = "204", description = "Restaurant deleted successfully")
    public void delete(
            @Parameter(description = "Restaurant ID", required = true)
            @PathVariable("id") UUID id) {
        service.delete(id);
    }
}
