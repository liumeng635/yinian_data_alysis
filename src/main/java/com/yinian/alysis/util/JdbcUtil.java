package com.yinian.alysis.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JdbcUtil {
	// 表示定义数据库的用户名  
    private static String USERNAME ;  
  
    // 定义数据库的密码  
    private static String PASSWORD;  
  
    // 定义数据库的驱动信息  
    private static String DRIVER;  
  
    // 定义访问数据库的地址  
    private static String URL;  
  
    // 定义数据库的链接  
    private Connection connection;  
  
    // 定义sql语句的执行对象  
    private PreparedStatement pstmt;  
  
    // 定义查询返回的结果集合  
    private ResultSet resultSet;  
      
    static{  
        //加载数据库配置信息，并给相关的属性赋值  
        loadConfig();  
    }  
  
    /** 
     * 加载数据库配置信息，并给相关的属性赋值 
     */  
    public static void loadConfig() {  
        try {  
            USERNAME = "jdbc:mysql://120.77.224.114:3306/yinian_count?autoReconnect=true";  
            PASSWORD = "root";  
            DRIVER= "com.mysql.jdbc.Driver";  
            URL = "YiNian1130";  
        } catch (Exception e) {  
            throw new RuntimeException("读取数据库配置文件异常！", e);  
        }  
    }  
  
    public JdbcUtil() {  
  
    }  
  
    /** 
     * 获取数据库连接 
     *  
     * @return 数据库连接 
     */  
    public Connection getConnection() {  
        try {  
            Class.forName(DRIVER); // 注册驱动  
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // 获取连接  
        } catch (Exception e) {  
            throw new RuntimeException("get connection error!", e);  
        }  
        return connection;  
    }  
  
    /** 
     * 执行更新操作 
     *  
     * @param sql 
     *            sql语句 
     * @param params 
     *            执行参数 
     * @return 执行结果 
     * @throws SQLException 
     */  
    public boolean updateByPreparedStatement(String sql, List<?> params)  
            throws SQLException {  
        boolean flag = false;  
        int result = -1;// 表示当用户执行添加删除和修改的时候所影响数据库的行数  
        pstmt = connection.prepareStatement(sql);  
        int index = 1;  
        // 填充sql语句中的占位符  
        if (params != null && !params.isEmpty()) {  
            for (int i = 0; i < params.size(); i++) {  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        result = pstmt.executeUpdate();  
        flag = result > 0 ? true : false;  
        return flag;  
    }  
  
    /** 
     * 执行查询操作 
     *  
     * @param sql 
     *            sql语句 
     * @param params 
     *            执行参数 
     * @return 
     * @throws SQLException 
     */  
    public List<Map<String, Object>> findResult(String sql, List<?> params)  
            throws SQLException {  
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        int index = 1;  
        pstmt = connection.prepareStatement(sql);  
        if (params != null && !params.isEmpty()) {  
            for (int i = 0; i < params.size(); i++) {  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        resultSet = pstmt.executeQuery();  
        ResultSetMetaData metaData = resultSet.getMetaData();  
        int cols_len = metaData.getColumnCount();  
        while (resultSet.next()) {  
            Map<String, Object> map = new HashMap<String, Object>();  
            for (int i = 0; i < cols_len; i++) {  
                String cols_name = metaData.getColumnName(i + 1);  
                Object cols_value = resultSet.getObject(cols_name);  
                if (cols_value == null) {  
                    cols_value = "";  
                }  
                map.put(cols_name, cols_value);  
            }  
            list.add(map);  
        }  
        return list;  
    }  
  
    /** 
     * 释放资源 
     */  
    public void releaseConn() {  
        if (resultSet != null) {  
            try {  
                resultSet.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
        if (pstmt != null) {  
            try {  
                pstmt.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
        if (connection != null) {  
            try {  
                connection.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    
    public static void main(String[] args) {
    	JdbcUtil jdbcUtil = new JdbcUtil();  
        jdbcUtil.getConnection();  
        try {  
            List<Map<String, Object>> result = jdbcUtil.findResult(  
                    "select * from wuyi_mid_table t where 1=1 and period >= str_to_date('2018-04-27 15','%Y-%m-%d %H:%m:%s') and period > str_to_date('2018-04-27 15','%Y-%m-%d %H:%m:%s') and t.type like '一%'", null);  
            for (Map<String, Object> m : result) {  
                System.out.println(m);  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } finally {  
            jdbcUtil.releaseConn();  
        }  
	}
}