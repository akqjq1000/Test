package com.idu.shop.domain.product;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class ProductOption {
    private int id;
    private int optionNo;
    private int productNo;
}
