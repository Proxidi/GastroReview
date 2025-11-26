package WebSiters.GastroReview.service;

import WebSiters.GastroReview.dto.NotificationRequest;
import WebSiters.GastroReview.dto.NotificationResponse;
import WebSiters.GastroReview.model.Notification;
import WebSiters.GastroReview.repository.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    public List<NotificationResponse> listar() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public Page<NotificationResponse> listar(Pageable pageable) {
        return repo.findAll(pageable).map(this::toResponse);
    }

    public List<NotificationResponse> listarPorUsuario(UUID userId) {
        return repo.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toResponse).toList();
    }

    public Page<NotificationResponse> listarPorUsuario(UUID userId, Pageable pageable) {
        return repo.findByUserId(userId, pageable).map(this::toResponse);
    }

    public List<NotificationResponse> listarNoLeidasPorUsuario(UUID userId) {
        return repo.findByUserIdAndReadOrderByCreatedAtDesc(userId, false).stream()
                .map(this::toResponse).toList();
    }

    public Page<NotificationResponse> listarNoLeidasPorUsuario(UUID userId, Pageable pageable) {
        return repo.findByUserIdAndRead(userId, false, pageable).map(this::toResponse);
    }

    public List<NotificationResponse> listarLeidasPorUsuario(UUID userId) {
        return repo.findByUserIdAndReadOrderByCreatedAtDesc(userId, true).stream()
                .map(this::toResponse).toList();
    }

    public Page<NotificationResponse> listarLeidasPorUsuario(UUID userId, Pageable pageable) {
        return repo.findByUserIdAndRead(userId, true, pageable).map(this::toResponse);
    }

    public List<NotificationResponse> listarPorTipo(String type) {
        return repo.findByType(type).stream()
                .map(this::toResponse).toList();
    }

    public Page<NotificationResponse> listarPorTipo(String type, Pageable pageable) {
        return repo.findByType(type, pageable).map(this::toResponse);
    }

    public long contarNoLeidas(UUID userId) {
        return repo.countByUserIdAndRead(userId, false);
    }

    public NotificationResponse obtener(Long id) {
        Notification notification = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found: " + id));
        return toResponse(notification);
    }

    public NotificationResponse crear(NotificationRequest req) {
        Notification notification = new Notification();
        notification.setUserId(req.userId());
        notification.setType(req.type());
        notification.setMessage(req.message());
        notification.setRead(req.read() != null ? req.read() : false);
        notification.setReferenceId(req.referenceId());
        notification.setMetadata(req.metadata());
        
        return toResponse(repo.save(notification));
    }

    public NotificationResponse actualizar(Long id, NotificationRequest req) {
        Notification existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found: " + id));

        existing.setUserId(req.userId());
        existing.setType(req.type());
        existing.setMessage(req.message());
        existing.setRead(req.read() != null ? req.read() : existing.isRead());
        existing.setReferenceId(req.referenceId());
        existing.setMetadata(req.metadata());
        return toResponse(repo.save(existing));
    }

    public NotificationResponse marcarComoLeida(Long id) {
        Notification existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found: " + id));

        existing.setRead(true);
        return toResponse(repo.save(existing));
    }

    public void marcarTodasComoLeidas(UUID userId) {
        List<Notification> unread = repo.findByUserIdAndReadOrderByCreatedAtDesc(userId, false);
        unread.forEach(n -> n.setRead(true));
        repo.saveAll(unread);
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Notification not found: " + id);
        }
        repo.deleteById(id);
    }

    private NotificationResponse toResponse(Notification notification) {
        return new NotificationResponse(
            notification.getId(),
            notification.getUserId(),
            notification.getType(),
            notification.getMessage(),
            notification.isRead(),
            notification.getReferenceId(),
            notification.getMetadata(),
            notification.getCreatedAt()
        );
    }
}