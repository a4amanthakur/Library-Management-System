/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author thakur-huni
 */
public class PnlItem extends javax.swing.JPanel {

    /**
     * Creates new form PnlItem
     */
    String bookId=null;
    public PnlItem() {
        initComponents();
       
    }
    public JPanel generatePanel()
    {
        return this;
    }
    void setBookDetails(String id,String isbn, String title, String author, String edition, String publisher, String pub_date, String cat, String language, String price, String quantity,String restQuantity, String summary,byte[] img)
    {
        bookId=id;
        lblContent.setText("<html><body style='font-family: Arial'>"
                + "<h1>"+title+"</h1><hr>"
                + "<h3>Price&nbsp; <strong> ₹"+price+"</storng></h3><br>"
                + "<strong>Author&nbsp;&nbsp; </strong>"+author+"<br>"
                + "<h3>Highlights</h3>"
                        + "<ul>"
                        + "<li><strong>Language :</strong>&nbsp;"+language+"</strong></li>"
                        + "<li><strong>Category :</strong>&nbsp;"+cat+"</li>"
                        + "<li><strong>ISBN :</strong>&nbsp;"+isbn+"</li>"
                        + "<li><strong>Edition :</strong>&nbsp;"+edition+"</li>"
                        + "<li><strong>Publisher :</strong>&nbsp;"+publisher+"</li>"
                        + "<li><strong>Publication Data :</strong>&nbsp;"+pub_date+"</li>"
                        + "</ul>"
                + "<h3>Description</h3>&nbsp;&nbsp;&nbsp; "+summary+"<br>"
                + "<strong>Total Books :</strong>&nbsp;"+quantity+"<br>"
                + "<strong>Stock of Book :</strong>&nbsp;"+restQuantity+"<br>"
                + "</body></html>");
        
        //seting image
                    ImageIcon image = new ImageIcon(img);
                    Image im = image.getImage();
                    Image myImg = im.getScaledInstance(322,350,Image.SCALE_SMOOTH);
                    ImageIcon newImage = new ImageIcon(myImg);
                    lblBookImg.setIcon(newImage);
        
    }
    
    void panelMouseEnter(JPanel pnl,JLabel lbl,String path)
    {
        pnl.setBackground(new Color(45,118,232));
        lbl.setForeground(Color.white);
        lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource(path)));
    }
    void panelMouseExit(JPanel pnl,JLabel lbl,String path)
    {
        pnl.setBackground(new Color(247,247,247));
        lbl.setForeground(Color.black);
        lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource(path)));
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblBookImg = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        lblContent = new javax.swing.JLabel();
        pnlBtnRefresh2 = new javax.swing.JPanel();
        lblRefresh2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(254, 254, 254));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        setPreferredSize(new java.awt.Dimension(668, 428));

        jPanel1.setBackground(new java.awt.Color(254, 254, 254));

        lblBookImg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBookImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/addBook_B.png"))); // NOI18N
        lblBookImg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBookImg, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBookImg, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(254, 254, 254));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblContent)
                .addContainerGap(349, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblContent)
                .addContainerGap(360, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel2);

        pnlBtnRefresh2.setBackground(new java.awt.Color(248, 247, 247));
        pnlBtnRefresh2.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlBtnRefresh2.setToolTipText("Add User");
        pnlBtnRefresh2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnRefresh2MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlBtnRefresh2MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlBtnRefresh2MouseEntered(evt);
            }
        });

        lblRefresh2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblRefresh2.setForeground(new java.awt.Color(1, 1, 1));
        lblRefresh2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRefresh2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/refresh_B.png"))); // NOI18N
        lblRefresh2.setText("Modify");
        lblRefresh2.setToolTipText("Click here to refresh table data");
        lblRefresh2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRefresh2MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblRefresh2MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblRefresh2MouseEntered(evt);
            }
        });

        javax.swing.GroupLayout pnlBtnRefresh2Layout = new javax.swing.GroupLayout(pnlBtnRefresh2);
        pnlBtnRefresh2.setLayout(pnlBtnRefresh2Layout);
        pnlBtnRefresh2Layout.setHorizontalGroup(
            pnlBtnRefresh2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblRefresh2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlBtnRefresh2Layout.setVerticalGroup(
            pnlBtnRefresh2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblRefresh2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBtnRefresh2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBtnRefresh2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void pnlBtnRefresh2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnRefresh2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnRefresh2MouseEntered

    private void pnlBtnRefresh2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnRefresh2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnRefresh2MouseExited

    private void pnlBtnRefresh2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnRefresh2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnRefresh2MouseClicked

    private void lblRefresh2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRefresh2MouseEntered
        panelMouseEnter(pnlBtnRefresh2, lblRefresh2,"/Icons/refresh_W.png");        // TODO add your handling code here:
    }//GEN-LAST:event_lblRefresh2MouseEntered

    private void lblRefresh2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRefresh2MouseExited
        panelMouseExit(pnlBtnRefresh2, lblRefresh2,"/Icons/refresh_B.png");
    }//GEN-LAST:event_lblRefresh2MouseExited

    private void lblRefresh2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRefresh2MouseClicked

    }//GEN-LAST:event_lblRefresh2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBookImg;
    private javax.swing.JLabel lblContent;
    private javax.swing.JLabel lblRefresh2;
    private javax.swing.JPanel pnlBtnRefresh2;
    // End of variables declaration//GEN-END:variables
}



/*


package librarymanagementsystem;

import java.sql.ResultSet;
import java.awt.Color;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ManageBook extends javax.swing.JPanel {

    
    LinkedList<JPanel> pnlData;
    boolean btnRefreshClick=false,colorFlag=false;
    BusinessLogic businessLogic;
    ResultSet rs;
    int totalBooks=0;
    
    
    public ManageBook(Dashboard dashboard) {
        initComponents();
        businessLogic=new BusinessLogic();
        
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(40);
    }

   void panelMouseEnter(JPanel pnl,JLabel lbl,String path)
    {
        pnl.setBackground(new Color(45,118,232));
        lbl.setForeground(Color.white);
        lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource(path)));
    }
    void panelMouseExit(JPanel pnl,JLabel lbl,String path)
    {
        pnl.setBackground(new Color(247,247,247));
        lbl.setForeground(Color.black);
        lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource(path)));
    }
    JPanel createPanel(ResultSet rs)
    {
        JPanel pnl = null;
        try 
        {
            PnlItem pnlItem=new PnlItem();
            pnlItem.setBookDetails(String.valueOf(rs.getInt(1)),rs.getString(2) ,rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11),rs.getString(12),rs.getString(13),rs.getBytes(14));
            pnl=pnlItem;
            pnl.setPreferredSize(new java.awt.Dimension(pnlMainContent.getWidth(), 444));
            
            
        } 
        catch (SQLException ex) 
        {
            
        }
        
        return pnl;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        pnlHead = new javax.swing.JPanel();
        pnlBtnRefresh2 = new javax.swing.JPanel();
        lblRefresh2 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        lblSearch = new javax.swing.JLabel();
        lblLoading = new javax.swing.JLabel();
        btnSearching = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlMainContent = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(615, 359));

        pnlHead.setBackground(new java.awt.Color(254, 254, 254));

        pnlBtnRefresh2.setBackground(new java.awt.Color(248, 247, 247));
        pnlBtnRefresh2.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlBtnRefresh2.setToolTipText("Add User");
        pnlBtnRefresh2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnRefresh2MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlBtnRefresh2MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlBtnRefresh2MouseEntered(evt);
            }
        });

        lblRefresh2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblRefresh2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRefresh2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/refresh_B.png"))); // NOI18N
        lblRefresh2.setText("Refresh Table Data");
        lblRefresh2.setToolTipText("Click here to refresh table data");
        lblRefresh2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRefresh2MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblRefresh2MouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblRefresh2MouseEntered(evt);
            }
        });

        javax.swing.GroupLayout pnlBtnRefresh2Layout = new javax.swing.GroupLayout(pnlBtnRefresh2);
        pnlBtnRefresh2.setLayout(pnlBtnRefresh2Layout);
        pnlBtnRefresh2Layout.setHorizontalGroup(
            pnlBtnRefresh2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblRefresh2, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        pnlBtnRefresh2Layout.setVerticalGroup(
            pnlBtnRefresh2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBtnRefresh2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblRefresh2))
        );

        txtSearch.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        txtSearch.setToolTipText("Search Genre by name");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        lblSearch.setText("Search by Name :");

        btnSearching.setBackground(new java.awt.Color(45, 118, 232));
        btnSearching.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search.png"))); // NOI18N
        btnSearching.setBorderPainted(false);
        btnSearching.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlHeadLayout = new javax.swing.GroupLayout(pnlHead);
        pnlHead.setLayout(pnlHeadLayout);
        pnlHeadLayout.setHorizontalGroup(
            pnlHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBtnRefresh2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(lblLoading)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addComponent(lblSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnSearching, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlHeadLayout.setVerticalGroup(
            pnlHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeadLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(pnlHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSearching, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlBtnRefresh2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSearch)
                        .addComponent(lblLoading, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setAutoscrolls(true);

        pnlMainContent.setBackground(new java.awt.Color(254, 254, 254));

        javax.swing.GroupLayout pnlMainContentLayout = new javax.swing.GroupLayout(pnlMainContent);
        pnlMainContent.setLayout(pnlMainContentLayout);
        pnlMainContentLayout.setHorizontalGroup(
            pnlMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 613, Short.MAX_VALUE)
        );
        pnlMainContentLayout.setVerticalGroup(
            pnlMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 304, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(pnlMainContent);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHead, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlHead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1))
        );
    }// </editor-fold>                        

    private void lblRefresh2MouseClicked(java.awt.event.MouseEvent evt) {                                         
        try {
            totalBooks=0;
            pnlData=new LinkedList<>();
            btnRefreshClick=true;
            rs=businessLogic.fetchBooks();
            while (rs.next())
            {
                    pnlData.add(totalBooks,createPanel(rs));
                    pnlMainContent.add(pnlData.get(totalBooks));
                    totalBooks++;
            }
            businessLogic.disconnectNow();
            
                
        } catch (SQLException ex) {
            Logger.getLogger(ManageBook.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                        

    private void lblRefresh2MouseExited(java.awt.event.MouseEvent evt) {                                        
        panelMouseExit(pnlBtnRefresh2, lblRefresh2,"/Icons/refresh_B.png");
    }                                       

    private void lblRefresh2MouseEntered(java.awt.event.MouseEvent evt) {                                         
        panelMouseEnter(pnlBtnRefresh2, lblRefresh2,"/Icons/refresh_W.png");        // TODO add your handling code here:
    }                                        

    private void pnlBtnRefresh2MouseClicked(java.awt.event.MouseEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void pnlBtnRefresh2MouseExited(java.awt.event.MouseEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void pnlBtnRefresh2MouseEntered(java.awt.event.MouseEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {                                      
        // TODO add your handling code here:
    }                                     

    private void btnSearchingActionPerformed(java.awt.event.ActionEvent evt) {                                             
          // TODO add your handling code here:
    }                                            


    // Variables declaration - do not modify                     
    private javax.swing.JButton btnSearching;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLoading;
    private javax.swing.JLabel lblRefresh2;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JPanel pnlBtnRefresh2;
    private javax.swing.JPanel pnlHead;
    private javax.swing.JPanel pnlMainContent;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration                   
}

*/
