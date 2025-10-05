package dev.byto.mpos.service;

import dev.byto.mpos.dto.CartDto;
import dev.byto.mpos.entity.Order;
import dev.byto.mpos.entity.OrderItem;
import dev.byto.mpos.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    @Transactional
    public Order placeOrder(String cartId, String customerName, String address) {
        CartDto cart = cartService.getCart(cartId);

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot place an order with an empty cart.");
        }

        Order order = new Order();
        order.setCustomerName(customerName);
        order.setAddress(address);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(cart.getTotal());

        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
            OrderItem item = new OrderItem();
            item.setProductId(cartItem.getProductId());
            item.setProductName(cartItem.getProductName());
            item.setQuantity(cartItem.getQuantity());
            item.setPriceAtOrder(cartItem.getPrice());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());

        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(cartId);

        return savedOrder;
    }
}
