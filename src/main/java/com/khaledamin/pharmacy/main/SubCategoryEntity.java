package com.khaledamin.pharmacy.main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "subcategories",schema = "pharmacydb")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class SubCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subcategory_id",columnDefinition = "BIGINT")
    private long subcategoryId;

    @Column(name = "subcategory_title",columnDefinition = "VARCHAR(255)")
    @NonNull
    private String subcategoryTitle;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryId",referencedColumnName = "category_id")
    private CategoryEntity category;

}
