package com.spring.database.chap02;

import com.spring.database.chap02.dto.PriceInfo;
import com.spring.database.chap02.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    void save(Product product);

    void update(Product product);

    void deleteById(Long id);

    List<Product> findAll();

    PriceInfo getPriceInfo();
}
