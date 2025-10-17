package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.CategoryRequest;
import WebSiters.GastroReview.dto.CategoryResponse;
import WebSiters.GastroReview.service.RestaurantCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Create restaurant category",
            description = "Creates a new restaurant category with the provided details."
    )
    @ApiResponse(responseCode = "201", description = "Category created successfully")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@RequestBody CategoryRequest req) {
        return service.create(req);
    }

    @Operation(
            summary = "Get restaurant category",
            description = "Retrieves a restaurant category by its ID."
    )
    @ApiResponse(responseCode = "200", description = "Category retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @GetMapping("/{id}")
    public CategoryResponse get(@PathVariable("id") Integer id) {
        return service.get(id);
    }

    @Operation(
            summary = "List restaurants",
            description = "Retrieves a paginated list of restaurants."
    )
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public Page<CategoryResponse> list(Pageable pageable) {
        return service.list(pageable);
    }

    @Operation(
            summary = "Update restaurant category",
            description = "Updates an existing restaurant category with the provided information."
    )
    @ApiResponse(responseCode = "200", description = "Category updated successfully")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable("id") Integer id,
                                   @RequestBody CategoryRequest req) {
        return service.update(id, req);
    }

    @Operation(
            summary = "Delete restaurant category",
            description = "Deletes a restaurant category by its ID."
    )
    @ApiResponse(responseCode = "204", description = "Category deleted successfully")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        service.delete(id);
    }
}
