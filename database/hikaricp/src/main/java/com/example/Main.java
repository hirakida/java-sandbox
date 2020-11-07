package com.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:demo-db");
        config.setUsername("sa");
        config.setPassword("");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource dataSource = new HikariDataSource(config);

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement()) {
                UserService userService = new UserService(statement);

                for (int i = 1; i <= 10; i++) {
                    userService.insert("name" + i, statement);
                    TimeUnit.MILLISECONDS.sleep(100);
                }

                for (int i = 1; i <= 10; i++) {
                    userService.update(i, "name_" + i, statement);
                    TimeUnit.MILLISECONDS.sleep(100);
                }

                userService.delete(1, statement);
                List<User> users = userService.findAll(statement);
                users.forEach(user -> log.info("{}", user));

                connection.commit();
            } catch (SQLException e) {
                log.error("{}", e.getErrorCode(), e);
                connection.rollback();
            }
        } catch (SQLException e) {
            log.error("{}", e.getErrorCode(), e);
        }

        TimeUnit.SECONDS.sleep(3);
    }
}
