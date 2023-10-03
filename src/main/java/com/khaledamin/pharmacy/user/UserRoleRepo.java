package com.khaledamin.pharmacy.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Long> {

    @Query(value = "SELECT roles.role_name FROM users LEFT JOIN users_roles_table ON users.user_id = users_roles_table.id LEFT JOIN roles ON users_roles_table.role_id = roles.role_id where users.user_id = :id", nativeQuery = true)
    List<String> getRolesByUserId(@Param("id") Long id);


    UserRole findByRoleName(String roleTitle);
}
