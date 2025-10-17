package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.RestaurantRequest;
import WebSiters.GastroReview.dto.RestaurantResponse;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.model.Restaurant;
import WebSiters.GastroReview.model.Users;
import WebSiters.GastroReview.repository.RestaurantRepository;
import WebSiters.GastroReview.repository.UsersRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantService {

    private final RestaurantRepository repo;
    private final UsersRepository usersRepo;

    public RestaurantService(RestaurantRepository repo, UsersRepository usersRepo) {
        this.repo = repo;
        this.usersRepo = usersRepo;
    }

    @Transactional(readOnly = true)
    public Page<RestaurantResponse> list(Pageable pageable) {
        Pageable fixedPageable = Pageable.ofSize(5).withPage(pageable.getPageNumber());
        return repo.findAll(fixedPageable).map(Mappers::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<RestaurantResponse> findByName(String name, Pageable pageable) {
        List<Restaurant> all = repo.findByNameContainingIgnoreCase(name);
        return paginateAndMap(all, pageable);
    }

    @Transactional(readOnly = true)
    public Page<RestaurantResponse> findByOwner(UUID ownerId, Pageable pageable) {
        List<Restaurant> all = repo.findByOwner_Id(ownerId);
        return paginateAndMap(all, pageable);
    }

    @Transactional(readOnly = true)
    public Page<RestaurantResponse> findByCity(String city, Pageable pageable) {
        List<Restaurant> all = repo.findByCityIgnoreCase(city);
        return paginateAndMap(all, pageable);
    }

    @Transactional(readOnly = true)
    public RestaurantResponse get(UUID id) {
        Restaurant r = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));
        return Mappers.toResponse(r);
    }

    @Transactional
    public RestaurantResponse create(RestaurantRequest in) {
        if (repo.existsByNameIgnoreCase(in.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Restaurant name already exists");
        }

        Restaurant r = new Restaurant();
        r.setName(in.getName());
        r.setDescription(in.getDescription());
        r.setPhone(in.getPhone());
        r.setEmail(in.getEmail());

        if (in.getOwnerId() != null) {
            Users owner = usersRepo.findById(in.getOwnerId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner user does not exist"));
            r.setOwner(owner);
        }

        try {
            r = repo.save(r);
            return Mappers.toResponse(r);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid restaurant data");
        }
    }

    @Transactional
    public RestaurantResponse update(UUID id, RestaurantRequest in) {
        Restaurant r = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));

        if (in.getName() != null) r.setName(in.getName());
        if (in.getDescription() != null) r.setDescription(in.getDescription());
        if (in.getPhone() != null) r.setPhone(in.getPhone());
        if (in.getEmail() != null) r.setEmail(in.getEmail());

        if (in.getOwnerId() != null) {
            Users owner = usersRepo.findById(in.getOwnerId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner user does not exist"));
            r.setOwner(owner);
        }

        try {
            r = repo.save(r);
            return Mappers.toResponse(r);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid restaurant data");
        }
    }

    @Transactional
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found");
        }
        repo.deleteById(id);
    }

    private Page<RestaurantResponse> paginateAndMap(List<Restaurant> all, Pageable pageable) {
        int pageSize = 5;
        int currentPage = pageable.getPageNumber();
        int start = currentPage * pageSize;
        int end = Math.min(start + pageSize, all.size());

        List<RestaurantResponse> content = all.subList(Math.min(start, end), end)
                .stream()
                .map(Mappers::toResponse)
                .toList();

        return new PageImpl<>(content, Pageable.ofSize(pageSize).withPage(currentPage), all.size());
    }
}
