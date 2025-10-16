package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.service.UsersService;
import WebSiters.GastroReview.dto.UserRequest;
import WebSiters.GastroReview.dto.UserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "CRUD operations for Users")
public class UsersController {

    private final UsersService service;

    @GetMapping
    @Operation(summary = "Get all users")
    public List<UserResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public UserResponse get(@PathVariable("id") UUID id) {
        return service.get(id);
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest in) {
        UserResponse created = service.create(in);
        return ResponseEntity.created(URI.create("/api/users/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user")
    public UserResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UserRequest in) {
        return service.update(id, in);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a user by ID")
    public void delete(@PathVariable("id") UUID id) {
        service.delete(id);
    }

    @GetMapping("/search/{email}")
    @Operation(summary = "Get user by email")
    public UserResponse getByEmail(@PathVariable String email) {
        return service.getByEmail(email);
    }
}
