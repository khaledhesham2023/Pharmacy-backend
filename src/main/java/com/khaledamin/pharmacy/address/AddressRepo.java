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

    @Query(value = "SELECT * FROM pharmacydb.addresses a inner join pharmacydb.users_addresses_table uat on a.address_id = uat.address_id inner join users u on uat.id = u.user_id  where uat.id = :userId and uat.address_id = :addressId;", nativeQuery = true)
    AddressEntity findAddressByUserIdAndAddressId(@Param("userId") long userId,@Param("addressId") long addressId);

    @Query(value = "SELECT * FROM addresses a LEFT JOIN users_addresses_table uat ON a.address_id = uat.address_id LEFT JOIN users u ON uat.id = u.user_id WHERE u.user_id = :userId AND a.is_default = true",nativeQuery = true)
    AddressEntity findByUserAndDefaultAddress(@Param("userId") long userId);

}
