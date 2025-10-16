package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.UserRole;
import WebSiters.GastroReview.model.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}
