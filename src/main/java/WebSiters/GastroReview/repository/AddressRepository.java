package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
