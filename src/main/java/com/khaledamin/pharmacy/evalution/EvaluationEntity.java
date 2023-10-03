package com.khaledamin.pharmacy.evalution;

import com.khaledamin.pharmacy.main.ProductEntity;
import com.khaledamin.pharmacy.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "evaluations",schema = "pharmacydb")
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id",columnDefinition = "BIGINT")
    private long evaluationId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId",referencedColumnName = "product_id")
    private ProductEntity product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id",referencedColumnName = "user_id")
    private UserEntity user;

    @Column(name = "rate",columnDefinition = "double")
    private double rate;

    public long getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(long evaluationId) {
        this.evaluationId = evaluationId;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}