package com.idu.shop.domain.member;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Member {
    private int id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;
    private int point;
    private String regdate;
}
