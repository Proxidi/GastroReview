package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.FavoriteRestaurantRequest;
import WebSiters.GastroReview.dto.FavoriteRestaurantResponse;
import WebSiters.GastroReview.model.FavoriteRestaurant;
import WebSiters.GastroReview.repository.FavoriteRestaurantRepository;
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
class FavoriteRestaurantServiceTest {

    @Mock
    private FavoriteRestaurantRepository favoriteRestaurantRepository;

    @InjectMocks
    private FavoriteRestaurantService favoriteRestaurantService;

    private FavoriteRestaurant favoriteRestaurant;
    private UUID userId;
    private UUID restaurantId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        restaurantId = UUID.randomUUID();

        favoriteRestaurant = new FavoriteRestaurant();
        favoriteRestaurant.setUserId(userId);
        favoriteRestaurant.setRestaurantId(restaurantId);
        favoriteRestaurant.setCreatedAt(OffsetDateTime.now());
    }

    @Test
    void testListar() {
        List<FavoriteRestaurant> favorites = Arrays.asList(favoriteRestaurant);
        when(favoriteRestaurantRepository.findAll()).thenReturn(favorites);

        List<FavoriteRestaurantResponse> result = favoriteRestaurantService.listar();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userId, result.get(0).userId());
        assertEquals(restaurantId, result.get(0).restaurantId());
        verify(favoriteRestaurantRepository, times(1)).findAll();
    }

    @Test
    void testListarConPaginacion() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<FavoriteRestaurant> favoritePage = new PageImpl<>(Arrays.asList(favoriteRestaurant));
        when(favoriteRestaurantRepository.findAll(pageable)).thenReturn(favoritePage);

        Page<FavoriteRestaurantResponse> result = favoriteRestaurantService.listar(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(favoriteRestaurantRepository, times(1)).findAll(pageable);
    }

    @Test
    void testListarPorUsuario() {
        List<FavoriteRestaurant> favorites = Arrays.asList(favoriteRestaurant);
        when(favoriteRestaurantRepository.findByUserId(userId)).thenReturn(favorites);

        List<FavoriteRestaurantResponse> result = favoriteRestaurantService.listarPorUsuario(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userId, result.get(0).userId());
        verify(favoriteRestaurantRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testListarPorUsuarioConPaginacion() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<FavoriteRestaurant> favoritePage = new PageImpl<>(Arrays.asList(favoriteRestaurant));
        when(favoriteRestaurantRepository.findByUserId(userId, pageable)).thenReturn(favoritePage);

        Page<FavoriteRestaurantResponse> result = favoriteRestaurantService.listarPorUsuario(userId, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(favoriteRestaurantRepository, times(1)).findByUserId(userId, pageable);
    }

    @Test
    void testListarPorRestaurante() {
        List<FavoriteRestaurant> favorites = Arrays.asList(favoriteRestaurant);
        when(favoriteRestaurantRepository.findByRestaurantId(restaurantId)).thenReturn(favorites);

        List<FavoriteRestaurantResponse> result = favoriteRestaurantService.listarPorRestaurante(restaurantId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(restaurantId, result.get(0).restaurantId());
        verify(favoriteRestaurantRepository, times(1)).findByRestaurantId(restaurantId);
    }

    @Test
    void testListarPorRestauranteConPaginacion() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<FavoriteRestaurant> favoritePage = new PageImpl<>(Arrays.asList(favoriteRestaurant));
        when(favoriteRestaurantRepository.findByRestaurantId(restaurantId, pageable)).thenReturn(favoritePage);

        Page<FavoriteRestaurantResponse> result = favoriteRestaurantService.listarPorRestaurante(restaurantId, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(favoriteRestaurantRepository, times(1)).findByRestaurantId(restaurantId, pageable);
    }

    @Test
    void testObtener() {
        when(favoriteRestaurantRepository.findByUserIdAndRestaurantId(userId, restaurantId))
            .thenReturn(Optional.of(favoriteRestaurant));

        FavoriteRestaurantResponse result = favoriteRestaurantService.obtener(userId, restaurantId);

        assertNotNull(result);
        assertEquals(userId, result.userId());
        assertEquals(restaurantId, result.restaurantId());
        verify(favoriteRestaurantRepository, times(1)).findByUserIdAndRestaurantId(userId, restaurantId);
    }

    @Test
    void testObtenerNoEncontrado() {
        when(favoriteRestaurantRepository.findByUserIdAndRestaurantId(userId, restaurantId))
            .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            favoriteRestaurantService.obtener(userId, restaurantId);
        });

        verify(favoriteRestaurantRepository, times(1)).findByUserIdAndRestaurantId(userId, restaurantId);
    }

    @Test
    void testVerificarFavorito() {
        when(favoriteRestaurantRepository.existsByUserIdAndRestaurantId(userId, restaurantId))
            .thenReturn(true);

        boolean result = favoriteRestaurantService.verificarFavorito(userId, restaurantId);

        assertTrue(result);
        verify(favoriteRestaurantRepository, times(1)).existsByUserIdAndRestaurantId(userId, restaurantId);
    }

    @Test
    void testVerificarFavoritoNoExiste() {
        when(favoriteRestaurantRepository.existsByUserIdAndRestaurantId(userId, restaurantId))
            .thenReturn(false);

        boolean result = favoriteRestaurantService.verificarFavorito(userId, restaurantId);

        assertFalse(result);
        verify(favoriteRestaurantRepository, times(1)).existsByUserIdAndRestaurantId(userId, restaurantId);
    }

    @Test
    void testCrear() {
        FavoriteRestaurantRequest request = new FavoriteRestaurantRequest(userId, restaurantId);

        when(favoriteRestaurantRepository.existsByUserIdAndRestaurantId(userId, restaurantId))
            .thenReturn(false);
        when(favoriteRestaurantRepository.save(any(FavoriteRestaurant.class)))
            .thenReturn(favoriteRestaurant);

        FavoriteRestaurantResponse result = favoriteRestaurantService.crear(request);

        assertNotNull(result);
        assertEquals(userId, result.userId());
        assertEquals(restaurantId, result.restaurantId());
        verify(favoriteRestaurantRepository, times(1)).existsByUserIdAndRestaurantId(userId, restaurantId);
        verify(favoriteRestaurantRepository, times(1)).save(any(FavoriteRestaurant.class));
    }

    @Test
    void testCrearYaExiste() {
        FavoriteRestaurantRequest request = new FavoriteRestaurantRequest(userId, restaurantId);

        when(favoriteRestaurantRepository.existsByUserIdAndRestaurantId(userId, restaurantId))
            .thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            favoriteRestaurantService.crear(request);
        });

        verify(favoriteRestaurantRepository, times(1)).existsByUserIdAndRestaurantId(userId, restaurantId);
        verify(favoriteRestaurantRepository, never()).save(any(FavoriteRestaurant.class));
    }

    @Test
    void testEliminar() {
        when(favoriteRestaurantRepository.existsByUserIdAndRestaurantId(userId, restaurantId))
            .thenReturn(true);
        doNothing().when(favoriteRestaurantRepository).deleteByUserIdAndRestaurantId(userId, restaurantId);

        favoriteRestaurantService.eliminar(userId, restaurantId);

        verify(favoriteRestaurantRepository, times(1)).existsByUserIdAndRestaurantId(userId, restaurantId);
        verify(favoriteRestaurantRepository, times(1)).deleteByUserIdAndRestaurantId(userId, restaurantId);
    }

    @Test
    void testEliminarNoEncontrado() {
        when(favoriteRestaurantRepository.existsByUserIdAndRestaurantId(userId, restaurantId))
            .thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            favoriteRestaurantService.eliminar(userId, restaurantId);
        });

        verify(favoriteRestaurantRepository, times(1)).existsByUserIdAndRestaurantId(userId, restaurantId);
        verify(favoriteRestaurantRepository, never()).deleteByUserIdAndRestaurantId(any(), any());
    }
}
