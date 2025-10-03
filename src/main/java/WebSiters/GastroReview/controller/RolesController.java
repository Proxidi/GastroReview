package WebSiters.GastroReview.controller;

import jakarta.validation.Valid;
import WebSiters.GastroReview.dto.*;
import WebSiters.GastroReview.model.Role;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.repository.RoleRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    private final RoleRepository repo;

    public RolesController(RoleRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public Page<RoleResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(Mappers::toDto);
    }

    @GetMapping("/{id}")
    public RoleResponse get(@PathVariable Integer id) {
        Role r = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
        return Mappers.toDto(r);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleResponse create(@Valid @RequestBody RoleRequest in) {
        if (repo.existsByNameIgnoreCase(in.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role name already exists");
        }
        Role r = Role.builder().name(in.name()).description(in.description()).build();
        try {
            r = repo.save(r);
            return Mappers.toDto(r);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role name already exists");
        }
    }

    @PutMapping("/{id}")
    public RoleResponse update(@PathVariable Integer id, @Valid @RequestBody RoleRequest in) {
        Role r = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
        if (in.name() != null) r.setName(in.name());
        if (in.description() != null) r.setDescription(in.description());
        try {
            r = repo.save(r);
            return Mappers.toDto(r);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role name already exists");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found");
        }
        repo.deleteById(id);
    }
}

