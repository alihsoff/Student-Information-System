import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class MyConnection {
    
    public static Connection getConnection(){
        Connection con = null;
        try{
        Class.forName("com.mysql.jdbc.Driver");
        String user = "root";
        String pass = "123456789";
        String url = "jdbc:mysql://localhost:3306/tms";
        con = DriverManager.getConnection(url, user,pass);
        }catch(Exception se){
        se.printStackTrace();
        }
        return con;
    }
}
