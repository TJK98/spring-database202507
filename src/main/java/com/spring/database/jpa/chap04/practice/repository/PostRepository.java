package com.spring.database.jpa.chap04.practice.repository;

import com.spring.database.jpa.chap04.practice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // TODO: N+1 문제 해결을 위해 Fetch Join을 사용하는 JPQL을 작성하세요.
    //  힌트: "SELECT p FROM Post p JOIN FETCH p.comments"
    @Query("SELECT p FROM Post p JOIN FETCH p.comments")
    List<Post> findAllWithComments();
}
