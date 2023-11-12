package com.khaledamin.pharmacy.model.changepassword;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateUserRequest {
    private long userId;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String phone;
    private String password;

}
