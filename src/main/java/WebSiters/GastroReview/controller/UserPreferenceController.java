package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.UserPreferenceRequest;
import WebSiters.GastroReview.dto.UserPreferenceResponse;
import WebSiters.GastroReview.service.UserPreferenceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-preferences")
public class UserPreferenceController {

    private final UserPreferenceService service;

    public UserPreferenceController(UserPreferenceService service) {
        this.service = service;
    }

    @GetMapping
    public Page<UserPreferenceResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{id}")
    public UserPreferenceResponse get(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserPreferenceResponse create(@RequestBody UserPreferenceRequest in) {
        return service.create(in);
    }

    @PutMapping("/{id}")
    public UserPreferenceResponse update(@PathVariable("id") Long id,
                                         @RequestBody UserPreferenceRequest in) {
        return service.update(id, in);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}
