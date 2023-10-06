package com.khaledamin.pharmacy.main;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "products", schema = "pharmacydb")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", columnDefinition = "BIGINT")
    private long productId;

    @Column(name = "product_name", columnDefinition = "VARCHAR(255)")
    @NonNull
    private String productName;

    @Column(name = "product_name_ar", columnDefinition = "VARCHAR(255)")
    @NonNull
    private String productNameAr;

    @Column(name = "product_image",columnDefinition = "VARCHAR(255)")
    @NonNull
    private String productImage;

    @Column(name = "product_brand", columnDefinition = "VARCHAR(255)")
    @NonNull
    private String productBrand;

    @Column(name = "product_brand_ar", columnDefinition = "VARCHAR(255)")
    @NonNull
    private String productBrandAr;

    @Column(name = "product_unit_price", columnDefinition = "DOUBLE")
    private double productUnitPrice;

    @Column(name = "product_pack_price", columnDefinition = "DOUBLE")
    private double productPackPrice;

    @Column(name = "product_unit", columnDefinition = "VARCHAR(255)")
    private String productUnit;

    @Column(name = "product_unit_ar", columnDefinition = "VARCHAR(255)")
    private String productUnitAr;

    @Column(name = "product_weight", columnDefinition = "VARCHAR(255)")
    private double productWeight;

    @Column(name = "product_rate", columnDefinition = "double")
    private double productRate;

    @Column(name = "product_details",columnDefinition = "LONGTEXT")
    private String productDetails;

    @Column(name = "product_details_ar",columnDefinition = "LONGTEXT")
    private String productDetailsAr;

    @Column(name = "product_active_principle",columnDefinition = "VARCHAR(255)")
    private String productActivePrincipal;

    @Column(name = "product_active_principle_ar",columnDefinition = "VARCHAR(255)")
    private String productActivePrincipalAr;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subcategoryId",referencedColumnName = "subcategory_id")
    private SubCategoryEntity subcategory;

    @Column(name = "manufacturer",columnDefinition = "VARCHAR(255)")
    private String manufacturer;


}
