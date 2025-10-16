package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.UserPreference;
import WebSiters.GastroReview.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {
    boolean existsByUserAndPrefKey(Users user, String prefKey);

    boolean existsByUser_IdAndPrefKeyIgnoreCase(UUID userId, String key);

    boolean existsByUser_IdAndPrefKeyIgnoreCaseAndIdNot(UUID targetUserId, String key, Long id);
}
