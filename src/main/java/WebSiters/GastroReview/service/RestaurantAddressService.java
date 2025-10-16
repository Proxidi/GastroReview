package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.RestaurantAddressRequest;
import WebSiters.GastroReview.dto.RestaurantAddressResponse;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.model.RestaurantAddress;
import WebSiters.GastroReview.model.RestaurantAddressId;
import WebSiters.GastroReview.repository.AddressRepository;
import WebSiters.GastroReview.repository.RestaurantAddressRepository;
import WebSiters.GastroReview.repository.RestaurantRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class RestaurantAddressService {

    private final RestaurantAddressRepository repo;
    private final RestaurantRepository restaurantRepo;
    private final AddressRepository addressRepo;

    public RestaurantAddressService(RestaurantAddressRepository repo,
                                    RestaurantRepository restaurantRepo,
                                    AddressRepository addressRepo) {
        this.repo = repo;
        this.restaurantRepo = restaurantRepo;
        this.addressRepo = addressRepo;
    }

    public Page<RestaurantAddressResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(Mappers::toResponse);
    }

    public RestaurantAddressResponse get(UUID restaurantId, Long addressId) {
        var id = new RestaurantAddressId(restaurantId, addressId);
        var ra = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "RestaurantAddress not found"));
        return Mappers.toResponse(ra);
    }

    @Transactional
    public RestaurantAddressResponse create(RestaurantAddressRequest in) {
        var id = new RestaurantAddressId(in.getRestaurantId(), in.getAddressId());
        if (repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Restaurant already linked to this address");
        }

        // Cargar asociaciones requeridas por el mapeo con @MapsId
        var restaurant = restaurantRepo.findById(in.getRestaurantId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));
        var address = addressRepo.findById(in.getAddressId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        var ra = RestaurantAddress.builder()
                .id(id)
                .restaurant(restaurant)
                .address(address)
                .isPrimary(Boolean.TRUE.equals(in.getIsPrimary()))
                .branchName(in.getBranchName())
                .build();

        try {
            if (ra.isPrimary()) {
                repo.findByIdRestaurantIdAndIsPrimaryTrue(in.getRestaurantId())
                        .ifPresent(prev -> { prev.setPrimary(false); repo.save(prev); });
            }
            ra = repo.saveAndFlush(ra);
            return Mappers.toResponse(ra);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Primary address already set for restaurant");
        }
    }

    @Transactional
    public RestaurantAddressResponse update(UUID restaurantId, Long addressId, RestaurantAddressRequest in) {
        var id = new RestaurantAddressId(restaurantId, addressId);
        var ra = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "RestaurantAddress not found"));

        if (in.getBranchName() != null) ra.setBranchName(in.getBranchName());

        if (in.getIsPrimary() != null) {
            if (in.getIsPrimary()) {
                final RestaurantAddressId currentId = ra.getId();
                repo.findByIdRestaurantIdAndIsPrimaryTrue(restaurantId)
                        .ifPresent(prev -> {
                            if (!prev.getId().equals(currentId)) {
                                prev.setPrimary(false);
                                repo.save(prev);
                            }
                        });
                ra.setPrimary(true);
            } else {
                ra.setPrimary(false);
            }
        }

        try {
            ra = repo.saveAndFlush(ra);
            return Mappers.toResponse(ra);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Primary address already set for restaurant");
        }
    }

    public void delete(UUID restaurantId, Long addressId) {
        var id = new RestaurantAddressId(restaurantId, addressId);
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "RestaurantAddress not found");
        }
        repo.deleteById(id);
    }
}
