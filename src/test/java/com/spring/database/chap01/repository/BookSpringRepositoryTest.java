package com.spring.database.chap01.repository;

import com.spring.database.chap01.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookSpringRepositoryTest {

    @Autowired
    BookSpringRepository bookSpringRepository;

    @Test
    @DisplayName("스프링 JDBC로 도서를 생성한다.")
    void saveTest() {
        //given
        Book newBook = Book.builder()
                .title("스프링 JDBC")
                .author("자바왕")
                .isbn("S001")
                .build();
        //when
        boolean flag = bookSpringRepository.save(newBook);
        //then
        assertTrue(flag);
    }


    @Test
    @DisplayName("스프링 JDBC로 책 제목과 저자를 수정한다.")
    void updateTest() {
        //given
        Book modifyBook = Book.builder()
                .title("수정된 스프링 책")
                .author("수정된 자바왕")
                .id(13L)
                .build();
        //when
        boolean b = bookSpringRepository.updateTitleAndAuthor(modifyBook);

        //then
        assertTrue(b);
    }


    @Test
    @DisplayName("스프링 JDBC로 도서 정보 삭제한다.")
    void deleteTest() {
        //given
        Long id = 4L;
        //when
        boolean b = bookSpringRepository.deleteById(id);
        //then
        assertTrue(b);
    }

    @Test
    @DisplayName("스프링 JDBC로 전체조회를 할 경우 도서 목록이 반환된다.")
    void findAllTest() {
        //given

        //when
        List<Book> bookList = bookSpringRepository.findAll();
        //then
        bookList.forEach(System.out::println);
        assertEquals(4, bookList.size());
    }
}