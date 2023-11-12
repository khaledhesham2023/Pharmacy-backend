package com.khaledamin.pharmacy.main;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "categories",schema = "pharmacydb")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id",columnDefinition = "BIGINT")
    private long categoryId;
    @Column(name = "category_image",columnDefinition = "VARCHAR(255)")
    @NonNull
    private String categoryImage;

    @Column(name = "category_name",columnDefinition = "VARCHAR(255)")
    @NonNull
    private String categoryName;


}
