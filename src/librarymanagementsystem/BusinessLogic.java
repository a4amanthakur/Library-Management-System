/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.io.File;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class BusinessLogic implements BusMethodInterface{
    private StringBuilder str;
    private Connection connection;
    private ResultSet rs;
    private PreparedStatement ps;
    private CallableStatement stmt;
    private boolean sts=false;
    private ConnectDatabase connectDB;
    
    public BusinessLogic()
    {
    }
    @Override
    public void connectNow()
    {
            str=new StringBuilder("");
            connectDB=new ConnectDatabase();
            connection=connectDB.connect();
  
    }
    @Override
    public void disconnectNow()
    {
        
            connectDB.disconnect();
    }
    
    
    @Override
    public ResultSet login(String username,String passworwd) 
    {
       try
       {
            connectNow();
            if(connection==null)
            {
                throw new MyExpection("");
            }
             
             ps= connection.prepareStatement("SELECT * FROM user_tb WHERE username=? AND password=?");
             ps.setString(1, username);
             ps.setString(2, passworwd);
             rs=ps.executeQuery();
             
       }
       catch(SQLException e)
       {
           System.out.println("my exp:"+e);
       }
       catch(MyExpection e)
       {
           JOptionPane.showMessageDialog(null,"Please check your Internet Connection.");
       }
        return rs;
    }
    
    @Override
    public boolean checkValidUsername(String username)
    {
        sts=true;
        try
       {
             connectNow();
             ps= connection.prepareStatement("SELECT username FROM user_tb WHERE username=?");
             ps.setString(1, username);
             rs=ps.executeQuery();
             if(rs.next())
             {
                 sts=false;
             }
             disconnectNow();
       }
       catch(SQLException e)
       {
           System.out.println("my exp:"+e);
       }
        return sts;
    }
   
    @Override
    public boolean checkValidUsername(String id,String username)
    {
        sts=true;
        try
       {
             connectNow();
             ps= connection.prepareStatement("SELECT username FROM user_tb WHERE username=? and id!=?");
             ps.setString(1, username);
             ps.setInt(2, Integer.parseInt(id));
             
             rs=ps.executeQuery();
             if(rs.next())
             {
                 sts=false;
             }
             disconnectNow();
       }
       catch(SQLException e)
       {
           System.out.println("my exp:"+e);
       }
        return sts;
    }
    
    @Override
    public boolean checkValidMobile(String mobile)
    {
        sts=true;
        try
       {
             connectNow();
             ps= connection.prepareStatement("SELECT username FROM user_tb WHERE mobile_num=?");
             ps.setString(1, mobile);
           
             rs=ps.executeQuery();
             if(rs.next())
             {
                 sts=false;
             }
             disconnectNow();
       }
       catch(SQLException e)
       {
           System.out.println("my exp:"+e);
       }
        return sts;
    }
    
    @Override
    public boolean checkValidMobile(String id,String mobile)
    {
        sts=true;
        try
       {
             connectNow();
             ps= connection.prepareStatement("SELECT username FROM user_tb WHERE mobile_num=? and id!=?");
             ps.setString(1, mobile);
             ps.setInt(2, Integer.parseInt(id));
             rs=ps.executeQuery();
             if(rs.next())
             {
                 sts=false;
             }
             disconnectNow();
       }
       catch(SQLException e)
       {
           System.out.println("my exp:"+e);
       }
        return sts;
    }
    @Override
    public boolean checkValidMail(String mail)
    {
        sts=true;
        try
       {
             connectNow();
             ps= connection.prepareStatement("SELECT username FROM user_tb WHERE mail_id=?");
             ps.setString(1, mail);
             rs=ps.executeQuery();
             if(rs.next())
             {
                 sts=false;
             }
             disconnectNow();
       }
       catch(SQLException e)
       {
           System.out.println("my exp:"+e);
       }
        return sts;
    }
    
     @Override
    public boolean checkValidMail(String id,String mail)
    {
        sts=true;
        try
       {
             connectNow();
             ps= connection.prepareStatement("SELECT username FROM user_tb WHERE mail_id=? and id!=?");
             ps.setString(1, mail);
             ps.setInt(2, Integer.parseInt(id));
             rs=ps.executeQuery();
             if(rs.next())
             {
                 sts=false;
             }
             disconnectNow();
       }
       catch(SQLException e)
       {
           System.out.println("my exp:"+e);
       }
        return sts;
    }
    
    
    
    @Override
     public boolean addNewUser(String name,String username,String password,String userType,String gender,String mobile,String mailId,String userQuality,FileInputStream fin,File img)
     {
         sts=false;
         try
         {
             connectNow();
             ps=connection.prepareStatement("INSERT INTO user_tb VALUES(?,?,?,?,?,?,?,?,?,?)");
             ps.setString(1, null);
             ps.setString(2, name);
             ps.setString(3,username);
             ps.setString(4, password);
             ps.setString(5,userType);
             ps.setString(6, gender);
             ps.setString(7,mobile);
             ps.setString(8, mailId);
             ps.setString(9,userQuality);
             ps.setBinaryStream(10, fin, img.length());
             int i=ps.executeUpdate();
             if(i>0)
             {
                 sts=true;
             }
             disconnectNow();
         } catch (SQLException ex) {
            Logger.getLogger(BusinessLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
         return sts;
     }
     @Override
     public boolean updateUser(String userId,String name,String username,String password,String userType,String gender,String mobile,String mailId,String userQuality)
     {
         sts=false;
         try
         {
             connectNow();
             ps=connection.prepareStatement("UPDATE user_tb set name=? , username=? , password=? , user_type=? , gender=? , mobile_num=? ,mail_id=? , user_quality=? where id=?");
             ps.setString(1, name);
             ps.setString(2,username);
             ps.setString(3, password);
             ps.setString(4,userType);
             ps.setString(5, gender);
             ps.setString(6,mobile);
             ps.setString(7, mailId);
             ps.setString(8,userQuality);
             ps.setInt(9,Integer.parseInt(userId));
             int i=ps.executeUpdate();
             if(i>0)
             {
                 sts=true;
             }
             disconnectNow();
         } catch (SQLException ex) {
            Logger.getLogger(BusinessLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
         return sts;
     }
     
     @Override
     public boolean deleteUser(String id)
     {
         sts=false;
         try
         {
             connectNow();
             ps=connection.prepareStatement("DELETE from user_tb where id=?");
             ps.setInt(1,Integer.parseInt(id));
             int i=ps.executeUpdate();
             if(i>0)
             {
                 sts=true;
             }
             disconnectNow();
         } catch (SQLException ex) {
            Logger.getLogger(BusinessLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
         return sts;
     }
     @Override
     public ResultSet fetchUser(String username,String userType)
     {
        
       try
       {
             connectNow();
             ps= connection.prepareStatement("SELECT * FROM user_tb WHERE username=? and user_type=?");
             ps.setString(1, username);
             ps.setString(2, userType);
             
            
             
             rs=ps.executeQuery();
             
       }
       catch(SQLException e)
       {
           JOptionPane.showMessageDialog(null, "Database not connected.");
       }
       catch(Exception e)
       {
           JOptionPane.showMessageDialog(null, "Database not connected.");
       }
         return rs;
     }
     
     @Override
     public ResultSet fetchUser(String userType)
     {
       try
       {
             connectNow();
            stmt=connection.prepareCall("{call  fetch_users(?)}");
            stmt.setString(1, userType);
             rs=stmt.executeQuery();
             
       }
       catch(SQLException e)
       {
           System.out.println("my exp:"+e);
       }
         return rs;
     }
    
     
     @Override
     public ResultSet loadSearchData(String searchTxt,String userType)
     {
         try
         {
             connectNow();
             str.delete(0,str.length());
             str.append(searchTxt).append("%");
             ps=connection.prepareStatement("SELECT * FROM user_tb WHERE username LIKE ? and user_type=?");
             ps.setString(1, str.toString());
             ps.setString(2, userType);
             
             rs=ps.executeQuery();
         }
         catch(SQLException e)
         {
             
         }
         return rs;
     }

     
     
     
    @Override
    public boolean addNewBookGenre(String name) 
    {
         sts=false;
         try
         {
             connectNow();
             ps=connection.prepareStatement("INSERT INTO book_category_tb VALUES(?,?)");
             ps.setString(1, null);
             ps.setString(2, name);
             
             int i=ps.executeUpdate();
             if(i>0)
             {
                 sts=true;
             }
             disconnectNow();
         } catch (SQLException ex) {
            Logger.getLogger(BusinessLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
         return sts;
    }

    @Override
    public boolean updateBookGenre(String id,String name) {
        sts=false;
         try
         {
             connectNow();
             ps=connection.prepareStatement("UPDATE book_category_tb set name=? where id=?");
             
             ps.setString(1, name);
             ps.setInt(2,Integer.parseInt(id));
             int i=ps.executeUpdate();
             if(i>0)
             {
                 sts=true;
             }
             disconnectNow();
         } catch (SQLException ex) {
            Logger.getLogger(BusinessLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
         return sts;
    }

    @Override
    public boolean deleteBookGenre(String id) 
    {
        sts=false;
         try
         {
             connectNow();
             ps=connection.prepareStatement("DELETE from book_category_tb where id=?");
             ps.setInt(1,Integer.parseInt(id));
             int i=ps.executeUpdate();
             if(i>0)
             {
                 sts=true;
             }
             disconnectNow();
         } catch (SQLException ex) {
            Logger.getLogger(BusinessLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
         return sts;
    }

    @Override
    public ResultSet fetchBookGenre() 
    {
        try
       {
             connectNow();
             ps= connection.prepareStatement("SELECT * FROM book_category_tb order by name");            
             rs=ps.executeQuery();
             
       }
       catch(SQLException e)
       {
           System.out.println("my exp:"+e);
       }
         return rs;
    }

    @Override
    public ResultSet loadSearchBookGenre(String searchTxt) 
    {
        try
         {
             connectNow();
             str.delete(0,str.length());
             str.append(searchTxt).append("%");
             ps=connection.prepareStatement("SELECT * FROM book_category_tb WHERE name LIKE ?");
             ps.setString(1, str.toString());             
             rs=ps.executeQuery();
         }
         catch(SQLException e)
         {
             
         }
         return rs; 
    }
    
    @Override
    public boolean checkValidBookGenre(String name)
    {
         sts=true;
        try
       {
             connectNow();
             ps= connection.prepareStatement("SELECT name FROM book_category_tb WHERE name=?");
             ps.setString(1, name);
             rs=ps.executeQuery();
             if(rs.next())
             {
                 sts=false;
             }
             disconnectNow();
       }
       catch(SQLException e)
       {
           System.out.println("my exp:"+e);
       }
        return sts;
        
    }
    
    @Override
    public boolean checkValidBookGenre(String id,String name)
    {
        sts=true;
        try
       {
             connectNow();
             ps= connection.prepareStatement("SELECT name FROM book_category_tb WHERE name=? and id!=?");
             ps.setString(1, name);
             ps.setInt(2,Integer.parseInt(id));
             rs=ps.executeQuery();
             if(rs.next())
             {
                 sts=false;
             }
             disconnectNow();
       }
       catch(SQLException e)
       {
           System.out.println("my exp:"+e);
       }
        return sts;
        
    }

    @Override
    public boolean checkValidISBN(String isbn)
    {
       sts=true;
        try
       {
             connectNow();
             ps= connection.prepareStatement("SELECT isbn FROM book_tb WHERE isbn=?");
             ps.setString(1, isbn);
             rs=ps.executeQuery();
             if(rs.next())
             {
                 sts=false;
             }
             disconnectNow();
       }
       catch(SQLException e)
       {
           System.out.println("my exp:"+e);
       }
        return sts;
    }

    @Override
    public boolean addNewBook(String isbn, String title, String author, String eddition, String publisher, String pub_date, String cat, String language, String price, String quantity, String summary,FileInputStream fin,File img) {
          sts=false;
         try
         {
             connectNow();
             ps=connection.prepareStatement("INSERT INTO book_tb VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
             ps.setString(1, null);
             ps.setString(2,isbn );
             ps.setString(3,title);
             ps.setString(4,author );
             ps.setString(5,eddition);
             ps.setString(6,publisher);
             ps.setString(7,pub_date);
             ps.setString(8,cat);
             ps.setString(9,language);
             ps.setString(10,price);
             ps.setString(11,quantity);
             ps.setString(12,quantity);
             ps.setString(13,summary);
             ps.setBinaryStream(14, fin, img.length());
             int i=ps.executeUpdate();
             if(i>0)
             {
                 sts=true;
             }
             disconnectNow();
         } catch (SQLException ex) {
            Logger.getLogger(BusinessLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
         return sts;
    }

    @Override
    public ResultSet fetchBooks() {
        try
       {
             connectNow();
             ps= connection.prepareStatement("SELECT * FROM book_tb order by title");            
             rs=ps.executeQuery();
             
       }
       catch(SQLException e)
       {
           System.out.println("my exp:"+e);
       }
         return rs;
    }

    @Override
    public boolean deleteBook(String id) {
        sts=false;
         try
         {
             connectNow();
             ps=connection.prepareStatement("DELETE from book_tb where id=?");
             ps.setInt(1,Integer.parseInt(id));
             int i=ps.executeUpdate();
             if(i>0)
             {
                 sts=true;
             }
             disconnectNow();
         } catch (SQLException ex) {
            Logger.getLogger(BusinessLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
         return sts;
    }
   
}
