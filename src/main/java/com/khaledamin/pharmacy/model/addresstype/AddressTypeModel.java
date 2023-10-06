package com.khaledamin.pharmacy.model.addresstype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddressTypeModel {

    private long typeId;
    private String typeName;
}
