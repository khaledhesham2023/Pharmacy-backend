package com.khaledamin.pharmacy.shipping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "shipping_methods",schema = "pharmacydb")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_id",columnDefinition = "BIGINT")
    private long shippingId;

    @Column(name = "shipping_name",columnDefinition = "VARCHAR(255)")
    private String shippingName;

    @Column(name = "shipping_amount",columnDefinition = "double")
    private double shippingAmount;

    public long getShippingId() {
        return shippingId;
    }

    public void setShippingId(long shippingId) {
        this.shippingId = shippingId;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public double getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(double shippingAmount) {
        this.shippingAmount = shippingAmount;
    }
}
