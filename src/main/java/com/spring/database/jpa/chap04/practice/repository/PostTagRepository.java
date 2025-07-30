package com.spring.database.jpa.chap04.practice.repository;

import com.spring.database.jpa.chap04.practice.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {
}
