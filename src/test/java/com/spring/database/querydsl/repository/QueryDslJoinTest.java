package com.spring.database.querydsl.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.database.querydsl.entity.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.spring.database.querydsl.entity.QAlbum.*;
import static com.spring.database.querydsl.entity.QGroup.*;
import static com.spring.database.querydsl.entity.QIdol.*;

@SpringBootTest
@Transactional
@Rollback(false)
public class QueryDslJoinTest {

    @Autowired
    IdolRepository idolRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    JPAQueryFactory factory;

    @Autowired
    EntityManager em;


    @BeforeEach
    void setUp() {
        //given
        Group leSserafim = new Group("르세라핌");
        Group ive = new Group("아이브");
        Group bts = new Group("방탄소년단");
        Group newjeans = new Group("뉴진스");

        groupRepository.save(leSserafim);
        groupRepository.save(ive);
        groupRepository.save(bts);
        groupRepository.save(newjeans);

        Idol idol1 = new Idol("김채원", 24, "여", leSserafim);
        Idol idol2 = new Idol("사쿠라", 26, "여", leSserafim);
        Idol idol3 = new Idol("가을", 22, "여", ive);
        Idol idol4 = new Idol("리즈", 20, "여", ive);
        Idol idol5 = new Idol("장원영", 20, "여", ive);
        Idol idol6 = new Idol("안유진", 21, "여", ive);
        Idol idol7 = new Idol("카즈하", 21, "여", leSserafim);
        Idol idol8 = new Idol("RM", 29, "남", bts);
        Idol idol9 = new Idol("정국", 26, "남", bts);
        Idol idol10 = new Idol("해린", 18, "여", newjeans);
        Idol idol11 = new Idol("혜인", 16, "여", newjeans);
        Idol idol12 = new Idol("김종국", 48, "남", null);
        Idol idol13 = new Idol("아이유", 31, "여", null);


        idolRepository.save(idol1);
        idolRepository.save(idol2);
        idolRepository.save(idol3);
        idolRepository.save(idol4);
        idolRepository.save(idol5);
        idolRepository.save(idol6);
        idolRepository.save(idol7);
        idolRepository.save(idol8);
        idolRepository.save(idol9);
        idolRepository.save(idol10);
        idolRepository.save(idol11);
        idolRepository.save(idol12);
        idolRepository.save(idol13);


        Album album1 = new Album("MAP OF THE SOUL 7", 2020, bts);
        Album album2 = new Album("FEARLESS", 2022, leSserafim);
        Album album3 = new Album("UNFORGIVEN", 2023, bts);
        Album album4 = new Album("ELEVEN", 2021, ive);
        Album album5 = new Album("LOVE DIVE", 2022, ive);
        Album album6 = new Album("OMG", 2023, newjeans);

        albumRepository.save(album1);
        albumRepository.save(album2);
        albumRepository.save(album3);
        albumRepository.save(album4);
        albumRepository.save(album5);
        albumRepository.save(album6);

        em.flush();
        em.clear();

    }


    @Test
    @DisplayName("Inner Join 예제")
    void innerJoinTest() {
        //given

        //when
        // 명시적으로 아이돌과 그룹을 조인하고 싶다.
        List<Idol> idolList = factory
                .select(idol)
                .from(idol)
                // innerJoin의 첫번째 파라미터는 from절에 쓴 엔터티의 연관관계객체
                // 두번째 파라미터는 실제로 조인할 엔터티
                .innerJoin(idol.group, group).fetchJoin()
                .fetch();

        //then
        System.out.println("\n\n");
        for (Idol i : idolList) {
            System.out.println("i = " + i + i.getGroup());
        }
    }

    @Test
    @DisplayName("Left Outer Join 예제")
    void leftJoinTest() {
        //given

        //when
        // 명시적으로 아이돌과 그룹을 조인하고 싶다.
        List<Idol> idolList = factory
                .select(idol)
                .from(idol)
                .leftJoin(idol.group, group).fetchJoin()
                .fetch();

        //then
        System.out.println("\n\n");
        for (Idol i : idolList) {
            Group g = i.getGroup();
            System.out.println("i = " + i + ((g != null ? g : " 그룹 없음")));
        }
    }

    @Test
    @DisplayName("그룹별 평균 나이 계산")
    void averageAgeTest() {
        //given

        //when
        List<Tuple> tuples = factory
                .select(group.groupName, idol.age.avg())
                .from(idol)
                .leftJoin(idol.group, group)
                .groupBy(idol.group)
                .fetch();

        //then
        for (Tuple tuple : tuples) {
            String groupName = tuple.get(group.groupName);
            Double average = tuple.get(idol.age.avg());
            System.out.printf("%s 그룹의 평균나이: %.2f\n", groupName, average);
        }
    }

    @Test
    @DisplayName("""
            2022년에 발매된 앨범이 있는
            아이돌의 정보
            (아이돌 명, 그룹명, 앨범명, 발매년도) 조회
            """)
    void albumtTest() {
        /*
            SELECT
                I.idol_name
                , G.group_name
                , A.album_name
                , A.release_year
            FROM tbl_idol I
            JOIN tbl_group G
            ON I.group_id = G.group_id
            JOIN tbl_album A
            ON G.group_id = A.group_id
            WHERE A.release_year = 2022
         */
        //given
        int year = 2022;
        //when
        List<Tuple> idolList = factory
                .select(
                        idol,
                        album
                )
                // 첫 타겟 idol -> idol 엔터티가 존재 확인
                .from(idol)
                // idol은 확인하여 idol에 존재하는 group이 QGroup과 같은 것을 확인
                .innerJoin(idol.group, group)
                // group의 존재는 확인 했으나 albums는 모름
                // 그래서 albums의 존재를 확인시켜 줘야 함
                .innerJoin(group.albums, album)
                .where(album.releaseYear.eq(year))
                .fetch();
        //then
        // (아이돌명, 그룹명, 앨범명, 발매년도)
        for (Tuple tuple : idolList) {
            Idol foundIdol = tuple.get(idol);
            Album foundAlbum = tuple.get(album);

            System.out.printf("\n# 아이돌명: %s, 그룹명: %s, 앨범명: %s, 발매년도: %d년\n\n"
                    , foundIdol.getIdolName(), foundIdol.getGroup().getGroupName()
                    , foundAlbum.getAlbumName(), foundAlbum.getReleaseYear()
            );
        }
    }

}