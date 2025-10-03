package WebSiters.GastroReview.controller;

import jakarta.validation.Valid;
import WebSiters.GastroReview.dto.*;
import WebSiters.GastroReview.model.Restaurant;
import WebSiters.GastroReview.model.Users;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.repository.RestaurantRepository;
import WebSiters.GastroReview.repository.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantsController {

    private final RestaurantRepository repo;
    private final UsersRepository usersRepo;

    public RestaurantsController(RestaurantRepository repo, UsersRepository usersRepo) {
        this.repo = repo;
        this.usersRepo = usersRepo;
    }

    @GetMapping
    public Page<RestaurantResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(Mappers::toDto);
    }

    @GetMapping("/{id}")
    public RestaurantResponse get(@PathVariable UUID id) {
        Restaurant r = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));
        return Mappers.toDto(r);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantResponse create(@Valid @RequestBody RestaurantRequest in) {
        Restaurant r = new Restaurant();
        r.setName(in.name());
        r.setDescription(in.description());
        r.setPhone(in.phone());
        r.setEmail(in.email());
        if (in.ownerId() != null) {
            Users owner = usersRepo.findById(in.ownerId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner user does not exist"));
            r.setOwner(owner);
        }
        r = repo.save(r);
        return Mappers.toDto(r);
    }

    @PutMapping("/{id}")
    public RestaurantResponse update(@PathVariable UUID id, @Valid @RequestBody RestaurantRequest in) {
        Restaurant r = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));
        if (in.name() != null) r.setName(in.name());
        if (in.description() != null) r.setDescription(in.description());
        if (in.phone() != null) r.setPhone(in.phone());
        if (in.email() != null) r.setEmail(in.email());
        if (in.ownerId() != null) {
            Users owner = usersRepo.findById(in.ownerId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner user does not exist"));
            r.setOwner(owner);
        } else if (in.ownerId() == null) {
            r.setOwner(null);
        }
        r = repo.save(r);
        return Mappers.toDto(r);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found");
        }
        repo.deleteById(id);
    }
}



