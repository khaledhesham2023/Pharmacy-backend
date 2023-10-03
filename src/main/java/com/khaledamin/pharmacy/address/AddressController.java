package com.khaledamin.pharmacy.address;

import com.khaledamin.pharmacy.base.BaseResponse;
import com.khaledamin.pharmacy.base.BaseResponseWithData;
import com.khaledamin.pharmacy.model.address.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/V1/")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("addresses/{id}")
    @PreAuthorize("hasAuthority('EDIT_ADDRESSES')")
    public ResponseEntity<BaseResponseWithData<List<AddressEntity>>> getUserAddresses(@PathVariable("id") long userId) {
        return ResponseEntity.ok(addressService.getAddresses(userId));
    }

    @PostMapping("addresses/deleteAddress/{addressId}")
    @PreAuthorize("hasAuthority('EDIT_ADDRESSES')")
    public ResponseEntity<BaseResponse> removeAddress(@PathVariable long addressId, @RequestBody RemoveAddressRequest request){
        return ResponseEntity.ok(addressService.removeAddress(addressId,request));
    }

    @PostMapping("addresses/addAddress")
    @PreAuthorize("hasAuthority('EDIT_ADDRESSES')")
    public ResponseEntity<AddAddressResponse> addUserAddress(@RequestBody AddAddressRequest request){
        return ResponseEntity.ok(addressService.addUserAddress(request));
    }

    @PutMapping("addresses/updateAddress/{addressId}")
    @PreAuthorize("hasAuthority('EDIT_ADDRESSES')")
    public ResponseEntity<AddAddressResponse> updateUserAddress(@RequestBody AddAddressRequest request, @PathVariable("addressId") long addressId){
        return ResponseEntity.ok(addressService.updateUserAddress(request,addressId));
    }

    @PutMapping("addresses/{addressId}")
    @PreAuthorize("hasAuthority('EDIT_ADDRESSES')")
    public ResponseEntity<BaseResponse> setDefaultAddress(@PathVariable("addressId") long addressId,@RequestBody DefaultAddressRequest request){
        return ResponseEntity.ok(addressService.setDefaultAddress(addressId,request));
    }

    @GetMapping("/addresses/types")
    @PreAuthorize("hasAuthority('EDIT_ADDRESSES')")
    public ResponseEntity<List<AddressTypeEntity>> getAddressTypes(){
        return ResponseEntity.ok(addressService.getAddressTypes());
    }

}
