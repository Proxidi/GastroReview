package WebSiters.GastroReview.graphql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import WebSiters.GastroReview.dto.AddressRequest;
import WebSiters.GastroReview.dto.AddressResponse;
import WebSiters.GastroReview.service.AddressService;
import jakarta.validation.Valid;

@Controller
public class AddressGraphql {
    @Autowired
    private AddressService service;

    @QueryMapping
    public List<AddressResponse> findAllAddresses() {
        return service.listar();
    }

    @QueryMapping
    public AddressResponse findAddressById(@Argument Long id) {
        return service.obtener(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public AddressResponse createAddress(@Valid @Argument AddressRequest req) {
        return service.crear(req);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public AddressResponse updateAddress(@Argument Long id, @Valid @Argument AddressRequest req) {
        return service.actualizar(id, req);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean deleteAddress(@Argument Long id) {
        service.eliminar(id);
        return true;
    }
}
