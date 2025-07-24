package com.spring.database.chap01.practice.pizza.repository;

import com.spring.database.chap01.practice.pizza.entity.PizzaMenu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PizzaMenuRepositoryTest {

    @Autowired
    PizzaMenuRepository pizzaMenuRepository;

    @Test
    @DisplayName("피자 메뉴 정보를 주면 pizza_menu 테이블에 저장된다.")
    void saveTest() {
        // given
        PizzaMenu pizza = PizzaMenu.builder()
                .name("고르곤졸라")
                .description("치즈가 진하게 올라간 피자")
                .price(18900.0)
                .build();

        // when
        boolean result = pizzaMenuRepository.save(pizza);

        // then
        System.out.println("등록 결과: " + result);
        assertTrue(result);
    }

    @Test
    @DisplayName("피자 메뉴의 이름과 가격을 수정해야 한다.")
    void updateTest() {
        // given
        PizzaMenu pizza = PizzaMenu.builder()
                .id(1) // 실제 존재하는 id로 설정해야 함
                .name("수정된 고르곤졸라")
                .price(20000.0)
                .build();

        // when
        boolean result = pizzaMenuRepository.updateNameAndPrice(pizza);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("id를 주면 해당 피자 메뉴가 삭제된다.")
    void deleteTest() {
        // given
        int id = 2; // 실제 존재하는 id로 설정해야 함

        // when
        boolean result = pizzaMenuRepository.deleteById(id);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("전체 피자 메뉴를 조회하면 리스트가 반환된다.")
    void findAllTest() {
        // given

        // when
        List<PizzaMenu> pizzaList = pizzaMenuRepository.findAll("id");

        // then
        pizzaList.forEach(System.out::println);
        assertNotNull(pizzaList);
        assertFalse(pizzaList.isEmpty());
    }

    @Test
    @DisplayName("id를 주면 해당 피자 메뉴 1개가 조회된다.")
    void findOneTest() {
        // given
        int id = 1; // 실제 존재하는 id로 설정해야 함

        // when
        PizzaMenu found = pizzaMenuRepository.findById(id);

        // then
        System.out.println("found = " + found);
        assertNotNull(found);
        assertEquals("고르곤졸라", found.getName()); // 예상 이름과 비교
    }
}
