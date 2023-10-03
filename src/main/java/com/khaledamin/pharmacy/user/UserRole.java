package com.khaledamin.pharmacy.user;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Table(name = "roles",schema = "pharmacydb")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id",columnDefinition = "BIGINT")
    private Long roleId;

    @NonNull
    @Column(name = "role_name",columnDefinition = "VARCHAR(255)")
    private String roleName;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "roles_authorities_table",
            joinColumns = @JoinColumn(name = "roleId",referencedColumnName = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permissionId",referencedColumnName = "permission_id")
    )
    private Collection<UserAuthority> authorities;
}
