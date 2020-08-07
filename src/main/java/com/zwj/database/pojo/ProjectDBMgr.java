package com.zwj.database.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zwj
 * @version 1.0
 * @date 2020/8/5
 */
public class ProjectDBMgr {
    /**
     * 保存项目编码与数据名称的映射关系。这里是硬编码，实际开发中这个关系数据可以保存到redis缓存中；
     * 新增一个项目或者删除一个项目只需要更新缓存。到时这个类的接口只需要修改为从缓存拿数据。
     */
    private Map<String, String> dbNameMap = new HashMap<String, String>();
    /**
     * 保存项目编码与数据库IP的映射关系。
     */
    private Map<String, DBData> dbIPMap = new HashMap<String, DBData>();

    public void setDbIPMap(Map<String, DBData> dbIPMap) {
        this.dbIPMap = dbIPMap;
    }

    private ProjectDBMgr() {
    }
    public static ProjectDBMgr instance() {
        return ProjectDBMgrBuilder.instance;
    }

    //实际开发中改为从缓存中获取
    public String getDBDriver(String projectCode) {
        if (dbIPMap.containsKey(projectCode)) {
            return dbIPMap.get(projectCode).getDriver();
        }
        return "";
    }
    public String getDBIP(String projectCode) {
        if (dbIPMap.containsKey(projectCode)) {
            return dbIPMap.get(projectCode).getAddress();
        }
        return "";
    }
    public String getDBTable(String projectCode) {
        if (dbIPMap.containsKey(projectCode)) {
            return dbIPMap.get(projectCode).getTable();
        }
        return "";
    }
    public String getDBPort(String projectCode) {
        if (dbIPMap.containsKey(projectCode)) {
            return dbIPMap.get(projectCode).getPort();
        }
        return "";
    }
    public String getDBUser(String projectCode) {
        if (dbIPMap.containsKey(projectCode)) {
            return dbIPMap.get(projectCode).getName();
        }
        return "";
    }

    public String getDBPassword(String projectCode) {
        if (dbIPMap.containsKey(projectCode)) {
            return dbIPMap.get(projectCode).getPassword();
        }
        return "";
    }


    private static class ProjectDBMgrBuilder {
        private static ProjectDBMgr instance = new ProjectDBMgr();
    }
}
