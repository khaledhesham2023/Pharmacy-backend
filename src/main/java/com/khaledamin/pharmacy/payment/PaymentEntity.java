package com.khaledamin.pharmacy.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.query.Param;

import javax.persistence.*;

@Entity
@Table(name = "payments",schema = "pharmacydb")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id",columnDefinition = "BIGINT")
    private long paymentId;

    @Column(name = "payment_name",columnDefinition = "VARCHAR(255)")
    private String paymentName;

    @Column(name = "payment_image",columnDefinition = "VARCHAR(255)")
    private String paymentIcon;
}
