package com.spring.database.chap02;

import com.spring.database.chap02.dto.PriceInfo;
import com.spring.database.chap02.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductMapperTest {

    @Autowired
    ProductMapper productMapper;

    @Test
    @DisplayName("새 상품을 저장할 수 있어야 한다.")
    void saveTest() {
        // given
        Product product = Product.builder()
                .name("에어팟 맥스")
                .price(749000)
                .stockQuantity(50)
                .description("애플의 프리미엄 헤드폰")
                .seller("Apple")
                .build();

        // when
        productMapper.save(product);

        // then
        // 단순 실행 확인용 (예외 발생 안 하면 성공)
    }

    @Test
    @DisplayName("MyBatis로 상품 정보를 수정할 수 있어야 한다.")
    void updateTest() {
        Product product = Product.builder()
                .id(3L)
                .name("수정된 상품명")
                .price(12345)
                .stockQuantity(7)
                .description("수정된 설명입니다.")
                .seller("수정판매자")
                .build();

        // when
        productMapper.update(product);

        // then - 성공적으로 수행되면 예외 없이 통과
        // 혹은 별도 조회 없이 실행 자체가 예외 없이 완료되면 성공으로 간주
    }

    @Test
    @DisplayName("MyBatis로 상품을 논리적으로 삭제할 수 있어야 한다.")
    void deleteTest() {
        // given
        Long deleteId = 3L; // 실제 존재하는 ID 사용

        // when
        productMapper.deleteById(deleteId);

        // then - 삭제된 상품이 다시 조회되지 않는지 확인하거나, 실행 성공 여부만 검증
    }

    @Test
    @DisplayName("삭제되지 않은 전체 상품 목록을 조회해야 한다.")
    void findAllTest() {
        // when
        List<Product> productList = productMapper.findAll();

        // then
        productList.forEach(System.out::println);
        assertEquals(2, productList.size());
    }

    @Test
    @DisplayName("전체 상품의 총액과 평균 가격을 조회할 수 있어야 한다.")
    void getPriceInfoTest() {
        // given
        // (사전 등록된 데이터가 있다고 가정)

        // when
        PriceInfo priceInfo = productMapper.getPriceInfo();

        // then
        assertNotNull(priceInfo);
        System.out.println("총액: " + priceInfo.getTotalPrice());
        System.out.println("평균: " + priceInfo.getAveragePrice());
    }

}