package dev.byto.mpos.dto;

import lombok.Data;

@Data
public class PlaceOrderRequest {
    private String customerName;
    private String address;
}
