package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.FriendshipRequest;
import WebSiters.GastroReview.dto.FriendshipResponse;
import WebSiters.GastroReview.service.FriendshipService;

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
@RequestMapping("/api/friendships")
@Tag(name = "Friendships", description = "Manage user following relationships")
public class FriendshipController {

    private final FriendshipService service;

    public FriendshipController(FriendshipService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "List friendships", description = "Retrieves a paginated list of all user follow relationships.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public Page<FriendshipResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{followerId}/{followedId}")
    @Operation(summary = "Get friendship", description = "Retrieves a specific following relationship by follower and followed IDs.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Friendship found",
                    content = @Content(schema = @Schema(implementation = FriendshipResponse.class))),
            @ApiResponse(responseCode = "404", description = "Friendship not found")
    })
    public FriendshipResponse get(
            @Parameter(description = "Follower user ID", required = true)
            @PathVariable UUID followerId,
            @Parameter(description = "Followed user ID", required = true)
            @PathVariable UUID followedId) {
        return service.get(followerId, followedId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create friendship", description = "Creates a new following relationship between users.")
    @ApiResponse(responseCode = "201", description = "Friendship created successfully")
    public FriendshipResponse create(
            @Parameter(description = "Friendship request body", required = true)
            @RequestBody FriendshipRequest in) {
        return service.create(in);
    }

    @DeleteMapping("/{followerId}/{followedId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete friendship", description = "Removes a following relationship between two users.")
    @ApiResponse(responseCode = "204", description = "Friendship deleted successfully")
    public void delete(
            @Parameter(description = "Follower user ID", required = true)
            @PathVariable UUID followerId,
            @Parameter(description = "Followed user ID", required = true)
            @PathVariable UUID followedId) {
        service.delete(followerId, followedId);
    }
}
