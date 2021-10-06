package com.idu.shop.domain.order;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class OrderOption {
    private int orderNo;
    private int optionNo;
    private int optionValueNo;
}
