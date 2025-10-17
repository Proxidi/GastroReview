package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.RestaurantAddress;
import WebSiters.GastroReview.model.RestaurantAddressId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantAddressRepository extends JpaRepository<RestaurantAddress, RestaurantAddressId> {

    Optional<RestaurantAddress> findByIdRestaurantIdAndIsPrimaryTrue(UUID restaurantId);

    List<RestaurantAddress> findByRestaurantId(UUID restaurantId);

    List<RestaurantAddress> findByAddressId(UUID addressId);

    List<RestaurantAddress> findByIsPrimaryTrue();

    boolean existsByRestaurantIdAndAddressId(UUID restaurantId, UUID addressId);
}
