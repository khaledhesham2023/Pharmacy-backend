package com.khaledamin.pharmacy.main;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@Table(name = "sliders",schema = "pharmacydb")
@AllArgsConstructor
@NoArgsConstructor
public class SliderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slider_id",columnDefinition = "BIGINT")
    private long sliderId;

    @Column(name = "slider_title",columnDefinition = "VARCHAR(255)")
    @NonNull
    private String sliderTitle;

    @Column(name = "slider_image",columnDefinition = "VARCHAR(255)")
    @NonNull
    private String sliderImage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId",referencedColumnName = "product_id")
    private ProductEntity product;
}
