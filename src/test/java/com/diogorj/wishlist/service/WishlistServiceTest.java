package com.diogorj.wishlist.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.diogorj.wishlist.dto.WishlistDTO;
import com.diogorj.wishlist.dto.WishlistResponseDTO;
import com.diogorj.wishlist.entity.Wishlist;
import com.diogorj.wishlist.exception.ApiException;
import com.diogorj.wishlist.mapper.Mapper;
import com.diogorj.wishlist.repository.WishlistRepository;
import com.diogorj.wishlist.service.impl.WishlistServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DisplayName("WishlistServiceTest")
@SpringBootTest
@AutoConfigureMockMvc
public class WishlistServiceTest implements Mapper {

    @Mock
    private WishlistRepository repository;

    @InjectMocks
    private WishlistServiceImpl wishlistService;

    private Wishlist wishlist;
    private WishlistDTO wishlistDTO;
    private WishlistResponseDTO wishlistResponse;

    private Optional<Wishlist> wishlistOptional;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        startWishlist();
    }

    @Test
    @DisplayName("deve adicionar um produto a um novo cliente")
    public void deveAdicionarUmProdutoAoClienteInformado() {
        Mockito.when(repository.findByCustomerId(wishlistDTO.getCustomerId())).thenReturn(wishlistOptional);
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(wishlist);

        // action
        wishlistService.create(wishlistDTO);

        // assertions
        Mockito.verify(repository).findByCustomerId(wishlistDTO.getCustomerId());
        Mockito.verify(repository).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("deve adicionar um produto a um cliente ja cadastrado")
    public void deveAdicionarUmProdutoAoClienteJaCadastrado() {
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(wishlist);
        Mockito.when(repository.findByCustomerId(wishlistDTO.getCustomerId())).thenReturn(wishlistOptional);
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(wishlist);

        // action
        wishlistService.create(wishlistDTO);

        // assertions
        Mockito.verify(repository).findByCustomerId(wishlistDTO.getCustomerId());
        Mockito.verify(repository).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("deve nao cadastrar se cliente ja tiver 20 produtos")
    public void deveDeveCadastrarSeClienteJaTiver20Produtos() {
         for (int i = 0; i < 19; i++) {
            wishlist.getProducts().add("product name"+i);
        }
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(wishlist);
        Mockito.when(repository.findByCustomerId(wishlistDTO.getCustomerId())).thenReturn(wishlistOptional);

        assertThrows(ApiException.class, () -> wishlistService.create(wishlistDTO));

        Mockito.verify(repository, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("deve nao concluir cadastro se nao informar productId")
    public void deveNaoSalvarSeNaoForInformadoProductId() {
        wishlistDTO.setProductId(null);
        assertThrows(ApiException.class, () -> wishlistService.create(wishlistDTO));
        Mockito.verify(repository, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("deve nao concluir cadastro se nao informar customarId")
    public void deveNaoSalvarSeNaoForInformadoCustomerId() {
        wishlistDTO.setCustomerId(null);
        assertThrows(ApiException.class, () -> wishlistService.create(wishlistDTO));
        Mockito.verify(repository, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("deve remover um produto do cliente informado")
    public void deveRemoverUmProdutoDoClienteInformado() {
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(wishlist);
        Mockito.when(repository.findByCustomerId(wishlistDTO.getCustomerId())).thenReturn(wishlistOptional);

        // action
        wishlistService.removeProductFromCustomer(wishlistDTO);

        // assertions
        Mockito.verify(repository).findByCustomerId(wishlistDTO.getCustomerId());
        Mockito.verify(repository).save(ArgumentMatchers.any());

    }

    @Test
    @DisplayName("deve retornar todos produtos do cliente")
    public void deveRetornarTodosProdutosDoCliente() {
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(wishlist);
        Mockito.when(repository.findByCustomerId(wishlistDTO.getCustomerId())).thenReturn(wishlistOptional);

        // action
        wishlistService.findAll(wishlistDTO.getCustomerId());

        // assertions
        Mockito.verify(repository).findByCustomerId(wishlistDTO.getCustomerId());
        Mockito.verify(repository, Mockito.never()).save(ArgumentMatchers.any());

    }

    @Test
    @DisplayName("deve verificar se existe produto informado para o cliente")
    public void deveVerificarSeExisteProdutoNoCliente() {
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(wishlist);
        Mockito.when(repository.findByCustomerId(wishlistDTO.getCustomerId())).thenReturn(wishlistOptional);

        // action
        wishlistService.existsInCustomer(wishlistDTO);

        // assertions
        Mockito.verify(repository).findByCustomerId(wishlistDTO.getCustomerId());
        Mockito.verify(repository, Mockito.never()).save(ArgumentMatchers.any());

    }

    private void startWishlist() {
        wishlistDTO = new WishlistDTO();
        wishlistDTO.setCustomerId("customerId");
        wishlistDTO.setProductId("productId");

        List<String> products = new ArrayList<>();
        products.add(wishlistDTO.getProductId());

        wishlistResponse = new WishlistResponseDTO();
        wishlistResponse.setCustomerId(wishlistDTO.getCustomerId());
        wishlistResponse.setProducts(products);
        wishlist = map(wishlistResponse, Wishlist.class);
        wishlistOptional = Optional.of(wishlist);
    }
}
