package com.diogorj.wishlist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "wishlist")
public class Wishlist{

    @Id
    private String id;
    private String customerId;
    private List<String> products;

    public Wishlist(String customerId, List<String> products) {
    }
}
