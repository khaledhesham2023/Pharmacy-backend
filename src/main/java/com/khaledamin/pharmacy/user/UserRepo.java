package com.khaledamin.pharmacy.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    @Query(value = "update users set password = :newPassword where user_id = :id", nativeQuery = true)
    @Modifying
    void changePassword(@Param("newPassword") String newPassword, @Param("id") long id);


    @Query(value = "update users set is_enabled = true where user_id = :id", nativeQuery = true)
    @Modifying
    void setEnabled(@Param("id") long id);

    UserEntity findByPhone(String phone);
}
