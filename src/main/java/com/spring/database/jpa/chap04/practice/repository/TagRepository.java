package com.spring.database.jpa.chap04.practice.repository;

import com.spring.database.jpa.chap04.practice.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
