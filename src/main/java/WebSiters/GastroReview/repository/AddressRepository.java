package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    
    List<Address> findByCity(String city);
    
    List<Address> findByCountry(String country);
    
    List<Address> findByCityAndCountry(String city, String country);
    
    boolean existsByStreetAndCityAndCountry(String street, String city, String country);
}