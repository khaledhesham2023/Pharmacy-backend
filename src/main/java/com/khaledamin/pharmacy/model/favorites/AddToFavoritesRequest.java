package com.khaledamin.pharmacy.model.favorites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddToFavoritesRequest {

    private long userId;
    private long productId;
}
