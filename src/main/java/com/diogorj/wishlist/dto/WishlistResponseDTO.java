package com.diogorj.wishlist.dto;

import lombok.Data;

import java.util.List;

@Data
public class WishlistResponseDTO {
    private String id;
    private String customerId;
    private List<String> products;

}
