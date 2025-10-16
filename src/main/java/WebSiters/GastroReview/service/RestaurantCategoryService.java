package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.CategoryRequest;
import WebSiters.GastroReview.dto.CategoryResponse;
import WebSiters.GastroReview.mapper.Mappers;
import WebSiters.GastroReview.model.RestaurantCategory;
import WebSiters.GastroReview.repository.RestaurantCategoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RestaurantCategoryService {

    private final RestaurantCategoryRepository repo;

    public RestaurantCategoryService(RestaurantCategoryRepository repo) {
        this.repo = repo;
    }

    public Page<CategoryResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(Mappers::toResponse);
    }

    public CategoryResponse get(Integer id) {
        RestaurantCategory c = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        return Mappers.toResponse(c);
    }

    public CategoryResponse create(CategoryRequest in) {
        if (repo.existsByNameIgnoreCase(in.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category name already exists");
        }
        RestaurantCategory c = RestaurantCategory.builder()
                .name(in.getName())
                .icon(in.getIcon())
                .build();
        try {
            c = repo.save(c);
            return Mappers.toResponse(c);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category name already exists");
        }
    }

    public CategoryResponse update(Integer id, CategoryRequest in) {
        RestaurantCategory c = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        if (in.getName() != null && !in.getName().equalsIgnoreCase(c.getName())) {
            if (repo.existsByNameIgnoreCaseAndIdNot(in.getName(), id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Category name already exists");
            }
            c.setName(in.getName());
        }
        if (in.getIcon() != null) c.setIcon(in.getIcon());
        try {
            c = repo.save(c);
            return Mappers.toResponse(c);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category name already exists");
        }
    }

    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
        repo.deleteById(id);
    }
}
