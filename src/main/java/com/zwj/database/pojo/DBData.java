package com.zwj.database.pojo;

import lombok.Data;

/**
 * @author zwj
 * @version 1.0
 * @date 2020/8/4
 */
@Data
public class DBData {
    private String driver;
    private String address;
    private String port;
    private String table;
    private String name;
    private String password;

    public DBData() {
    }

    public DBData(String driver, String address, String port, String table, String name, String password) {
        this.address = address;
        this.driver = driver;
        this.port = port;
        this.table = table;
        this.name = name;
        this.password = password;
    }
}
