package com.spring.database.jpa.chap04.practice.repository;

import com.spring.database.jpa.chap04.practice.entity.Comment;
import com.spring.database.jpa.chap04.practice.entity.Post;
import com.spring.database.jpa.chap04.practice.entity.PostTag;
import com.spring.database.jpa.chap04.practice.entity.Tag;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    PostTagRepository postTagRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void setUp() {
        Post post = Post.builder()
                .title("내가 하는 게임")
                .content("롤이랑 피파함.")
                .build();

        postRepository.save(post);

        Comment c1 = Comment.builder().text("리그 오브 레전드").build();
        Comment c2 = Comment.builder().text("피파 온라인").build();

        post.addComment(c1);
        post.addComment(c2);

        commentRepository.saveAll(List.of(c1, c2));

        Tag javaTag = Tag.builder().tagName("LOL").build();
        Tag springTag = Tag.builder().tagName("FIFA").build();
        tagRepository.saveAll(List.of(javaTag, springTag));

        PostTag pt1 = PostTag.builder().post(post).tag(javaTag).build();
        PostTag pt2 = PostTag.builder().post(post).tag(springTag).build();
        postTagRepository.saveAll(List.of(pt1, pt2));

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("게시글과 댓글을 Fetch Join으로 한 번에 조회한다.")
    void fetchJoinPostWithComments() {
        // given

        // when
        List<Post> posts = postRepository.findAllWithComments();

        // then
        for (Post post : posts) {
            System.out.println("post.getTitle(): " + post.getTitle());
            for (Comment comment : post.getComments()) {
                System.out.println("comment.getText(): " + comment.getText());
            }
        }
    }

}