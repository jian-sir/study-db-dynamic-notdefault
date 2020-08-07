package com.zwj.database.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zwj.database.mapper.EslMapper;
import com.zwj.database.pojo.DBData;
import com.zwj.database.pojo.DBIdentifier;
import com.zwj.database.config.DbConfig;
import com.zwj.database.pojo.ProjectDBMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author zwj
 * @version 1.0
 * @date 2020/8/5
 */
@RestController
public class WSUser {

    @Autowired
    private EslMapper eslMapper;

    @Autowired
    private DbConfig dbConfig;
    /**
     * 查询项目中所有用户信息
     *
     * @param projectCode 项目编码
     * @return 用户列表
     */
    @RequestMapping(value="/test", method=RequestMethod.GET)
    public List<Map<String, Object>> queryUser(@RequestParam(value="projectCode", required=true) String projectCode) {
        DBIdentifier.setProjectCode(projectCode);
        if ("server-db".equals(projectCode)) {
            return eslMapper.select2();
        } else {
            return eslMapper.select1();
        }

    }

    @RequestMapping(value="/init", method=RequestMethod.GET)
    public void init() {
        Map<String, DBData> dbIPMap = new HashMap<>();
        dbIPMap.put("server-db", new DBData(dbConfig.getDriver(), dbConfig.getIp(), dbConfig.getPort(), dbConfig.getTable(), dbConfig.getUsername(), dbConfig.getPassword()));
        dbIPMap.put("client-db", new DBData(dbConfig.getDriver(), dbConfig.getIp(), dbConfig.getPort(), "test", dbConfig.getUsername(), dbConfig.getPassword()));
        ProjectDBMgr.instance().setDbIPMap(dbIPMap);
    }


}