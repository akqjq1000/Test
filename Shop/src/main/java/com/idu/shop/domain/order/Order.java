package com.idu.shop.domain.order;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class Order {
    private int id;
    private int orderCnt;
    private String orderSequence;
    private int memberNo;
    private int productNo;
    private int count;
    private String orderDate;
}
