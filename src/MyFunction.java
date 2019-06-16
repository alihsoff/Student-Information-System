
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyFunction {
    public static int countData(String tableName){
    Connection con = MyConnection.getConnection();
    int total = 0;
    Statement St;
    try{
        String sql = "SELECT count(*) FROM "+tableName;
        St = con.createStatement();
        ResultSet res = St.executeQuery(sql);
        while(res.next())
        total = res.getInt(1);
    }catch(SQLException se){
        System.out.println(se);    
    }
        return total;
    }
    
}