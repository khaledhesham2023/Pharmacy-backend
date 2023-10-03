package com.khaledamin.pharmacy.model.signup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupResponse {

    private String message;

    private boolean status;

    private String email;

    private String username;
}
