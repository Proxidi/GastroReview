package WebSiters.GastroReview.controller;

import WebSiters.GastroReview.dto.ReviewRequest;
import WebSiters.GastroReview.dto.ReviewResponse;
import WebSiters.GastroReview.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    private ReviewResponse reviewResponse;
    private ReviewRequest reviewRequest;
    private UUID reviewId;
    private UUID userId;
    private UUID restaurantId;
    private UUID dishId;

    @BeforeEach
    void setUp() {
        reviewId = UUID.randomUUID();
        userId = UUID.randomUUID();
        restaurantId = UUID.randomUUID();
        dishId = UUID.randomUUID();

        reviewResponse = new ReviewResponse(
            reviewId,
            userId,
            restaurantId,
            dishId,
            "Excelente comida",
            "La comida estuvo deliciosa",
            false,
            true,
            OffsetDateTime.now()
        );

        reviewRequest = new ReviewRequest(
            userId,
            restaurantId,
            dishId,
            "Excelente comida",
            "La comida estuvo deliciosa",
            false,
            true
        );
    }

    @Test
    void testListar() throws Exception {
        Page<ReviewResponse> page = new PageImpl<>(Arrays.asList(reviewResponse));
        when(reviewService.listar(any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/reviews")
                .param("page", "0")
                .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content[0].id").value(reviewId.toString()));

        verify(reviewService, times(1)).listar(any(PageRequest.class));
    }

    @Test
    void testListarTodos() throws Exception {
        List<ReviewResponse> reviews = Arrays.asList(reviewResponse);
        when(reviewService.listar()).thenReturn(reviews);

        mockMvc.perform(get("/api/v1/reviews/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].id").value(reviewId.toString()));

        verify(reviewService, times(1)).listar();
    }

    @Test
    void testListarPorUsuario() throws Exception {
        Page<ReviewResponse> page = new PageImpl<>(Arrays.asList(reviewResponse));
        when(reviewService.listarPorUsuario(eq(userId), any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/reviews/user/{userId}", userId)
                .param("page", "0")
                .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content[0].userId").value(userId.toString()));

        verify(reviewService, times(1)).listarPorUsuario(eq(userId), any(PageRequest.class));
    }

    @Test
    void testListarTodosPorUsuario() throws Exception {
        List<ReviewResponse> reviews = Arrays.asList(reviewResponse);
        when(reviewService.listarPorUsuario(userId)).thenReturn(reviews);

        mockMvc.perform(get("/api/v1/reviews/user/{userId}/all", userId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].userId").value(userId.toString()));

        verify(reviewService, times(1)).listarPorUsuario(userId);
    }

    @Test
    void testListarPorRestaurante() throws Exception {
        Page<ReviewResponse> page = new PageImpl<>(Arrays.asList(reviewResponse));
        when(reviewService.listarPorRestaurante(eq(restaurantId), any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/reviews/restaurant/{restaurantId}", restaurantId)
                .param("page", "0")
                .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content[0].restaurantId").value(restaurantId.toString()));

        verify(reviewService, times(1)).listarPorRestaurante(eq(restaurantId), any(PageRequest.class));
    }

    @Test
    void testListarTodosPorRestaurante() throws Exception {
        List<ReviewResponse> reviews = Arrays.asList(reviewResponse);
        when(reviewService.listarPorRestaurante(restaurantId)).thenReturn(reviews);

        mockMvc.perform(get("/api/v1/reviews/restaurant/{restaurantId}/all", restaurantId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].restaurantId").value(restaurantId.toString()));

        verify(reviewService, times(1)).listarPorRestaurante(restaurantId);
    }

    @Test
    void testListarPorPlatillo() throws Exception {
        Page<ReviewResponse> page = new PageImpl<>(Arrays.asList(reviewResponse));
        when(reviewService.listarPorPlatillo(eq(dishId), any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/reviews/dish/{dishId}", dishId)
                .param("page", "0")
                .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content[0].dishId").value(dishId.toString()));

        verify(reviewService, times(1)).listarPorPlatillo(eq(dishId), any(PageRequest.class));
    }

    @Test
    void testListarConImagen() throws Exception {
        Page<ReviewResponse> page = new PageImpl<>(Arrays.asList(reviewResponse));
        when(reviewService.listarConImagen(any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/reviews/with-image")
                .param("page", "0")
                .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content[0].hasImage").value(true));

        verify(reviewService, times(1)).listarConImagen(any(PageRequest.class));
    }

    @Test
    void testListarTodosConImagen() throws Exception {
        List<ReviewResponse> reviews = Arrays.asList(reviewResponse);
        when(reviewService.listarConImagen()).thenReturn(reviews);

        mockMvc.perform(get("/api/v1/reviews/with-image/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].hasImage").value(true));

        verify(reviewService, times(1)).listarConImagen();
    }

    @Test
    void testObtener() throws Exception {
        when(reviewService.obtener(reviewId)).thenReturn(reviewResponse);

        mockMvc.perform(get("/api/v1/reviews/{id}", reviewId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(reviewId.toString()))
            .andExpect(jsonPath("$.title").value("Excelente comida"));

        verify(reviewService, times(1)).obtener(reviewId);
    }

    @Test
    void testCrear() throws Exception {
        when(reviewService.crear(any(ReviewRequest.class))).thenReturn(reviewResponse);

        mockMvc.perform(post("/api/v1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(reviewId.toString()))
            .andExpect(jsonPath("$.title").value("Excelente comida"));

        verify(reviewService, times(1)).crear(any(ReviewRequest.class));
    }

    @Test
    void testActualizar() throws Exception {
        when(reviewService.actualizar(eq(reviewId), any(ReviewRequest.class))).thenReturn(reviewResponse);

        mockMvc.perform(put("/api/v1/reviews/{id}", reviewId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(reviewId.toString()))
            .andExpect(jsonPath("$.title").value("Excelente comida"));

        verify(reviewService, times(1)).actualizar(eq(reviewId), any(ReviewRequest.class));
    }

    @Test
    void testEliminar() throws Exception {
        doNothing().when(reviewService).eliminar(reviewId);

        mockMvc.perform(delete("/api/v1/reviews/{id}", reviewId))
            .andExpect(status().isNoContent());

        verify(reviewService, times(1)).eliminar(reviewId);
    }
}
