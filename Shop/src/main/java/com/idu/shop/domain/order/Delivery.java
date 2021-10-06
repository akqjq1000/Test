package com.idu.shop.domain.order;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class Delivery {
    private int id;
    private int memberNo;
    private int memberShippingAddressNo;
    private int courierNo;
    private String code;
    private String orderSequence;
    private int status;
}
