package WebSiters.GastroReview.controller;

import jakarta.validation.Valid;
import WebSiters.GastroReview.dto.RoleRequest;
import WebSiters.GastroReview.dto.RoleResponse;
import WebSiters.GastroReview.service.RoleService;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "CRUD operations for User Roles")
public class RolesController {

    private final RoleService service;

    public RolesController(RoleService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "List roles", description = "Retrieves a paginated list of system roles.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public Page<RoleResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get role by ID", description = "Fetches details of a specific role by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role found"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public RoleResponse get(
            @Parameter(description = "Role ID", required = true)
            @PathVariable("id") Integer id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create role", description = "Creates a new role in the system.")
    @ApiResponse(responseCode = "201", description = "Role created successfully")
    public RoleResponse create(
            @Parameter(description = "Role request body", required = true)
            @Valid @RequestBody RoleRequest in) {
        return service.create(in);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update role", description = "Updates an existing role.")
    @ApiResponse(responseCode = "200", description = "Role updated successfully")
    public RoleResponse update(
            @Parameter(description = "Role ID", required = true)
            @PathVariable("id") Integer id,
            @Valid @RequestBody RoleRequest in) {
        return service.update(id, in);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete role", description = "Deletes a role by its ID.")
    @ApiResponse(responseCode = "204", description = "Role deleted successfully")
    public void delete(
            @Parameter(description = "Role ID", required = true)
            @PathVariable("id") Integer id) {
        service.delete(id);
    }
}
