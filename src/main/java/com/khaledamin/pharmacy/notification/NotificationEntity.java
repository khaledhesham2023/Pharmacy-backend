package com.khaledamin.pharmacy.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "notifications")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id",columnDefinition = "BIGINT")
    private long notificationId;

    @Column(name = "notification_icon",columnDefinition = "VARCHAR(255)")
    private String notificationIcon;

    @Column(name = "notification_title",columnDefinition = "VARCHAR(255)")
    private String notificationTitle;

    @Column(name = "notification_time",columnDefinition = "VARCHAR(255)")
    private String notificationTime;


}
