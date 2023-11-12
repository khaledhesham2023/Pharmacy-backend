package com.khaledamin.pharmacy.model.changepassword;

import com.khaledamin.pharmacy.base.BaseResponse;
import com.khaledamin.pharmacy.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateUserResponse {
    private BaseResponse response;
    private User user;
}
