package com.khaledamin.pharmacy.cart;

import com.khaledamin.pharmacy.base.BaseResponse;
import com.khaledamin.pharmacy.main.ProductEntity;
import com.khaledamin.pharmacy.main.ProductRepo;
import com.khaledamin.pharmacy.model.cart.CartProductItem;
import com.khaledamin.pharmacy.model.cart.CreateCartRequest;
import com.khaledamin.pharmacy.model.cart.RemoveProductRequest;
import com.khaledamin.pharmacy.model.cart.UpdateQuantityRequest;
import com.khaledamin.pharmacy.model.category.ProductItem;
import com.khaledamin.pharmacy.user.UserEntity;
import com.khaledamin.pharmacy.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CartRepo cartRepo;

    public BaseResponse addItemToCart(CreateCartRequest request) {
        CartEntity cartItem = cartRepo.findByUserAndProduct(userRepo.findById(request.getUserId()).orElseThrow(), productRepo.findById(request.getProductId()).orElseThrow());
        if (cartItem == null) {
            CartEntity newCartItem = CartEntity.builder()
                    .product(productRepo.findById(request.getProductId()).orElseThrow())
                    .user(userRepo.findById(request.getUserId()).orElseThrow())
                    .quantity(request.getQuantity())
                    .build();
            cartRepo.save(newCartItem);
            return BaseResponse.builder()
                    .status(true)
                    .message("Product added successfully")
                    .build();
        } else {
            cartItem.setQuantity(request.getQuantity());
            cartRepo.save(cartItem);
            return BaseResponse.builder()
                    .message("Product Quantity updated successfully")
                    .status(true)
                    .build();
        }
        }

        public List<CartEntity> getCartProductsByCartId ( long cartId){
            UserEntity user = userRepo.findById(cartId).orElseThrow();
            List<CartEntity> userCarts = cartRepo.findByUser(user);
            return userCarts;
        }

        public BaseResponse updateQuantity (UpdateQuantityRequest request){
            CartEntity cart = cartRepo.findByUserAndProduct(userRepo.findById(request.getUserId()).orElseThrow(), productRepo.findById(request.getProductId()).orElseThrow());
            cart.setQuantity(request.getQuantity());
            cartRepo.save(cart);
            return BaseResponse.builder()
                    .status(true)
                    .message("Quantity updated successfully")
                    .build();
        }

        public BaseResponse removeProduct (RemoveProductRequest request){
            CartEntity cart = cartRepo.findByUserAndProduct(userRepo.findById(request.getUserId()).orElseThrow(), productRepo.findById(request.getProductId()).orElseThrow());
            if (cart != null) {
                cartRepo.delete(cart);
                return BaseResponse.builder()
                        .status(true)
                        .message("Product removed successfully")
                        .build();
            } else {
                return BaseResponse.builder()
                        .status(false)
                        .message("Product doesn't exist in that cart")
                        .build();
            }
        }
    }
