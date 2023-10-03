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
        ProductEntity product = productRepo.findById(request.getProductId()).orElseThrow();
        UserEntity user = userRepo.findById(request.getUserId()).orElseThrow();
        List<CartEntity> carts = cartRepo.findByUser(user);
        for (CartEntity cart : carts){
            if (request.getProductId() == cart.getProduct().getProductId()){
                cart.setQuantity(request.getQuantity());
                cartRepo.save(cart);
                return BaseResponse.builder().status(true).message("Quantity updated successfully").build();
            }
        }
        CartEntity cart = new CartEntity();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(request.getQuantity());
        cartRepo.save(cart);
        return BaseResponse.builder()
                .status(true)
                .message("Product added successfully")
                .build();
    }

    public List<CartProductItem> getCartProductsByCartId(long cartId) {
        List<CartProductItem> cartProductItems = new ArrayList<>();
        UserEntity user = userRepo.findById(cartId).orElseThrow();
        List<CartEntity> userCarts = cartRepo.findByUser(user);
        for (CartEntity cart : userCarts) {
            CartProductItem cartProductItem = CartProductItem.builder()
                    .productId(cart.getProduct().getProductId())
                    .productImage(cart.getProduct().getProductImage())
                    .productActivePrincipal(cart.getProduct().getProductActivePrincipal())
                    .productDetails(cart.getProduct().getProductDetails())
                    .productWeight(cart.getProduct().getProductId())
                    .productUnit(cart.getProduct().getProductUnit())
                    .productPackPrice(cart.getProduct().getProductPackPrice())
                    .productUnitPrice(cart.getProduct().getProductUnitPrice())
                    .productBrand(cart.getProduct().getProductBrand())
                    .productName(cart.getProduct().getProductName())
                    .productRate(cart.getProduct().getProductRate())
                    .quantity(cart.getQuantity())
                    .build();
            cartProductItems.add(cartProductItem);
        }
        return cartProductItems;
    }

    public BaseResponse updateQuantity(UpdateQuantityRequest request){
        UserEntity user = userRepo.findById(request.getUserId()).orElseThrow();
        ProductEntity product = productRepo.findById(request.getProductId()).orElseThrow();
        CartEntity cart = cartRepo.findByUserAndProduct(user,product);
        cart.setQuantity(request.getQuantity());
        cartRepo.save(cart);
        return BaseResponse.builder()
                .status(true)
                .message("Quantity updated successfully")
                .build();
    }

    public BaseResponse removeProduct(RemoveProductRequest request) {
        UserEntity user = userRepo.findById(request.getUserId()).orElseThrow();
        ProductEntity product = productRepo.findById(request.getProductId()).orElseThrow();
        CartEntity cart = cartRepo.findByUserAndProduct(user, product);
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
