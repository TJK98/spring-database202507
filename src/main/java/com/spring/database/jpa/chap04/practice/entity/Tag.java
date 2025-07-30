package com.spring.database.jpa.chap04.practice.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_tag")
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String tagName;

    // TODO: PostTag와의 1:N 관계 설정 (다대다 해결)
    // - Tag가 삭제되면 PostTag도 함께 삭제되어야 합니다.
    private List<PostTag> postTags = new ArrayList<>();
}
