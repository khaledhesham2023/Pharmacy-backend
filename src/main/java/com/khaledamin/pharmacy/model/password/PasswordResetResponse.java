package com.khaledamin.pharmacy.model.password;

import com.khaledamin.pharmacy.model.otp.OTPStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordResetResponse {
private String message;
private OTPStatus status;
private String otp;
}
