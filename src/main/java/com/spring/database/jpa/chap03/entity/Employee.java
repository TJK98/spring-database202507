package com.spring.database.jpa.chap03.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
// 연관 관계 필드는 순환 참조 방지를 위해 제외해야 한다.
@ToString
        (exclude = {"department"}) // 출력에서 department를 지워 버린다.
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

// 사원 N
@Entity
@Table(name = "tbl_emp")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id; // 사원번호

    @Column(name = "emp_name", nullable = false)
    private String name; // 사원명

    // DBMS처럼 한 쪽 (N쪽)에 상대의 데이터를 포함시키는 전략
    // -> 단 반향 매핑
    // ManyToOne은 무조건 LAZY를 거는 게 좋다.
    @ManyToOne(fetch = FetchType.LAZY) // 사원 입장에서는 사원이 많고 부서가 하나이기 때문, 필요 없을 때 조인을 하지 않는 전략
    @JoinColumn(name = "dept_id") // FK를 포함시키는 건 DB 패러다임에 맞춰야 함, 상대방의 PK 이름과 똑같이 하면 NATURAL JOIN이 가능하다.
    private Department department; // 부서 정보를 통째로 포함

    // 부서 변경 편의 메서드
    public void changeDepartment(Department department) {
        // ManyToOne 필드가 변경이 일어나면 반대쪽 OneToMany도 같이 갱신
        this.department = department;
        if (department != null) {
            department.getEmployees().add(this);
        }
    }

//    @Override
//    public String toString() {
//        return "Employee{" +
//               "id=" + id +
//               ", name='" + name + '\'' +
//               ", department=" + department.toString() +
//               '}';
//    }
}
