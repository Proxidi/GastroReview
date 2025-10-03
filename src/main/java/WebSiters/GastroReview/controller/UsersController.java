package WebSiters.GastroReview.controller;

import jakarta.validation.Valid;
import WebSiters.GastroReview.dto.*;
import WebSiters.GastroReview.model.Users;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.repository.UsersRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersRepository repo;

    public UsersController(UsersRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public Page<UserResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(Mappers::toDto);
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable UUID id) {
        Users u = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return Mappers.toDto(u);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody UserRequest in) {
        Users u = Users.builder()
                .email(in.email())
                .hashPassword(in.hashPassword())
                .build();
        try {
            u = repo.save(u);
            return Mappers.toDto(u);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable UUID id, @Valid @RequestBody UserRequest in) {
        Users u = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (in.email() != null) u.setEmail(in.email());
        if (in.hashPassword() != null) u.setHashPassword(in.hashPassword());
        try {
            u = repo.save(u);
            return Mappers.toDto(u);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        repo.deleteById(id);
    }
}


