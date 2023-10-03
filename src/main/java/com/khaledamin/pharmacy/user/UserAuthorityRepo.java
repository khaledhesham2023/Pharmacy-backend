package com.khaledamin.pharmacy.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserAuthorityRepo extends JpaRepository<UserAuthority,Long> {

    @Query(value = "SELECT DISTINCT authorities.permission_name FROM users LEFT JOIN users_roles_table ON users.user_id = users_roles_table.id LEFT JOIN roles ON users_roles_table.role_id = roles.role_id LEFT JOIN roles_authorities_table ON roles.role_id = roles_authorities_table.role_id LEFT JOIN authorities ON roles_authorities_table.permission_id = authorities.permission_id WHERE users.user_id = :id",nativeQuery = true)
    List<String> getAuthoritiesByUserId(@Param("id") Long id);
}
