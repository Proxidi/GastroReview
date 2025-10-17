package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.AddressRequest;
import WebSiters.GastroReview.dto.AddressResponse;
import WebSiters.GastroReview.service.AddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
@Tag(name = "Addresses", description = "CRUD operations for Addresses")
public class AddressController {

    private final AddressService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new address", description = "Registers a new address in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Address created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public AddressResponse create(
            @Parameter(description = "Address data", required = true)
            @RequestBody AddressRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get address by ID", description = "Retrieves a specific address by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressResponse.class))),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    public AddressResponse get(
            @Parameter(description = "ID of the address", required = true)
            @PathVariable("id") Long id) {
        return service.get(id);
    }

    @GetMapping
    @Operation(summary = "List addresses", description = "Retrieves a paginated list of addresses.")
    @ApiResponse(responseCode = "200", description = "Addresses retrieved successfully")
    public Page<AddressResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an address", description = "Updates an existing address by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddressResponse.class))),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    public AddressResponse update(
            @Parameter(description = "ID of the address to update", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Updated address data", required = true)
            @RequestBody AddressRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an address", description = "Removes an address from the system by its ID.")
    @ApiResponse(responseCode = "204", description = "Address deleted successfully")
    public void delete(
            @Parameter(description = "ID of the address to delete", required = true)
            @PathVariable("id") Long id) {
        service.delete(id);
    }
}
