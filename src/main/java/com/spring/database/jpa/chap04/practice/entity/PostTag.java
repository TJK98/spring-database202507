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
@Table(name = "tbl_post_tag")
public class PostTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: Post와의 N:1 관계 설정 (연관관계의 주인)
    // - 지연 로딩(LAZY)을 설정하고, @JoinColumn으로 'post_id'를 지정하세요.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    // TODO: Tag와의 N:1 관계 설정 (연관관계의 주인)
    // - 지연 로딩(LAZY)을 설정하고, @JoinColumn으로 'tag_id'를 지정하세요.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
