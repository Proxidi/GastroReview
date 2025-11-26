package WebSiters.GastroReview.mapper;

import WebSiters.GastroReview.dto.*;
import WebSiters.GastroReview.model.*;

import java.util.Optional;

public final class Mappers {

    private Mappers() {}

    // ==================== User Mappers ====================
    public static UserResponse toResponse(Users u) {
        if (u == null) return null;
        return UserResponse.builder()
                .id(u.getId())
                .email(u.getEmail() )
                .build();
    }

    // ==================== Restaurant Mappers ====================
    public static RestaurantResponse toResponse(Restaurant r) {
        if (r == null) return null;
        return RestaurantResponse.builder()
                .id(r.getId())
                .name(Optional.ofNullable(r.getName()).orElse(""))
                .description(Optional.ofNullable(r.getDescription()).orElse(""))
                .phone(Optional.ofNullable(r.getPhone()).orElse(""))
                .email(Optional.ofNullable(r.getEmail()).orElse(""))
                .ownerId(r.getOwnerId())
                .createdAt(r.getCreatedAt())
                .build();
    }

    // ==================== Address Mappers ====================
    public static AddressResponse toResponse(Address a) {
        if (a == null) return null;
        return new AddressResponse(
                a.getId(),
                a.getStreet(),
                a.getSite(),
                a.getNeighborhood(),
                a.getCity(),
                a.getStateRegion(),
                a.getPostalCode(),
                a.getCountry(),
                a.getLatitude(),
                a.getLongitude(),
                a.getCreatedAt()
        );
    }

    // ==================== Dish Mappers ====================
    public static Dish toDish(DishRequest request) {
        if (request == null) return null;
        Dish dish = new Dish();
        dish.setRestaurantId(request.restaurantId());
        dish.setName(request.name());
        dish.setDescription(request.description());
        dish.setPriceCents(request.priceCents());
        dish.setAvailable(request.available() != null ? request.available() : true);
        return dish;
    }

    public static void updateDishFromRequest(Dish dish, DishRequest request) {
        if (dish == null || request == null) return;
        dish.setRestaurantId(request.restaurantId());
        dish.setName(request.name());
        dish.setDescription(request.description());
        dish.setPriceCents(request.priceCents());
        if (request.available() != null) {
            dish.setAvailable(request.available());
        }
    }

    public static DishResponse toResponse(Dish d) {
        if (d == null) return null;
        return new DishResponse(
                d.getId(),
                d.getRestaurantId(),
                d.getName(),
                d.getDescription(),
                d.getPriceCents(),
                Boolean.valueOf(d.isAvailable()),
                d.getCreatedAt()
        );
    }

    // ==================== Review Mappers ====================
    public static ReviewResponse toResponse(Review r) {
        if (r == null) return null;
        return new ReviewResponse(
                r.getId(),
                r.getUserId(),
                r.getRestaurantId(),
                r.getDishId(),
                r.getTitle(),
                r.getContent(),
                Boolean.valueOf(r.isHasAudio()),
                Boolean.valueOf(r.isHasImage()),
                r.getPublishedAt()
        );
    }

    // ==================== RestaurantCategory Mappers ====================
    public static RestaurantCategoryResponse toResponse(RestaurantCategory rc) {
        if (rc == null) return null;
        return new RestaurantCategoryResponse(
                rc.getId(),
                rc.getName(),
                rc.getIcon()
        );
    }

    // ==================== FavoriteRestaurant Mappers ====================
    public static FavoriteRestaurantResponse toResponse(FavoriteRestaurant fr) {
        if (fr == null) return null;
        return new FavoriteRestaurantResponse(
                fr.getUserId(),
                fr.getRestaurantId(),
                fr.getCreatedAt()
        );
    }

    // ==================== FavoriteReview Mappers ====================
    public static FavoriteReviewResponse toResponse(FavoriteReview fr) {
        if (fr == null) return null;
        return new FavoriteReviewResponse(
                fr.getUserId(),
                fr.getReviewId(),
                fr.getCreatedAt()
        );
    }

    // ==================== ReviewImage Mappers ====================
    public static ReviewImageResponse toResponse(ReviewImage ri) {
        if (ri == null) return null;
        return new ReviewImageResponse(
                ri.getId(),
                ri.getReviewId(),
                ri.getUrl(),
                ri.getAltText()
        );
    }

    // ==================== ReviewAudio Mappers ====================
    public static ReviewAudioResponse toResponse(ReviewAudio ra) {
        if (ra == null) return null;
        return new ReviewAudioResponse(
                ra.getId(),
                ra.getReviewId(),
                ra.getUrl(),
                ra.getDurationSeconds(),
                ra.getTranscription()
        );
    }

    // ==================== Alert Mappers ====================
    public static AlertResponse toResponse(Alert a) {
        if (a == null) return null;
        return new AlertResponse(
                a.getId(),
                a.getType(),
                a.getRestaurantId(),
                a.getReviewId(),
                a.getDetail(),
                a.getCreatedAt()
        );
    }

    // ==================== Notification Mappers ====================
    public static NotificationResponse toResponse(Notification n) {
        if (n == null) return null;
        return new NotificationResponse(
                n.getId(),
                n.getUserId(),
                n.getType(),
                n.getMessage(),
                Boolean.valueOf(n.isRead()),
                n.getReferenceId(),
                n.getMetadata(),
                n.getCreatedAt()
        );
    }

    // ==================== RestaurantImage Mappers ====================
    public static RestaurantImageResponse toResponse(RestaurantImage ri) {
        if (ri == null) return null;
        return new RestaurantImageResponse(
                ri.getId(),
                ri.getRestaurantId(),
                ri.getUrl(),
                ri.getAltText(),
                ri.getUploadedBy(),
                ri.getCreatedAt()
        );
    }

    // ==================== UserAddress Mappers ====================
    public static UserAddressResponse toResponse(UserAddress ua) {
        if (ua == null) return null;
        return new UserAddressResponse(
                ua.getUserId(),
                ua.getAddressId(),
                ua.getType(),
                Boolean.valueOf(ua.isActive())
        );
    }
}

