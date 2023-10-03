package com.khaledamin.pharmacy.sms;

import com.khaledamin.pharmacy.model.SmsRequest;
import com.khaledamin.pharmacy.model.SmsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/V1/")
public class TwilioController {

    @Autowired
    private TwilioService twilioService;

    @PostMapping("sendOTPs")
    public ResponseEntity<SmsResponse> sendOtp(@RequestBody SmsRequest smsRequest){
        return ResponseEntity.ok(twilioService.sendSms(smsRequest));
    }
}
