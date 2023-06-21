package com.diogorj.wishlist.service;

import com.diogorj.wishlist.dto.WishlistDTO;
import com.diogorj.wishlist.dto.WishlistResponseDTO;

public interface WishlistService {
    WishlistResponseDTO create(WishlistDTO wishlist);
    WishlistResponseDTO removeProductFromCustomer(WishlistDTO wishlist);
    WishlistResponseDTO findAll(String customerId);

    boolean existsInCustomer(WishlistDTO wishlist);

}
