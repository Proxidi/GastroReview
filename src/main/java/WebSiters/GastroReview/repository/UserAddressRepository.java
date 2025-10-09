package WebSiters.GastroReview.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserAddressRepository<UserAddress> extends JpaRepository<UserAddress, Object> {
    
    List<UserAddress> findByUserId(UUID userId);
    
    Optional<UserAddress> findByUserIdAndAddressId(UUID userId, Long addressId);
    
    void deleteByUserIdAndAddressId(UUID userId, Long addressId);
    
    boolean existsByUserIdAndAddressId(UUID userId, Long addressId);
}