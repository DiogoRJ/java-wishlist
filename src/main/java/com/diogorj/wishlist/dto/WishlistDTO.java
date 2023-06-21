package com.diogorj.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDTO {

    private String customerId;
    private String productId;
}
