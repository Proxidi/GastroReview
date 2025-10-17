package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.UserProfileRequest;
import WebSiters.GastroReview.dto.UserProfileResponse;
import WebSiters.GastroReview.service.UserProfileService;

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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user-profiles")
@Tag(name = "User Profiles", description = "CRUD operations for user profiles")
public class UserProfilesController {

    private final UserProfileService service;

    public UserProfilesController(UserProfileService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "List user profiles", description = "Retrieves a paginated list of user profiles.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public Page<UserProfileResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get profile by user ID", description = "Retrieves a user profile by its associated user ID.")
    @ApiResponse(responseCode = "200", description = "Profile found",
            content = @Content(schema = @Schema(implementation = UserProfileResponse.class)))
    public UserProfileResponse get(
            @Parameter(description = "User ID (UUID)", required = true)
            @PathVariable("userId") UUID userId
    ) {
        return service.get(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create or update profile", description = "Upserts a user profile with the provided data.")
    @ApiResponse(responseCode = "201", description = "Profile created or updated successfully",
            content = @Content(schema = @Schema(implementation = UserProfileResponse.class)))
    public UserProfileResponse upsert(
            @Parameter(description = "Profile payload", required = true)
            @Valid @RequestBody UserProfileRequest in
    ) {
        return service.upsert(in);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update profile", description = "Updates an existing user profile.")
    @ApiResponse(responseCode = "200", description = "Profile updated successfully",
            content = @Content(schema = @Schema(implementation = UserProfileResponse.class)))
    public UserProfileResponse update(
            @Parameter(description = "User ID (UUID)", required = true)
            @PathVariable("userId") UUID userId,
            @Parameter(description = "Updated profile payload", required = true)
            @Valid @RequestBody UserProfileRequest in
    ) {
        return service.update(userId, in);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete profile", description = "Deletes a user profile by user ID.")
    @ApiResponse(responseCode = "204", description = "Profile deleted successfully")
    public void delete(
            @Parameter(description = "User ID (UUID)", required = true)
            @PathVariable("userId") UUID userId
    ) {
        service.delete(userId);
    }
}
