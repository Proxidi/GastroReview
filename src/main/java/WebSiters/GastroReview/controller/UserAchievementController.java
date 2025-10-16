package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.UserAchievementRequest;
import WebSiters.GastroReview.dto.UserAchievementResponse;
import WebSiters.GastroReview.service.UserAchievementService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-achievements")
public class UserAchievementController {

    private final UserAchievementService service;

    public UserAchievementController(UserAchievementService service) {
        this.service = service;
    }

    @GetMapping
    public Page<UserAchievementResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{id}")
    public UserAchievementResponse get(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserAchievementResponse create(@RequestBody UserAchievementRequest in) {
        return service.create(in);
    }

    @PutMapping("/{id}")
    public UserAchievementResponse update(@PathVariable("id") Long id,
                                          @RequestBody UserAchievementRequest in) {
        return service.update(id, in);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}
