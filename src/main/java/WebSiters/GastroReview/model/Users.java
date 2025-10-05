package WebSiters.GastroReview.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.*;
import WebSiters.GastroReview.model.Restaurant;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Users {

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @Email @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(name = "hash_password", nullable = false)
    private String hashPassword;
    
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    @PrimaryKeyJoinColumn
    private UserProfile profile;


    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Restaurant> restaurants = new ArrayList<>(); 

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
    }
}
