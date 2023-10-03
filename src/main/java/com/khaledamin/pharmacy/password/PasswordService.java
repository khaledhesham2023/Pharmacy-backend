package com.khaledamin.pharmacy.password;

import com.khaledamin.pharmacy.config.TwilioConfig;
import com.khaledamin.pharmacy.model.otp.OTPStatus;
import com.khaledamin.pharmacy.model.otp.ValidateOTPRequest;
import com.khaledamin.pharmacy.model.password.*;
import com.khaledamin.pharmacy.user.UserEntity;
import com.khaledamin.pharmacy.user.UserRepo;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Service
public class PasswordService {

    @Autowired
    private TwilioConfig twilioConfig;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    Map<String, String> otpMap = new HashMap<>();

    public PasswordResetResponse sendOTP(PasswordResetRequest request) {
        try {
            UserEntity user = userRepo.findByPhone(request.getNumber().substring(2));
            PhoneNumber to = new PhoneNumber(request.getNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrial_number());
            String otp = generateOTP();
            String otpMessage = "Welcome to Pharmacy, " + user.getUsername() + ". Your verification code is " + otp;
            otpMap.put(user.getUsername(), otp);
            Message.creator(to, from, otpMessage).create();
            return PasswordResetResponse.builder()
                    .message("Sms sent successfully")
                    .status(OTPStatus.SENT)
                    .otp(otp)
                    .build();

        } catch (Exception exception) {
            return PasswordResetResponse.builder()
                    .message(exception.getMessage())
                    .status(OTPStatus.NOT_SENT)
                    .build();
        }
    }


    private String generateOTP() {
        return new DecimalFormat("0000").format(new Random().nextInt(9999));
    }

    public PasswordResetResponse validateOTP(ValidateOTPRequest request) {
        UserEntity user = userRepo.findByPhone(request.getPhone());
        if (Objects.equals(request.getOtp(), otpMap.get(user.getUsername()))) {
            return PasswordResetResponse.builder()
                    .message("Your verification code is valid, you can proceed to the next step.")
                    .status(OTPStatus.VALID).build();
        } else {
            return PasswordResetResponse.builder()
                    .message("Your verification code is invalid, please try again.")
                    .status(OTPStatus.INVALID)
                    .build();
        }
    }

    public PasswordChangeResponse changePassword(PasswordChangeRequest request) {
        UserEntity user = userRepo.findByUsername(request.getUsername());
        String newPassword = passwordEncoder.encode(request.getNewPassword());
        if (user != null && request.getNewPassword().equals(request.getConfirmNewPassword())) {
            userRepo.changePassword(newPassword, user.getId());
            return PasswordChangeResponse.builder()
                    .status(true)
                    .message("Password Changed successfully.")
                    .build();
        } else {
            return PasswordChangeResponse.builder()
                    .status(false)
                    .message("Error during changing password.")
                    .build();
        }
    }

    public PasswordChangeResponse validateUser(ValidateOTPRequest request) {
        UserEntity user = userRepo.findByPhone(request.getPhone());
        if (request.getOtp().equals(otpMap.get(user.getUsername()))) {
            userRepo.setEnabled(user.getId());
            otpMap.remove(user.getUsername());
            return PasswordChangeResponse.builder()
                    .status(true)
                    .message("User is valid, You can sign in now with your account.")
                    .build();
        } else {
            return PasswordChangeResponse.builder()
                    .status(false)
                    .message("Error during validating user account.")
                    .build();
        }
    }

    public PasswordChangeResponse resetPassword(ResetPasswordRequest request) {
        try {
            UserEntity user = userRepo.findByPhone(request.getPhoneNumber());
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                String newPassword = passwordEncoder.encode(request.getPassword());
                userRepo.changePassword(newPassword, user.getId());
                return PasswordChangeResponse.builder()
                        .status(true)
                        .message("Password reset successfully")
                        .build();
            } else {
                return PasswordChangeResponse.builder()
                        .status(false)
                        .message("Password already matches the existing one.")
                        .build();
            }
        } catch (Exception e) {
            return PasswordChangeResponse.builder()
                    .status(false)
                    .message(e.getMessage())
                    .build();
        }
    }
}
