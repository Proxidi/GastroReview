package WebSiters.GastroReview.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "restaurant_categories")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RestaurantCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    private String icon;

    // N:M con restaurants a través de la tabla puente restaurant_category
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "restaurant_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id"))
    @Builder.Default
    private Set<Restaurant> restaurants = new HashSet<>();
}
