package com.khaledamin.pharmacy.config;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Configuration
@RequiredArgsConstructor
public class UserConfig {

    @Bean
    @Scope(scopeName = "prototype")
    public String generateUserCode() {
        return RandomStringUtils.randomNumeric(4);
    }

    @Bean
    @Scope(scopeName = "prototype")
    public String getNow() {
        return new Date().toString();
    }
}
