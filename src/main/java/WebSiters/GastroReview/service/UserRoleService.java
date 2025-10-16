package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.UserRoleRequest;
import WebSiters.GastroReview.dto.UserRoleResponse;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.model.Role;
import WebSiters.GastroReview.model.UserRole;
import WebSiters.GastroReview.model.UserRoleId;
import WebSiters.GastroReview.model.Users;
import WebSiters.GastroReview.repository.RoleRepository;
import WebSiters.GastroReview.repository.UserRoleRepository;
import WebSiters.GastroReview.repository.UsersRepository;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- Import de Spring
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepo;
    private final UsersRepository userRepo;
    private final RoleRepository roleRepo;

    public UserRoleService(UserRoleRepository userRoleRepo,
                           UsersRepository userRepo,
                           RoleRepository roleRepo) {
        this.userRoleRepo = userRoleRepo;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    // Lectura: readOnly = true
    @Transactional(readOnly = true)
    public Page<UserRoleResponse> list(Pageable pageable) {
        return userRoleRepo.findAll(pageable).map(Mappers::toResponse);
    }

    // Lectura: readOnly = true
    @Transactional(readOnly = true)
    public UserRoleResponse get(UUID userId, Integer roleId) {
        var id = new UserRoleId(userId, roleId);
        var ur = userRoleRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "UserRole not found"));
        return Mappers.toResponse(ur); // mapper que NO toca relaciones LAZY
    }

    // Escritura
    @Transactional
    public UserRoleResponse create(@Valid UserRoleRequest in) {
        if (in == null || in.getUserId() == null || in.getRoleId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId and roleId are required");
        }

        // Validar FK existentes (evita 500 por integridad)
        Users user = userRepo.findById(in.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Role role = roleRepo.findById(in.getRoleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));

        var id = new UserRoleId(in.getUserId(), in.getRoleId());
        if (userRoleRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already has this role");
        }

        var entity = UserRole.builder()
                .id(id)
                .user(user)
                .role(role)
                .build();

        try {
            var saved = userRoleRepo.saveAndFlush(entity);
            return Mappers.toResponse(saved);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already has this role", e);
        }
    }

    // Escritura: NO permite cambiar la PK (userId/roleId) ni las relaciones
    @Transactional
    public UserRoleResponse update(UUID userId, Integer roleId, @Valid UserRoleRequest in) {
        if (in == null || in.getUserId() == null || in.getRoleId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId and roleId are required");
        }

        // 🔒 Prohibir cambio de PK / relaciones
        if (!Objects.equals(userId, in.getUserId()) || !Objects.equals(roleId, in.getRoleId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se permite cambiar userId/roleId. Para cambiar el rol usa: DELETE del actual + POST del nuevo."
            );
        }

        var id = new UserRoleId(userId, roleId);
        var existing = userRoleRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "UserRole not found"));

        // Si agregas campos propios en user_roles (no-FK), actualízalos aquí.
        // Por ahora no se modifica nada y devolvemos el actual.
        return Mappers.toResponse(existing);
    }

    // Escritura
    @Transactional
    public void delete(UUID userId, Integer roleId) {
        var id = new UserRoleId(userId, roleId);
        if (!userRoleRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "UserRole not found");
        }
        try {
            userRoleRepo.deleteById(id);
            userRoleRepo.flush(); // forzar cualquier violación de integridad ahora
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete: in use by other data", e);
        }
    }
}
