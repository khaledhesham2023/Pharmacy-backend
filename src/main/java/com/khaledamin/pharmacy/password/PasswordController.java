package com.khaledamin.pharmacy.password;

import com.khaledamin.pharmacy.model.otp.ValidateOTPRequest;
import com.khaledamin.pharmacy.model.password.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/V1/")
public class PasswordController {

    @Autowired
    private PasswordService passwordService;

    @PostMapping("sendOTP")
    public ResponseEntity<PasswordResetResponse> sendOTP(@RequestBody @Valid PasswordResetRequest request) {
        return ResponseEntity.ok(passwordService.sendOTP(request));
    }

    @PostMapping("validateOTP")
    public ResponseEntity<PasswordResetResponse> validateOTP(@RequestBody @Valid ValidateOTPRequest request) {
        return ResponseEntity.ok(passwordService.validateOTP(request));
    }

    @PutMapping("changePassword")
    @PreAuthorize("hasAuthority('CHANGE_PASSWORD')")
    public ResponseEntity<PasswordChangeResponse> changePassword(@RequestBody @Valid PasswordChangeRequest request) {
        return ResponseEntity.ok(passwordService.changePassword(request));
    }

    @PostMapping("validateUser")
    public ResponseEntity<PasswordChangeResponse> validateUser(@RequestBody @Valid ValidateOTPRequest request) {
        return ResponseEntity.ok(passwordService.validateUser(request));
    }

    @PutMapping("resetPassword")
    public ResponseEntity<PasswordChangeResponse> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        return ResponseEntity.ok(passwordService.resetPassword(request));
    }

}
