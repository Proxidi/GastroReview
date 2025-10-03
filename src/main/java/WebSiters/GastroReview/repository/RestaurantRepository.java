package WebSiters.GastroReview.repository;


import WebSiters.GastroReview.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
}
