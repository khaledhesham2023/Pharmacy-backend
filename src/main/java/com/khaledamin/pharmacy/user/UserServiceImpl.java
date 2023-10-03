package com.khaledamin.pharmacy.user;

import com.khaledamin.pharmacy.config.UserConfig;
import com.khaledamin.pharmacy.filter.JwtService;
import com.khaledamin.pharmacy.model.login.LoginBaseResponse;
import com.khaledamin.pharmacy.model.login.LoginRequest;
import com.khaledamin.pharmacy.model.login.LoginResponse;
import com.khaledamin.pharmacy.model.signup.SignupRequest;
import com.khaledamin.pharmacy.model.signup.SignupResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserRoleRepo userRoleRepo;

    @Autowired
    private UserConfig userConfig;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public SignupResponse signup(SignupRequest request) {
        UserEntity user = userRepo.findByUsername(request.getUsername());
        UserRole role = userRoleRepo.findByRoleName("USER_CUSTOMER");
        if (user == null) {
            UserEntity newUser = UserEntity.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .code("M" + userConfig.generateUserCode())
                    .isEnabled(false)
                    .roles(List.of(role))
                    .phone(request.getPhone())
                    .build();
            userRepo.save(newUser);
            return new SignupResponse("User created successfully. Please Verify your account.", true, request.getEmail(), request.getUsername());
        }
        return new SignupResponse("User already exists", false, user.getEmail(), user.getUsername());
    }

    @Override
    public LoginBaseResponse login(LoginRequest request) {
        UserEntity user = userRepo.findByUsername(request.getUsername());
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword()) && user.isEnabled()) {
            return LoginBaseResponse.builder()
                    .status(true)
                    .message("User logged in successfully")
                    .userData(LoginResponse.builder()
                            .id(user.getId())
                            .firstname(user.getFirstname())
                            .lastname(user.getLastname())
                            .username(user.getUsername())
                            .phone(user.getPhone())
                            .code(user.getCode())
                            .email(user.getEmail())
                            .roles(userRoleRepo.getRolesByUserId(user.getId()))
                            .token(jwtService.generateToken(user))
                            .addresses(user.getAddresses())
                            .build())
                    .build();
        } else {
            return LoginBaseResponse.builder()
                    .status(false)
                    .message("Error logging in user, please verify account or try again.")
                    .userData(null)
                    .build();
        }
    }
}
