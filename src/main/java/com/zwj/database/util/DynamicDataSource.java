package com.zwj.database.util;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

import com.zwj.database.pojo.DBIdentifier;
import com.zwj.database.pojo.DDSHolder;
import com.zwj.database.pojo.ProjectDBMgr;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
/**
 * @author zwj
 * @version 1.0
 * @date 2020/8/5
 */
public class DynamicDataSource extends DataSource {
    private static Logger log = LogManager.getLogger(DynamicDataSource.class);
    /**
     * 改写本方法是为了在请求不同工程的数据时去连接不同的数据库。
     */
    @Override
    public Connection getConnection(){
        String projectCode = DBIdentifier.getProjectCode();
        //1、获取数据源
        DataSource dds = DDSHolder.instance().getDDS(projectCode);
        //2、如果数据源不存在则创建
        if (dds == null) {
            try {
                DataSource newDDS = initDDS(projectCode);
                DDSHolder.instance().addDDS(projectCode, newDDS);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                log.error("Init data source fail. projectCode:" + projectCode);
                return null;
            }
        }
        dds = DDSHolder.instance().getDDS(projectCode);
        try {
            return dds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 以当前数据对象作为模板复制一份。
     *
     * @return dds
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private DataSource initDDS(String projectCode) throws IllegalArgumentException, IllegalAccessException {
        DataSource dds = new DataSource();
        // 2、复制PoolConfiguration的属性
        PoolProperties property = new PoolProperties();
        Field[] pfields = PoolProperties.class.getDeclaredFields();
        for (Field f : pfields) {
            f.setAccessible(true);
            Object value = f.get(this.getPoolProperties());
            try
            {
                f.set(property, value);
            } catch (Exception e) {
                continue;
            }
        }
        dds.setPoolProperties(property);

        dds.setDriverClassName(ProjectDBMgr.instance().getDBDriver(projectCode));
        // 3、设置数据库名称和IP(一般来说，端口和用户名、密码都是统一固定的)
        String urlFormat = this.getUrl();
        String url = String.format(urlFormat, ProjectDBMgr.instance().getDBIP(projectCode),
                ProjectDBMgr.instance().getDBPort(projectCode),
                ProjectDBMgr.instance().getDBTable(projectCode));
        dds.setUrl(url);
        dds.setUsername(ProjectDBMgr.instance().getDBUser(projectCode));
        dds.setPassword(ProjectDBMgr.instance().getDBPassword(projectCode));
        return dds;
    }
}
