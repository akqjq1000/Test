package com.idu.shop.domain.order;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Review {
    private int id;
    private int memberNo;
    private int productNo;
    private int ratio;
    private String comments;
    private String regdate;
}
