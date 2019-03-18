package com.springboot.test.config;


import com.springboot.test.controller.ApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class JdbcConfig {

    private static Logger logger= LoggerFactory.getLogger(JdbcConfig.class);

    public static void createJDBC(String sql){
        Connection conn=null;
        PreparedStatement pre = null;
        Statement state=null;
        ResultSet result = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/zj?generateSimpleParameterMetadata=true&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC";
            String user = "root";
            String password = "";
            conn = DriverManager.getConnection(url, user, password);// 获取连接
            state= conn.createStatement();

            logger.info("sql*****:"+sql);

            result= state.executeQuery(sql);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (result != null){
                    result.close();
                }
                if (state != null){
                    state.close();
                }
                if (conn != null){
                    conn.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }
}
