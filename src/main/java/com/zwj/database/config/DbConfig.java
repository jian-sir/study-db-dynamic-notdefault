package com.zwj.database.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zwj
 * @version 1.0
 * @date 2020/8/5
 */
@Data
@ConfigurationProperties(prefix = "server-db")
@Component
public class DbConfig {
    private String driver;

    private String ip;

    private String port;

    private String table;

    private String username;

    private String password;
}
