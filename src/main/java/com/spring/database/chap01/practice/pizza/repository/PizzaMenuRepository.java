package com.spring.database.chap01.practice.pizza.repository;

import com.spring.database.chap01.practice.pizza.entity.PizzaMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// 역할: 피자 메뉴에 대한 CRUD를 처리하는 JDBC Repository
@RequiredArgsConstructor
@Repository
public class PizzaMenuRepository {

    private final DataSource dataSource;

    // CREATE - 피자 메뉴 등록
    public boolean save(PizzaMenu pizza) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = """
                        INSERT INTO pizza_menu
                            (name, description, price)
                        VALUES
                            (?, ?, ?)
                        """;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pizza.getName());
            pstmt.setString(2, pizza.getDescription());
            pstmt.setDouble(3, pizza.getPrice());

            return pstmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE - 이름과 가격 수정 (예시)
    public boolean updateNameAndPrice(PizzaMenu pizza) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = """
                        UPDATE pizza_menu
                        SET name = ?, price = ?
                        WHERE id = ?
                        """;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pizza.getName());
            pstmt.setDouble(2, pizza.getPrice());
            pstmt.setInt(3, pizza.getId());

            return pstmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE - ID로 삭제
    public boolean deleteById(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = """
                        DELETE FROM pizza_menu
                        WHERE id = ?
                        """;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            return pstmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ - 전체 조회
    public List<PizzaMenu> findAll(String sortBy) {
        List<PizzaMenu> pizzaList = new ArrayList<>();

        // 정렬 필드 검증
        if (!List.of("id", "name", "price").contains(sortBy)) {
            sortBy = "id";
        }

        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM pizza_menu ORDER BY " + sortBy;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                pizzaList.add(new PizzaMenu(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pizzaList;
    }

    // READ - 단일 조회
    public PizzaMenu findById(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = """
                        SELECT * FROM pizza_menu
                        WHERE id = ?
                        """;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) return new PizzaMenu(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
