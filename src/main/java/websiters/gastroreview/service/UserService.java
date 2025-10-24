package websiters.gastroreview.service;

import websiters.gastroreview.dto.UserRequest;
import websiters.gastroreview.dto.UserResponse;
import websiters.gastroreview.mapper.Mappers;
import websiters.gastroreview.model.User;
import websiters.gastroreview.repository.UsersRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class UserService {

    private final UsersRepository repo;

    public UserService(UsersRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> list(String email, Pageable pageable) {
        Page<User> users;

        if (email != null && !email.isBlank()) {
            users = repo.findByEmailContainingIgnoreCase(email.trim(), pageable);
        } else {
            users = repo.findAll(pageable);
        }

        return users.map(u -> Mappers.toResponse(u));
    }

    @Transactional(readOnly = true)
    public UserResponse getByEmail(String email) {
        User u = repo.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return Mappers.toResponse(u);
    }

    @Transactional(readOnly = true)
    public UserResponse get(UUID id) {
        User u = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return Mappers.toResponse(u);
    }

    @Transactional
    public UserResponse create(UserRequest in) {
        User u = User.builder()
                .email(in.getEmail())
                .hashPassword(in.getHash_password())
                .build();
        try {
            u = repo.save(u);
            return Mappers.toResponse(u);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
    }

    @Transactional
    public UserResponse update(UUID id, UserRequest in) {
        User u = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (in.getEmail() != null) u.setEmail(in.getEmail());
        if (in.getHash_password() != null) u.setHashPassword(in.getHash_password());

        try {
            u = repo.save(u);
            return Mappers.toResponse(u);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
    }

}
