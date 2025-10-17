package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.UserRequest;
import WebSiters.GastroReview.dto.UserResponse;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.model.Users;
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
public class UsersService {

    private final UsersRepository repo;

    public UsersService(UsersRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> list(String email, Pageable pageable) {
        Page<Users> users;

        if (email != null && !email.isBlank()) {
            users = repo.findByEmailContainingIgnoreCase(email.trim(), pageable);
        } else {
            users = repo.findAll(pageable);
        }

        return users.map(u -> Mappers.toResponse(u).build());
    }

    @Transactional(readOnly = true)
    public UserResponse getByEmail(String email) {
        Users u = repo.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return Mappers.toResponse(u).build();
    }

    @Transactional(readOnly = true)
    public UserResponse get(UUID id) {
        Users u = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return Mappers.toResponse(u).build();
    }

    @Transactional
    public UserResponse create(UserRequest in) {
        Users u = Users.builder()
                .email(in.getEmail())
                .hashPassword(in.getHashPassword())
                .build();
        try {
            u = repo.save(u);
            return Mappers.toResponse(u).build();
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
    }

    @Transactional
    public UserResponse update(UUID id, UserRequest in) {
        Users u = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (in.getEmail() != null) u.setEmail(in.getEmail());
        if (in.getHashPassword() != null) u.setHashPassword(in.getHashPassword());

        try {
            u = repo.save(u);
            return Mappers.toResponse(u).build();
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
    }

    @Transactional
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        repo.deleteById(id);
    }
}
