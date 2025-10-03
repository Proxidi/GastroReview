package WebSiters.GastroReview.repository;


import WebSiters.GastroReview.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsByNameIgnoreCase(String name);
}
