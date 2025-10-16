package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.Friendship;
import WebSiters.GastroReview.model.FriendshipId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipRepository extends JpaRepository<Friendship, FriendshipId> {
}
