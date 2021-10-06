package com.idu.shop.domain.product;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class ProductImage {

    private int id;
    private String origFilename;
    private String filename;
    private String filePath;
    private int productNo;

}
