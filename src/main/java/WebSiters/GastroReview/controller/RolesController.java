package WebSiters.GastroReview.controller;

import jakarta.validation.Valid;
import WebSiters.GastroReview.dto.RoleRequest;
import WebSiters.GastroReview.dto.RoleResponse;
import WebSiters.GastroReview.service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    private final RoleService service;

    public RolesController(RoleService service) {
        this.service = service;
    }

    @GetMapping
    public Page<RoleResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/{id}")
    public RoleResponse get(@PathVariable("id") Integer id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleResponse create(@Valid @RequestBody RoleRequest in) {
        return service.create(in);
    }

    @PutMapping("/{id}")
    public RoleResponse update(@PathVariable("id") Integer id,
                               @Valid @RequestBody RoleRequest in) {
        return service.update(id, in);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        service.delete(id);
    }
}
