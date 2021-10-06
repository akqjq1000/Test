package com.idu.shop.domain.order;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class Courier {
    private int id;
    private String name;
    private String phone;
    private String courier;
}
