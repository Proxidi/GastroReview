package WebSiters.GastroReview.model;

import WebSiters.GastroReview.model.FriendshipId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "friendships")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Friendship {

    @EmbeddedId
    private FriendshipId id;

    // follower
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("followerId")
    @JoinColumn(name = "follower_id", nullable = false)
    private Users follower;

    // followed
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("followedId")
    @JoinColumn(name = "followed_id", nullable = false)
    private Users followed;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;
}
