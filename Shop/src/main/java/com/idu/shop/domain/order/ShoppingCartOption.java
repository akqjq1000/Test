package com.idu.shop.domain.order;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ShoppingCartOption {
    private int shoppingCartNo;
    private int optionNo;
    private int optionValueNo;
}
