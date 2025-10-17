package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<Users, UUID> {

    Optional<Users> findByEmail(String email);

    Page<Users> findByEmailContainingIgnoreCase(String email, Pageable pageable);
}
