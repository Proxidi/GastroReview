package websiters.gastroreview.mapper;

import websiters.gastroreview.dto.*;
import websiters.gastroreview.model.*;


import java.util.Optional;

public final class Mappers {

    private Mappers() {

    }

    public static UserResponse toResponse(User entity) {
        if (entity == null) return null;

        return UserResponse.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .build();
    }

    public static User toEntity(UserRequest dto) {
        if (dto == null) return null;

        return User.builder()
                .email(dto.getEmail())
                .hashPassword(dto.getHash_password())
                .build();
    }

    public static void copyToEntity(UserRequest dto, User entity) {
        if (dto == null || entity == null) return;

        if (dto.getEmail() != null && !dto.getEmail().isBlank())
            entity.setEmail(dto.getEmail());

        if (dto.getHash_password() != null && !dto.getHash_password().isBlank())
            entity.setHashPassword(dto.getHash_password());
    }

    public static UserProfileResponse toResponse(UserProfile entity) {
        if (entity == null) return null;

        return UserProfileResponse.builder()
                .user_Id(entity.getUserId())
                .photo_Url(Optional.ofNullable(entity.getPhotoUrl()).orElse(""))
                .name(Optional.ofNullable(entity.getName()).orElse(""))
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .bio(Optional.ofNullable(entity.getBio()).orElse(""))
                .build();
    }

    public static RoleResponse toResponse(Role entity) {
        if (entity == null) return null;

        return RoleResponse.builder()
                .id(entity.getId())
                .name(Optional.ofNullable(entity.getName()).orElse(""))
                .description(Optional.ofNullable(entity.getDescription()).orElse(""))
                .build();
    }

    public static RestaurantResponse toResponse(Restaurant entity) {
        if (entity == null) return null;

        return RestaurantResponse.builder()
                .id(entity.getId())
                .name(Optional.ofNullable(entity.getName()).orElse(""))
                .description(Optional.ofNullable(entity.getDescription()).orElse(""))
                .phone(Optional.ofNullable(entity.getPhone()).orElse(""))
                .email(Optional.ofNullable(entity.getEmail()).orElse(""))
                .owner_Id(entity.getOwner() != null ? entity.getOwner().getId() : null)
                .build();
    }

    public static AddressResponse toResponse(Address entity) {
        if (entity == null) return null;

        return new AddressResponse(
                entity.getId(),
                Optional.ofNullable(entity.getStreet()).orElse(""),
                Optional.ofNullable(entity.getSite()).orElse(""),
                Optional.ofNullable(entity.getNeighborhood()).orElse(""),
                Optional.ofNullable(entity.getCity()).orElse(""),
                Optional.ofNullable(entity.getStateRegion()).orElse(""),
                entity.getPostalCode(),
                Optional.ofNullable(entity.getCountry()).orElse(""),
                entity.getLatitude(),
                entity.getLongitude()
        );
    }

    public static CategoryResponse toResponse(RestaurantCategory entity) {
        if (entity == null) return null;

        return CategoryResponse.builder()
                .id(entity.getId())
                .name(Optional.ofNullable(entity.getName()).orElse(""))
                .icon(Optional.ofNullable(entity.getIcon()).orElse(""))
                .build();
    }

    public static UserPreferenceResponse toResponse(UserPreference entity) {
        if (entity == null) return null;

        return new UserPreferenceResponse(
                entity.getId(),
                Optional.ofNullable(entity.getPrefKey()).orElse(""),
                entity.getValue()
        );
    }

    public static UserAchievementResponse toResponse(UserAchievement entity) {
        if (entity == null) return null;

        return new UserAchievementResponse(
                entity.getId(),
                Optional.ofNullable(entity.getBadge()).orElse(""),
                Optional.ofNullable(entity.getLevel()).orElse(1),
                Optional.ofNullable(entity.getStars()).orElse(0)
        );
    }

    public static UserRoleResponse toResponse(UserRole entity) {
        if (entity == null) return null;

        return new UserRoleResponse(
                entity.getId().getUserId(),
                entity.getId().getRoleId(),
                entity.getAssignedAt()
        );
    }

    public static FriendshipResponse toResponse(Friendship entity) {
        if (entity == null) return null;

        return new FriendshipResponse(
                entity.getId().getFollowerId(),
                entity.getId().getFollowedId(),
                entity.getCreatedAt()
        );
    }

    public static RestaurantAddressResponse toResponse(RestaurantAddress entity) {
        if (entity == null) return null;

        return new RestaurantAddressResponse(
                entity.getId().getRestaurantId(),
                entity.getId().getAddressId(),
                entity.isPrimary(),
                Optional.ofNullable(entity.getBranchName()).orElse("")
        );
    }
}
