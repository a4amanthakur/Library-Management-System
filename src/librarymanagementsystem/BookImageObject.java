/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

/**
 *
 * @author thakur-huni
 */
public class BookImageObject {
    String id;
    byte[] img;
    
    void setId(String id)
    {
        this.id=id;
    }
    void setImg(byte[] img)
    {
        this.img=img;
    }
    String getId()
    {
        return this.id;
    }
    byte[] getImg()
    {
        return this.img;
    }
}
