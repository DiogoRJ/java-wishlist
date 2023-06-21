package com.diogorj.wishlist.repository;

import com.diogorj.wishlist.entity.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends MongoRepository<Wishlist, String> {
    Optional<Wishlist> findByCustomerId(String productId);
}
