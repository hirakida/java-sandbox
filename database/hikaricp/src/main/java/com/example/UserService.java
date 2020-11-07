package com.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class UserService {
    private static final String CREATE_SQL = """
            CREATE TABLE user (
              `id` INT PRIMARY KEY AUTO_INCREMENT,
              `name` VARCHAR(30) NOT NULL,
              `created_at` DATETIME NOT NULL,
              `updated_at` DATETIME NOT NULL
            );
            """;
    private static final String SELECT_SQL = "SELECT id, name, created_at, updated_at FROM user;";
    private static final String INSERT_SQL = """
            INSERT INTO user(name, created_at, updated_at)
            VALUES ('%s', '%s', '%s')
            """;
    private static final String UPDATE_SQL = """
            UPDATE user SET name = '%s', updated_at = '%s'
            WHERE id = %d;
            """;
    private static final String DELETE_SQL = "DELETE FROM user WHERE id = %d;";

    public UserService(Statement statement) {
        try {
            statement.execute(CREATE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> findAll(Statement statement) throws SQLException {
        List<User> users = new ArrayList<>();
        try (ResultSet rs = statement.executeQuery(SELECT_SQL)) {
            while (rs.next()) {
                users.add(toUser(rs));
            }
        }
        return users;
    }

    public int insert(String name, Statement statement) throws SQLException {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        String sql = INSERT_SQL.formatted(name, timestamp, timestamp);
        return statement.executeUpdate(sql);
    }

    public int update(long id, String name, Statement statement) throws SQLException {
        String sql = UPDATE_SQL.formatted(name, Timestamp.valueOf(LocalDateTime.now()), id);
        return statement.executeUpdate(sql);
    }

    public int delete(long id, Statement statement) throws SQLException {
        String sql = DELETE_SQL.formatted(id);
        return statement.executeUpdate(sql);
    }

    private static User toUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setUpdatedAt(rs.getTimestamp("updated_at"));
        return user;
    }
}
