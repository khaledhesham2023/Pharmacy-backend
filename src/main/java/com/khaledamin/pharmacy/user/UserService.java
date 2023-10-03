package com.khaledamin.pharmacy.user;

import com.khaledamin.pharmacy.model.login.LoginBaseResponse;
import com.khaledamin.pharmacy.model.login.LoginRequest;
import com.khaledamin.pharmacy.model.signup.SignupRequest;
import com.khaledamin.pharmacy.model.signup.SignupResponse;

public interface UserService {

    SignupResponse signup(SignupRequest request);

    LoginBaseResponse login(LoginRequest request);
}
