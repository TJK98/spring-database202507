package com.spring.database.chap02.entity;

/*
    CREATE TABLE products (
    id BIGINT AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL COMMENT '상품명',
    price INT NOT NULL COMMENT '가격',
    stock_quantity INT NOT NULL COMMENT '재고수량',
    description TEXT COMMENT '상품설명',
    seller VARCHAR(50) NOT NULL COMMENT '판매자',
    status VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '상품상태',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='상품';
 */

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Product {

    // CREATE TABLE products (
    private Long id; //id BIGINT AUTO_INCREMENT,
    private String name; // name VARCHAR(100) NOT NULL COMMENT '상품명',
    private int price; // price INT NOT NULL COMMENT '가격',
    private int stockQuantity; // stock_quantity INT NOT NULL COMMENT '재고수량',
    private String description; // description TEXT COMMENT '상품설명',
    private String seller; // seller VARCHAR(50) NOT NULL COMMENT '판매자',

    // ACTIVE : 삭제되지 않은 상품, DELETED : 제거된 상품 (논리적 삭제)
    private String status; // status VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '상품상태',
    private LocalDateTime createdAt; // created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    /*PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='상품';*/

    public Product(String name, int price, int stockQuantity, String description, String seller) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.description = description;
        this.seller = seller;
        this.status = "ACTIVE";
    }
}
