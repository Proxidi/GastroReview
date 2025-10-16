package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.CategoryRequest;
import WebSiters.GastroReview.dto.CategoryResponse;
import WebSiters.GastroReview.service.RestaurantCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurant-categories")
@RequiredArgsConstructor
public class RestaurantCategoryController {

    private final RestaurantCategoryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@RequestBody CategoryRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public CategoryResponse get(@PathVariable("id") Integer id) {
        return service.get(id);
    }

    @GetMapping
    public Page<CategoryResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable("id") Integer id,
                                   @RequestBody CategoryRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        service.delete(id);
    }
}
