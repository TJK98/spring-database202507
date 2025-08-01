package com.spring.database.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.database.querydsl.entity.Group;
import com.spring.database.querydsl.entity.Idol;
import com.spring.database.querydsl.entity.QIdol;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.spring.database.querydsl.entity.QIdol.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QueryDslBasicTest {

    @Autowired
    IdolRepository idolRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    EntityManager em; // 순수 JPA의 핵심 객체

    @Autowired
    JdbcTemplate jdbcTemplate; // JDBC의 핵심 객체

    @Autowired
    JPAQueryFactory factory; // QueryDsl 핵심 객체, 직접 config 파일을 만들어서 스프링이 관리하게 한다.

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

        idolRepository.save(idol1);
        idolRepository.save(idol2);
        idolRepository.save(idol3);
        idolRepository.save(idol4);

        em.flush();
        em.clear();

    }

    @Test
    @DisplayName("순수 JPQL로 특정 이름의 아이돌 조회하기")
    void rawJpqlTest() {
        //given
        String jpql = """
                SELECT i
                FROM Idol i
                WHERE i.idolName = ?1
                """;

        //when
        Idol foundIdol = em.createQuery(jpql, Idol.class)
                .setParameter(1, "가을")
                .getSingleResult();

        //then
        System.out.println("\n\nfoundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup = " + foundIdol.getGroup());
    }

    @Test
    @DisplayName("순수 SQL로 특정 이름의 아이돌 조회하기")
    void nativeSqlTest() {
        //given
        String sql = """
                SELECT *
                FROM tbl_idol
                WHERE idol_name = ?1
                """;
        //when
        Idol foundIdol = (Idol) em.createNativeQuery(sql, Idol.class)
                .setParameter(1, "리즈")
                .getSingleResult();
        //then
                System.out.println("\n\nfoundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup = " + foundIdol.getGroup());
    }

    @Test
    @DisplayName("JDBC로 특정 이름 아이돌 조회하기")
    void jdbcTest() {
        //given
        String sql = """
                SELECT *
                FROM tbl_idol
                WHERE idol_name = ?
                """;
        //when
        Idol foundIdol = jdbcTemplate.queryForObject(sql,
                (rs, n) -> new Idol(
//                        rs.getLong("idol_id")
//                        , rs.getString("idol_name")
//                        , rs.getInt("age")
//                        , null
                ),
                "김채원"
        );

        Group group = jdbcTemplate.queryForObject("""
                        SELECT * FROM tbl_group WHERE group_id = ?
                        """,
                (rs, n) -> new Group(
//                        rs.getLong("group_id")
//                        , rs.getString("group_name")
//                        , null
                ),
                1
        );

        foundIdol.setGroup(group);

        //then
        System.out.println("\n\nfoundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());
    }

    @Test
    @DisplayName("QueryDsl로 특정 이름의 아이돌 조회하기")
    void queryDslTest() {
        //given
        //JPAQueryFactory factory = new JPAQueryFactory(em);
        //when
        Idol foundIdol = factory
                .selectFrom(idol)
                .where(idol.idolName.eq("사쿠라"))
                .fetchOne();
        //then
        System.out.println("\n\nfoundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());
    }

    @Test
    @DisplayName("이름 AND 나이로 아이돌 조회하가")
    void searchTest() {
        //given
        String name = "리즈";
        int age = 20;
        //when
        Idol foundIdol = factory
                .selectFrom(idol)
                .where(
                        idol.idolName.eq(name)
                                .and(idol.age.eq(age))
                )
                .fetchOne();

        //then
        System.out.println("\n\nfoundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());

        //        idol.idolName.eq("리즈") // idolName = '리즈'
        //        idol.idolName.ne("리즈") // idolName != '리즈'
        //        idol.idolName.eq("리즈").not() // idolName != '리즈'
        //        idol.idolName.isNotNull() //이름이 is not null
        //        idol.age.in(10, 20) // age in (10,20)
        //        idol.age.notIn(10, 20) // age not in (10, 20)
        //        idol.age.between(10,30) //between 10, 30
        //        idol.age.goe(30) // age >= 30
        //        idol.age.gt(30) // age > 30
        //        idol.age.loe(30) // age <= 30
        //        idol.age.lt(30) // age < 30
        //        idol.idolName.like("_김%")  // like _김%
        //        idol.idolName.contains("김") // like %김%
        //        idol.idolName.startsWith("김") // like 김%
        //        idol.idolName.endsWith("김") // like %김
    }

    @Test
    @DisplayName("다양한 조회 결과 반환하기")
    void fetchTest() {

        // fetch() : 다중 행 조회
        List<Idol> idolList = factory
                .selectFrom(idol)
                .fetch();

        System.out.println("\n\n==========fetch===========");
        idolList.forEach(System.out::println);

        // fetchFirst() : 다중행 조회에서 첫번째 행을 반환
        Idol fetchFirst = factory
                .selectFrom(idol)
                .where(idol.age.loe(22))
                .fetchFirst();

        System.out.println("\n\n==========fetch first===========");
        System.out.println("fetchFirst = " + fetchFirst);

        // 단일행 조회시 NPE에 취약하기 때문에 Optional을 사용하고 싶을 때는
        Optional <Idol> fetchOne = Optional.ofNullable(
                factory
                        .selectFrom(idol)
                        .where(idol.idolName.eq("김채원"))
                        .fetchOne()
        );

        Idol foundIdol = fetchOne.orElse(new Idol());
    }

/*    @Test
    @DisplayName("나이를 통해 아이돌 조회하기")
    void ageTest() {
        //given
        int age = 24;
        //when
        Optional<List<Idol>> idolList = Optional.ofNullable(
                factory
                        .selectFrom(idol)
                        .where(
                                idol.age.goe(age)
                        )
                        .fetch()
        );
        //then
        List<Idol> idols = idolList.orElseThrow();
        idols.forEach(System.out::println);
    }

    @Test
    @DisplayName("성을 통해 아이돌 조회하기")
    void firstNameTest() {
        //given
        String firstName = "김";
        //when
        Optional<List<Idol>> idolList = Optional.ofNullable(
                factory
                        .selectFrom(idol)
                        .where(
                                idol.idolName.startsWith(firstName)
                        )
                        .fetch()
        );
        //then
        List<Idol> idols = idolList.orElseThrow();
        idols.forEach(System.out::println);
    }*/

    @Test
    @DisplayName("나이가 24세 이상인 아이돌 조회")
    void testAgeGoe() {
        // given
        int ageThreshold = 24;

        // when
        List<Idol> result = factory
                .selectFrom(idol)
                .where(idol.age.goe(ageThreshold))
                .fetch();

        // then
        assertFalse(result.isEmpty());

        for (Idol idol : result) {
            System.out.println("\n\nIdol: " + idol);
            assertTrue(idol.getAge() >= ageThreshold);
        }
    }

    @Test
    @DisplayName("이름에 '김'이 포함된 아이돌 조회")
    void testNameContains() {
        // given
        String substring = "김";

        // when
        List<Idol> result = factory
                .selectFrom(idol)
                .where(idol.idolName.contains(substring))
                .fetch();

        // then
        assertFalse(result.isEmpty());
        for (Idol idol : result) {
            System.out.println("Idol: " + idol);
            assertTrue(idol.getIdolName().contains(substring));
        }
    }

    @Test
    @DisplayName("나이가 20세에서 25세 사이인 아이돌 조회")
    void testAgeBetween() {
        // given
        int ageStart = 20;
        int ageEnd = 25;


        // when
        List<Idol> result = factory
                .selectFrom(idol)
                .where(idol.age.between(ageStart, ageEnd))
                .fetch();

        // then
        assertFalse(result.isEmpty());
        for (Idol idol : result) {
            System.out.println("Idol: " + idol);
            assertTrue(idol.getAge() >= ageStart && idol.getAge() <= ageEnd);
        }
    }


    @Test
    @DisplayName("르세라핌 그룹에 속한 아이돌 조회")
    void testGroupEquals() {
        /*
            String sql = """
                SELECT
                    I.*, G.group_name
                FROM tbl_idol I
                INNER JOIN tbl_group G
                ON I.group_id = G.group_id
                WHERE G.group_name = '르세라핌'
                """;
         */
        // given
        String groupName = "르세라핌";

        // when
        List<Idol> result = factory
                .selectFrom(idol)
                .where(idol.group.groupName.eq(groupName))
                .fetch();

        // then
        assertFalse(result.isEmpty());
        for (Idol idol : result) {
            System.out.println("Idol: " + idol);
            assertEquals(groupName, idol.getGroup().getGroupName());
        }
    }
}