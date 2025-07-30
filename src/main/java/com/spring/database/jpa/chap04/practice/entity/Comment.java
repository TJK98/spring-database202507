package com.spring.database.jpa.chap04.practice.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_comment")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    private String author;

    // TODO: Post와의 N:1 단방향 관계 설정
    // - 연관관계의 주인입니다. (외래키를 가짐)
    // - 지연 로딩(LAZY)을 설정하는 것이 성능에 유리합니다.
    // - @JoinColumn을 사용하여 외래키 컬럼명을 'post_id'로 지정하세요.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
