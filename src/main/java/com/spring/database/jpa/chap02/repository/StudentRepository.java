package com.spring.database.jpa.chap02.repository;

import com.spring.database.jpa.chap02.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// JpaRepository의 제네릭에는 첫번째는 엔터티, 두번째는 아이디 타입
public interface StudentRepository extends JpaRepository<Student, String> {

    // Query Method: 메서드에 특별한 이름 규칙을 사용해서 SQL을 생성, 필드명을 적어야 한다.
    List<Student> findByName(String name);

    // WHERE city = ? AND major = ?
    List<Student> findByCityAndMajor(String city, String major);

    // WHRE major LIKE '%?%'
    List<Student> findByMajorContaining(String major);

    // WHRE major LIKE '?%'
    List<Student> findByMajorStartingWith(String major);

    // WHRE major LIKE '%?'
    List<Student> findByMajorEndingWith(String major);

    // WHRE age <= ?
//    List<Student> findByAgeLessThanEqual(int age);

    // JPQL 사용하기
    // 도시 이름으로 학생 조회하기
    @Query(value = "SELECT s FROM Student s WHERE s.city = ?1") // tbl_student가 아닌 자바 클래스인 Student를 써야 한다. 파라미터가 하나이면 ?1을 써야한다. 두개이면 ?2
    List<Student> getStudentsByCity(String city);

    // 순수 SQL 사용하기
    // 도시 AND 이름으로 학생 조회하기
    @Query(value = """
                    SELECT *
                    FROM tbl_student
                    WHERE city = ?2
                        AND stu_name = ?1
                  """, nativeQuery = true) // 데이터베이스 테이블 이름을 써야 한다. 순서에 맞게 ?숫자를 붙여야한다. nativeQuery = true을 넣어야 순수 SQL로 인식한다.
    List<Student> getStudents(String city, String name);
}
