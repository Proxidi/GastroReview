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

    public static AddressResponse toResponse(Address a) {
        if (a == null) return null;
        return new AddressResponse(
                a.getId(),
                Optional.ofNullable(a.getStreet()).orElse(""),
                Optional.ofNullable(a.getSite()).orElse(""),
                Optional.ofNullable(a.getNeighborhood()).orElse(""),
                Optional.ofNullable(a.getCity()).orElse(""),
                Optional.ofNullable(a.getStateRegion()).orElse(""),
                Optional.ofNullable(a.getPostalCode()).orElse(null),
                Optional.ofNullable(a.getCountry()).orElse(""),
                a.getLatitude(),
                a.getLongitude()
        );
    }


    public static CategoryResponse toResponse(RestaurantCategory c) {
        if (c == null) return null;
        return CategoryResponse.builder()
                .id(c.getId())
                .name(Optional.ofNullable(c.getName()).orElse(""))
                .icon(Optional.ofNullable(c.getIcon()).orElse(""))
                .build();
    }

    public static UserPreferenceResponse toResponse(UserPreference p) {
        if (p == null) return null;
        return new UserPreferenceResponse(
                p.getId(),
                Optional.ofNullable(p.getPrefKey()).orElse(""),
                p.getValue()
        );
    }

    public static UserAchievementResponse toResponse(UserAchievement a) {
        if (a == null) return null;
        return new UserAchievementResponse(
                a.getId(),
                Optional.ofNullable(a.getBadge()).orElse(""),
                Optional.ofNullable(a.getLevel()).orElse(1),
                Optional.ofNullable(a.getStars()).orElse(0)
        );
    }

    public static UserRoleResponse toResponse(UserRole ur) {
        return new UserRoleResponse(
                ur.getId().getUserId(),
                ur.getId().getRoleId(),
                ur.getAssignedAt()
        );
    }


    public static FriendshipResponse toResponse(Friendship f) {
        if (f == null) return null;
        return new FriendshipResponse(
                f.getId().getFollowerId(),
                f.getId().getFollowedId(),
                f.getCreatedAt()
        );
    }

    public static RestaurantAddressResponse toResponse(RestaurantAddress ra) {
        if (ra == null) return null;
        return new RestaurantAddressResponse(
                ra.getId().getRestaurantId(),
                ra.getId().getAddressId(),
                ra.isPrimary(),
                Optional.ofNullable(ra.getBranchName()).orElse("")
        );
    }
}
