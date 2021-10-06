package com.idu.shop.domain.member;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class MemberShippingAddress {
    private int id;
    private int representative;
    private int memberNo;
    private String name;
    private String phone;
    private String address;
    private String comments;
}
