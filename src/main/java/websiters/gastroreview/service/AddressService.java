package websiters.gastroreview.service;

import websiters.gastroreview.dto.AddressRequest;
import websiters.gastroreview.dto.AddressResponse;
import websiters.gastroreview.mapper.Mappers;
import websiters.gastroreview.model.Address;
import websiters.gastroreview.repository.AddressRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AddressService {

    private final AddressRepository repo;

    public AddressService(AddressRepository repo) {
        this.repo = repo;
    }

    public Page<AddressResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(Mappers::toResponse);
    }

    public AddressResponse get(UUID id) {
        Address a = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));
        return Mappers.toResponse(a);
    }

    public AddressResponse update(UUID id, AddressRequest in) {
        Address a = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        if (in.getStreet() != null) a.setStreet(in.getStreet());
        if (in.getSite() != null) a.setSite(in.getSite());
        if (in.getNeighborhood() != null) a.setNeighborhood(in.getNeighborhood());
        if (in.getCity() != null) a.setCity(in.getCity());
        if (in.getState_region() != null) a.setStateRegion(in.getState_region());
        if (in.getPostal_code() != null) a.setPostalCode(in.getPostal_code());
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

    public Page<AddressResponse> findByCity(String city, Pageable pageable) {
        return repo.findByCityIgnoreCase(city, pageable)
                .map(Mappers::toResponse);
    }

    public Page<AddressResponse> findByCountry(String country, Pageable pageable) {
        return repo.findByCountryIgnoreCase(country, pageable)
                .map(Mappers::toResponse);
    }

    public Page<AddressResponse> findByStreet(String street, Pageable pageable) {
        return repo.findByStreetContainingIgnoreCase(street, pageable)
                .map(Mappers::toResponse);
    }

    public Page<AddressResponse> findByCityAndCountry(String city, String country, Pageable pageable) {
        return repo.findByCityIgnoreCaseAndCountryIgnoreCase(city, country, pageable)
                .map(Mappers::toResponse);
    }
}
