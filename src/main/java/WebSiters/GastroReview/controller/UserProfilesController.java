package WebSiters.GastroReview.controller;

import jakarta.validation.Valid;
import WebSiters.GastroReview.dto.UserProfileRequest;
import WebSiters.GastroReview.dto.UserProfileResponse;
import WebSiters.GastroReview.service.UserProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user-profiles")
public class UserProfilesController {

    private final UserProfileService service;

    public UserProfilesController(UserProfileService service) {
        this.service = service;
    }

    @GetMapping
    public Page<UserProfileResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{userId}")
    public UserProfileResponse get(@PathVariable("userId") UUID userId) {
        return service.get(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserProfileResponse upsert(@Valid @RequestBody UserProfileRequest in) {
        return service.upsert(in);
    }

    @PutMapping("/{userId}")
    public UserProfileResponse update(@PathVariable("userId") UUID userId,
                                      @Valid @RequestBody UserProfileRequest in) {
        return service.update(userId, in);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("userId") UUID userId) {
        service.delete(userId);
    }
}
