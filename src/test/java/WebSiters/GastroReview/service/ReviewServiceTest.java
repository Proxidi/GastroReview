package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.ReviewRequest;
import WebSiters.GastroReview.dto.ReviewResponse;
import WebSiters.GastroReview.model.Review;
import WebSiters.GastroReview.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    private Review review;
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

        review = new Review();
        review.setId(reviewId);
        review.setUserId(userId);
        review.setRestaurantId(restaurantId);
        review.setDishId(dishId);
        review.setTitle("Excelente comida");
        review.setContent("La comida estuvo deliciosa");
        review.setHasAudio(false);
        review.setHasImage(true);
        review.setPublishedAt(OffsetDateTime.now());
    }

    @Test
    void testListar() {
        List<Review> reviews = Arrays.asList(review);
        when(reviewRepository.findAll()).thenReturn(reviews);

        List<ReviewResponse> result = reviewService.listar();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(reviewId, result.get(0).id());
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void testListarConPaginacion() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Review> reviewPage = new PageImpl<>(Arrays.asList(review));
        when(reviewRepository.findAll(pageable)).thenReturn(reviewPage);

        Page<ReviewResponse> result = reviewService.listar(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(reviewRepository, times(1)).findAll(pageable);
    }

    @Test
    void testListarPorUsuario() {
        List<Review> reviews = Arrays.asList(review);
        when(reviewRepository.findByUserIdOrderByPublishedAtDesc(userId)).thenReturn(reviews);

        List<ReviewResponse> result = reviewService.listarPorUsuario(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userId, result.get(0).userId());
        verify(reviewRepository, times(1)).findByUserIdOrderByPublishedAtDesc(userId);
    }

    @Test
    void testListarPorRestaurante() {
        List<Review> reviews = Arrays.asList(review);
        when(reviewRepository.findByRestaurantIdOrderByPublishedAtDesc(restaurantId)).thenReturn(reviews);

        List<ReviewResponse> result = reviewService.listarPorRestaurante(restaurantId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(restaurantId, result.get(0).restaurantId());
        verify(reviewRepository, times(1)).findByRestaurantIdOrderByPublishedAtDesc(restaurantId);
    }

    @Test
    void testListarPorPlatillo() {
        List<Review> reviews = Arrays.asList(review);
        when(reviewRepository.findByDishIdOrderByPublishedAtDesc(dishId)).thenReturn(reviews);

        List<ReviewResponse> result = reviewService.listarPorPlatillo(dishId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dishId, result.get(0).dishId());
        verify(reviewRepository, times(1)).findByDishIdOrderByPublishedAtDesc(dishId);
    }

    @Test
    void testListarConImagen() {
        review.setHasImage(true);
        List<Review> reviews = Arrays.asList(review);
        when(reviewRepository.findByHasImageTrue()).thenReturn(reviews);

        List<ReviewResponse> result = reviewService.listarConImagen();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).hasImage());
        verify(reviewRepository, times(1)).findByHasImageTrue();
    }

    @Test
    void testListarConAudio() {
        review.setHasAudio(true);
        List<Review> reviews = Arrays.asList(review);
        when(reviewRepository.findByHasAudioTrue()).thenReturn(reviews);

        List<ReviewResponse> result = reviewService.listarConAudio();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).hasAudio());
        verify(reviewRepository, times(1)).findByHasAudioTrue();
    }

    @Test
    void testObtener() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        ReviewResponse result = reviewService.obtener(reviewId);

        assertNotNull(result);
        assertEquals(reviewId, result.id());
        assertEquals("Excelente comida", result.title());
        verify(reviewRepository, times(1)).findById(reviewId);
    }

    @Test
    void testObtenerNoEncontrado() {
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.obtener(reviewId);
        });

        verify(reviewRepository, times(1)).findById(reviewId);
    }

    @Test
    void testCrear() {
        ReviewRequest request = new ReviewRequest(
            userId,
            restaurantId,
            dishId,
            "Nueva review",
            "Contenido de la review",
            false,
            true
        );

        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        ReviewResponse result = reviewService.crear(request);

        assertNotNull(result);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testActualizar() {
        ReviewRequest request = new ReviewRequest(
            userId,
            restaurantId,
            dishId,
            "Review actualizada",
            "Contenido actualizado",
            true,
            false
        );

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        ReviewResponse result = reviewService.actualizar(reviewId, request);

        assertNotNull(result);
        verify(reviewRepository, times(1)).findById(reviewId);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testActualizarNoEncontrado() {
        ReviewRequest request = new ReviewRequest(
            userId,
            restaurantId,
            dishId,
            "Review actualizada",
            "Contenido actualizado",
            true,
            false
        );

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.actualizar(reviewId, request);
        });

        verify(reviewRepository, times(1)).findById(reviewId);
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void testEliminar() {
        when(reviewRepository.existsById(reviewId)).thenReturn(true);
        doNothing().when(reviewRepository).deleteById(reviewId);

        reviewService.eliminar(reviewId);

        verify(reviewRepository, times(1)).existsById(reviewId);
        verify(reviewRepository, times(1)).deleteById(reviewId);
    }

    @Test
    void testEliminarNoEncontrado() {
        when(reviewRepository.existsById(reviewId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.eliminar(reviewId);
        });

        verify(reviewRepository, times(1)).existsById(reviewId);
        verify(reviewRepository, never()).deleteById(any());
    }
}
