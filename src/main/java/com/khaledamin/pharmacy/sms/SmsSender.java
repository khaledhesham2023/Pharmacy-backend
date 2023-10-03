package com.khaledamin.pharmacy.sms;

import com.khaledamin.pharmacy.model.SmsRequest;
import com.khaledamin.pharmacy.model.SmsResponse;

public interface SmsSender {
    SmsResponse sendSms(SmsRequest smsRequest);
}
