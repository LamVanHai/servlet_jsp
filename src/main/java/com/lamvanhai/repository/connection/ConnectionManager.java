package com.lamvanhai.repository.connection;

import com.lamvanhai.common.Config;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionManager {
    private volatile static HikariConfig config;
    private volatile static HikariDataSource ds;

    static {
        config = new HikariConfig();
        config.setJdbcUrl(Config.url);
        config.setUsername(Config.user);
        config.setPassword(Config.password);
        config.setDriverClassName(Config.driverClassName);
        config.setMaximumPoolSize(Config.maxPoolSize);
        config.setConnectionTimeout(Config.connectionTimeOut * 1000);
        config.setMaxLifetime(Config.connetionLifeTime * 1000);
        ds = new HikariDataSource(config);
    }

    public static HikariDataSource getDs() {
        return ds;
    }
}
