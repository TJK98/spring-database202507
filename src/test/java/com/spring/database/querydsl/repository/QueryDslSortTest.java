package com.spring.database.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.database.querydsl.entity.Group;
import com.spring.database.querydsl.entity.Idol;
import com.spring.database.querydsl.entity.QIdol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.spring.database.querydsl.entity.QIdol.*;

@SpringBootTest
@Transactional
public class QueryDslSortTest {

    @Autowired
    IdolRepository idolRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    JPAQueryFactory factory;


    @BeforeEach
    void setUp() {

        //given
        Group leSserafim = new Group("르세라핌");
        Group ive = new Group("아이브");

        groupRepository.save(leSserafim);
        groupRepository.save(ive);

        Idol idol1 = new Idol("김채원", 24, leSserafim);
        Idol idol2 = new Idol("사쿠라", 26, leSserafim);
        Idol idol3 = new Idol("가을", 22, ive);
        Idol idol4 = new Idol("리즈", 20, ive);
        Idol idol5 = new Idol("장원영", 20, ive);

        idolRepository.save(idol1);
        idolRepository.save(idol2);
        idolRepository.save(idol3);
        idolRepository.save(idol4);
        idolRepository.save(idol5);

    }

    @Test
    @DisplayName("기본 정렬 조회")
    void sortingTest() {
        //given

        //when

        // 나이와 이름 2차 내림차 정렬
        List<Idol> idolList = factory
                .selectFrom(idol)
                //.stream()
                //.sorted(Comparator.comparing(Idol::getAge).reversed())
                //.collect(Collectors.toList())
                .orderBy(idol.age.desc(), idol.idolName.desc())
                .fetch();
        //then
        System.out.println("\n\n==========results===========");
        idolList.forEach(System.out::println);
    }

    @Test
    @DisplayName("페이징 처리하기")
    void pagingTest() {
        //given
        int limit = 2; // 한 페이지 당 몇 개 씩 보여줄건지

        // 보통 클라이언트들은 offset을 주지 않고 pageNo을 준다.
        int pageNo = 1;

        //int offset = 0; // 어디서부터 보여줄건지 (첫번째 데이터는 0번)
        int offset = (pageNo - 1) * limit;

        //when
        List<Idol> idolList = factory
                .selectFrom(idol)
                .orderBy(idol.age.desc())
                .offset(offset)
                .limit(limit)
                .fetch();

        //then
        System.out.println("\n\n==========results===========");
        idolList.forEach(System.out::println);
    }
}
