package com.spring.database.chap01.practice.pizza.routes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PizzaRouteController {

    @GetMapping("/pizza-menu")
    public String pizzaPage() {
        // templates 폴더 기준 경로 (확장자 생략)
        return "pizza-page";
    }
}
