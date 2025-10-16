package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.UserAchievementRequest;
import WebSiters.GastroReview.dto.UserAchievementResponse;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.model.UserAchievement;
import WebSiters.GastroReview.model.Users;
import WebSiters.GastroReview.repository.UserAchievementRepository;
import WebSiters.GastroReview.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAchievementService {

    private final UserAchievementRepository repo;
    private final UsersRepository usersRepo;


    public Page<UserAchievementResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(Mappers::toResponse);
    }

    public UserAchievementResponse get(Long id) {
        UserAchievement a = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Achievement not found"));
        return Mappers.toResponse(a);
    }


    public UserAchievementResponse create(UserAchievementRequest in) {
        UUID userId = Objects.requireNonNull(in.getUserId(), "userId is required");
        String rawBadge = Objects.requireNonNull(in.getBadge(), "badge is required");

        String badge = rawBadge.trim();
        Integer level = (in.getLevel() != null) ? in.getLevel() : 1;
        Integer stars = (in.getStars() != null) ? in.getStars() : 0;

        log.info("CREATE achievement userId={}, badge='{}', level={}, stars={}", userId, badge, level, stars);

        if (repo.existsForUserAndBadge(userId, badge)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Badge already exists for user");
        }

        Users user = usersRepo.findById(userId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        UserAchievement a = UserAchievement.builder()
                .user(user)
                .badge(badge)
                .level(level)
                .stars(stars)
                .build();

        try {
            a = repo.saveAndFlush(a);
            return Mappers.toResponse(a);
        } catch (DataIntegrityViolationException ex) {
            log.warn("Unique constraint on userId={}, badge='{}' at persist", userId, badge);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Badge already exists for user");
        }
    }


    public UserAchievementResponse update(Long id, UserAchievementRequest in) {
        UserAchievement a = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Achievement not found"));

        UUID targetUserId = (in.getUserId() != null) ? in.getUserId() : a.getUser().getId();

        if (in.getBadge() != null) {
            String newBadge = in.getBadge().trim();
            String currentBadge = a.getBadge() != null ? a.getBadge().trim() : null;

            if (currentBadge == null || !currentBadge.equalsIgnoreCase(newBadge)) {
                if (repo.existsForUserAndBadgeExcluding(targetUserId, newBadge, id)) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Badge already exists for user");
                }
                a.setBadge(newBadge);
            }
        }
        if (in.getUserId() != null && !in.getUserId().equals(a.getUser().getId())) {
            Users newUser = usersRepo.findById(in.getUserId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            a.setUser(newUser);
        }

        if (in.getLevel() != null) a.setLevel(in.getLevel());
        if (in.getStars() != null) a.setStars(in.getStars());

        try {
            a = repo.saveAndFlush(a);
            return Mappers.toResponse(a);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Badge already exists for user");
        }
    }


    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Achievement not found");
        }
        repo.deleteById(id);
    }
}
