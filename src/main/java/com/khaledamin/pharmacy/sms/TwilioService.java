package com.khaledamin.pharmacy.sms;

import com.khaledamin.pharmacy.model.SmsRequest;
import com.khaledamin.pharmacy.model.SmsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    @Autowired
    private TwilioSmsSender smsSender;

    public SmsResponse sendSms(SmsRequest request){
         return smsSender.sendSms(request);
    }
}
