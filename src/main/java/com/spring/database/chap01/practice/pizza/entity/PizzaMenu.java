package com.spring.database.chap01.practice.pizza.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity // 필수: 이 클래스가 JPA 엔티티임을 선언
@Table(name = "pizza_menu") // (선택) 클래스명과 테이블명이 다르면 꼭 명시

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PizzaMenu {

    @Id // 필수: 기본키(PK) 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // (선택) DB에서 auto_increment 사용할 경우 필요
    private int id;

    // 필드명(name)과 컬럼명(name)이 동일하므로 @Column 생략 가능
    private String name;

    // 필드명(description) == 컬럼명(description) → 생략 가능
    private String description;

    // 필드명(price) == 컬럼명(price) → 생략 가능
    private double price;

    // 필드명(isAvailable) != 컬럼명(is_available) → 반드시 @Column으로 매핑 지정
    @Column(name = "is_available")
    private boolean isAvailable;

    // created_at은 DB가 자동으로 채우는 값 → insert/update 하지 않도록 설정 필요
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    // updated_at도 DB에서 자동 갱신됨 → insert/update 비활성화
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;
}
