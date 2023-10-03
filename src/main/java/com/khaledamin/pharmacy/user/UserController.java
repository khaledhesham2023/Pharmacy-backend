package com.khaledamin.pharmacy.user;

import com.khaledamin.pharmacy.model.login.LoginBaseResponse;
import com.khaledamin.pharmacy.model.login.LoginRequest;
import com.khaledamin.pharmacy.model.signup.SignupRequest;
import com.khaledamin.pharmacy.model.signup.SignupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/V1/")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Valid SignupRequest signupRequest) {
        return ResponseEntity.ok(userService.signup(signupRequest));
    }

    @PostMapping("login")
    public ResponseEntity<LoginBaseResponse> login(@RequestBody @Valid LoginRequest loginRequest){
        return ResponseEntity.ok(userService.login(loginRequest));
    }

}
