package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.UserAchievementRequest;
import WebSiters.GastroReview.dto.UserAchievementResponse;
import WebSiters.GastroReview.service.UserAchievementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-achievements")
@Tag(name = "User Achievements", description = "CRUD operations for user achievements")
public class UserAchievementController {

    private final UserAchievementService service;

    public UserAchievementController(UserAchievementService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "List user achievements", description = "Retrieves a paginated list of user achievements.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public Page<UserAchievementResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get achievement by ID", description = "Retrieves a specific user achievement by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Achievement found",
                    content = @Content(schema = @Schema(implementation = UserAchievementResponse.class))),
            @ApiResponse(responseCode = "404", description = "Achievement not found")
    })
    public UserAchievementResponse get(
            @Parameter(description = "Achievement ID", required = true)
            @PathVariable("id") Long id
    ) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create achievement", description = "Creates a new user achievement.")
    @ApiResponse(responseCode = "201", description = "Achievement created successfully",
            content = @Content(schema = @Schema(implementation = UserAchievementResponse.class)))
    public UserAchievementResponse create(
            @Parameter(description = "Achievement payload", required = true)
            @RequestBody UserAchievementRequest in
    ) {
        return service.create(in);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update achievement", description = "Updates an existing user achievement.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Achievement updated successfully",
                    content = @Content(schema = @Schema(implementation = UserAchievementResponse.class))),
            @ApiResponse(responseCode = "404", description = "Achievement not found")
    })
    public UserAchievementResponse update(
            @Parameter(description = "Achievement ID", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Updated achievement payload", required = true)
            @RequestBody UserAchievementRequest in
    ) {
        return service.update(id, in);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete achievement", description = "Deletes a user achievement by its ID.")
    @ApiResponse(responseCode = "204", description = "Achievement deleted successfully")
    public void delete(
            @Parameter(description = "Achievement ID", required = true)
            @PathVariable("id") Long id
    ) {
        service.delete(id);
    }
}
