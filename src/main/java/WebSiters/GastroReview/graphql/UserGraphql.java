package WebSiters.GastroReview.graphql;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import WebSiters.GastroReview.dto.UserResponse;
import WebSiters.GastroReview.service.UserService;

@Controller
public class UserGraphql {

    @Autowired
    private UserService service;

    @QueryMapping
    public List<UserResponse> findAllUsers() {
        return service.listar();
    }

    @QueryMapping
    public UserResponse findUserById(@Argument String id) {
        return service.obtener(UUID.fromString(id));
    }

    @QueryMapping
    public UserResponse findUserByEmail(@Argument String email) {
        return service.obtenerPorEmail(email);
    }
}
