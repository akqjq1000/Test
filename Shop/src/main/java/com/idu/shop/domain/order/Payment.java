package com.idu.shop.domain.order;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode


public class Payment {
    private int id;
    private int memberNo;
    private String orderSequence;
    private int memberShippingAddressNo;
    private int couponNo;
    private int cardType;
    private int moneyType;
    private String cardNumber;
    private String moneyNumber;
    private int paymentWay;
    private int totalPrice;
    private String paymentDate;
}
