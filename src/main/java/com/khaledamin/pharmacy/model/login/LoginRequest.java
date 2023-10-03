package com.khaledamin.pharmacy.model.login;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    @NonNull
    @NotBlank(message = "Username must be provided")
    private String username;

    @NonNull
    @NotBlank(message = "Password must be provided.")
    @Length(min = 8,message = "Password must be more than 8 characters")
    private String password;
}
