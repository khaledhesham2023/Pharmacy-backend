package com.khaledamin.pharmacy.sms;

import com.khaledamin.pharmacy.config.TwilioConfig;
import com.khaledamin.pharmacy.model.SmsRequest;
import com.khaledamin.pharmacy.model.SmsResponse;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwilioSmsSender implements SmsSender {

    @Autowired
    private TwilioConfig twilioConfig;

    @Override
    public SmsResponse sendSms(SmsRequest smsRequest) {
        if (isPhoneNumberValid(smsRequest.getPhoneNumber())) {
            PhoneNumber to = new PhoneNumber("+201021998698");
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrial_number());
            String message = smsRequest.getMessage();
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();
            return SmsResponse.builder()
                    .status(true)
                    .message(smsRequest.getMessage())
//                    .verificationCode("12345")
                    .build();
        } else {
            return SmsResponse.builder()
                    .status(false)
                    .message("Sms sending failed")
//                    .verificationCode(null)
                    .build();
        }

    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        return true;
    }
}
