package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.FriendshipRequest;
import WebSiters.GastroReview.dto.FriendshipResponse;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.model.Friendship;
import WebSiters.GastroReview.model.FriendshipId;
import WebSiters.GastroReview.repository.FriendshipRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class FriendshipService {

    private final FriendshipRepository repo;

    public FriendshipService(FriendshipRepository repo) {
        this.repo = repo;
    }

    public Page<FriendshipResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(Mappers::toResponse);
    }

    public FriendshipResponse get(UUID followerId, UUID followedId) {
        Friendship f = repo.findById(new FriendshipId(followerId, followedId)).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Friendship not found"));
        return Mappers.toResponse(f);
    }

    public FriendshipResponse create(FriendshipRequest in) {
        if (in.getFollowerId().equals(in.getFollowedId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Follower and followed must be different");
        }
        FriendshipId id = new FriendshipId(in.getFollowerId(), in.getFollowedId());
        if (repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Already following");
        }
        Friendship f = Friendship.builder().id(id).build();
        try {
            f = repo.save(f);
            return Mappers.toResponse(f);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Already following");
        }
    }

    // No hay update semántico; sólo eliminar relación.
    public void delete(UUID followerId, UUID followedId) {
        FriendshipId id = new FriendshipId(followerId, followedId);
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Friendship not found");
        }
        repo.deleteById(id);
    }
}

