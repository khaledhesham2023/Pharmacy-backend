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

    @PostMapping("{lang}/sendOTP")
    public ResponseEntity<PasswordResetResponse> sendOTP(@RequestBody @Valid PasswordResetRequest request,@PathVariable("lang") String language) {
        return ResponseEntity.ok(passwordService.sendOTP(request,language));
    }

    @PostMapping("{lang}/validateOTP")
    public ResponseEntity<PasswordResetResponse> validateOTP(@RequestBody @Valid ValidateOTPRequest request, @PathVariable("lang") String language) {
        return ResponseEntity.ok(passwordService.validateOTP(request,language));
    }

    @PutMapping("{lang}/changePassword")
    @PreAuthorize("hasAuthority('CHANGE_PASSWORD')")
    public ResponseEntity<PasswordChangeResponse> changePassword(@RequestBody @Valid PasswordChangeRequest request, @PathVariable("lang") String language) {
        return ResponseEntity.ok(passwordService.changePassword(request,language));
    }

    @PostMapping("{lang}/validateUser")
    public ResponseEntity<PasswordChangeResponse> validateUser(@RequestBody @Valid ValidateOTPRequest request, @PathVariable("lang") String language) {
        return ResponseEntity.ok(passwordService.validateUser(request,language));
    }

    @PutMapping("{lang}/resetPassword")
    public ResponseEntity<PasswordChangeResponse> resetPassword(@RequestBody @Valid ResetPasswordRequest request, @PathVariable("lang") String language) {
        return ResponseEntity.ok(passwordService.resetPassword(request,language));
    }

}
