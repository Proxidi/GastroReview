package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.UserRequest;
import WebSiters.GastroReview.dto.UserResponse;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.model.Users;
import WebSiters.GastroReview.repository.UsersRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Service
public class UsersService {

    private final UsersRepository repo;

    public UsersService(UsersRepository repo) {
        this.repo = repo;
    }

    // ===== NUEVOS: listados estilo StudentController =====

    /** Listado completo sin paginación */
    public List<UserResponse> findAll() {
        return repo.findAll()
                .stream()
                .map(u -> Mappers.toResponse(u).build())
                .collect(Collectors.toList());
    }

    /** Listado con paginación por parámetros page/pageSize */
    public List<UserResponse> findAll(int page, int pageSize) {
        PageRequest pr = PageRequest.of(page, pageSize);
        return repo.findAll(pr)
                .stream()
                .map(u -> Mappers.toResponse(u).build())
                .collect(Collectors.toList());
    }

    /** Búsqueda por email */
    public UserResponse getByEmail(String email) {
        Users u = repo.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
        return Mappers.toResponse(u).build();
    }

    // ===== TUS MÉTODOS EXISTENTES (se mantienen) =====

    public Page<UserResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(u -> Mappers.toResponse(u).build());
    }

    public UserResponse get(UUID id) {
        Users u = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
        return Mappers.toResponse(u).build();
    }

    public UserResponse create(UserRequest in) {
        Users u = Users.builder()
                .email(in.getEmail())
                .hashPassword(in.getHashPassword())
                .build();
        try {
            u = repo.save(u);
            return Mappers.toResponse(u).build();
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(CONFLICT, "Email already exists");
        }
    }

    public UserResponse update(UUID id, UserRequest in) {
        Users u = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));

        if (in.getEmail() != null) u.setEmail(in.getEmail());
        if (in.getHashPassword() != null) u.setHashPassword(in.getHashPassword());

        try {
            u = repo.save(u);
            return Mappers.toResponse(u).build();
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(CONFLICT, "Email already exists");
        }
    }

    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "User not found");
        }
        repo.deleteById(id);
    }
}
