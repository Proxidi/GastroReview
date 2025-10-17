package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.UserRoleRequest;
import WebSiters.GastroReview.dto.UserRoleResponse;
import WebSiters.GastroReview.service.UserRoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user-roles")
@Validated
@Tag(name = "User Roles", description = "CRUD operations for user-role assignments")
public class UserRoleController {

    private final UserRoleService service;

    public UserRoleController(UserRoleService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "List user roles", description = "Retrieves a paginated list of user-role assignments.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public Page<UserRoleResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{userId}/{roleId}")
    @Operation(summary = "Get user-role", description = "Retrieves a specific user-role assignment.")
    @ApiResponse(responseCode = "200", description = "Assignment found",
            content = @Content(schema = @Schema(implementation = UserRoleResponse.class)))
    public UserRoleResponse get(
            @Parameter(description = "User ID (UUID)", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Role ID (integer)", required = true)
            @PathVariable Integer roleId
    ) {
        return service.get(userId, roleId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create user-role", description = "Creates a new user-role assignment.")
    @ApiResponse(responseCode = "201", description = "Assignment created successfully",
            content = @Content(schema = @Schema(implementation = UserRoleResponse.class)))
    public UserRoleResponse create(
            @Parameter(description = "User-role payload", required = true)
            @Valid @RequestBody UserRoleRequest in
    ) {
        return service.create(in);
    }

    @PutMapping("/{userId}/{roleId}")
    @Operation(summary = "Update user-role", description = "Updates an existing user-role assignment.")
    @ApiResponse(responseCode = "200", description = "Assignment updated successfully",
            content = @Content(schema = @Schema(implementation = UserRoleResponse.class)))
    public UserRoleResponse update(
            @Parameter(description = "User ID (UUID)", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Role ID (integer)", required = true)
            @PathVariable Integer roleId,
            @Parameter(description = "Updated user-role payload", required = true)
            @Valid @RequestBody UserRoleRequest in
    ) {
        return service.update(userId, roleId, in);
    }

    @DeleteMapping("/{userId}/{roleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete user-role", description = "Deletes a user-role assignment.")
    @ApiResponse(responseCode = "204", description = "Assignment deleted successfully")
    public void delete(
            @Parameter(description = "User ID (UUID)", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Role ID (integer)", required = true)
            @PathVariable Integer roleId
    ) {
        service.delete(userId, roleId);
    }
}
