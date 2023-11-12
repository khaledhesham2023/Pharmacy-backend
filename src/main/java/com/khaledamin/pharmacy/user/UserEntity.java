package com.khaledamin.pharmacy.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaledamin.pharmacy.address.AddressEntity;
import com.khaledamin.pharmacy.main.ProductEntity;
import com.khaledamin.pharmacy.notification.NotificationEntity;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Table(name = "users", schema = "pharmacydb")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", columnDefinition = "BIGINT", unique = true)
    private Long id;

    @Column(name = "username", columnDefinition = "VARCHAR(255)", unique = true)
    @NonNull
    private String username;

    @Column(name = "firstname", columnDefinition = "VARCHAR(255)")
    @NonNull
    private String firstname;

    @Column(name = "lastname", columnDefinition = "VARCHAR(255)")
    @NonNull
    private String lastname;

    @Column(name = "password", columnDefinition = "VARCHAR(255)")
    @NonNull
    private String password;

    @Column(name = "email", columnDefinition = "VARCHAR(255)", unique = true)
    @NonNull
    private String email;

    @Column(name = "phone", columnDefinition = "VARCHAR(255)", unique = true)
    @NonNull
    private String phone;

    @Column(name = "is_enabled", columnDefinition = "boolean")
    private boolean isEnabled;

    @Column(name = "code", columnDefinition = "VARCHAR(255)", unique = true)
    @NonNull
    private String code;

    @ManyToMany
    @JoinTable(name = "user_products_table",
            joinColumns = @JoinColumn(name = "id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "productId", referencedColumnName = "product_id"))
    private Collection<ProductEntity> favoriteProducts;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles_table",
            joinColumns = @JoinColumn(name = "id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "role_id",
                    foreignKey = @ForeignKey(name = "role_id", foreignKeyDefinition = "BIGINT"))
    )
    private Collection<UserRole> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(username));
        for (UserRole role : roles
        ) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
            for (UserAuthority authority :
                    role.getAuthorities()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(authority.getPermissionName()));
            }
        }
        return grantedAuthorities;
    }
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_addresses_table",
            joinColumns = @JoinColumn(name = "id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "addressId", referencedColumnName = "address_id"))
    private List<AddressEntity> addresses;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(joinColumns = @JoinColumn(name = "id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "notificationId", referencedColumnName = "notification_id"))
    private Collection<NotificationEntity> notifications;

    @Override
    public @NonNull String getPassword() {
        return this.password;
    }

    @Override
    public @NonNull String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}

