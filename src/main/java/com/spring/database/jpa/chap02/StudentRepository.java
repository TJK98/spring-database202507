package com.spring.database.jpa.chap02;

import com.spring.database.jpa.chap02.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository의 제네릭에는 첫번째는 엔터이, 두번째는 아이디 타입
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
}
