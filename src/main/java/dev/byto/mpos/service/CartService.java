package dev.byto.mpos.service;

import dev.byto.mpos.dto.AddItemRequest;
import dev.byto.mpos.dto.CartDto;
import dev.byto.mpos.dto.CartItemDto;
import dev.byto.mpos.entity.Product;
import dev.byto.mpos.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class CartService {

    private final Map<String, List<CartItemDto>> carts = new ConcurrentHashMap<>();
    private final ProductRepository productRepository;

    public void addItemToCart(String cartId, AddItemRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        List<CartItemDto> cartItems = carts.computeIfAbsent(cartId, k -> new ArrayList<>());

        cartItems.stream()
                .filter(item -> item.getProductId().equals(request.getProductId()))
                .findFirst()
                .ifPresentOrElse(
                        item -> item.setQuantity(item.getQuantity() + request.getQuantity()),
                        () -> {
                            CartItemDto newItem = new CartItemDto(
                                    product.getId(),
                                    product.getName(),
                                    request.getQuantity(),
                                    product.getPrice(),
                                    BigDecimal.ZERO
                            );
                            cartItems.add(newItem);
                        }
                );
    }

    public CartDto getCart(String cartId) {
        List<CartItemDto> cartItems = carts.getOrDefault(cartId, new ArrayList<>());

        BigDecimal total = BigDecimal.ZERO;
        for (CartItemDto item : cartItems) {
            BigDecimal subtotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            item.setSubtotal(subtotal);
            total = total.add(subtotal);
        }

        CartDto cartDto = new CartDto();
        cartDto.setItems(cartItems);
        cartDto.setTotal(total);
        return cartDto;
    }

    public void clearCart(String cartId) {
        carts.remove(cartId);
    }
}
