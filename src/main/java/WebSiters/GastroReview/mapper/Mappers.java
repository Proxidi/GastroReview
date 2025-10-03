package WebSiters.GastroReview.mapper;

import WebSiters.GastroReview.dto.*;
import WebSiters.GastroReview.model.*;

public class Mappers {

    public static UserResponse toDto(Users u) {
        return new UserResponse(u.getId(), u.getEmail());

    }

    public static UserProfileResponse toDto(UserProfile p) {
        return new UserProfileResponse(
                p.getUserId(), p.getPhotoUrl(), p.getName(), p.isActive(), p.getCreatedAt(), p.getBio()
        );
    }

    public static RoleResponse toDto(Role r) {
        return new RoleResponse(r.getId(), r.getName(), r.getDescription());
    }

    public static RestaurantResponse toDto(Restaurant r) {
        return new RestaurantResponse(
                r.getId(), r.getName(), r.getDescription(), r.getPhone(), r.getEmail(),
                r.getOwner() != null ? r.getOwner().getId() : null,
                r.getCreatedAt()
        );
    }
}
