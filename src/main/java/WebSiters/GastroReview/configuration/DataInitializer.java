package WebSiters.GastroReview.configuration;

import WebSiters.GastroReview.model.Users;
import WebSiters.GastroReview.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Eliminar usuarios anteriores con username simple
        usersRepository.findByEmail("admin").ifPresent(usersRepository::delete);
        usersRepository.findByEmail("user").ifPresent(usersRepository::delete);
        usersRepository.findByEmail("foodie").ifPresent(usersRepository::delete);

        // Crear usuario admin si no existe
        if (!usersRepository.existsByEmail("admin@gastroreview.com")) {
            Users admin = new Users();
            admin.setId(UUID.randomUUID());
            admin.setEmail("admin@gastroreview.com");
            admin.setHashPassword(passwordEncoder.encode("admin"));
            usersRepository.save(admin);
            System.out.println("Usuario admin creado: admin@gastroreview.com / admin");
        }

        // Crear usuario normal si no existe
        if (!usersRepository.existsByEmail("user@gastroreview.com")) {
            Users user = new Users();
            user.setId(UUID.randomUUID());
            user.setEmail("user@gastroreview.com");
            user.setHashPassword(passwordEncoder.encode("user"));
            usersRepository.save(user);
            System.out.println("Usuario user creado: user@gastroreview.com / user");
        }

        // Crear usuario foodie si no existe
        if (!usersRepository.existsByEmail("foodie@gastroreview.com")) {
            Users foodie = new Users();
            foodie.setId(UUID.randomUUID());
            foodie.setEmail("foodie@gastroreview.com");
            foodie.setHashPassword(passwordEncoder.encode("foodie"));
            usersRepository.save(foodie);
            System.out.println("Usuario foodie creado: foodie@gastroreview.com / foodie");
        }
    }
}
