package com.khaledamin.pharmacy.address;

import com.khaledamin.pharmacy.user.UserEntity;
import com.twilio.rest.api.v2010.account.Address;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "addresses", schema = "pharmacydb")
@Builder
public class AddressEntity {
    @Id
    @Column(name = "address_id", columnDefinition = "BIGINT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;
    @Column(name = "area", columnDefinition = "VARCHAR(255)")
    @NonNull
    private String area;
    @Column(name = "street", columnDefinition = "VARCHAR(255)")
    @NonNull
    private String street;
    @Column(name = "block_no", columnDefinition = "BIGINT")
    private int blockNo;
    @Column(name = "floor_no", columnDefinition = "BIGINT")
    private int floorNo;
    @Column(name = "apartment_no", columnDefinition = "BIGINT")
    private int apartmentNo;
    @Column(name = "additional_details", columnDefinition = "LONGTEXT")
    private String additionalDetails;
    @Column(name = "latitude", columnDefinition = "DOUBLE")
    private double latitude;
    @Column(name = "longitude", columnDefinition = "DOUBLE")
    private double longitude;
    @Column(name = "governorate", columnDefinition = "VARCHAR(255)")
    @NonNull
    private String governorate;
    @Column(name = "city", columnDefinition = "VARCHAR(255)")
    @NonNull
    private String city;

    @Column(name = "phone", columnDefinition = "VARCHAR(255)")
    @NonNull
    private String phone;

    @Column(name = "is_default", columnDefinition = "boolean")
    private boolean isDefault;

    @Column(name = "address_name", columnDefinition = "VARCHAR(255)")
    private String addressName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "typeId",referencedColumnName = "type_id")
    private AddressTypeEntity addressType;

    public AddressTypeEntity getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressTypeEntity addressType) {
        this.addressType = addressType;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getGovernorate() {
        return governorate;
    }

    public void setGovernorate(String governorate) {
        this.governorate = governorate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }
}
