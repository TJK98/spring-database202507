package com.spring.database.chap01.practice.pizza.controller;

import com.spring.database.chap01.practice.pizza.entity.PizzaMenu;
import com.spring.database.chap01.practice.pizza.repository.PizzaMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pizza-menus")
public class PizzaMenuController {

    private final PizzaMenuRepository pizzaMenuRepository;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PizzaMenu pizza) {
        if (pizza.getName() == null || pizza.getPrice() == 0) {
            return ResponseEntity.badRequest().body("이름과 가격은 필수입니다.");
        }

        // 기본값 보정
        pizza.setAvailable(true);

        boolean flag = pizzaMenuRepository.save(pizza);
        return ResponseEntity.ok(flag ? "등록 성공!" : "등록 실패!");
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "id") String sort) {
        List<PizzaMenu> pizzas = pizzaMenuRepository.findAll(sort);
        return ResponseEntity.ok(pizzas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable int id) {
        PizzaMenu found = pizzaMenuRepository.findById(id);
        return ResponseEntity.ok(found);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        boolean flag = pizzaMenuRepository.deleteById(id);
        return ResponseEntity.ok(flag ? "삭제 성공!" : "삭제 실패!");
    }
}