package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.UserPreferenceRequest;
import WebSiters.GastroReview.dto.UserPreferenceResponse;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.model.UserPreference;
import WebSiters.GastroReview.model.Users;
import WebSiters.GastroReview.repository.UserPreferenceRepository;
import WebSiters.GastroReview.repository.UsersRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class UserPreferenceService {

    private final UserPreferenceRepository repo;
    private final UsersRepository usersRepo;

    public UserPreferenceService(UserPreferenceRepository repo, UsersRepository usersRepo) {
        this.repo = repo;
        this.usersRepo = usersRepo;
    }

    @Transactional(readOnly = true)
    public Page<UserPreferenceResponse> list(UUID userId, String prefKey, Pageable pageable) {
        Page<UserPreference> preferences;

        if (userId != null && prefKey != null && !prefKey.isBlank()) {
            preferences = repo.findByUser_IdAndPrefKeyContainingIgnoreCase(userId, prefKey.trim(), pageable);
        } else if (userId != null) {
            preferences = repo.findByUser_Id(userId, pageable);
        } else if (prefKey != null && !prefKey.isBlank()) {
            preferences = repo.findByPrefKeyContainingIgnoreCase(prefKey.trim(), pageable);
        } else {
            preferences = repo.findAll(pageable);
        }

        return preferences.map(Mappers::toResponse);
    }

    @Transactional(readOnly = true)
    public UserPreferenceResponse get(UUID id) {
        UserPreference p = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Preference not found"));
        return Mappers.toResponse(p);
    }

    @Transactional
    public UserPreferenceResponse create(UserPreferenceRequest in) {
        UUID userId = in.getUserId();

        if (repo.existsByUser_IdAndPrefKeyIgnoreCase(userId, in.getPrefKey())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Preference key already exists for user");
        }

        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        UserPreference p = UserPreference.builder()
                .user(user)
                .prefKey(in.getPrefKey())
                .value(in.getValue())
                .build();

        try {
            p = repo.save(p);
            return Mappers.toResponse(p);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Preference key already exists for user");
        }
    }

    @Transactional
    public UserPreferenceResponse update(UUID id, UserPreferenceRequest in) {
        UserPreference p = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Preference not found"));

        UUID targetUserId = (in.getUserId() != null) ? in.getUserId() : p.getUser().getId();

        if (in.getPrefKey() != null && !in.getPrefKey().equalsIgnoreCase(p.getPrefKey())) {
            if (repo.existsByUser_IdAndPrefKeyIgnoreCaseAndIdNot(targetUserId, in.getPrefKey(), id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Preference key already exists for user");
            }
            p.setPrefKey(in.getPrefKey());
        }

        if (in.getUserId() != null) {
            Users newUser = usersRepo.findById(in.getUserId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            p.setUser(newUser);
        }

        if (in.getValue() != null) p.setValue(in.getValue());

        try {
            p = repo.save(p);
            return Mappers.toResponse(p);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Preference key already exists for user");
        }
    }

    @Transactional
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Preference not found");
        }
        repo.deleteById(id);
    }
}
