package com.khaledamin.pharmacy.user;

import com.khaledamin.pharmacy.base.BaseResponse;
import com.khaledamin.pharmacy.main.ProductEntity;
import com.khaledamin.pharmacy.model.category.ProductItem;
import com.khaledamin.pharmacy.model.changepassword.UpdateUserRequest;
import com.khaledamin.pharmacy.model.changepassword.UpdateUserResponse;
import com.khaledamin.pharmacy.model.favorites.AddToFavoritesRequest;
import com.khaledamin.pharmacy.model.login.LoginBaseResponse;
import com.khaledamin.pharmacy.model.login.LoginRequest;
import com.khaledamin.pharmacy.model.signup.SignupRequest;
import com.khaledamin.pharmacy.model.signup.SignupResponse;
import com.khaledamin.pharmacy.notification.NotificationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

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

    @PostMapping("favorites/add")
    public ResponseEntity<BaseResponse> addToFavorites(@RequestBody AddToFavoritesRequest request){
        return ResponseEntity.ok(userService.addToFavorites(request));
    }

    @GetMapping("favorites/list/{userId}")
    public ResponseEntity<List<ProductEntity>> getUserFavorites(@PathVariable("userId") long userId){
        return ResponseEntity.ok(userService.getUserFavorites(userId));
    }

    @PostMapping("favorites/remove")
    public ResponseEntity<BaseResponse> getUserFavorites(@RequestBody AddToFavoritesRequest request){
        return ResponseEntity.ok(userService.removeFromFavorites(request));
    }

    @PutMapping("updateUser")
    public ResponseEntity<UpdateUserResponse> updateUser(@RequestBody UpdateUserRequest request){
        return ResponseEntity.ok(userService.updateUser(request));
    }

    @GetMapping("notifications/{id}")
    public ResponseEntity<Collection<NotificationEntity>> getUserNotifications(@PathVariable("id") long userId){
        return ResponseEntity.ok(userService.getUserNotifications(userId));
    }



}
