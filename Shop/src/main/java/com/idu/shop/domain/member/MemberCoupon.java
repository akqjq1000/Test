package com.idu.shop.domain.member;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class MemberCoupon {
    private int id;
    private int couponNo;
    private int memberNo;
}
