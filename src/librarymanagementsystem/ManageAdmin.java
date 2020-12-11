/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author thakur-huni
 */
public class ManageAdmin extends javax.swing.JPanel implements Runnable{

    /**
     * Creates new form ManageAdmin
     */
    ButtonGroup radioBtnGrp;
    FileInputStream fin;
    File img;
    BusMethodInterface busLogic;
    boolean correctSts=false,mailSts=true,mobileSts=true,usernameSts=true,pwdSts=true,pwdCSts=true,fieldSts=false;
    StringBuilder pwd;
    ResultSet rs;
    DefaultTableModel model;
    String userId;
    String userType;
    Thread threadRefresh,threadDelete,threadUpdate,threadSearch;
    boolean btnRefreshClick=false,btnAddClick=false,btnDeleteClick=false,btnUpdateClick=false,btnSearchClick=false;
    int i,row,column;
    Object val[];
    public ManageAdmin() throws SQLException {
        initComponents();
        setRadioButton();
        pwd=new StringBuilder("");
        threadRefresh=null;
        threadDelete=null;
        threadUpdate=null;
        threadSearch=null;
        busLogic=new BusinessLogic();
        
        jScrollPane3.getVerticalScrollBar().setUnitIncrement(40);
        jScrollPane4.getVerticalScrollBar().setUnitIncrement(40);
    }
    
    void fetchAdminToTable() throws SQLException
    {
        if(threadRefresh==null)
          {
            btnRefreshClick=true;
            threadRefresh=new Thread(this);
            threadRefresh.start();
        }
        
        
    }
    void updateAdmin()
    {
        if(threadUpdate==null)
        {
            btnUpdateClick=true;
            threadUpdate=new Thread(this);
            threadUpdate.start();
        }
    }
    void deleteAdmin()
    {
        if(threadDelete==null)
        {
            btnDeleteClick=true;
            threadDelete=new Thread(this);
            threadDelete.start();
        }
    }
    void searchAdmin()
    {
        if(threadSearch==null)
        {
            btnSearchClick=true;
            threadSearch=new Thread(this);
            threadSearch.start();
        }
    }
    @Override
    public void run()
    {
        
      if(btnRefreshClick==true)
      {
          refreshTable();
      }
     
      if(btnUpdateClick==true)
      {
          lblBuffering.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gif/blackFastLoading.gif")));
          updateAdminNow();
          
      }
      if(btnDeleteClick==true)
      {
          lblBuffering.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gif/blackFastLoading.gif")));
          deleteAdminNow();
          
      }
      
      if(btnSearchClick==true)
      {
          btnSearching.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gif/blackFastLoading.gif")));
          btnSearching.setBackground(Color.white);
          searchAdminNow();
         
      }
      
     
    }
    
    void searchAdminNow()
    {
        btnSearchClick=false;
        if(!threadSearch.interrupted())
        {
                    try {
                   rs=busLogic.loadSearchData(txtSearch.getText(),"Admin");
                   model.setRowCount(0);
                   while (rs.next()) {
                       Object o[] = {rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5),rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9),rs.getString(10)};
                       model.addRow(o);
                   }
                   btnSearching.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search.png")));
                   btnSearching.setBackground(new Color(45,118,232));
                   busLogic.disconnectNow();
                   
                 
               } catch (SQLException ex) {
                   Logger.getLogger(ManageAdmin.class.getName()).log(Level.SEVERE, null, ex);
               }
        }
        threadSearch.interrupt();
        threadSearch=null;
        
    }
    void updateAdminNow()
    {
        btnUpdateClick=false;
        if(!threadUpdate.interrupted())
        {
            pwd.delete(0, pwd.length());
            String gender="Male";
            

            if(radioMale.isSelected())
            {
                gender="Male";
            }
            else if(radioFemale.isSelected())
            {
                gender="Female";
            }
            else if(radioOthers.isSelected())
            {
                gender="Others";
            }
            
            
            if(cmbUserType.getSelectedItem().equals("Admin"))
            {
                userType="Admin";
                
            }
            else
            {
                userType="User";
                
            }
            correctSts=busLogic.updateUser(userId,txtName.getText(),txtUsername.getText(),txtPassword2.getText(), userType , gender,txtMobile.getText(), txtMailID.getText(),lblUserSts.getSelectedItem().toString());
            if(correctSts==true)
            {
                 lblBuffering.setIcon(null);
                JOptionPane.showMessageDialog(this, txtName.getText()+" Updated successfully.");
               
                
                clearAllFields();
            }
        }
        threadUpdate.interrupt();
        threadUpdate=null;
        btnRefreshClick=true;
        try {
                    fetchAdminToTable();
                } catch (SQLException ex) {
                    Logger.getLogger(ManageAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    void deleteAdminNow()
    {
        btnDeleteClick=false;
        if(!threadDelete.interrupted())
        {
            correctSts=busLogic.deleteUser(userId);
                   if(correctSts==true)
                   {
                        lblBuffering.setIcon(null);
                       JOptionPane.showMessageDialog(this, txtName.getText()+" Deleted successfully.");
                       clearAllFields();
                      
                   }
        }
        threadDelete.interrupt();
        threadDelete=null;
        btnRefreshClick=true;
        try {
            fetchAdminToTable();
        } catch (SQLException ex) {
            Logger.getLogger(ManageAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void refreshTable()
    {
        btnRefreshClick=false;
        if(!threadRefresh.interrupted())
        {
            lblLoading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gif/blackFastLoading.gif")));
            try {
                rs=busLogic.fetchUser("Admin");
                model=(DefaultTableModel)userTable.getModel();
                model.setRowCount(0);
                while (rs.next()) {
                    Object o[] = {rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5),rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9),rs.getString(10)};
                    model.addRow(o);
                }
                busLogic.disconnectNow();
                threadRefresh.interrupt();
                threadRefresh=null;
                lblLoading.setIcon(null);
            } catch (SQLException ex) {
                Logger.getLogger(ManageAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
 private void setRadioButton()
    {
        radioBtnGrp=new ButtonGroup();
        radioBtnGrp.add(radioMale);
        radioBtnGrp.add(radioFemale);
        radioBtnGrp.add(radioOthers);
    }
 private boolean validateFields()
     {
        if(isAllFieldFilled()==true)
        {
            correctSts=false;
            try
            {
                
                    
                    if(txtName.getText().length()<=0)
                    {
                        JOptionPane.showMessageDialog(this,"Error: Please enter your full name.");
                        txtName.grabFocus();
                        correctSts=false;
                        throw new MyExpection("");
                    }


                    if(mobileSts==false)
                    {
                        JOptionPane.showMessageDialog(this,"Error: Please enter valid mobile number.");
                        correctSts=false;
                        txtMobile.grabFocus();
                        throw new MyExpection("");
                    }
                    else
                    {
                        if(!busLogic.checkValidMobile(userId,txtMobile.getText()))
                        {
                            correctSts=false;
                            txtMobile.grabFocus();
                            
                            lblMobileAuth2.setText("<html><body><strong>*Error: Mobile number already exist.</strong></body></html>");
                            throw new MyExpection("");
                        }
                        else
                        {
                            correctSts=true;
                            lblMobileAuth2.setText("");
                        } 
                    }


                    if( mailSts==false)
                    {

                        JOptionPane.showMessageDialog(this,"Error: Please enter valid mail address.");
                        correctSts=false;
                        txtMailID.grabFocus();
                        throw new MyExpection("");
                    }
                    else
                    {
                        correctSts=false;
                        if(!busLogic.checkValidMail(userId, txtMailID.getText()))
                        {
                            correctSts=false;
                            txtMailID.grabFocus();
                            lblEmailAuth2.setText("<html><body><strong>*Error: Mail-ID already exist.</strong></body></html>");
                            throw new MyExpection("");
                        }
                        else
                        {
                            correctSts=true;
                            lblMobileAuth2.setText("");
                        } 
                    }
                  
                    if(cmbUserType.getSelectedItem().toString().equals("--Select--") )
                    {
                        JOptionPane.showMessageDialog(this,"Error: Please select user type (i.e. Admin / Normal user).");
                        correctSts=false;
                        cmbUserType.grabFocus();
                        throw new MyExpection("");
                    }
                    
                    if( usernameSts==false)
                    {
                        JOptionPane.showMessageDialog(this,"Error: Please enter new username.");
                        correctSts=false;
                        txtUsername.grabFocus();
                        throw new MyExpection("");
                    }
                    else
                    {
                        if(!busLogic.checkValidUsername(userId,txtUsername.getText()))
                        {
                            correctSts=false;
                            txtUsername.grabFocus();
                          lblUsernameAuth.setText("<html><body><strong>*Error: Username already exist.</strong></body></html>");
                          throw new MyExpection("");
                        }
                        else
                        {
                          correctSts=true;
                          lblUsernameAuth.setText("");

                        } 
                    }

                    if(cmbUserType.getSelectedItem().equals("Admin"))
                    {
                        if(txtPassword2.getText().length()<=0  || pwdSts==false)
                         {
                             JOptionPane.showMessageDialog(this,"Error: Please enter new password.");
                             correctSts=false;
                             txtPassword2.grabFocus();
                             throw new MyExpection("");
                         }
                         else if(txtConfirmPassword.getText().length()<=0  || pwdCSts==false)
                         {
                            JOptionPane.showMessageDialog(this,"Error: Please confirm new password.");
                             txtConfirmPassword.grabFocus();
                            correctSts=false;
                            throw new MyExpection("");
                        }
                        else
                         {
                             correctSts=true;
                         }
                    }

                    else
                    {
                        correctSts=true;
                        pnlBtnUpdateUser.grabFocus();
                    }
            }//end of try
            catch(MyExpection e)
            {
                    
            }
        }
        else
        {
            correctSts=false;
        }
        return correctSts;
    }
    public boolean isAllFieldFilled()
    {
        try
        {
            fieldSts=true;
            if(userId==null)
                    {
                        JOptionPane.showMessageDialog(this,"Error: Please select row first from table.");
                        userTable.grabFocus();
                        fieldSts=false;
                        throw new MyExpection("");
                    }
            if(txtName.getText().length()<=0)
            {
                fieldSts=false;
                JOptionPane.showMessageDialog(this, "Please enter your name.");
                txtName.grabFocus();
                throw new MyExpection("");
            }
            if(txtMobile.getText().length()<=0)
            {
                fieldSts=false;
                JOptionPane.showMessageDialog(this, "Please enter your mobile number.");
                txtMobile.grabFocus();
                throw new MyExpection("");
            }
            if(txtMailID.getText().length()<=0)
            {
                fieldSts=false;
                 JOptionPane.showMessageDialog(this, "Please enter your mail-ID.");
                 txtMailID.grabFocus();
                throw new MyExpection("");
            }
           
            if(cmbUserType.getSelectedItem().equals("--Select--"))
            {
                fieldSts=false;
                 JOptionPane.showMessageDialog(this, "Please select user-type(Admin / Normal User).");
                 cmbUserType.grabFocus();
                 throw new MyExpection("");
            }
            if(txtUsername.getText().length()<=0)
            {
                fieldSts=false;
                 JOptionPane.showMessageDialog(this, "Please enter your username.");
                txtUsername.grabFocus();
                 throw new MyExpection("");
            }
            if(txtPassword2.getText().length()<=0)
            {
                fieldSts=false;
                 JOptionPane.showMessageDialog(this, "Please enter new password.");
                txtPassword2.grabFocus();
                 throw new MyExpection("");
            }
            if(txtConfirmPassword.getText().length()<=0)
            {
                fieldSts=false;
                 JOptionPane.showMessageDialog(this, "Please enter confirm password.");
                txtConfirmPassword.grabFocus();
                 throw new MyExpection("");
            }
        }
        catch(MyExpection e)
        {
            
        }
        return fieldSts;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        pnlControls2 = new javax.swing.JPanel();
        pnlPersonalDetailsSec2 = new javax.swing.JPanel();
        lblName2 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblName10 = new javax.swing.JLabel();
        txtMobile = new javax.swing.JTextField();
        lblName11 = new javax.swing.JLabel();
        radioMale = new javax.swing.JRadioButton();
        radioFemale = new javax.swing.JRadioButton();
        lblName12 = new javax.swing.JLabel();
        txtMailID = new javax.swing.JTextField();
        radioOthers = new javax.swing.JRadioButton();
        lblPasswordSts3 = new javax.swing.JLabel();
        lblEmailAuth2 = new javax.swing.JLabel();
        lblMobileAuth2 = new javax.swing.JLabel();
        pnlImgSec = new javax.swing.JPanel();
        lblStsImg = new javax.swing.JLabel();
        lblUserSts = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtConfirmPassword = new javax.swing.JPasswordField();
        lblName3 = new javax.swing.JLabel();
        cmbUserType = new javax.swing.JComboBox<>();
        lblName13 = new javax.swing.JLabel();
        lblName14 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPasswordSts = new javax.swing.JLabel();
        lblUsernameAuth = new javax.swing.JLabel();
        txtPassword2 = new javax.swing.JPasswordField();
        lblName15 = new javax.swing.JLabel();
        lblConfirmPwdSts = new javax.swing.JLabel();
        pnlBtnUpdateUser = new javax.swing.JPanel();
        lblUpdateUser = new javax.swing.JLabel();
        pnlBtnAllClear = new javax.swing.JPanel();
        lblAllClear = new javax.swing.JLabel();
        pnlDeleteUser = new javax.swing.JPanel();
        lblDeleteUser = new javax.swing.JLabel();
        lblBuffering = new javax.swing.JLabel();
        pnlHeadBtn = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnSearching = new javax.swing.JButton();
        pnlBtnRefresh = new javax.swing.JPanel();
        lblRefresh = new javax.swing.JLabel();
        lblLoading = new javax.swing.JLabel();

        setBackground(new java.awt.Color(254, 254, 254));
        setPreferredSize(new java.awt.Dimension(615, 359));

        userTable.setAutoCreateRowSorter(true);
        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "USERNAME", "PASSWORD", "USER-TYPE", "GENDER", "MOBILE", "MAIL-ID", "USER QUALITY"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        userTable.setToolTipText("Click on any row to select/ edit user.");
        userTable.setRowHeight(20);
        userTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(userTable);

        jPanel1.setBackground(new java.awt.Color(254, 254, 254));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Selected Row Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setAutoscrolls(true);

        pnlControls2.setBackground(new java.awt.Color(254, 254, 254));

        pnlPersonalDetailsSec2.setBackground(new java.awt.Color(254, 254, 254));
        pnlPersonalDetailsSec2.setBorder(null);
        pnlPersonalDetailsSec2.setMaximumSize(new java.awt.Dimension(100, 100));

        lblName2.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblName2.setText("Name");

        txtName.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNameKeyReleased(evt);
            }
        });

        lblName10.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblName10.setText("Mobile");

        txtMobile.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        txtMobile.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMobileKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMobileKeyReleased(evt);
            }
        });

        lblName11.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblName11.setText("Gender");

        radioMale.setBackground(new java.awt.Color(254, 254, 254));
        radioMale.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        radioMale.setSelected(true);
        radioMale.setText("Male");

        radioFemale.setBackground(new java.awt.Color(254, 254, 254));
        radioFemale.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        radioFemale.setText("Female");

        lblName12.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblName12.setText("Mail-ID");

        txtMailID.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        txtMailID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMailIDKeyReleased(evt);
            }
        });

        radioOthers.setBackground(new java.awt.Color(254, 254, 254));
        radioOthers.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        radioOthers.setText("Others");
        radioOthers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioOthersActionPerformed(evt);
            }
        });

        lblPasswordSts3.setForeground(new java.awt.Color(241, 43, 21));

        lblEmailAuth2.setForeground(new java.awt.Color(241, 43, 21));

        lblMobileAuth2.setForeground(new java.awt.Color(241, 43, 21));

        javax.swing.GroupLayout pnlPersonalDetailsSec2Layout = new javax.swing.GroupLayout(pnlPersonalDetailsSec2);
        pnlPersonalDetailsSec2.setLayout(pnlPersonalDetailsSec2Layout);
        pnlPersonalDetailsSec2Layout.setHorizontalGroup(
            pnlPersonalDetailsSec2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPersonalDetailsSec2Layout.createSequentialGroup()
                .addGroup(pnlPersonalDetailsSec2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblName2)
                    .addComponent(lblName11)
                    .addComponent(lblName10))
                .addGap(10, 10, 10)
                .addGroup(pnlPersonalDetailsSec2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPersonalDetailsSec2Layout.createSequentialGroup()
                        .addComponent(radioMale, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioFemale, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioOthers, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                    .addComponent(txtMobile)
                    .addComponent(txtName)))
            .addGroup(pnlPersonalDetailsSec2Layout.createSequentialGroup()
                .addComponent(lblName12)
                .addGap(10, 10, 10)
                .addGroup(pnlPersonalDetailsSec2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMailID)
                    .addGroup(pnlPersonalDetailsSec2Layout.createSequentialGroup()
                        .addGroup(pnlPersonalDetailsSec2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMobileAuth2)
                            .addComponent(lblEmailAuth2))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(pnlPersonalDetailsSec2Layout.createSequentialGroup()
                .addGap(192, 192, 192)
                .addComponent(lblPasswordSts3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlPersonalDetailsSec2Layout.setVerticalGroup(
            pnlPersonalDetailsSec2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPersonalDetailsSec2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlPersonalDetailsSec2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName2)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPersonalDetailsSec2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblName11)
                    .addGroup(pnlPersonalDetailsSec2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radioMale)
                        .addComponent(radioFemale)
                        .addComponent(radioOthers)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlPersonalDetailsSec2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName10)
                    .addComponent(txtMobile, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMobileAuth2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPersonalDetailsSec2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName12)
                    .addComponent(txtMailID, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmailAuth2)
                .addGap(0, 0, 0)
                .addComponent(lblPasswordSts3)
                .addContainerGap())
        );

        pnlImgSec.setBackground(new java.awt.Color(254, 254, 254));
        pnlImgSec.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "User Quality", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        lblStsImg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStsImg.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblUserSts.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Good", "Bad", "Defaulter" }));

        javax.swing.GroupLayout pnlImgSecLayout = new javax.swing.GroupLayout(pnlImgSec);
        pnlImgSec.setLayout(pnlImgSecLayout);
        pnlImgSecLayout.setHorizontalGroup(
            pnlImgSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlImgSecLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblStsImg, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                .addGap(20, 20, 20))
            .addComponent(lblUserSts, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlImgSecLayout.setVerticalGroup(
            pnlImgSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlImgSecLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStsImg, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblUserSts, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(248, 247, 247));
        jPanel2.setBorder(null);

        txtConfirmPassword.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        txtConfirmPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtConfirmPasswordKeyReleased(evt);
            }
        });

        lblName3.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblName3.setText("Password");

        cmbUserType.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        cmbUserType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select--", "Admin", "Normal User" }));
        cmbUserType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbUserTypeItemStateChanged(evt);
            }
        });
        cmbUserType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbUserTypeActionPerformed(evt);
            }
        });

        lblName13.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblName13.setText("Username");

        lblName14.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblName14.setText("User-type");

        txtUsername.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        txtUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUsernameKeyReleased(evt);
            }
        });

        lblPasswordSts.setForeground(new java.awt.Color(241, 43, 21));

        lblUsernameAuth.setForeground(new java.awt.Color(241, 43, 21));
        lblUsernameAuth.setToolTipText("");

        txtPassword2.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        txtPassword2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPassword2KeyReleased(evt);
            }
        });

        lblName15.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblName15.setText("Confirm");

        lblConfirmPwdSts.setForeground(new java.awt.Color(241, 43, 21));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblName14)
                        .addGap(18, 18, 18)
                        .addComponent(cmbUserType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblName13)
                        .addGap(19, 19, 19)
                        .addComponent(txtUsername))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblName3)
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtPassword2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblName15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUsernameAuth)
                                    .addComponent(lblPasswordSts))
                                .addGap(256, 256, 256)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblConfirmPwdSts)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtConfirmPassword))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbUserType, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName14))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName13))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblName3)
                            .addComponent(txtConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblName15)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUsernameAuth)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblPasswordSts)
                        .addGap(13, 13, 13))
                    .addComponent(lblConfirmPwdSts, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        pnlBtnUpdateUser.setBackground(new java.awt.Color(248, 247, 247));
        pnlBtnUpdateUser.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlBtnUpdateUser.setToolTipText("Add User");
        pnlBtnUpdateUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnUpdateUserMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlBtnUpdateUserMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlBtnUpdateUserMouseEntered(evt);
            }
        });

        lblUpdateUser.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        lblUpdateUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUpdateUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/update_B.png"))); // NOI18N
        lblUpdateUser.setText("Update");

        javax.swing.GroupLayout pnlBtnUpdateUserLayout = new javax.swing.GroupLayout(pnlBtnUpdateUser);
        pnlBtnUpdateUser.setLayout(pnlBtnUpdateUserLayout);
        pnlBtnUpdateUserLayout.setHorizontalGroup(
            pnlBtnUpdateUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblUpdateUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlBtnUpdateUserLayout.setVerticalGroup(
            pnlBtnUpdateUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblUpdateUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlBtnAllClear.setBackground(new java.awt.Color(248, 247, 247));
        pnlBtnAllClear.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlBtnAllClear.setToolTipText("Add User");
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

        lblAllClear.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        lblAllClear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAllClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/cancel_B.png"))); // NOI18N
        lblAllClear.setText("Cancel");

        javax.swing.GroupLayout pnlBtnAllClearLayout = new javax.swing.GroupLayout(pnlBtnAllClear);
        pnlBtnAllClear.setLayout(pnlBtnAllClearLayout);
        pnlBtnAllClearLayout.setHorizontalGroup(
            pnlBtnAllClearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAllClear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlBtnAllClearLayout.setVerticalGroup(
            pnlBtnAllClearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBtnAllClearLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAllClear)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlDeleteUser.setBackground(new java.awt.Color(248, 247, 247));
        pnlDeleteUser.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlDeleteUser.setToolTipText("Add User");
        pnlDeleteUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlDeleteUserMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlDeleteUserMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlDeleteUserMouseEntered(evt);
            }
        });

        lblDeleteUser.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        lblDeleteUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDeleteUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/delete_B.png"))); // NOI18N
        lblDeleteUser.setText("Delete");

        javax.swing.GroupLayout pnlDeleteUserLayout = new javax.swing.GroupLayout(pnlDeleteUser);
        pnlDeleteUser.setLayout(pnlDeleteUserLayout);
        pnlDeleteUserLayout.setHorizontalGroup(
            pnlDeleteUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDeleteUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlDeleteUserLayout.setVerticalGroup(
            pnlDeleteUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDeleteUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        lblBuffering.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnlControls2Layout = new javax.swing.GroupLayout(pnlControls2);
        pnlControls2.setLayout(pnlControls2Layout);
        pnlControls2Layout.setHorizontalGroup(
            pnlControls2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControls2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlControls2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlControls2Layout.createSequentialGroup()
                        .addComponent(pnlPersonalDetailsSec2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(pnlImgSec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlControls2Layout.createSequentialGroup()
                        .addComponent(pnlBtnAllClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(80, 80, 80)
                        .addComponent(pnlBtnUpdateUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(lblBuffering, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(pnlDeleteUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pnlControls2Layout.setVerticalGroup(
            pnlControls2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlControls2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlControls2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlImgSec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlPersonalDetailsSec2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlControls2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlBtnUpdateUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlBtnAllClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDeleteUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblBuffering, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(82, Short.MAX_VALUE))
        );

        jScrollPane4.setViewportView(pnlControls2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        pnlHeadBtn.setBackground(new java.awt.Color(254, 254, 254));

        txtSearch.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        txtSearch.setToolTipText("Search by  Username");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jLabel1.setText("Search by Username:");

        btnSearching.setBackground(new java.awt.Color(45, 118, 232));
        btnSearching.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search.png"))); // NOI18N
        btnSearching.setBorderPainted(false);
        btnSearching.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlHeadBtnLayout = new javax.swing.GroupLayout(pnlHeadBtn);
        pnlHeadBtn.setLayout(pnlHeadBtnLayout);
        pnlHeadBtnLayout.setHorizontalGroup(
            pnlHeadBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeadBtnLayout.createSequentialGroup()
                .addContainerGap(74, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnSearching, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlHeadBtnLayout.setVerticalGroup(
            pnlHeadBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeadBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHeadBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHeadBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(btnSearching, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pnlBtnRefresh.setBackground(new java.awt.Color(248, 247, 247));
        pnlBtnRefresh.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlBtnRefresh.setToolTipText("Add User");
        pnlBtnRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnRefreshMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlBtnRefreshMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlBtnRefreshMouseEntered(evt);
            }
        });

        lblRefresh.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        lblRefresh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/refresh_B.png"))); // NOI18N
        lblRefresh.setText("Refresh Table");
        lblRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRefreshMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblRefreshMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblRefreshMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout pnlBtnRefreshLayout = new javax.swing.GroupLayout(pnlBtnRefresh);
        pnlBtnRefresh.setLayout(pnlBtnRefreshLayout);
        pnlBtnRefreshLayout.setHorizontalGroup(
            pnlBtnRefreshLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblRefresh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
        );
        pnlBtnRefreshLayout.setVerticalGroup(
            pnlBtnRefreshLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBtnRefreshLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblRefresh)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBtnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(lblLoading)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlHeadBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlHeadBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlBtnRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblLoading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameKeyReleased

    private void txtMobileKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMobileKeyPressed

    }//GEN-LAST:event_txtMobileKeyPressed

    private void txtMobileKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMobileKeyReleased

        if(txtMobile.getText().length()>10 ||txtMobile.getText().length()<10 || !Pattern.matches("[0123456789]+", txtMobile.getText()))
        {
            mobileSts=false;
            lblMobileAuth2.setText("<html><body><strong>*Error: Please enter a valid number (10 Digits only).</strong></body></html>");
        }
        else
        {

            mobileSts=true;
            lblMobileAuth2.setText("");
          
        }

    }//GEN-LAST:event_txtMobileKeyReleased

    private void txtMailIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMailIDKeyReleased
        if(Pattern.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+.com$", txtMailID.getText()))
        {
            lblEmailAuth2.setText("");
            mailSts=true;
            
        }
        else
        {
            mailSts=false;
            lblEmailAuth2.setText("<html><body><strong>*Error: Please enter a valid mail-ID.</strong></body></html>");

        }
    }//GEN-LAST:event_txtMailIDKeyReleased

    private void radioOthersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioOthersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioOthersActionPerformed

    private void txtConfirmPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConfirmPasswordKeyReleased
       /* if(cmbUserType.getSelectedItem().equals("Admin"))
        {*/
            pwdCSts=false;
            if(!txtPassword2.getText().equals(txtConfirmPassword.getText()))
            {
                pwdCSts=false;
                lblConfirmPwdSts.setText("<html><body><strong>*Error: Password and Confirm password must be same.</strong></body></html>");
            }
            else
            {
                pwdCSts=true;
                lblConfirmPwdSts.setText(" ");
            }
       /* }
        else
        {
            pwdCSts=true;
        }
        */
    }//GEN-LAST:event_txtConfirmPasswordKeyReleased

    private void cmbUserTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbUserTypeItemStateChanged

        if(cmbUserType.getSelectedItem().equals("--Select--"))
        {
            //System.out.println("select:"+isPwdSts);
            txtConfirmPassword.enable();
            txtPassword2.enable();
            txtConfirmPassword.setText(null);
            txtPassword2.setText(null);
            lblPasswordSts.setText("");
            lblConfirmPwdSts.setText(" ");
        }
        else if(cmbUserType.getSelectedItem().equals("Admin"))
        {
            txtConfirmPassword.enable();
            txtPassword2.enable();
            txtConfirmPassword.setText(null);
            txtPassword2.setText(null);
            lblPasswordSts.setText("");
            lblConfirmPwdSts.setText(" ");
        }
        else
        {
           // txtConfirmPassword.disable();
           // txtPassword2.disable();

           // txtConfirmPassword.setText("System");
           // txtPassword2.setText("System");
           // lblPasswordSts.setText("<html><body><strong>*Note : For normal user, Password is as follows: </strong><br><li>Combination of:</li>1. First 3 letter of your name.<br>2. First 3 digit of your mobile number.<br><br>Example:<strong> if name: aman & mobile 8360811111</strong> <br>Then password is: <strong style=color:'blue'>ama836</strong></body></html>");
            lblConfirmPwdSts.setText(" ");

        }
    }//GEN-LAST:event_cmbUserTypeItemStateChanged

    private void txtUsernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsernameKeyReleased
        if(Pattern.matches("^[A-Za-z0-9+_.-]+", txtUsername.getText()))
        {
            usernameSts=true;
           
        }
        else
        {
            usernameSts=false;
            lblUsernameAuth.setText("<html><body><strong>*Error: Please enter only [A-Z],[a-z],[0-9],[_.-]</strong></body></html>");
        }

    }//GEN-LAST:event_txtUsernameKeyReleased

    private void txtPassword2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassword2KeyReleased
       /* if(cmbUserType.getSelectedItem().equals("Admin") )
        {*/
            pwdSts=false;
            if(txtPassword2.getText().length()<4)
            {
                pwdSts=false;
                lblPasswordSts.setText("<html><body><strong>*Error: Password must be atleast 4 characters.</strong></body></html>");

            }
            else
            {

                pwdSts=true;

                lblPasswordSts.setText(" ");
                if(!txtPassword2.getText().equals(txtConfirmPassword.getText()))
                {
                    pwdCSts=false;
                    lblConfirmPwdSts.setText("<html><body><strong>*Error: Password and Confirm password must be same.</strong></body></html>");
                }
                else
                {
                    pwdCSts=true;
                    lblConfirmPwdSts.setText(" ");
                }
            }

       /* }
        else
        {
            pwdSts=true;
        }*/
    }//GEN-LAST:event_txtPassword2KeyReleased

    private void pnlBtnUpdateUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnUpdateUserMouseClicked
        correctSts=validateFields();
        if(correctSts==true)
        {
            updateAdmin();
        }

    }//GEN-LAST:event_pnlBtnUpdateUserMouseClicked

    private void pnlBtnUpdateUserMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnUpdateUserMouseExited
        panelMouseExit(pnlBtnUpdateUser,lblUpdateUser,"/Icons/update_B.png");
    }//GEN-LAST:event_pnlBtnUpdateUserMouseExited

    private void pnlBtnUpdateUserMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnUpdateUserMouseEntered
            panelMouseEnter(pnlBtnUpdateUser,lblUpdateUser,"/Icons/update_W.png");
    }//GEN-LAST:event_pnlBtnUpdateUserMouseEntered

    private void pnlBtnAllClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAllClearMouseClicked
        clearAllFields();
        
    }//GEN-LAST:event_pnlBtnAllClearMouseClicked

    private void pnlBtnAllClearMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAllClearMouseExited
        panelMouseExit(pnlBtnAllClear,lblAllClear,"/Icons/cancel_B.png");
    }//GEN-LAST:event_pnlBtnAllClearMouseExited

    private void pnlBtnAllClearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAllClearMouseEntered
        panelMouseEnter(pnlBtnAllClear,lblAllClear,"/Icons/cancel_W.png");
    }//GEN-LAST:event_pnlBtnAllClearMouseEntered

    private void userTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userTableMouseClicked
         row = userTable.getSelectedRow();
         column = userTable.getColumnCount();
        val = new Object[column];
        for( i = 0; i < column; i++) {
            val[i]=userTable.getValueAt(row, i);
        }
        userId=val[0].toString();
        txtName.setText(val[1].toString());
        txtUsername.setText(val[2].toString());
        
            cmbUserType.setSelectedItem("Admin");
        
        switch (val[5].toString()) {
            case "Male":
                radioMale.setSelected(true);
                break;
            case "Female":
                radioFemale.setSelected(true);
                break;
            default:
                radioOthers.setSelected(true);
                break;
        }
        
   
        txtMobile.setText(val[6].toString());
        txtMailID.setText(val[7].toString());
        switch (val[8].toString()) {
            case "Good":
                 lblUserSts.setSelectedItem("Good");
                lblStsImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/fiveStar.png")));
                break;
            case "Bad":
               lblUserSts.setSelectedItem("Bad");
                lblStsImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/badUser.png")));
                break;
            default:
                lblUserSts.setSelectedItem("Defaulter");
                lblStsImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/defaulter.png")));
                break;
        }
        txtPassword2.setText(val[3].toString());
        txtConfirmPassword.setText(val[3].toString());
        
    }//GEN-LAST:event_userTableMouseClicked

    private void pnlDeleteUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDeleteUserMouseClicked
        if(userId!=null)
        {
            if(JOptionPane.showConfirmDialog(this, "Do you really want to delete "+txtName.getText()+"?","Select an Option",1)==0)
            {
                 deleteAdmin();
             }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Please select user first.");
        }
            

    }//GEN-LAST:event_pnlDeleteUserMouseClicked

    private void pnlDeleteUserMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDeleteUserMouseExited
        panelMouseExit(pnlDeleteUser,lblDeleteUser,"/Icons/delete_B.png");
    }//GEN-LAST:event_pnlDeleteUserMouseExited

    private void pnlDeleteUserMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDeleteUserMouseEntered
           panelMouseEnter(pnlDeleteUser,lblDeleteUser,"/Icons/delete_W.png");
    }//GEN-LAST:event_pnlDeleteUserMouseEntered

    private void pnlBtnRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnRefreshMouseClicked
        try {
            fetchAdminToTable();
        } catch (SQLException ex) {
            Logger.getLogger(ManageAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_pnlBtnRefreshMouseClicked

    private void pnlBtnRefreshMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnRefreshMouseExited
        panelMouseExit(pnlBtnRefresh,lblRefresh,"/Icons/refresh_B.png");
    }//GEN-LAST:event_pnlBtnRefreshMouseExited

    private void pnlBtnRefreshMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnRefreshMouseEntered
        panelMouseEnter(pnlBtnRefresh,lblRefresh,"/Icons/refresh_W.png");
    }//GEN-LAST:event_pnlBtnRefreshMouseEntered

    private void lblRefreshMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRefreshMouseEntered
                panelMouseEnter(pnlBtnRefresh,lblRefresh,"/Icons/refresh_W.png");
    }//GEN-LAST:event_lblRefreshMouseEntered

    private void lblRefreshMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRefreshMouseExited
              panelMouseExit(pnlBtnRefresh,lblRefresh,"/Icons/refresh_B.png");
    }//GEN-LAST:event_lblRefreshMouseExited

    private void lblRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRefreshMouseClicked
        try {
            fetchAdminToTable();
        } catch (SQLException ex) {
            Logger.getLogger(ManageAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lblRefreshMouseClicked

    private void cmbUserTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbUserTypeActionPerformed
            // TODO add your handling code here:
    }//GEN-LAST:event_cmbUserTypeActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
       
        
    }//GEN-LAST:event_txtSearchKeyReleased

    @SuppressWarnings("empty-statement")
    private void btnSearchingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchingActionPerformed
       
            if(txtSearch.getText().length()>0)
            {
                searchAdmin();
            }
            else
            {
                txtSearch.grabFocus();
                JOptionPane.showMessageDialog(this, "Please enter username to search.");
            }
       
    }//GEN-LAST:event_btnSearchingActionPerformed
    
     public void clearAllFields()
      {
            pwd.delete(0, pwd.length());
                fin=null;
                img=null;
            correctSts=false;
            fieldSts=false;
            //isPwdSts=false;
            txtName.setText(null);
            txtMobile.setText(null);
            txtMailID.setText(null);
            lblStsImg.setIcon(null);
            txtUsername.setText(null);
            txtPassword2.setText(null);
            txtConfirmPassword.setText(null);
            cmbUserType.setSelectedItem("--Select--");
            userId=null;
          //  pwdSts=true;
          //  pwdCSts=true;
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearching;
    private javax.swing.JComboBox<String> cmbUserType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblAllClear;
    private javax.swing.JLabel lblBuffering;
    private javax.swing.JLabel lblConfirmPwdSts;
    private javax.swing.JLabel lblDeleteUser;
    private javax.swing.JLabel lblEmailAuth2;
    private javax.swing.JLabel lblLoading;
    private javax.swing.JLabel lblMobileAuth2;
    private javax.swing.JLabel lblName10;
    private javax.swing.JLabel lblName11;
    private javax.swing.JLabel lblName12;
    private javax.swing.JLabel lblName13;
    private javax.swing.JLabel lblName14;
    private javax.swing.JLabel lblName15;
    private javax.swing.JLabel lblName2;
    private javax.swing.JLabel lblName3;
    private javax.swing.JLabel lblPasswordSts;
    private javax.swing.JLabel lblPasswordSts3;
    private javax.swing.JLabel lblRefresh;
    private javax.swing.JLabel lblStsImg;
    private javax.swing.JLabel lblUpdateUser;
    private javax.swing.JComboBox<String> lblUserSts;
    private javax.swing.JLabel lblUsernameAuth;
    private javax.swing.JPanel pnlBtnAllClear;
    private javax.swing.JPanel pnlBtnRefresh;
    private javax.swing.JPanel pnlBtnUpdateUser;
    private javax.swing.JPanel pnlControls2;
    private javax.swing.JPanel pnlDeleteUser;
    private javax.swing.JPanel pnlHeadBtn;
    private javax.swing.JPanel pnlImgSec;
    private javax.swing.JPanel pnlPersonalDetailsSec2;
    private javax.swing.JRadioButton radioFemale;
    private javax.swing.JRadioButton radioMale;
    private javax.swing.JRadioButton radioOthers;
    private javax.swing.JPasswordField txtConfirmPassword;
    private javax.swing.JTextField txtMailID;
    private javax.swing.JTextField txtMobile;
    private javax.swing.JTextField txtName;
    private javax.swing.JPasswordField txtPassword2;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
}
