package WebSiters.GastroReview.service;

import WebSiters.GastroReview.configuration.JwtTokenProvider;
import WebSiters.GastroReview.dto.LoginRequest;
import WebSiters.GastroReview.dto.LoginResponse;
import WebSiters.GastroReview.dto.RegisterRequest;
import WebSiters.GastroReview.model.Role;
import WebSiters.GastroReview.model.Users;
import WebSiters.GastroReview.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        String token = tokenProvider.generateToken(authentication);

        Users user = usersRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new LoginResponse(token, user.getEmail(), user.getId().toString());
    }

    public LoginResponse register(RegisterRequest registerRequest) {
        // Verificar si el usuario ya existe
        if (usersRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Crear nuevo usuario con rol USER por defecto
        Users user = new Users();
        user.setId(UUID.randomUUID());
        user.setEmail(registerRequest.getEmail());
        user.setHashPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.USER);

        usersRepository.save(user);

        // Generar token con rol
        String token = tokenProvider.generateToken(user.getEmail(), Role.USER.getAuthority());

        return new LoginResponse(token, user.getEmail(), user.getId().toString());
    }

    public LoginResponse registerAdmin(RegisterRequest registerRequest) {
        // Verificar si el usuario ya existe
        if (usersRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Crear nuevo usuario con rol ADMIN
        Users user = new Users();
        user.setId(UUID.randomUUID());
        user.setEmail(registerRequest.getEmail());
        user.setHashPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.ADMIN);

        usersRepository.save(user);

        // Generar token con rol
        String token = tokenProvider.generateToken(user.getEmail(), Role.ADMIN.getAuthority());

        return new LoginResponse(token, user.getEmail(), user.getId().toString());
    }
}
