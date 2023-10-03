package com.khaledamin.pharmacy.model.otp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateOTPRequest {

    @NonNull
    @NotBlank(message = "One Time Password must be provided")
    @Length(min = 4,max = 4,message = "One Time Password shouldn't be exceeding or less than 4 numbers")
    private String otp;

    @NonNull
    @NotBlank(message = "Phone number must be provided")
    private String phone;
}
