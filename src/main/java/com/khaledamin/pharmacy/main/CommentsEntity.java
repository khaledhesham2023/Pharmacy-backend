package com.khaledamin.pharmacy.main;

import com.khaledamin.pharmacy.user.UserEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "comments", schema = "pharmacydb")
public class CommentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", columnDefinition = "BIGINT")
    private long commentId;

    @Column(name = "comment", columnDefinition = "LONGTEXT")
    private String comment;

}
