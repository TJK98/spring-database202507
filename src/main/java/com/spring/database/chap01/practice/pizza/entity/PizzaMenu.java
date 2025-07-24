package com.spring.database.chap01.practice.pizza.entity;

/*
CREATE TABLE IF NOT EXISTS pizza_menu (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(8, 2) NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
*/

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PizzaMenu {

    private int id;
    private String name;
    private String description;
    private double price;

    @Builder.Default
    private boolean isAvailable = true;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PizzaMenu(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.description = rs.getString("description");
        this.price = rs.getDouble("price");
        this.isAvailable = rs.getBoolean("is_available");
        this.createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        this.updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
    }
}
