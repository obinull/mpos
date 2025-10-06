package dev.byto.mpos.controller;

import dev.byto.mpos.dto.AddItemRequest;
import dev.byto.mpos.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<Object>> addItemToCart(@RequestBody AddItemRequest addItemRequest, Principal principal) {
        cartService.addItemToCart(principal.getName(), addItemRequest);
        ApiResponse<Object> response = new ApiResponse<>("SUCCESS", "Item added to cart successfully", null);
        return ResponseEntity.ok(response);
    }

    /**
     * API for showing the order cart
     */
    @GetMapping("/cart")
    public ResponseEntity<ApiResponse<CartDto>> getCart(Principal principal) {
        CartDto cart = cartService.getCart(principal.getName());
        ApiResponse<CartDto> response = new ApiResponse<>("SUCCESS", "Cart retrieved successfully", cart);
        return ResponseEntity.ok(response);
    }

    /**
     * API for saving (placing) an order from the cart
     */
    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<Order>> placeOrder(@RequestBody PlaceOrderRequest request, Principal principal) {
        Order order = orderService.placeOrder(
                principal.getName(),
                request.getCustomerName(),
                request.getAddress()
        );
        ApiResponse<Order> response = new ApiResponse<>("SUCCESS", "Order placed successfully", order);
        return ResponseEntity.ok(response);
    }
}
