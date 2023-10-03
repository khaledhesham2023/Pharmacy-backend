package com.khaledamin.pharmacy.model.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginBaseResponse {
    private boolean status;
    private String message;
    private LoginResponse userData;
}
