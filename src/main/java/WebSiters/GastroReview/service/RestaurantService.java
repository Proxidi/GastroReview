package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.RestaurantRequest;
import WebSiters.GastroReview.dto.RestaurantResponse;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.model.Restaurant;
import WebSiters.GastroReview.model.Users;
import WebSiters.GastroReview.repository.RestaurantRepository;
import WebSiters.GastroReview.repository.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class RestaurantService {

    private final RestaurantRepository repo;
    private final UsersRepository usersRepo;

    public RestaurantService(RestaurantRepository repo, UsersRepository usersRepo) {
        this.repo = repo;
        this.usersRepo = usersRepo;
    }

    public Page<RestaurantResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(Mappers::toResponse);
    }

    public RestaurantResponse get(UUID id) {
        Restaurant r = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));
        return Mappers.toResponse(r);
    }

    public RestaurantResponse create(RestaurantRequest in) {
        Restaurant r = new Restaurant();
        r.setName(in.getName());
        r.setDescription(in.getDescription());
        r.setPhone(in.getPhone());
        r.setEmail(in.getEmail());

        if (in.getOwnerId() != null) {
            Users owner = usersRepo.findById(in.getOwnerId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner user does not exist"));
            r.setOwner(owner);
        }

        r = repo.save(r);
        return Mappers.toResponse(r);
    }

    public RestaurantResponse update(UUID id, RestaurantRequest in) {
        Restaurant r = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        if (in.getName() != null) r.setName(in.getName());
        if (in.getDescription() != null) r.setDescription(in.getDescription());
        if (in.getPhone() != null) r.setPhone(in.getPhone());
        if (in.getEmail() != null) r.setEmail(in.getEmail());

        if (in.getOwnerId() != null) {
            Users owner = usersRepo.findById(in.getOwnerId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner user does not exist"));
            r.setOwner(owner);
        }

        r = repo.save(r);
        return Mappers.toResponse(r);
    }

    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found");
        }
        repo.deleteById(id);
    }
}
