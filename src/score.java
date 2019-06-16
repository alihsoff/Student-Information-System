
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Acer
 */
public class score {
    public void insertUpdateDeleteScore(char operation,int std_id,int crs_id,float score,String description){
        
            Connection con = MyConnection.getConnection();
            PreparedStatement pSt;
        if(operation == 'i'){ // insert
            try{
                String sql="INSERT INTO score(student_id, course_id,student_score,description)"
                        + " VALUES (?,?,?,?)";
                pSt = con.prepareStatement(sql);
                pSt.setInt(1, std_id);
                pSt.setInt(2, crs_id);
                pSt.setFloat(3, score);
                pSt.setString(4, description);
                if(pSt.executeUpdate()!=0){
                    JOptionPane.showMessageDialog(null, "Qiymət yazıldı!!!");
                }
            }catch(SQLException se){
                System.out.println(se);
            }
            
        }
        else if(operation == 'u'){ // update
            try{
                String sql="UPDATE score SET student_score = ?,description = ? WHERE student_id = ? AND course_id = ?";
                pSt = con.prepareStatement(sql);
                pSt.setFloat(1, score);
                pSt.setString(2, description);
                pSt.setInt(3, std_id);
                pSt.setInt(4, crs_id);
                if(pSt.executeUpdate()!=0)
                JOptionPane.showMessageDialog(null, "Yeniləndi!!!");
            }catch(SQLException se){
                System.out.println(se);
            }
            
        }

        else if(operation == 'd'){
                    try{
                String sql="Delete FROM course WHERE student_id = ? AND course_id = ?";
                pSt = con.prepareStatement(sql);
                pSt.setInt(1, std_id);
                pSt.setInt(2, crs_id);
                if(pSt.executeUpdate()!=0){
                    JOptionPane.showMessageDialog(null, "Qiymət Silindi");
                }
            }catch(SQLException se){
                System.out.println(se);
            }
        
        }
        
    }
    
        public void fillScoreJtable(JTable table){
            Connection con = MyConnection.getConnection();
            Statement St;
            String sql = "select * from score";
        try{
        St = con.createStatement();
        ResultSet res = St.executeQuery(sql);
        
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        
        Object[] row;
        
        while(res.next()){
        row = new Object[5];
        row[0] = getStdCrs("student",res.getInt(1),false);
        row[1] = getStdCrs("student",res.getInt(1),true);
        row[2] = getStdCrs("course",res.getInt(2),false);
        row[3] = res.getFloat(3);
        row[4] = res.getString(4);
        
        model.addRow(row);
        }
        }catch(Exception se){
            System.out.println(se);
        }
    
    }
        
        public String getStdCrs(String tableName,int id,boolean lname){
            Connection con = MyConnection.getConnection();
            PreparedStatement pSt;
            String sql = "select * from "+tableName+" WHERE id = ?";
        try{
        pSt = con.prepareStatement(sql);
        pSt.setInt(1, id);
        ResultSet res = pSt.executeQuery();
        
        
        while(res.next()){
            if(!lname)
            return res.getString(2);
            
            return res.getString(3);
        }
        }catch(Exception se){
            System.out.println(se);
        }
        return null;
        }
            
}

