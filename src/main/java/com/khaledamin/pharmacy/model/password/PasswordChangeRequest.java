package com.khaledamin.pharmacy.model.password;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeRequest {
    private String oldPassword;
    @NonNull
    @NotBlank(message = "Password must be provided.")
    @Length(min = 8,message = "Password must be more than 8 characters")
    private String newPassword;

    private String confirmNewPassword;
    @NonNull
    @NotBlank(message = "Username must be provided")
    private String username;
}
