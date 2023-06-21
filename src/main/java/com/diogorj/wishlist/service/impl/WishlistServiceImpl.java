package com.diogorj.wishlist.service.impl;

import com.diogorj.wishlist.dto.WishlistDTO;
import com.diogorj.wishlist.dto.WishlistResponseDTO;
import com.diogorj.wishlist.entity.Wishlist;
import com.diogorj.wishlist.exception.ApiException;
import com.diogorj.wishlist.mapper.Mapper;
import com.diogorj.wishlist.repository.WishlistRepository;
import com.diogorj.wishlist.service.WishlistService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Slf4j
public class WishlistServiceImpl implements WishlistService, Mapper {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Override
    public WishlistResponseDTO create(WishlistDTO wishlist) {

        // validacao
        this.validacao(wishlist);

        Optional<Wishlist> wishlistAux = wishlistRepository.findByCustomerId(wishlist.getCustomerId());

        if (Objects.nonNull(wishlistAux) && wishlistAux.isPresent()) {

            if (Objects.nonNull(wishlistAux.get().getProducts())
                    && wishlistAux.get().getProducts().size() > 19) {
                throw new ApiException("Não foi possivel cadastrar um novo produto, limite de 20 produtos atingido.");
            }

            if (Objects.nonNull(wishlistAux.get().getProducts())) {
                wishlistAux.get().getProducts().add(wishlist.getProductId());
            } else {
                List<String> products = new ArrayList<>();
                products.add(wishlist.getProductId());
                wishlistAux.get().setProducts(products);
            }

            var persisted = wishlistRepository.save(wishlistAux.get());

            return map(persisted, WishlistResponseDTO.class);
        } else {
            var builder = new Wishlist();
            builder.setCustomerId(wishlist.getCustomerId());

            List<String> products = new ArrayList<>();
            products.add(wishlist.getProductId());
            builder.setProducts(products);
            builder.setId("12312331");

            log.info("\n\nWishList: {}\n\n", builder);

            Wishlist persisted = wishlistRepository.save(builder);
            log.info("\n\nSaved: {}\n\n", persisted);

            return map(persisted, WishlistResponseDTO.class);
        }

    }

    @Override
    public WishlistResponseDTO removeProductFromCustomer(WishlistDTO toRemove) {

        this.validacao(toRemove);

        var remove = wishlistRepository.findByCustomerId(toRemove.getCustomerId());

        if (!remove.isPresent()) {
            throw new ApiException("O \"customerId\" não foi localizado.");
        }

        var haveProduct = remove.get().getProducts().stream().anyMatch(filter -> filter.equalsIgnoreCase(toRemove.getProductId()));

        if (haveProduct) {
            remove.get().getProducts().remove(toRemove.getProductId());
            var deleted = wishlistRepository.save(remove.get());
            return map(deleted, WishlistResponseDTO.class);
        } else {
            throw new ApiException("Produto informado não está vinculado ao customerId: "+toRemove.getCustomerId());
        }
    }

    @Override
    public WishlistResponseDTO findAll(String customerId) {

        if (StringUtils.isBlank(customerId)) {
            throw new ApiException("O preenchimento do \"customerId\" é obrigatório.");
        }

        var findAll = wishlistRepository.findByCustomerId(customerId);

        if (!findAll.isPresent()) {
            throw new ApiException("O \"customerId\" não foi localizado.");
        }



        return map(findAll.get(), WishlistResponseDTO.class);
    }

    @Override
    public boolean existsInCustomer(WishlistDTO wishlist) {

        this.validacao(wishlist);

        var find = wishlistRepository.findByCustomerId(wishlist.getCustomerId());

        if (!find.isPresent()) {
            throw new ApiException("O \"customerId\" não foi localizado.");
        }

        if (Objects.nonNull(find.get().getProducts())) {
            return find.get().getProducts().stream().anyMatch(any -> any.equalsIgnoreCase(wishlist.getProductId()));
        }

        return false;
    }


    private void validacao(WishlistDTO wishlist) {
        if (StringUtils.isBlank(wishlist.getCustomerId())) {
            throw new ApiException("O preenchimento do \"customerId\" é obrigatório.");
        }
        if (StringUtils.isBlank(wishlist.getProductId())) {
            throw new ApiException("O preenchimento do \"productId\" é obrigatório.");
        }
    }

}
