package com.khaledamin.pharmacy.address;

import com.khaledamin.pharmacy.base.BaseResponse;
import com.khaledamin.pharmacy.base.BaseResponseWithData;
import com.khaledamin.pharmacy.model.address.*;
import com.khaledamin.pharmacy.user.UserEntity;
import com.khaledamin.pharmacy.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AddressService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private AddressTypeRepo addressTypeRepo;

    public BaseResponseWithData<List<AddressEntity>> getAddresses(long userId) {
        UserEntity user = userRepo.findById(userId).orElseThrow();
        return new BaseResponseWithData<List<AddressEntity>>("Addresses retrieved successfully", user.getAddresses());

    }

    public BaseResponse removeAddress(long addressId, RemoveAddressRequest request) {
        UserEntity user = userRepo.findById(request.getUserId()).orElseThrow();
        List<AddressEntity> userAddresses = user.getAddresses();
        for (AddressEntity address : userAddresses) {
            if (address.getAddressId() == addressId) {
                boolean isDeleted = userAddresses.remove(address);
                if (isDeleted) {
                    addressRepo.deleteById(addressId);
                    user.setAddresses(userAddresses);
                    userRepo.save(user);
                    return new BaseResponse(true, "Address Removed Successfully");
                }
            }
        }
        return new BaseResponse(false, "Address doesn't belong to that user");
    }

    public AddAddressResponse addUserAddress(AddAddressRequest request) {
        UserEntity user = userRepo.findById(request.getUserId()).orElseThrow();
        List<AddressEntity> addresses = user.getAddresses();
        if (request.getIsDefault() == true) {
            for (AddressEntity address : addresses) {
                address.setIsDefault(false);
            }
            addresses.add(AddressEntity.builder()
                    .area(request.getArea())
                    .latitude(request.getLatitude())
                    .longitude(request.getLongitude())
                    .street(request.getStreet())
                    .blockNo(request.getBlockNo())
                    .floorNo(request.getFloorNo())
                    .apartmentNo(request.getApartmentNo())
                    .additionalDetails(request.getAdditionalDetails())
                    .city(request.getCity())
                    .governorate(request.getGovernorate())
                    .addressName(request.getAddressName())
                    .phone(request.getPhone())
                    .isDefault(request.getIsDefault())
                    .type(addressTypeRepo.findById(request.getAddressTypeId()).orElseThrow().getTypeName())
                    .build());
            userRepo.save(user);
        } else {
            addresses.add(AddressEntity.builder()
                    .area(request.getArea())
                    .latitude(request.getLatitude())
                    .longitude(request.getLongitude())
                    .street(request.getStreet())
                    .blockNo(request.getBlockNo())
                    .floorNo(request.getFloorNo())
                    .apartmentNo(request.getApartmentNo())
                    .additionalDetails(request.getAdditionalDetails())
                    .city(request.getCity())
                    .governorate(request.getGovernorate())
                    .addressName(request.getAddressName())
                    .phone(request.getPhone())
                    .isDefault(request.getIsDefault())
                    .type(addressTypeRepo.findById(request.getAddressTypeId()).orElseThrow().getTypeName())
                    .build());
            userRepo.save(user);
        }
        return AddAddressResponse.builder()
                .status(true)
                .message("Address Added Successfully")
                .build();
    }

    public AddAddressResponse updateUserAddress(AddAddressRequest request, long addressId) {
        UserEntity user = userRepo.findById(request.getUserId()).orElseThrow();
        List<AddressEntity> userAddresses = user.getAddresses();
        for (AddressEntity address : userAddresses) {
            if (address.getAddressId() == addressId) {
                address.setArea(request.getArea());
                address.setLatitude(request.getLatitude());
                address.setLongitude(request.getLongitude());
                address.setStreet(request.getStreet());
                address.setBlockNo(request.getBlockNo());
                address.setFloorNo(request.getFloorNo());
                address.setApartmentNo(request.getApartmentNo());
                address.setAdditionalDetails(request.getAdditionalDetails());
                address.setCity(request.getCity());
                address.setGovernorate(request.getGovernorate());
                address.setType(addressTypeRepo.findById(request.getAddressTypeId()).orElseThrow().getTypeName());
                user.setAddresses(userAddresses);
                userRepo.save(user);
                return AddAddressResponse.builder()
                        .status(true)
                        .message("Address Updated Successfully")
                        .build();
            }
        }
        return AddAddressResponse.builder()
                .status(false)
                .message("Address does not belong to that user")
                .build();
    }

    public BaseResponse setDefaultAddress(long addressId, DefaultAddressRequest request) {
        List<AddressEntity> userAddresses = userRepo.findById(request.getUserId()).get().getAddresses();
        for (AddressEntity address : userAddresses) {
            if (address.getAddressId() == addressId) {
                address.setIsDefault(true);
            } else {
                address.setIsDefault(false);
            }
            addressRepo.save(address);
        }
        return BaseResponse.builder()
                .status(true)
                .message("Address set as default")
                .build();
    }
    public List<AddressTypeEntity> getAddressTypes(){
        return addressTypeRepo.findAll();
    }
}