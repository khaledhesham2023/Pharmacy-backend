package com.khaledamin.pharmacy.model.login;

import com.khaledamin.pharmacy.address.AddressEntity;
import com.khaledamin.pharmacy.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private long id;
    private String firstname;
    private String lastname;
    private String username;
    private String phone;
    private String email;
    private String code;
    private String token;
    private Collection<String> roles;
    private Collection<AddressEntity> addresses;
}
