package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.LoginRequest;
import WebSiters.GastroReview.dto.LoginResponse;
import WebSiters.GastroReview.dto.RegisterRequest;
import WebSiters.GastroReview.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication endpoints for login and registration")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticate user and return JWT token")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/register-admin")
    @Operation(summary = "Register admin user", description = "Register a new admin user (TEMPORARY - remove after creating users)")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            LoginResponse response = authService.registerAdmin(registerRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error registering admin: {}", e.getMessage(), e);
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/test")
    @Operation(summary = "Test endpoint", description = "Test if JWT authentication is working")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("JWT Authentication is working!");
    }
}
