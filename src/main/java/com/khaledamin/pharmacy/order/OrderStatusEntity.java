package com.khaledamin.pharmacy.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "order_statuses",schema = "pharmacydb")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id",columnDefinition = "BIGINT")
    private long statusId;
    @Column(name = "status_name",columnDefinition = "VARCHAR(255)")
    private String statusName;

}
