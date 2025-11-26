package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.RestaurantCategoryRequest;
import WebSiters.GastroReview.dto.RestaurantCategoryResponse;
import WebSiters.GastroReview.model.RestaurantCategory;
import WebSiters.GastroReview.repository.RestaurantCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantCategoryService {

    private final RestaurantCategoryRepository repo;

    public RestaurantCategoryService(RestaurantCategoryRepository repo) {
        this.repo = repo;
    }

    public List<RestaurantCategoryResponse> listar() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public RestaurantCategoryResponse obtener(Integer id) {
        RestaurantCategory category = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));
        return toResponse(category);
    }

    public RestaurantCategoryResponse obtenerPorNombre(String name) {
        RestaurantCategory category = repo.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + name));
        return toResponse(category);
    }

    public RestaurantCategoryResponse crear(RestaurantCategoryRequest req) {
        if (repo.existsByName(req.name())) {
            throw new IllegalArgumentException("Category already exists with name: " + req.name());
        }

        RestaurantCategory category = new RestaurantCategory();
        // El ID se genera automáticamente (SERIAL en PostgreSQL)
        category.setName(req.name());
        category.setIcon(req.icon());

        return toResponse(repo.save(category));
    }

    public RestaurantCategoryResponse actualizar(Integer id, RestaurantCategoryRequest req) {
        RestaurantCategory existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));

        if (!existing.getName().equals(req.name()) && repo.existsByName(req.name())) {
            throw new IllegalArgumentException("Category already exists with name: " + req.name());
        }

        existing.setName(req.name());
        existing.setIcon(req.icon());
        return toResponse(repo.save(existing));
    }

    public void eliminar(Integer id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Category not found: " + id);
        }
        repo.deleteById(id);
    }

    private RestaurantCategoryResponse toResponse(RestaurantCategory category) {
        return new RestaurantCategoryResponse(
            category.getId(),
            category.getName(),
            category.getIcon()
        );
    }
}