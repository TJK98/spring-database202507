package com.spring.database.jpa.chap04.practice.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"comments", "postTags"})
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String content;

    // TODO: Comment와의 1:N 양방향 관계 설정
    // - 연관관계의 주인이 아니므로 'mappedBy'를 사용해야 합니다. (mappedBy = "post")
    // - Post가 삭제되면 Comment도 함께 삭제되어야 합니다. (cascade)
    // - Post의 댓글 목록에서 Comment를 제거하면 DB에서도 삭제되어야 합니다. (orphanRemoval)
    // - @Builder.Default 와 new ArrayList<>()로 초기화하는 것이 안전합니다.
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    // TODO: PostTag와의 1:N 관계 설정 (다대다 해결)
    // - Post가 삭제되면 PostTag도 함께 삭제되어야 합니다. (cascade, orphanRemoval)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PostTag> postTags = new ArrayList<>();

    // == 연관관계 편의 메서드 == //
    // 양방향 편의 메서드는 반드시 연관관계의 "소유자가 아닌 쪽"에 둡니다.
    // TODO: Post에 Comment를 추가하는 편의 메서드를 작성하세요.
    //  - 파라미터로 받은 Comment 객체의 Post를 'this'로 설정해야 합니다.
    //  - Post의 comments 리스트에 파라미터로 받은 Comment를 추가해야 합니다.
    public void addComment(Comment comment) {
        comment.setPost(this);             // 연관관계의 주인(Comment)의 post 설정
        this.comments.add(comment);        // 비주인(Post)의 comments 리스트에 추가
    }
}
