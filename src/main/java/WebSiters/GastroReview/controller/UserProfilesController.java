package WebSiters.GastroReview.controller;

import jakarta.validation.Valid;
import WebSiters.GastroReview.dto.*;
import WebSiters.GastroReview.model.UserProfile;
import WebSiters.GastroReview.model.Users;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.repository.UserProfileRepository;
import WebSiters.GastroReview.repository.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/user-profiles")
public class UserProfilesController {

    private final UserProfileRepository repo;
    private final UsersRepository usersRepo;

    public UserProfilesController(UserProfileRepository repo, UsersRepository usersRepo) {
        this.repo = repo;
        this.usersRepo = usersRepo;
    }

    @GetMapping
    public Page<UserProfileResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(Mappers::toDto);
    }

    @GetMapping("/{userId}")
    public UserProfileResponse get(@PathVariable UUID userId) {
        UserProfile p = repo.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User profile not found"));
        return Mappers.toDto(p);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserProfileResponse upsert(@Valid @RequestBody UserRequest in) {
        Users user = usersRepo.findById(in.userId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist"));
        UserProfile p = repo.findById(in.userId()).orElse(new UserProfile());
        p.setUser(user);
        p.setPhotoUrl(in.photoUrl());
        p.setName(in.name());
        if (in.active() != null) p.setActive(in.active());
        p.setBio(in.bio());
        p = repo.save(p);
        return Mappers.toDto(p);
    }

    @PutMapping("/{userId}")
    public UserProfileResponse update(@PathVariable UUID userId, @Valid @RequestBody UserProfileRequest in) {
        UserProfile p = repo.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User profile not found"));
        if (in.photoUrl() != null) p.setPhotoUrl(in.photoUrl());
        if (in.name() != null) p.setName(in.name());
        if (in.active() != null) p.setActive(in.active());
        if (in.bio() != null) p.setBio(in.bio());
        p = repo.save(p);
        return Mappers.toDto(p);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID userId) {
        if (!repo.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User profile not found");
        }
        repo.deleteById(userId);
    }
}




