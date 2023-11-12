package com.khaledamin.pharmacy.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.query.Param;

import javax.persistence.*;

@Entity
@Table(name = "address_types",schema = "pharmacydb")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddressTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id",columnDefinition = "BIGINT")
    private long typeId;

    @Column(name = "type_name",columnDefinition = "VARCHAR(255)")
    private String typeName;



}