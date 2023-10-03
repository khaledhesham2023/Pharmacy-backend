package com.khaledamin.pharmacy.cart;

import com.khaledamin.pharmacy.base.BaseResponse;
import com.khaledamin.pharmacy.model.cart.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/V1/")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("cart/addItemToCart")
    @PreAuthorize("hasAuthority('CREATE_CART')")
    public ResponseEntity<BaseResponse> addItemToCart(@RequestBody CreateCartRequest request) {
        return ResponseEntity.ok(cartService.addItemToCart(request));
    }
    @GetMapping("cart/getUserCarts/{id}")
    @PreAuthorize("hasAuthority('CREATE_CART')")
    public ResponseEntity<List<CartProductItem>> getCartProductsByCartId(@PathVariable("id") long cartId) {
        return ResponseEntity.ok(cartService.getCartProductsByCartId(cartId));
    }

    @PutMapping("cart/updateQuantity")
    @PreAuthorize("hasAuthority('CREATE_CART')")
    public ResponseEntity<BaseResponse> updateQuantity(@RequestBody UpdateQuantityRequest request){
        return ResponseEntity.ok(cartService.updateQuantity(request));
    }

    @PostMapping("cart/deleteCartItem")
    @PreAuthorize("hasAuthority('CREATE_CART')")
    public ResponseEntity<BaseResponse> deleteCartItem(@RequestBody RemoveProductRequest request){
        return ResponseEntity.ok(cartService.removeProduct(request));
    }
}
