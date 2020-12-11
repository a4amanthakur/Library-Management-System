/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.awt.Color;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author thakur-huni
 */
public class User extends javax.swing.JPanel {

    /**
     * Creates new form User
     */
    private String username;
    private boolean profileSts=false;
    private ResultSet rs;
    private BusMethodInterface busLogic;
    Dashboard dashboard;
    public User(String username,Dashboard dashboard) throws SQLException {
        initComponents();
        this.dashboard=dashboard;   
        this.username=username;
        busLogic=new BusinessLogic();
        fetchUserDetails(username);
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(40);
    }
    
    public void fetchUserDetails(String username) 
    {
        if(profileSts==false)
        {
            try {
                profileSts=true;
                rs=busLogic.fetchUser(username,"Admin");
                
                if(rs.next())
                {
                    txtId.setText(rs.getString(1));
                    txtName.setText(rs.getString(2));
                    txtusername.setText(rs.getString(3));
                    txtUserType.setText(rs.getString(5));
                    txtGender.setText(rs.getString(6));
                    txtMoible.setText(rs.getString(7));
                    txtMailId.setText(rs.getString(8));
                    txtSts.setText(rs.getString(9));
                    //fetching img
                    byte[] img = rs.getBytes("image");
                    //Resize The ImageIcon
                    
                    ImageIcon image = new ImageIcon(img);
                    Image im = image.getImage();
                    
                    Image myImg = im.getScaledInstance(178,162,Image.SCALE_SMOOTH);
                    ImageIcon newImage = new ImageIcon(myImg);
                    lblUserImg.setIcon(newImage);
                    busLogic.disconnectNow();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database not connected.");
            }
        }
        else
        {
            System.out.println("hii else");
        }
        
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

        jScrollPane1 = new javax.swing.JScrollPane();
        pnlMain = new javax.swing.JPanel();
        lblID = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        lblName = new javax.swing.JLabel();
        txtusername = new javax.swing.JTextField();
        lblUsername = new javax.swing.JLabel();
        lblGender = new javax.swing.JLabel();
        txtGender = new javax.swing.JTextField();
        txtMailId = new javax.swing.JTextField();
        lblGender1 = new javax.swing.JLabel();
        pnlImage = new javax.swing.JPanel();
        lblUserImg = new javax.swing.JLabel();
        lblGender2 = new javax.swing.JLabel();
        txtUserType = new javax.swing.JTextField();
        txtSts = new javax.swing.JTextField();
        lblGender3 = new javax.swing.JLabel();
        pnlAdvance = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMoible = new javax.swing.JTextField();
        lblGender4 = new javax.swing.JLabel();
        pnlBtnAllClear = new javax.swing.JPanel();
        lblLogout = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();

        setBackground(new java.awt.Color(254, 254, 254));
        setBorder(null);
        setAutoscrolls(true);
        setPreferredSize(new java.awt.Dimension(590, 352));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setAutoscrolls(true);

        pnlMain.setBackground(new java.awt.Color(254, 254, 254));
        pnlMain.setBorder(null);

        lblID.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblID.setText("ID");

        txtId.setEditable(false);
        txtId.setBorder(null);
        txtId.setOpaque(true);

        txtName.setEditable(false);
        txtName.setBorder(null);
        txtName.setOpaque(true);

        lblName.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblName.setText("Name");

        txtusername.setEditable(false);
        txtusername.setBorder(null);
        txtusername.setOpaque(true);

        lblUsername.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblUsername.setText("Username");

        lblGender.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblGender.setText("Gender");

        txtGender.setEditable(false);
        txtGender.setBorder(null);
        txtGender.setOpaque(true);

        txtMailId.setEditable(false);
        txtMailId.setBorder(null);
        txtMailId.setOpaque(true);

        lblGender1.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblGender1.setText("Mail-ID");

        javax.swing.GroupLayout pnlImageLayout = new javax.swing.GroupLayout(pnlImage);
        pnlImage.setLayout(pnlImageLayout);
        pnlImageLayout.setHorizontalGroup(
            pnlImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblUserImg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pnlImageLayout.setVerticalGroup(
            pnlImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblUserImg, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        lblGender2.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblGender2.setText("User Type");

        txtUserType.setEditable(false);
        txtUserType.setBorder(null);
        txtUserType.setOpaque(true);

        txtSts.setEditable(false);
        txtSts.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtSts.setOpaque(true);

        lblGender3.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblGender3.setText("Your Status");

        pnlAdvance.setBackground(new java.awt.Color(248, 247, 247));
        pnlAdvance.setBorder(javax.swing.BorderFactory.createTitledBorder("Advance Settings"));

        jLabel1.setText("Change Password");

        javax.swing.GroupLayout pnlAdvanceLayout = new javax.swing.GroupLayout(pnlAdvance);
        pnlAdvance.setLayout(pnlAdvanceLayout);
        pnlAdvanceLayout.setHorizontalGroup(
            pnlAdvanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdvanceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlAdvanceLayout.setVerticalGroup(
            pnlAdvanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdvanceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtMoible.setEditable(false);
        txtMoible.setBorder(null);
        txtMoible.setOpaque(true);

        lblGender4.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblGender4.setText("Mobile");

        pnlBtnAllClear.setBackground(new java.awt.Color(248, 247, 247));
        pnlBtnAllClear.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlBtnAllClear.setToolTipText("Logout");
        pnlBtnAllClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnAllClearMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlBtnAllClearMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlBtnAllClearMouseEntered(evt);
            }
        });

        lblLogout.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        lblLogout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/logout_B.png"))); // NOI18N
        lblLogout.setText("Logout");
        lblLogout.setToolTipText("Logout");
        lblLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogoutMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblLogoutMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblLogoutMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout pnlBtnAllClearLayout = new javax.swing.GroupLayout(pnlBtnAllClear);
        pnlBtnAllClear.setLayout(pnlBtnAllClearLayout);
        pnlBtnAllClearLayout.setHorizontalGroup(
            pnlBtnAllClearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlBtnAllClearLayout.setVerticalGroup(
            pnlBtnAllClearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMainLayout.createSequentialGroup()
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblGender)
                            .addComponent(lblGender2)
                            .addComponent(lblGender3)
                            .addComponent(lblGender1)
                            .addComponent(lblGender4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtGender)
                            .addComponent(txtMailId)
                            .addComponent(txtUserType)
                            .addComponent(txtSts, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                            .addComponent(txtMoible)
                            .addComponent(pnlBtnAllClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMainLayout.createSequentialGroup()
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblName)
                            .addComponent(lblID)
                            .addComponent(lblUsername))
                        .addGap(26, 26, 26)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtusername)
                            .addComponent(txtId))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAdvance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblID))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblName)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblUsername)
                            .addComponent(txtusername, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblGender)
                            .addComponent(txtGender, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMoible, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGender4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMailId, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGender1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUserType, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGender2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSts, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGender3)))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(pnlImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlAdvance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlBtnAllClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(pnlMain);

        lblTitle.setFont(new java.awt.Font("Ubuntu", 1, 25)); // NOI18N
        lblTitle.setText("Profile");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void pnlBtnAllClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAllClearMouseClicked
       
    }//GEN-LAST:event_pnlBtnAllClearMouseClicked

    private void pnlBtnAllClearMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAllClearMouseExited
    }//GEN-LAST:event_pnlBtnAllClearMouseExited

    private void pnlBtnAllClearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAllClearMouseEntered
           
    }//GEN-LAST:event_pnlBtnAllClearMouseEntered

    private void lblLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoutMouseClicked
         if(JOptionPane.showConfirmDialog(this, "Do you really want to Logout ?","Select an Option",1)==0)
      {
          profileSts=false;
           dashboard.logoutNow();
      }        // TODO add your handling code here:
    }//GEN-LAST:event_lblLogoutMouseClicked

    private void lblLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoutMouseEntered
         panelMouseEnter(pnlBtnAllClear,lblLogout,"/Icons/logout_W.png");        // TODO add your handling code here:
    }//GEN-LAST:event_lblLogoutMouseEntered

    private void lblLogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoutMouseExited
                panelMouseExit(pnlBtnAllClear,lblLogout,"/Icons/logout_B.png");
        // TODO add your handling code here:
    }//GEN-LAST:event_lblLogoutMouseExited
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblGender;
    private javax.swing.JLabel lblGender1;
    private javax.swing.JLabel lblGender2;
    private javax.swing.JLabel lblGender3;
    private javax.swing.JLabel lblGender4;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblLogout;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUserImg;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel pnlAdvance;
    private javax.swing.JPanel pnlBtnAllClear;
    private javax.swing.JPanel pnlImage;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JTextField txtGender;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtMailId;
    private javax.swing.JTextField txtMoible;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSts;
    private javax.swing.JTextField txtUserType;
    private javax.swing.JTextField txtusername;
    // End of variables declaration//GEN-END:variables
}
