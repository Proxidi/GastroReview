package WebSiters.GastroReview.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "user_address")
public class UserAddress {

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "user_id")
    private UUID userId;

    @Id
    @Column(name = "address_id")
    private Long addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", insertable = false, updatable = false)
    private Address address;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    // Constructors
    public UserAddress() {}

    public UserAddress(UUID userId, Long addressId, Users user, Address address, String type, boolean active) {
        this.userId = userId;
        this.addressId = addressId;
        this.user = user;
        this.address = address;
        this.type = type;
        this.active = active;
    }

    // Getters and Setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // equals and hashCode for composite key
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAddress that = (UserAddress) o;
        return userId != null && userId.equals(that.userId) &&
               addressId != null && addressId.equals(that.addressId);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(userId, addressId);
    }

    // Builder
    public static UserAddressBuilder builder() {
        return new UserAddressBuilder();
    }

    public static class UserAddressBuilder {
        private UUID userId;
        private Long addressId;
        private Users user;
        private Address address;
        private String type;
        private boolean active = true;

        UserAddressBuilder() {}

        public UserAddressBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public UserAddressBuilder addressId(Long addressId) {
            this.addressId = addressId;
            return this;
        }

        public UserAddressBuilder user(Users user) {
            this.user = user;
            return this;
        }

        public UserAddressBuilder address(Address address) {
            this.address = address;
            return this;
        }

        public UserAddressBuilder type(String type) {
            this.type = type;
            return this;
        }

        public UserAddressBuilder active(boolean active) {
            this.active = active;
            return this;
        }

        public UserAddress build() {
            return new UserAddress(userId, addressId, user, address, type, active);
        }
    }
}
