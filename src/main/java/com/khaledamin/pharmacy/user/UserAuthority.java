package com.khaledamin.pharmacy.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;

@Entity
@Data
@Table(name = "authorities",schema = "pharmacydb")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuthority  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "permission_id",columnDefinition = "BIGINT")
    private Long permissionId;

    @NonNull
    @Column(name = "permission_name",columnDefinition = "VARCHAR(255)")
    private String permissionName;

}
