package dev.byto.mpos.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private String name;
    private String type;
    private BigDecimal price;
}
