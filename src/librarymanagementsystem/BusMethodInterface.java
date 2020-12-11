
package librarymanagementsystem;

import java.io.File;
import java.io.FileInputStream;
import java.sql.ResultSet;

public interface BusMethodInterface {
    void connectNow();
    void disconnectNow();
    ResultSet login(String username,String passworwd);
    boolean checkValidUsername(String username);
    boolean checkValidMobile(String mobile);
    boolean checkValidMail(String mail);
    
    boolean checkValidUsername(String id,String username);
    boolean checkValidMobile(String id,String mobile);
    boolean checkValidMail(String id,String mail);
    
    boolean addNewUser(String name,String username,String password,String userType,String gender,String mobile,String mailId,String userQuality,FileInputStream fin,File img);
    boolean updateUser(String userId,String name,String username,String password,String userType,String gender,String mobile,String mailId,String userQuality);
    boolean deleteUser(String id);
  
    ResultSet fetchUser(String username,String userType);
    ResultSet fetchUser(String userType);
    ResultSet loadSearchData(String searchTxt,String userType);
    
    
    boolean addNewBookGenre(String name);
    boolean updateBookGenre(String id,String name);
    boolean deleteBookGenre(String id);
    ResultSet fetchBookGenre();
    ResultSet loadSearchBookGenre(String searchTxt);
    boolean checkValidBookGenre(String name);
    boolean checkValidBookGenre(String id,String name);
    
    boolean checkValidISBN(String isbn);
    boolean addNewBook(String isbn,String title,String author,String eddition,String publisher,String pub_date,String cat,String language,String price,String quantity,String summary,FileInputStream fin,File img);
  //  ResultSet fetchAdmin(String username);
    
    ResultSet fetchBooks();
    boolean deleteBook(String id);

}
