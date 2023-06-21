package com.diogorj.wishlist.controller;

import com.diogorj.wishlist.dto.WishlistDTO;
import com.diogorj.wishlist.dto.WishlistResponseDTO;
import com.diogorj.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<WishlistResponseDTO> addProduct(@RequestBody @Validated WishlistDTO wishlist) {
        log.info("wishlist create.: {}", wishlist);
        return ResponseEntity.ok(wishlistService.create(wishlist));

    }

    @DeleteMapping(path = "/delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<WishlistResponseDTO> removeProduct(@RequestBody @Validated WishlistDTO wishlist) {
        log.info("wishlist remove product.: {}", wishlist);
        return ResponseEntity.ok(wishlistService.removeProductFromCustomer(wishlist));

    }

    @GetMapping(path = "/findAll/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<WishlistResponseDTO> findAll(@PathVariable String customerId) {
        log.info("wishlist findAll.: {}", customerId);
        return ResponseEntity.ok(wishlistService.findAll(customerId));
    }

    @GetMapping(path = "/exists")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> existsInCustomer(@RequestBody @Validated WishlistDTO wishlist) {
        log.info("wishlist existsInCustomer.: {}", wishlist);
        return ResponseEntity.ok(wishlistService.existsInCustomer(wishlist));
    }


}
