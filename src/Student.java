
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Student {
    public void insertUpdateDeleteStudent(char operation,String id,String fname,
            String lname,String sex,String bdate,String phone,String address,String ImgPath) throws FileNotFoundException{
        
        Connection con = MyConnection.getConnection();
        PreparedStatement pSt;
        if(operation == 'i'){ // insert
            try{
                String sql="INSERT INTO student(first_name, last_name, sex, birthdate, phone, address, pp)"
                        + " VALUES (?,?,?,?,?,?,?)";
                pSt = con.prepareStatement(sql);
                pSt.setString(1, fname);
                pSt.setString(2, lname);
                pSt.setString(3, sex);
                pSt.setString(4, bdate);
                pSt.setString(5, phone);
                pSt.setString(6, address);
                
                InputStream img = new FileInputStream(new File(ImgPath));
                pSt.setBlob(7, img);
                if(pSt.executeUpdate()!=0){
                    JOptionPane.showMessageDialog(null, "Tələbə Əlavə Edildi!!!");
                }
            }catch(SQLException se){
                System.out.println(se);
            }
            
        }
        else if(operation == 'u'){ // update
            try{
                if(ImgPath==null){ // if photo exsist
                String sql="UPDATE student SET first_name=?, last_name=?,sex=?,birthdate=?,phone=?,address=? WHERE id = ?";
                pSt = con.prepareStatement(sql);
                pSt.setString(1, fname);
                pSt.setString(2, lname);
                pSt.setString(3, sex);
                pSt.setString(4, bdate);
                pSt.setString(5, phone);
                pSt.setString(6, address);
                pSt.setString(7, id);
                if(pSt.executeUpdate()!=0){
                JOptionPane.showMessageDialog(null, "Yeniləndi!!!");
                }
                }else{
                String sql="UPDATE student SET first_name=?, last_name=?,sex=?,birthdate=?,phone=?,address=?,pp=? WHERE id = ?";
                pSt = con.prepareStatement(sql);
                pSt.setString(1, fname);
                pSt.setString(2, lname);
                pSt.setString(3, sex);
                pSt.setString(4, bdate);
                pSt.setString(5, phone);
                pSt.setString(6, address);
                InputStream img = new FileInputStream(new File(ImgPath));
                pSt.setBlob(7, img);
                pSt.setString(8, id);
                if(pSt.executeUpdate()!=0){
                JOptionPane.showMessageDialog(null, "Yeniləndi!!!");
                }
                }
            }catch(SQLException se){
                System.out.println(se);
            }
            
        }
        else if(operation == 'd'){
    Object[] options = {"Sil!",
                    "Silmə!"};
    int YesOrNo = JOptionPane.showOptionDialog(null,
    "Tələbənin qiymətləridə silinəcək!!!",
    "Silinsin?",
    JOptionPane.OK_CANCEL_OPTION,
    JOptionPane.QUESTION_MESSAGE,
    null,     //do not use a custom Icon
    options,  //the titles of buttons
    options[0]); //default button title
            if(YesOrNo == 0){
                    try{
                String sql="Delete FROM student Where id = ?";
                pSt = con.prepareStatement(sql);
                pSt.setString(1, id);
                if(pSt.executeUpdate()!=0){
                    JOptionPane.showMessageDialog(null, "Tələbə Silindi");
                }
            }catch(SQLException se){
                System.out.println(se);
            }
          }
        }
        
    }
    public void fillStudentJtable(JTable table,String valueToSearch){
        Connection con = MyConnection.getConnection();
        PreparedStatement pSt;
        String sql = "select * from student where CONCAT(first_name,last_name,phone)like ?";
        try{
        pSt = con.prepareStatement(sql);
        pSt.setString(1, "%"+valueToSearch+"%");
        
        ResultSet res = pSt.executeQuery();
        
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        
        Object[] row;
        
        while(res.next()){
        row = new Object[7];
        row[0] = res.getInt(1);
        row[1] = res.getString(2);
        row[2] = res.getString(3);
        row[3] = res.getString(4);
        row[4] = res.getString(5);
        row[5] = res.getString(6);
        row[6] = res.getString(7);
                
        model.addRow(row);
        }
        }catch(Exception se){
            System.out.println(se);
        }
    
    }
    public byte[] getImage(String id){
    Connection con = MyConnection.getConnection();
        PreparedStatement pSt;
        String sql = "select pp from student where id=?";
        try{
        pSt = con.prepareStatement(sql);
        pSt.setString(1, id);
        
        ResultSet res = pSt.executeQuery();
        while(res.next()){
        return res.getBytes("pp");
        }
    }catch(SQLException se){
            System.out.println("se");
    }
        return null;
    }
    
            public int getStudentId(String name){
        
            int nameId = 0;
            Connection con = MyConnection.getConnection();
            PreparedStatement pSt;
            String sql = "select * from student where first_name = ?";
        try{
        pSt = con.prepareStatement(sql);
        pSt.setString(1, name);
        
        ResultSet res = pSt.executeQuery();
        if(res.next()){
            nameId = res.getInt("id");
        }
        
        }catch(Exception se){
            System.out.println(se);
        }          
            return nameId;
        
        }
    
}
