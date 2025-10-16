package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.UserRoleRequest;
import WebSiters.GastroReview.dto.UserRoleResponse;
import WebSiters.GastroReview.service.UserRoleService;
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
public class UserRoleController {

    private final UserRoleService service;

    public UserRoleController(UserRoleService service) {
        this.service = service;
    }

    @GetMapping
    public Page<UserRoleResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{userId}/{roleId}")
    public UserRoleResponse get(@PathVariable UUID userId, @PathVariable Integer roleId) {
        return service.get(userId, roleId);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserRoleResponse create(@Valid @RequestBody UserRoleRequest in) {
        return service.create(in);
    }

    @PutMapping("/{userId}/{roleId}")
    public UserRoleResponse update(
            @PathVariable UUID userId,
            @PathVariable Integer roleId,
            @Valid @RequestBody UserRoleRequest in) {

        return service.update(userId, roleId, in);
    }



    @DeleteMapping("/{userId}/{roleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID userId, @PathVariable Integer roleId) {
        service.delete(userId, roleId);
    }
}
