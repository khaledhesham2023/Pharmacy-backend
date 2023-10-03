package com.khaledamin.pharmacy.model.signup;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequest {

    @NonNull
    @NotBlank(message = "firstname must be provided.")
    private String firstname;

    @NonNull
    @NotBlank(message = "lastname must be provided")
    private String lastname;

    @NonNull
    @NotBlank(message = "username must be provided")
    private String username;

    @NonNull
    @NotBlank(message = "password must be provided")
    @Length(min = 8, message = "Password shouldn't be less than 8 characters")
    private String password;

    @NonNull
    @NotBlank(message = "phone number must be provided")
    @Length(min = 11, max = 11, message = "Phone number shouldn't be more or less than 11 numbers")
    private String phone;

    @NonNull
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "E-mail must be in correct format")
    private String email;
}
