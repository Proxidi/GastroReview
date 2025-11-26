package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.AddressRequest;
import WebSiters.GastroReview.dto.AddressResponse;
import WebSiters.GastroReview.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@Tag(name = "Addresses", description = "API de gestión de direcciones")
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @GetMapping
    public List<AddressResponse> listar() {
        return service.listar();
    }

    @GetMapping("/city/{city}")
    public List<AddressResponse> listarPorCiudad(@PathVariable String city) {
        return service.listarPorCiudad(city);
    }

    @GetMapping("/country/{country}")
    public List<AddressResponse> listarPorPais(@PathVariable String country) {
        return service.listarPorPais(country);
    }

    @GetMapping("/{id}")
    public AddressResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressResponse crear(@Valid @RequestBody AddressRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public AddressResponse actualizar(@PathVariable Long id,
                                       @Valid @RequestBody AddressRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}