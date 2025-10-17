package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.service.UsersService;
import WebSiters.GastroReview.dto.UserRequest;
import WebSiters.GastroReview.dto.UserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "CRUD operations for Users")
public class UsersController {

    private final UsersService service;

    // ============================
    // GET ALL USERS
    // ============================
    @GetMapping
    @Operation(summary = "Get all users",
            description = "Retrieves a list of all registered users in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public List<UserResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID",
            description = "Fetches a specific user based on their unique identifier (UUID).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    public UserResponse get(
            @Parameter(description = "Unique identifier of the user", required = true)
            @PathVariable("id") UUID id) {
        return service.get(id);
    }

    @PostMapping
    @Operation(summary = "Create a new user",
            description = "Registers a new user in the system using the provided request body.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content)
    })
    public ResponseEntity<UserResponse> create(
            @Parameter(description = "User data for creation", required = true)
            @Valid @RequestBody UserRequest in) {

        UserResponse created = service.create(in);
        return ResponseEntity.created(URI.create("/api/users/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user",
            description = "Updates the information of an existing user identified by their UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    public UserResponse update(
            @Parameter(description = "UUID of the user to update", required = true)
            @PathVariable("id") UUID id,
            @Parameter(description = "Updated user data", required = true)
            @Valid @RequestBody UserRequest in) {

        return service.update(id, in);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a user by ID",
            description = "Removes a user from the system using their unique UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    public void delete(
            @Parameter(description = "UUID of the user to delete", required = true)
            @PathVariable("id") UUID id) {

        service.delete(id);
    }

    @GetMapping("/search/{email}")
    @Operation(summary = "Get user by email",
            description = "Retrieves a user using their unique email address.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    public UserResponse getByEmail(
            @Parameter(description = "Email of the user to search", required = true)
            @PathVariable String email) {
        return service.getByEmail(email);
    }
}
