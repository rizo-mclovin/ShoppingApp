package com.example.waikan.services;

import com.example.waikan.models.CartItem;
import com.example.waikan.models.ShoppingCart;
import com.example.waikan.repositories.CartItemRepository;
import com.example.waikan.repositories.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@SuppressWarnings("all")
public class ShoppingCartService {
    private final ProductService productService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;

    public ShoppingCart addShoppingCartFirstTime(Long id) {
        ShoppingCart shoppingCart = new ShoppingCart();
        CartItem cartItem = new CartItem();
        cartItem.setDate(new Date());
        cartItem.setProduct(productService.getProductById(id));
        shoppingCart.getItems().add(cartItem);
        shoppingCart.setDate(new Date());
        return shoppingCartRepository.save(shoppingCart);

    }

    public CartItem updateShoppingCartItem(Long id, int quantity) {
        CartItem cartItem = cartItemRepository.findById(id).get();
        cartItem.setQuantity(quantity);
        return cartItemRepository.saveAndFlush(cartItem);

    }


    public ShoppingCart getShoppingCartBySessionTokent(String sessionToken) {

        return  shoppingCartRepository.findBySessionToken(sessionToken);
    }
//    public Optional<ShoppingCart> removeCartIemFromShoppingCart(Long id) {
//        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findById(id);
//        Set<CartItem> items = shoppingCart.get().getItems();
//        CartItem cartItem = null;
//        for(CartItem item : items) {
//            if(item.getId().equals(id)) {
//                cartItem = item;
//            }
//        }
//        items.remove(cartItem);
//        cartItemRepository.delete(cartItem);
//        shoppingCart.get().setItems(items);
//        return shoppingCartRepository.save(shoppingCart);
//    }

//    public void clearShoppingCart(String sessionToken) {
//        ShoppingCart sh = shoppingCartRepository.findBySessionToken(sessionToken);
//        shoppingCartRepository.delete(sh);
//
//    }

}
