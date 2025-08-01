package com.spring.database.querydsl.dto;

import com.querydsl.core.Tuple;
import com.spring.database.querydsl.entity.QIdol;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

// 그룹명과 평균 나이를 매핑할 클래스
public class GroupAverageAge {

    private String groupName;
    private double averageAge;

    // Tuple을 전달 받아서 DTO로 변환하는 생성자
    public GroupAverageAge(Tuple tuple) {
        this.groupName = tuple.get(QIdol.idol.group.groupName);
        this.averageAge = tuple.get(QIdol.idol.age.avg());
    }

    // 정적 팩토리 매서드 패턴
    // Tuple을 전달 받아서 DTO로 변환하는 메서드
    public static GroupAverageAge from(Tuple tuple) {
        //return new GroupAverageAge(tuple); // 생성자가 있다면 생성자를 리턴해주면 된다.
        // 생성자가 없었다면
        return GroupAverageAge.builder()
                .groupName(tuple.get(QIdol.idol.group.groupName))
                .averageAge(tuple.get(QIdol.idol.age.avg()))
                .build();
    }
}
