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

    public PasswordResetResponse sendOTP(PasswordResetRequest request, String language) {
        try {
            UserEntity user = userRepo.findByPhone(request.getNumber().substring(2));
            PhoneNumber to = new PhoneNumber(request.getNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrial_number());
            String otp = generateOTP();
            String otpMessage = "";
            String message = "";
            switch (language) {
                case "ar":
                    otpMessage = "مرحبا في صيدليتي , " + user.getUsername() + ". كود التفعيل هو " + otp;
                    message = "تم ارسال الرسالة النصية بنجاح";
                    break;
                case "en":
                    otpMessage = "Welcome to Pharmacy, " + user.getUsername() + ". Your verification code is " + otp;
                    message = "Sms sent successfully";
                    break;
            }
            otpMap.put(user.getUsername(), otp);
            Message.creator(to, from, otpMessage).create();
//            user.getNotifications().add()
            return PasswordResetResponse.builder()
                    .message(message)
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

    public PasswordResetResponse validateOTP(ValidateOTPRequest request, String language) {
        String message = "";
        String errorMessage = "";
        switch (language) {
            case "ar":
                message = "كود التفعيل صالح , يمكنك الانتقال الى الخطوة التالية";
                errorMessage = "كود التفعيل غير صالح , برجاء المحاولة مرة أخرى";
                break;
            case "en":
                message = "Your verification code is valid, you can proceed to the next step.";
                errorMessage = "Your verification code is invalid, please try again.";
                break;
        }
        UserEntity user = userRepo.findByPhone(request.getPhone());
        if (Objects.equals(request.getOtp(), otpMap.get(user.getUsername()))) {
            return PasswordResetResponse.builder()
                    .message(message)
                    .status(OTPStatus.VALID).build();
        } else {
            return PasswordResetResponse.builder()
                    .message(errorMessage)
                    .status(OTPStatus.INVALID)
                    .build();
        }
    }

    public PasswordChangeResponse changePassword(PasswordChangeRequest request, String language) {
        String message = "";
        String errorMessage = "";
        switch (language) {
            case "ar":
                message = "تم تغيير كلمة المرور بنجاح";
                errorMessage = "خطأ أثناء تغيير كلمة المرور";
                break;
            case "en":
                message = "Password Changed successfully.";
                errorMessage = "Error during changing password.";
                break;
        }
        UserEntity user = userRepo.findById(request.getUserId()).orElseThrow();
        String newPassword = passwordEncoder.encode(request.getNewPassword());
        if (user != null && request.getNewPassword().equals(request.getConfirmNewPassword())) {
            userRepo.changePassword(newPassword, user.getId());
            return PasswordChangeResponse.builder()
                    .status(true)
                    .message(message)
                    .build();
        } else {
            return PasswordChangeResponse.builder()
                    .status(false)
                    .message(errorMessage)
                    .build();
        }
    }

    public PasswordChangeResponse validateUser(ValidateOTPRequest request,String language) {
        UserEntity user = userRepo.findByPhone(request.getPhone());
        String message = "";
        String errorMessage = "";
        switch (language){
            case "ar":
                message = "حساب المستخدم صالح , يمكنك تسجيل الدخول الأن باستخدام الحساب.";
                errorMessage = "خطأ أثناء تفعيل حساب المستخدم";
                break;
            case "en":
                message = "User is valid, You can sign in now with your account.";
                errorMessage = "Error during validating user account.";
                break;
        }
        if (request.getOtp().equals(otpMap.get(user.getUsername()))) {
            userRepo.setEnabled(user.getId());
            otpMap.remove(user.getUsername());
            return PasswordChangeResponse.builder()
                    .status(true)
                    .message(message)
                    .build();
        } else {
            return PasswordChangeResponse.builder()
                    .status(false)
                    .message(errorMessage)
                    .build();
        }
    }

    public PasswordChangeResponse resetPassword(ResetPasswordRequest request, String language) {
        try {
            UserEntity user = userRepo.findByPhone(request.getPhoneNumber());
            String message = "";
            String errorMessage = "";
            switch (language){
                case "ar":
                    message = "تم اعادة تعيين كلمة المرور بنجاح";
                    errorMessage = "كلمة المرور مماثلة لكلمة المرور الحالية";
                    break;
                case "en":
                    message = "Password reset successfully";
                    errorMessage = "Password already matches the existing one.";
                    break;
            }
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                String newPassword = passwordEncoder.encode(request.getPassword());
                userRepo.changePassword(newPassword, user.getId());
                return PasswordChangeResponse.builder()
                        .status(true)
                        .message(message)
                        .build();
            } else {
                return PasswordChangeResponse.builder()
                        .status(false)
                        .message(errorMessage)
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
