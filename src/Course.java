
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
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
public class Course {
        public void insertUpdateDeleteCourse(char operation,String id,String c_name,int hours){
        
            Connection con = MyConnection.getConnection();
            PreparedStatement pSt;
        if(operation == 'i'){ // insert
            try{
                String sql="INSERT INTO course(c_name, hours_number)"
                        + " VALUES (?,?)";
                pSt = con.prepareStatement(sql);
                pSt.setString(1, c_name);
                pSt.setInt(2, hours);
                
                if(pSt.executeUpdate()!=0){
                    JOptionPane.showMessageDialog(null, "Dərs Əlavə Edildi!!!");
                }
            }catch(SQLException se){
                System.out.println(se);
            }
            
        }
        else if(operation == 'u'){ // update
            try{
                String sql="UPDATE course SET c_name=?, hours_number=? WHERE id = ?";
                pSt = con.prepareStatement(sql);
                pSt.setString(1, c_name);
                pSt.setInt(2, hours);
                pSt.setString(3, id);
                if(pSt.executeUpdate()!=0)
                JOptionPane.showMessageDialog(null, "Yeniləndi!!!");
            }catch(SQLException se){
                System.out.println(se);
            }
            
        }

        else if(operation == 'd'){
    Object[] options = {"Sil!",
                    "Silmə!"};
    int YesOrNo = JOptionPane.showOptionDialog(null,
    "Dərsi alan tələbələrin qiymətləri də silinəcək!!!",
    "Silinsin?",
    JOptionPane.OK_CANCEL_OPTION,
    JOptionPane.QUESTION_MESSAGE,
    null,     //do not use a custom Icon
    options,  //the titles of buttons
    options[0]); //default button title
            if(YesOrNo == 0){
                    try{
                String sql="Delete FROM course Where id = ?";
                pSt = con.prepareStatement(sql);
                pSt.setString(1, id);
                if(pSt.executeUpdate()!=0){
                    JOptionPane.showMessageDialog(null, "Dərs Silindi");
                }
            }catch(SQLException se){
                System.out.println(se);
            }
            }
        
        }
        
    }
        public void fillCourseJtable(JTable table){
            Connection con = MyConnection.getConnection();
            Statement St;
            String sql = "select * from course";
        try{
        St = con.createStatement();
        ResultSet res = St.executeQuery(sql);
        
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        
        Object[] row;
        
        while(res.next()){
        row = new Object[3];
        row[0] = res.getInt(1);
        row[1] = res.getString(2);
        row[2] = res.getInt(3);
                
        model.addRow(row);
        }
        }catch(Exception se){
            System.out.println(se);
        }
    
    }
        
        public void fillCourseCombo(JComboBox combo){
            Connection con = MyConnection.getConnection();
            Statement St;
            String sql = "select * from course";
        try{
        St = con.createStatement();
        ResultSet res = St.executeQuery(sql);
        
        
        while(res.next()){
        combo.addItem(res.getString("c_name"));
        }
        }catch(Exception se){
            System.out.println(se);
        }
    
    }
        
        public boolean isCourseExist(String c_name){
            Connection con = MyConnection.getConnection();
            PreparedStatement pSt;
            String sql = "select * from course where c_name = ?";
        try{
        pSt = con.prepareStatement(sql);
        pSt.setString(1, c_name);
        
        ResultSet res = pSt.executeQuery();
        if(res.next()){
            return true;
        }
        
        }catch(Exception se){
            System.out.println(se);
        }
        return false;
        }
        
        
        public int getCourseId(String c_name){
        
            int courseId = 0;
            Connection con = MyConnection.getConnection();
            PreparedStatement pSt;
            String sql = "select * from course where c_name = ?";
        try{
        pSt = con.prepareStatement(sql);
        pSt.setString(1, c_name);
        
        ResultSet res = pSt.executeQuery();
        if(res.next()){
            courseId = res.getInt("id");
        }
        
        }catch(Exception se){
            System.out.println(se);
        }          
            return courseId;
        
        }
}
