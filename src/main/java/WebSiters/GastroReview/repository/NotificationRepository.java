package WebSiters.GastroReview.repository;

import WebSiters.GastroReview.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    List<Notification> findByUserId(UUID userId);
    
    List<Notification> findByUserIdAndRead(UUID userId, boolean read);
    
    List<Notification> findByUserIdOrderByCreatedAtDesc(UUID userId);
    
    List<Notification> findByUserIdAndReadOrderByCreatedAtDesc(UUID userId, boolean read);
    
    List<Notification> findByType(String type);
    
    long countByUserIdAndRead(UUID userId, boolean read);
}