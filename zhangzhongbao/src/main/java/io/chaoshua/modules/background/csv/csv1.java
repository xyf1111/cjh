package io.chaoshua.modules.background.csv;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Created by Administrator on 2018/8/23 0023.
 */
public class csv1 {
    public static void  main(String[] args){
        String filePath = "E:/数据/data/chaoshua/data/withdrawals.csv";
        Connection conn = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/chaoshuaqianyi?allowMultiQueries=true&useUnicode=true&useSSL=false";
        PreparedStatement pstmt = null;
        try {
            Class.forName(driver);
            conn =  DriverManager.getConnection(url, "root", "root");
                    String sql = "LOAD DATA LOCAL INFILE '" + filePath
                    + "' INTO TABLE " + " withdrawals" + " "
                    +"character set utf8mb4"+""
                    + "  fields terminated by ',' optionally enclosed by '\"' escaped by '\"'"+""
                     +"LINES TERMINATED BY '\\n'"+""
                    +" IGNORE 1 ROWS";
            pstmt = conn.prepareStatement(sql);
            if (pstmt.isWrapperFor(com.mysql.jdbc.Statement.class)) {
                com.mysql.jdbc.PreparedStatement mysqlStatement = pstmt
                        .unwrap(com.mysql.jdbc.PreparedStatement.class);
                 mysqlStatement.executeUpdate();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                if(conn != null){
                    conn.close();
                }
                if(pstmt != null){
                    pstmt.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }
    }
}
