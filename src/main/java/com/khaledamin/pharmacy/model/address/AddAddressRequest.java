package com.khaledamin.pharmacy.model.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;


@AllArgsConstructor
@NoArgsConstructor
public class AddAddressRequest {
    @NotBlank(message = "Area must be provided")
    @NonNull
    private String area;
    @NotBlank(message = "Latitude must be provided")
    private double latitude;
    @NotBlank(message = "Longitude must be provided")
    private double longitude;
    @NotBlank(message = "Street must be provided")
    @NonNull
    private String street;
    @NotBlank(message = "Block Number must be provided")
    private int blockNo;
    private int floorNo;
    private int apartmentNo;
    private String additionalDetails;
    @NotBlank(message = "City must be provided")
    @NonNull
    private String city;
    @NotBlank(message = "Governorate must be provided")
    @NonNull
    private String governorate;
    private long userId;
    private String addressName;
    @NonNull
    @NotBlank(message = "Phone number must be provided")
    private String phone;

    @NonNull
    @NotBlank(message = "Address type must be provided")
    private long addressTypeId;

    public long getAddressTypeId() {
        return addressTypeId;
    }

    public void setAddressTypeId(long addressTypeId) {
        this.addressTypeId = addressTypeId;
    }

    private boolean isDefault;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getBlockNo() {
        return blockNo;
    }

    public void setBlockNo(int blockNo) {
        this.blockNo = blockNo;
    }

    public int getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(int floorNo) {
        this.floorNo = floorNo;
    }

    public int getApartmentNo() {
        return apartmentNo;
    }

    public void setApartmentNo(int apartmentNo) {
        this.apartmentNo = apartmentNo;
    }

    public String getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(String additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGovernorate() {
        return governorate;
    }

    public void setGovernorate(String governorate) {
        this.governorate = governorate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public long getAddressTypeId() {
//        return addressTypeId;
//    }
//
//    public void setAddressTypeId(long addressTypeId) {
//        this.addressTypeId = addressTypeId;
//    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
}
