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

import java.util.Collection;
import java.util.List;

public interface UserService {

    SignupResponse signup(SignupRequest request, String language);

    LoginBaseResponse login(LoginRequest request,String language);

    BaseResponse addToFavorites(AddToFavoritesRequest request);

    List<ProductEntity> getUserFavorites(long userId);

    BaseResponse removeFromFavorites(AddToFavoritesRequest request);

    UpdateUserResponse updateUser(UpdateUserRequest request);

    Collection<NotificationEntity> getUserNotifications(long userId);


}
