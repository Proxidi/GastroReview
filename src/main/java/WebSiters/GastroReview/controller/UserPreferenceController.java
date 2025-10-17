package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.UserPreferenceRequest;
import WebSiters.GastroReview.dto.UserPreferenceResponse;
import WebSiters.GastroReview.service.UserPreferenceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-preferences")
@Tag(name = "User Preferences", description = "CRUD operations for user preferences")
public class UserPreferenceController {

    private final UserPreferenceService service;

    public UserPreferenceController(UserPreferenceService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "List user preferences", description = "Retrieves a paginated list of user preferences.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public Page<UserPreferenceResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get preference by ID", description = "Retrieves a specific user preference by its ID.")
    @ApiResponse(responseCode = "200", description = "Preference found",
            content = @Content(schema = @Schema(implementation = UserPreferenceResponse.class)))
    public UserPreferenceResponse get(
            @Parameter(description = "Preference ID", required = true)
            @PathVariable("id") Long id
    ) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create preference", description = "Creates a new user preference.")
    @ApiResponse(responseCode = "201", description = "Preference created successfully",
            content = @Content(schema = @Schema(implementation = UserPreferenceResponse.class)))
    public UserPreferenceResponse create(
            @Parameter(description = "Preference payload", required = true)
            @RequestBody UserPreferenceRequest in
    ) {
        return service.create(in);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update preference", description = "Updates an existing user preference.")
    @ApiResponse(responseCode = "200", description = "Preference updated successfully",
            content = @Content(schema = @Schema(implementation = UserPreferenceResponse.class)))
    public UserPreferenceResponse update(
            @Parameter(description = "Preference ID", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Updated preference payload", required = true)
            @RequestBody UserPreferenceRequest in
    ) {
        return service.update(id, in);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete preference", description = "Deletes a user preference by its ID.")
    @ApiResponse(responseCode = "204", description = "Preference deleted successfully")
    public void delete(
            @Parameter(description = "Preference ID", required = true)
            @PathVariable("id") Long id
    ) {
        service.delete(id);
    }
}
