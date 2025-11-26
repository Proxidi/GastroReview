package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserIdOrderByCreatedAtDesc(UUID userId);
    Page<Notification> findByUserId(UUID userId, Pageable pageable);

    List<Notification> findByUserIdAndReadOrderByCreatedAtDesc(UUID userId, boolean read);
    Page<Notification> findByUserIdAndRead(UUID userId, boolean read, Pageable pageable);

    List<Notification> findByType(String type);
    Page<Notification> findByType(String type, Pageable pageable);

    List<Notification> findByUserId(UUID userId);

    long countByUserIdAndRead(UUID userId, boolean read);
}