package dev.byto.mpos.controller;

import dev.byto.mpos.dto.AddItemRequest;
import dev.byto.mpos.dto.CartDto;
import dev.byto.mpos.dto.PlaceOrderRequest;
import dev.byto.mpos.entity.Order;
import dev.byto.mpos.service.CartService;
import dev.byto.mpos.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderCartController {

    private final CartService cartService;
    private final OrderService orderService;

    /**
     * API for adding a new product to order cart
     */
    @PostMapping("/cart/items")
    public ResponseEntity<Void> addItemToCart(@RequestBody AddItemRequest addItemRequest, Principal principal) {
        cartService.addItemToCart(principal.getName(), addItemRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * API for showing the order cart
     */
    @GetMapping("/cart")
    public ResponseEntity<CartDto> getCart(Principal principal) {
        return ResponseEntity.ok(cartService.getCart(principal.getName()));
    }

    /**
     * API for saving (placing) an order from the cart
     */
    @PostMapping("/orders")
    public ResponseEntity<Order> placeOrder(@RequestBody PlaceOrderRequest request, Principal principal) {
        Order order = orderService.placeOrder(
                principal.getName(),
                request.getCustomerName(),
                request.getAddress()
        );
        return ResponseEntity.ok(order);
    }
}
