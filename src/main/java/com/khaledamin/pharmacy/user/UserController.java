package com.khaledamin.pharmacy.user;

import com.khaledamin.pharmacy.model.login.LoginBaseResponse;
import com.khaledamin.pharmacy.model.login.LoginRequest;
import com.khaledamin.pharmacy.model.signup.SignupRequest;
import com.khaledamin.pharmacy.model.signup.SignupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/V1/")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("signup/{language}")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Valid SignupRequest signupRequest, @PathVariable("language") String language) {
        return ResponseEntity.ok(userService.signup(signupRequest,language));
    }

    @PostMapping("login/{language}")
    public ResponseEntity<LoginBaseResponse> login(@RequestBody @Valid LoginRequest loginRequest, @PathVariable("language") String language){
        return ResponseEntity.ok(userService.login(loginRequest,language));
    }

}
