package WebSiters.GastroReview.service;


import WebSiters.GastroReview.dto.AddressRequest;
import WebSiters.GastroReview.dto.AddressResponse;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.model.Address;
import WebSiters.GastroReview.repository.AddressRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AddressService {

    private final AddressRepository repo;

    public AddressService(AddressRepository repo) {
        this.repo = repo;
    }

    public Page<AddressResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(Mappers::toResponse);
    }

    public AddressResponse get(Long id) {
        Address a = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));
        return Mappers.toResponse(a);
    }

    public AddressResponse create(AddressRequest in) {
        Address a = Address.builder()
                .street(in.getStreet())
                .site(in.getSite())
                .neighborhood(in.getNeighborhood())
                .city(in.getCity())
                .stateRegion(in.getStateRegion())
                .postalCode(in.getPostalCode())
                .country(in.getCountry() != null ? in.getCountry() : "MX")
                .latitude(in.getLatitude())
                .longitude(in.getLongitude())
                .build();
        try {
            a = repo.save(a);
            return Mappers.toResponse(a);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid address data");
        }
    }

    public AddressResponse update(Long id, AddressRequest in) {
        Address a = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));
        if (in.getStreet() != null) a.setStreet(in.getStreet());
        if (in.getSite() != null) a.setSite(in.getSite());
        if (in.getNeighborhood() != null) a.setNeighborhood(in.getNeighborhood());
        if (in.getCity() != null) a.setCity(in.getCity());
        if (in.getStateRegion() != null) a.setStateRegion(in.getStateRegion());
        if (in.getPostalCode() != null) a.setPostalCode(in.getPostalCode());
        if (in.getCountry() != null) a.setCountry(in.getCountry());
        if (in.getLatitude() != null) a.setLatitude(in.getLatitude());
        if (in.getLongitude() != null) a.setLongitude(in.getLongitude());
        try {
            a = repo.save(a);
            return Mappers.toResponse(a);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid address data");
        }
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found");
        }
        repo.deleteById(id);
    }
}

