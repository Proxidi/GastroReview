package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.UserAchievement;
import WebSiters.GastroReview.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {

    @Query("""
        select case when count(a) > 0 then true else false end
        from UserAchievement a
        where a.user.id = :userId
          and lower(trim(a.badge)) = lower(trim(:badge))
    """)
    boolean existsForUserAndBadge(@Param("userId") UUID userId,
                                  @Param("badge") String badge);

    @Query("""
        select case when count(a) > 0 then true else false end
        from UserAchievement a
        where a.user.id = :userId
          and lower(trim(a.badge)) = lower(trim(:badge))
          and a.id <> :excludeId
    """)
    boolean existsForUserAndBadgeExcluding(@Param("userId") UUID userId,
                                           @Param("badge") String badge,
                                           @Param("excludeId") Long excludeId);
}




