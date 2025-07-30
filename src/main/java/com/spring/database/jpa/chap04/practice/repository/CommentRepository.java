package com.spring.database.jpa.chap04.practice.repository;

import com.spring.database.jpa.chap04.practice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
