package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.UserResponse;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.model.Users;
import WebSiters.GastroReview.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UsersRepository repo;

    public UserService(UsersRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> listar() {
        return repo.findAll().stream()
                .map(Mappers::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponse obtener(UUID id) {
        Users user = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
        return Mappers.toResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse obtenerPorEmail(String email) {
        Users user = repo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
        return Mappers.toResponse(user);
    }
}
