package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.FriendshipRequest;
import WebSiters.GastroReview.dto.FriendshipResponse;
import WebSiters.GastroReview.service.FriendshipService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/friendships")
public class FriendshipController {

    private final FriendshipService service;

    public FriendshipController(FriendshipService service) {
        this.service = service;
    }

    @GetMapping
    public Page<FriendshipResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{followerId}/{followedId}")
    public FriendshipResponse get(@PathVariable UUID followerId, @PathVariable UUID followedId) {
        return service.get(followerId, followedId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FriendshipResponse create(@RequestBody FriendshipRequest in) {
        return service.create(in);
    }

    // No hay update semántico en relaciones de seguimiento
    @DeleteMapping("/{followerId}/{followedId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID followerId, @PathVariable UUID followedId) {
        service.delete(followerId, followedId);
    }
}

