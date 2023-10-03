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

    @Column(name = "slider_title_ar",columnDefinition = "VARCHAR(255)")
    @NonNull
    private String sliderTitleAr;

    @Column(name = "slider_image",columnDefinition = "VARCHAR(255)")
    @NonNull
    private String sliderImage;

    @Column(name = "slider_title",columnDefinition = "VARCHAR(255)")
    @NonNull
    private String sliderTitle;
}
