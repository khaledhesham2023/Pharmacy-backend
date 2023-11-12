package com.khaledamin.pharmacy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {
    private long id;
    private String firstname;
    private String lastname;
    private String username;
    private String phone;
    private String email;
    private String code;
}
