package com.khaledamin.pharmacy.model.password;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResetPasswordRequest {

    @NonNull
    @NotBlank(message = "Phone number must be provided")
    @Length(min = 11,message = "Mobile Phone should have 11 numbers",max = 11)
    private String phoneNumber;

    @NonNull
    @NotBlank(message = "Password must be provided")
    @Length(min = 8,message = "Password must have at least 8 characters")
    private String password;

}
