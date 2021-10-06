package com.idu.shop.domain.product;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class Product {
    private int id;
    private String name;
    private int price;
    private String code;
    private String copName;
    private String courier;
    private int ratio;
    private String description;
    private String contentsImage;
    private int bCateNo;
    private int mCateNo;
    private int sCateNo;
    private int isNew;
    private int isHit;
    private int isSale;
    private int discount;
    private int status;
    private int stock;
    private int shippingFee;
    private int soldCount;
    private String regdate;
    private String productImage;
}
