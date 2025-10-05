package WebSiters.GastroReview.mapper;

import WebSiters.GastroReview.dto.*;
import WebSiters.GastroReview.model.*;


import java.util.Optional;

public final class Mappers {

    private Mappers() {}

    public static UserResponse.UserResponseBuilder toResponse(Users u) {
        if (u == null) return null;
        return UserResponse.builder()
                .id(u.getId())
                .email(u.getEmail());
    }

    public static UserProfileResponse toResponse(UserProfile p) {
        if (p == null) return null;
        return UserProfileResponse.builder()
                .userId(p.getUserId())
                .photoUrl(Optional.ofNullable(p.getPhotoUrl()).orElse(""))
                .name(Optional.ofNullable(p.getName()).orElse(""))
                .active(p.isActive())
                .createdAt(p.getCreatedAt())
                .bio(Optional.ofNullable(p.getBio()).orElse(""))
                .build();
    }

    public static RoleResponse toResponse(Role r) {
        if (r == null) return null;
        return RoleResponse.builder()
                .id(r.getId())
                .name(Optional.ofNullable(r.getName()).orElse(""))
                .description(Optional.ofNullable(r.getDescription()).orElse(""))
                .build();
    }

    public static RestaurantResponse toResponse(Restaurant r) {
        if (r == null) return null;
        return RestaurantResponse.builder()
                .id(r.getId())
                .name(Optional.ofNullable(r.getName()).orElse(""))
                .description(Optional.ofNullable(r.getDescription()).orElse(""))
                .phone(Optional.ofNullable(r.getPhone()).orElse(""))
                .email(Optional.ofNullable(r.getEmail()).orElse(""))
                .ownerId(r.getOwner() != null ? r.getOwner().getId() : null)
                .createdAt(r.getCreatedAt())
                .build();
    }
}

