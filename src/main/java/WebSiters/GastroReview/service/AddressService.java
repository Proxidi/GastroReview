package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.AddressRequest;
import WebSiters.GastroReview.dto.AddressResponse;
import WebSiters.GastroReview.model.Address;
import WebSiters.GastroReview.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository repo;

    public AddressService(AddressRepository repo) {
        this.repo = repo;
    }

    public List<AddressResponse> listar() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public List<AddressResponse> listarPorCiudad(String city) {
        return repo.findByCity(city).stream().map(this::toResponse).toList();
    }

    public List<AddressResponse> listarPorPais(String country) {
        return repo.findByCountry(country).stream().map(this::toResponse).toList();
    }

    public AddressResponse obtener(Long id) {
        Address address = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Address not found: " + id));
        return toResponse(address);
    }

    public AddressResponse crear(AddressRequest req) {
        if (repo.existsByStreetAndCityAndCountry(req.street(), req.city(), req.country())) {
            throw new IllegalArgumentException(
                "Address already exists with this street, city and country");
        }

        Address address = Address.builder()
                .street(req.street())
                .site(req.site())
                .neighborhood(req.neighborhood())
                .city(req.city())
                .stateRegion(req.stateRegion())
                .postalCode(req.postalCode())
                .country(req.country())
                .latitude(req.latitude())
                .longitude(req.longitude())
                .build();
        return toResponse(repo.save(address));
    }

    public AddressResponse actualizar(Long id, AddressRequest req) {
        Address existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Address not found: " + id));

        existing.setStreet(req.street());
        existing.setSite(req.site());
        existing.setNeighborhood(req.neighborhood());
        existing.setCity(req.city());
        existing.setStateRegion(req.stateRegion());
        existing.setPostalCode(req.postalCode());
        existing.setCountry(req.country());
        existing.setLatitude(req.latitude());
        existing.setLongitude(req.longitude());
        
        return toResponse(repo.save(existing));
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Address not found: " + id);
        }
        repo.deleteById(id);
    }

    private AddressResponse toResponse(Address address) {
        return new AddressResponse(
            address.getId(),
            address.getStreet(),
            address.getSite(),
            address.getNeighborhood(),
            address.getCity(),
            address.getStateRegion(),
            address.getPostalCode(),
            address.getCountry(),
            address.getLatitude(),
            address.getLongitude(),
            address.getCreatedAt()
        );
    }
}