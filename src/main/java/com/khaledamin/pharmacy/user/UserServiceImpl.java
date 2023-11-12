package com.khaledamin.pharmacy.user;

import com.khaledamin.pharmacy.base.BaseResponse;
import com.khaledamin.pharmacy.config.TwilioConfig;
import com.khaledamin.pharmacy.config.UserConfig;
import com.khaledamin.pharmacy.filter.JwtService;
import com.khaledamin.pharmacy.main.ProductEntity;
import com.khaledamin.pharmacy.main.ProductRepo;
import com.khaledamin.pharmacy.model.User;
import com.khaledamin.pharmacy.model.category.ProductItem;
import com.khaledamin.pharmacy.model.changepassword.UpdateUserRequest;
import com.khaledamin.pharmacy.model.changepassword.UpdateUserResponse;
import com.khaledamin.pharmacy.model.favorites.AddToFavoritesRequest;
import com.khaledamin.pharmacy.model.login.LoginBaseResponse;
import com.khaledamin.pharmacy.model.login.LoginRequest;
import com.khaledamin.pharmacy.model.login.LoginResponse;
import com.khaledamin.pharmacy.model.signup.SignupRequest;
import com.khaledamin.pharmacy.model.signup.SignupResponse;
import com.khaledamin.pharmacy.notification.NotificationEntity;
import com.khaledamin.pharmacy.notification.NotificationRepo;
import com.twilio.rest.api.v2010.account.ValidationRequest;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
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
    private ProductRepo productRepo;

    @Autowired
    private TwilioConfig twilioConfig;

    @Autowired
    private UserConfig userConfig;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private NotificationRepo notificationRepo;

    @Override
    public SignupResponse signup(SignupRequest request, String language) {
        String messageSuccess = "";
        String messageFailed = "";
        switch (language) {
            case "ar":
                messageSuccess = "تم انشاء حساب المستخدم بنجاح , يرجى تأكيد الحساب";
                messageFailed = "المستخدم موجود بالفعل";
                break;
            case "en":
                messageSuccess = "Account created successfully. Please Verify your account.";
                messageFailed = "User already exists";
                break;
        }
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
                    .favoriteProducts(new ArrayList<>())
                    .notifications(new ArrayList<>())
                    .build();
            userRepo.save(newUser);
//            ValidationRequest.creator(new PhoneNumber(twilioConfig.getTrial_number())).setFriendlyName(request.getPhone()).create();
            return new SignupResponse(messageSuccess, true, request.getEmail(), request.getUsername());
        }
        return new SignupResponse(messageFailed, false, user.getEmail(), user.getUsername());
    }

    @Override
    public LoginBaseResponse login(LoginRequest request, String language) {
        String messageSuccess = "";
        String messageFailed = "";
        switch (language) {
            case "en":
                messageSuccess = "User logged in successfully";
                messageFailed = "Error logging in user, please verify account or try again.";
                break;
            case "ar":
                messageSuccess = "تم تسجيل دخول المستخدم بنجاح";
                messageFailed = "خطأ في تسجيل دخول المستخدم, برجاء تفعيل الحساب أو حاول مرة أخرى ";
                break;
        }
        UserEntity user = userRepo.findByUsername(request.getUsername());
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword()) && user.isEnabled() == true) {
            return LoginBaseResponse.builder()
                    .status(true)
                    .message(messageSuccess)
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
                            .isVerified(user.isEnabled())
                            .addresses(user.getAddresses())
                            .build())
                    .build();
        } else if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword()) && user.isEnabled() == false) {
            return LoginBaseResponse.builder()
                    .status(true)
                    .message(messageSuccess)
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
                            .isVerified(user.isEnabled())
                            .addresses(user.getAddresses())
                            .isVerified(false)
                            .build())
                    .build();
        } else {
            return LoginBaseResponse.builder()
                    .status(false)
                    .message(messageFailed)
                    .userData(null)
                    .build();
        }
    }

    @Override
    public BaseResponse addToFavorites(AddToFavoritesRequest request) {
        UserEntity user = userRepo.findById(request.getUserId()).orElseThrow();
        ProductEntity product = productRepo.findById(request.getProductId()).orElseThrow();
        Collection<ProductEntity> favoriteProducts = user.getFavoriteProducts();
        for (ProductEntity productEntity : favoriteProducts) {
            if (productEntity.getProductId() == request.getProductId()) {
                return BaseResponse.builder().message("Already in favorites list").status(false).build();
            }
        }
        favoriteProducts.add(product);
        userRepo.save(user);
        return BaseResponse.builder().message("Added to favorites").status(true).build();
    }

    @Override
    public List<ProductEntity> getUserFavorites(long userId) {
        UserEntity user = userRepo.findById(userId).orElseThrow();
        Collection<ProductEntity> favoriteProducts = user.getFavoriteProducts();
        return favoriteProducts.stream().toList();
    }

    @Override
    public BaseResponse removeFromFavorites(AddToFavoritesRequest request) {
        UserEntity user = userRepo.findById(request.getUserId()).orElseThrow();
        Collection<ProductEntity> userProducts = user.getFavoriteProducts();
        boolean favoriteRemoved = userProducts.remove(productRepo.findById(request.getProductId()).orElseThrow());
        if (favoriteRemoved == true) {
            user.setFavoriteProducts(userProducts);
            userRepo.save(user);
            return BaseResponse.builder().status(true).message("Product removed from favorites successfully").build();
        } else {
            return BaseResponse.builder().status(false).message("Product not on favorites list").build();

        }
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest request) {
        UserEntity user = userRepo.findById(request.getUserId()).orElseThrow();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        userRepo.save(user);
        return UpdateUserResponse.builder()
                .response(BaseResponse.builder().status(true).message("User data updated successfully").build())
                .user(User.builder()
                        .code(user.getCode())
                        .email(request.getEmail())
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .id(user.getId())
                        .phone(request.getPhone())
                        .username(user.getUsername())
                        .build()
                )
                .build();
    }

    @Override
    public Collection<NotificationEntity> getUserNotifications(long userId) {
        UserEntity user = userRepo.findById(userId).orElseThrow();
        return user.getNotifications();
    }
}