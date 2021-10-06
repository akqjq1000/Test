package com.idu.shop.domain;

import com.idu.shop.domain.category.bCategory;
import com.idu.shop.domain.category.mCategory;
import com.idu.shop.domain.category.sCategory;
import com.idu.shop.domain.member.Heart;
import com.idu.shop.domain.member.Member;
import com.idu.shop.domain.member.MemberCoupon;
import com.idu.shop.domain.member.MemberShippingAddress;
import com.idu.shop.domain.option.Option;
import com.idu.shop.domain.option.OptionValue;
import com.idu.shop.domain.order.*;
import com.idu.shop.domain.product.Product;
import com.idu.shop.domain.product.ProductImage;
import com.idu.shop.domain.product.ProductOption;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class DataContainer {
    Member member;
    MemberShippingAddress memberShippingAddress;
    MemberCoupon memberCoupon;
    Heart heart;
    Option option;
    OptionValue optionValue;
    bCategory bCategory;
    mCategory mCategory;
    sCategory sCategory;
    Coupon coupon;
    Delivery delivery;
    Order order;
    OrderOption orderOption;
    Payment payment;
    Review review;
    ShoppingCart shoppingCart;
    Product product;
    ProductOption productOption;
    ProductImage productImage;
    Courier courier;

    List<Member> memberList;
    List<MemberShippingAddress> memberShippingAddressList;
    List<MemberCoupon> memberCouponList;
    List<Heart> heartList;
    List<Option> optionList;
    List<OptionValue> optionValueList;
    List<bCategory> bCategoryList;
    List<mCategory> mCategoryList;
    List<sCategory> sCategoryList;
    List<Coupon> couponList;
    List<Delivery> deliveryList;
    List<Order> orderList;
    List<OrderOption> orderOptionList;
    List<Payment> paymentList;
    List<Review> reviewList;
    List<ShoppingCart> shoppingCartList;
    List<Product> productList;
    List<ProductOption> productOptionList;
    List<ProductImage> productImageList;
    List<Courier> courierList;
}
