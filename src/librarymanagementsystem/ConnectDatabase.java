
package librarymanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConnectDatabase {
    private Connection con=null;
    
    public Connection connect()
    {
        if(con==null)
        {
            try
            {
            //for local phpmyadmin     
              /*
                  Class.forName("com.mysql.jdbc.Driver");
                  con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagementSystem","root","");
              */


            //for remote phpmyadmin

                Class.forName("com.mysql.jdbc.Driver");
                con=  DriverManager.getConnection("jdbc:mysql://amans-webserver.cel7s2vhdev5.ap-south-1.rds.amazonaws.com:3306/librarymanagementsystem","amanthakuronly4u","$$Aman928");
             }
            catch(ClassNotFoundException | SQLException e)
            {
                System.out.println("exp:"+e);
                JOptionPane.showMessageDialog(null,"Error: Database not connected");
            }
            System.out.println("establishing new connection.");
        }
        else
        {
            System.out.println("already connected");
        }
            return con;
    }
    
    public void disconnect()
    {
        try
        {
           con.close();
           System.out.println("disconnected.");
        }
        catch(Exception e)
        {
           
        }
    }
    public static void main(String[] args) {
        ConnectDatabase db=new ConnectDatabase();
        db.connect();
        db.disconnect();
    }
   
   
}

/*

***** www.db4free.net ****
Host name : db4free.net
Port no.  : 3306 
db name   : library_system88
username  : amanthakuronly4u
password  : $$A@man123

*/