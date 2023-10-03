package com.khaledamin.pharmacy.address;

import com.khaledamin.pharmacy.model.address.GetAddressesRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface AddressRepo extends JpaRepository<AddressEntity,Long> {

    @Query(value = "DELETE FROM users_addresses_table WHERE id = :id AND address_id = :addressId",nativeQuery = true)
    @Modifying
    void deleteUserAddress(@Param("id") long userId,@Param("addressId") long addressId);

    @Query(value = "INSERT INTO users_addresses_table(id,address_id) VALUES (:id,:addressId)", nativeQuery = true)
    @Modifying
    void addUserAddress(@Param("id") long userId,@Param("addressId") long addressId);

}
