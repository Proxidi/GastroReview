package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.RestaurantCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantCategoryRepository extends JpaRepository<RestaurantCategory, Integer> {

    Optional<RestaurantCategory> findByName(String name);

    boolean existsByName(String name);
}