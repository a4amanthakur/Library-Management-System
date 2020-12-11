
package librarymanagementsystem;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author thakur-huni
 */
public class AddUser extends javax.swing.JPanel implements Runnable{

    /**
     * Creates new form AddUser
     */
    ButtonGroup radioBtnGrp;
    FileInputStream fin;
    File img;
    BusMethodInterface busLogic;
    boolean correctSts=false,mailSts,mobileSts,usernameSts,pwdSts,pwdCSts,fieldSts=false;
    StringBuilder pwd;
    Dashboard dashboard;
    Thread thread;
    public AddUser(Dashboard dashboard) {
        this.dashboard=dashboard;
        initComponents();
        thread=null;
        pwd=new StringBuilder("");
        radioBtnGrp=new ButtonGroup();
        setRadioButton();
        busLogic=new BusinessLogic();
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(40);
    }

    private void setRadioButton()
    {
        radioBtnGrp.add(radioMale);
        radioBtnGrp.add(radioFemale);
        radioBtnGrp.add(radioOthers);
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
    void selectImage() throws FileNotFoundException, SQLException
    {
        JFileChooser file=new JFileChooser();
        FileNameExtensionFilter filter=new FileNameExtensionFilter("*.png","*,jpg","*.jpeg");
        file.addChoosableFileFilter(filter);
        int a=file.showSaveDialog(this);
        if(a==JFileChooser.APPROVE_OPTION)
        {
            img=new File(file.getSelectedFile().getAbsolutePath());
            if(img.length()<1048576)
                {
                    lblUserImg.setIcon(ResizeImage(file.getSelectedFile().getAbsolutePath()));
                    fin=new FileInputStream(img);
                }
                else
                {
                    JOptionPane.showMessageDialog(this,"Image Size must be less than 1MB");
                }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "No image selected.");
        }
    }
    
     public ImageIcon ResizeImage(String imgPath){
        ImageIcon MyImage = new ImageIcon(imgPath);
        Image img = MyImage.getImage();
        Image newImage = img.getScaledInstance(lblUserImg.getWidth(), lblUserImg.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        return image;
    }
    
      public void clearAllFields()
      {
            correctSts=false;
            //isPwdSts=false;
            txtName.setText(null);
            txtMobile.setText(null);
            txtMailID.setText(null);
            lblUserImg.setIcon(null);
            txtUsername.setText(null);
            txtPassword2.setText(null);
            txtConfirmPassword.setText(null);
            cmbUserType.setSelectedItem("--Select--");
          //  pwdSts=true;
          //  pwdCSts=true;
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
                        if(!busLogic.checkValidMobile(txtMobile.getText()))
                        {
                            correctSts=false;
                            txtMobile.grabFocus();
                            
                            lblMobileAuth.setText("<html><body><strong>*Error: Mobile number already exist.</strong></body></html>");
                            throw new MyExpection("");
                        }
                        else
                        {
                            correctSts=true;
                            lblMobileAuth.setText("");
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
                        if(!busLogic.checkValidMail(txtMailID.getText()))
                        {
                            correctSts=false;
                            txtMailID.grabFocus();
                            lblEmailAuth.setText("<html><body><strong>*Error: Mail-ID already exist.</strong></body></html>");
                            throw new MyExpection("");
                        }
                        else
                        {
                            correctSts=true;
                            lblMobileAuth.setText("");
                        } 
                    }
                    if(fin==null || img==null)
                    {
                        JOptionPane.showMessageDialog(this,"Error: Please select an image.");
                        correctSts=false;
                        pnlBtnSelectImage.grabFocus();
                        throw new MyExpection("");
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
                        if(!busLogic.checkValidUsername(txtUsername.getText()))
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
                        pnlBtnAddUser.grabFocus();
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
            if(fin==null || img==null)
            {
                fieldSts=false;
                 JOptionPane.showMessageDialog(this, "Please select an image.");
                 pnlBtnSelectImage.grabFocus();
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlControls = new javax.swing.JPanel();
        pnlPersonalDetailsSec = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblName4 = new javax.swing.JLabel();
        txtMobile = new javax.swing.JTextField();
        lblName5 = new javax.swing.JLabel();
        radioMale = new javax.swing.JRadioButton();
        radioFemale = new javax.swing.JRadioButton();
        lblName6 = new javax.swing.JLabel();
        txtMailID = new javax.swing.JTextField();
        radioOthers = new javax.swing.JRadioButton();
        lblPasswordSts1 = new javax.swing.JLabel();
        lblEmailAuth = new javax.swing.JLabel();
        lblMobileAuth = new javax.swing.JLabel();
        pnlImgSec = new javax.swing.JPanel();
        lblUserImg = new javax.swing.JLabel();
        pnlBtnSelectImage = new javax.swing.JPanel();
        lblSelectImage = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtConfirmPassword = new javax.swing.JPasswordField();
        lblName2 = new javax.swing.JLabel();
        cmbUserType = new javax.swing.JComboBox<>();
        lblName1 = new javax.swing.JLabel();
        lblName3 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPasswordSts = new javax.swing.JLabel();
        lblUsernameAuth = new javax.swing.JLabel();
        txtPassword2 = new javax.swing.JPasswordField();
        lblName7 = new javax.swing.JLabel();
        lblConfirmPwdSts = new javax.swing.JLabel();
        pnlBtnAddUser = new javax.swing.JPanel();
        lblAddUser = new javax.swing.JLabel();
        pnlBtnAllClear = new javax.swing.JPanel();
        lblAllClear = new javax.swing.JLabel();
        lblBuffring = new javax.swing.JLabel();

        setBackground(new java.awt.Color(254, 254, 254));
        setPreferredSize(new java.awt.Dimension(590, 352));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setAutoscrolls(true);

        pnlControls.setBackground(new java.awt.Color(254, 254, 254));

        pnlPersonalDetailsSec.setBackground(new java.awt.Color(254, 254, 254));
        pnlPersonalDetailsSec.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Personal Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        pnlPersonalDetailsSec.setMaximumSize(new java.awt.Dimension(100, 100));

        lblName.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblName.setText("Name");

        txtName.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNameKeyReleased(evt);
            }
        });

        lblName4.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblName4.setText("Mobile");

        txtMobile.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        txtMobile.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMobileKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMobileKeyReleased(evt);
            }
        });

        lblName5.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblName5.setText("Gender");

        radioMale.setBackground(new java.awt.Color(254, 254, 254));
        radioMale.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        radioMale.setSelected(true);
        radioMale.setText("Male");

        radioFemale.setBackground(new java.awt.Color(254, 254, 254));
        radioFemale.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        radioFemale.setText("Female");

        lblName6.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblName6.setText("Mail-ID");

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

        lblPasswordSts1.setForeground(new java.awt.Color(241, 43, 21));

        lblEmailAuth.setForeground(new java.awt.Color(241, 43, 21));

        lblMobileAuth.setForeground(new java.awt.Color(241, 43, 21));

        javax.swing.GroupLayout pnlPersonalDetailsSecLayout = new javax.swing.GroupLayout(pnlPersonalDetailsSec);
        pnlPersonalDetailsSec.setLayout(pnlPersonalDetailsSecLayout);
        pnlPersonalDetailsSecLayout.setHorizontalGroup(
            pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPersonalDetailsSecLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPersonalDetailsSecLayout.createSequentialGroup()
                        .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblName)
                            .addComponent(lblName5)
                            .addComponent(lblName4))
                        .addGap(10, 10, 10)
                        .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlPersonalDetailsSecLayout.createSequentialGroup()
                                .addComponent(radioMale, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioFemale, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioOthers, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                            .addComponent(txtMobile)
                            .addComponent(txtName)))
                    .addGroup(pnlPersonalDetailsSecLayout.createSequentialGroup()
                        .addComponent(lblName6)
                        .addGap(10, 10, 10)
                        .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMailID)
                            .addGroup(pnlPersonalDetailsSecLayout.createSequentialGroup()
                                .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblMobileAuth)
                                    .addComponent(lblEmailAuth))
                                .addGap(0, 0, Short.MAX_VALUE))))))
            .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlPersonalDetailsSecLayout.createSequentialGroup()
                    .addGap(172, 172, 172)
                    .addComponent(lblPasswordSts1)
                    .addContainerGap(187, Short.MAX_VALUE)))
        );
        pnlPersonalDetailsSecLayout.setVerticalGroup(
            pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPersonalDetailsSecLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblName5)
                    .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(radioMale)
                        .addComponent(radioFemale)
                        .addComponent(radioOthers)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName4)
                    .addComponent(txtMobile, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMobileAuth)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName6)
                    .addComponent(txtMailID, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmailAuth)
                .addGap(92, 92, 92))
            .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlPersonalDetailsSecLayout.createSequentialGroup()
                    .addGap(137, 137, 137)
                    .addComponent(lblPasswordSts1)
                    .addContainerGap(130, Short.MAX_VALUE)))
        );

        pnlImgSec.setBackground(new java.awt.Color(254, 254, 254));
        pnlImgSec.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select Profile Picture", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        lblUserImg.setBackground(new java.awt.Color(254, 254, 254));

        pnlBtnSelectImage.setBackground(new java.awt.Color(248, 247, 247));
        pnlBtnSelectImage.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlBtnSelectImage.setToolTipText("Add User");
        pnlBtnSelectImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnSelectImageMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlBtnSelectImageMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlBtnSelectImageMouseEntered(evt);
            }
        });

        lblSelectImage.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblSelectImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/selectImage_B.png"))); // NOI18N
        lblSelectImage.setText("Add Image...");

        javax.swing.GroupLayout pnlBtnSelectImageLayout = new javax.swing.GroupLayout(pnlBtnSelectImage);
        pnlBtnSelectImage.setLayout(pnlBtnSelectImageLayout);
        pnlBtnSelectImageLayout.setHorizontalGroup(
            pnlBtnSelectImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBtnSelectImageLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblSelectImage)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        pnlBtnSelectImageLayout.setVerticalGroup(
            pnlBtnSelectImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBtnSelectImageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSelectImage)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlImgSecLayout = new javax.swing.GroupLayout(pnlImgSec);
        pnlImgSec.setLayout(pnlImgSecLayout);
        pnlImgSecLayout.setHorizontalGroup(
            pnlImgSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBtnSelectImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlImgSecLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUserImg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlImgSecLayout.setVerticalGroup(
            pnlImgSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlImgSecLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUserImg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlBtnSelectImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel1.setBackground(new java.awt.Color(248, 247, 247));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "User Authentication Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        txtConfirmPassword.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        txtConfirmPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtConfirmPasswordKeyReleased(evt);
            }
        });

        lblName2.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblName2.setText("Password");

        cmbUserType.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        cmbUserType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Select--", "Admin", "Normal User" }));
        cmbUserType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbUserTypeItemStateChanged(evt);
            }
        });

        lblName1.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblName1.setText("Username");

        lblName3.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblName3.setText("User-type");

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

        lblName7.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblName7.setText("Confirm");

        lblConfirmPwdSts.setForeground(new java.awt.Color(241, 43, 21));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblName3)
                        .addGap(18, 18, 18)
                        .addComponent(cmbUserType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblName1)
                        .addGap(19, 19, 19)
                        .addComponent(txtUsername))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblName2)
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtPassword2, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblName7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUsernameAuth)
                                    .addComponent(lblPasswordSts))
                                .addGap(256, 256, 256)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblConfirmPwdSts)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtConfirmPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbUserType, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName1))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblName2)
                            .addComponent(txtConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblName7)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUsernameAuth)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblPasswordSts)
                        .addGap(13, 13, 13))
                    .addComponent(lblConfirmPwdSts, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        pnlBtnAddUser.setBackground(new java.awt.Color(248, 247, 247));
        pnlBtnAddUser.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlBtnAddUser.setToolTipText("Add User");
        pnlBtnAddUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnAddUserMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlBtnAddUserMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlBtnAddUserMouseEntered(evt);
            }
        });

        lblAddUser.setFont(new java.awt.Font("Ubuntu", 1, 19)); // NOI18N
        lblAddUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAddUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/addUser_B.png"))); // NOI18N
        lblAddUser.setText("Add User");
        lblAddUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAddUserMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAddUserMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAddUserMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout pnlBtnAddUserLayout = new javax.swing.GroupLayout(pnlBtnAddUser);
        pnlBtnAddUser.setLayout(pnlBtnAddUserLayout);
        pnlBtnAddUserLayout.setHorizontalGroup(
            pnlBtnAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAddUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlBtnAddUserLayout.setVerticalGroup(
            pnlBtnAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAddUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
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
        lblAllClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/allClear_B.png"))); // NOI18N
        lblAllClear.setText("All Clear");
        lblAllClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAllClearMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAllClearMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAllClearMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout pnlBtnAllClearLayout = new javax.swing.GroupLayout(pnlBtnAllClear);
        pnlBtnAllClear.setLayout(pnlBtnAllClearLayout);
        pnlBtnAllClearLayout.setHorizontalGroup(
            pnlBtnAllClearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAllClear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlBtnAllClearLayout.setVerticalGroup(
            pnlBtnAllClearLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAllClear, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlControlsLayout = new javax.swing.GroupLayout(pnlControls);
        pnlControls.setLayout(pnlControlsLayout);
        pnlControlsLayout.setHorizontalGroup(
            pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlControlsLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlControlsLayout.createSequentialGroup()
                        .addComponent(pnlBtnAllClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(57, 57, 57)
                        .addComponent(lblBuffring)
                        .addGap(2, 2, 2)
                        .addComponent(pnlBtnAddUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlControlsLayout.createSequentialGroup()
                        .addComponent(pnlPersonalDetailsSec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(pnlImgSec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        pnlControlsLayout.setVerticalGroup(
            pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlControlsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlImgSec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPersonalDetailsSec, javax.swing.GroupLayout.PREFERRED_SIZE, 271, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlControlsLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlBtnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlBtnAllClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlControlsLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lblBuffring)))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(pnlControls);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void pnlBtnSelectImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnSelectImageMouseClicked
        try {
            selectImage();
        } catch (FileNotFoundException | SQLException ex) {
            Logger.getLogger(AddUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_pnlBtnSelectImageMouseClicked

    private void pnlBtnSelectImageMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnSelectImageMouseExited
        panelMouseExit(pnlBtnSelectImage, lblSelectImage,"/Icons/selectImage_B.png");
    }//GEN-LAST:event_pnlBtnSelectImageMouseExited

    private void pnlBtnSelectImageMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnSelectImageMouseEntered
        panelMouseEnter(pnlBtnSelectImage, lblSelectImage,"/Icons/selectImage_W.png");
    }//GEN-LAST:event_pnlBtnSelectImageMouseEntered
    
    private void pnlBtnAddUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAddUserMouseClicked
          
       
    }//GEN-LAST:event_pnlBtnAddUserMouseClicked

    private void pnlBtnAddUserMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAddUserMouseExited
        panelMouseExit(pnlBtnAddUser,lblAddUser,"/Icons/addUser_B.png");
    }//GEN-LAST:event_pnlBtnAddUserMouseExited

    private void pnlBtnAddUserMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAddUserMouseEntered
         panelMouseEnter(pnlBtnAddUser,lblAddUser,"/Icons/addUser_W.png");
    }//GEN-LAST:event_pnlBtnAddUserMouseEntered

    private void pnlBtnAllClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAllClearMouseClicked
           
            
    }//GEN-LAST:event_pnlBtnAllClearMouseClicked

    private void pnlBtnAllClearMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAllClearMouseExited
        panelMouseExit(pnlBtnAllClear,lblAllClear,"/Icons/allClear_B.png");
    }//GEN-LAST:event_pnlBtnAllClearMouseExited

    private void pnlBtnAllClearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAllClearMouseEntered
        
    }//GEN-LAST:event_pnlBtnAllClearMouseEntered

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
                txtConfirmPassword.disable();
                txtPassword2.disable();
                
                txtConfirmPassword.setText("System");
                txtPassword2.setText("System");
                lblPasswordSts.setText("<html><body><strong>*Note : For normal user, Password is as follows: </strong><br><li>Combination of:</li>1. First 3 letter of your name.<br>2. First 3 digit of your mobile number.<br><br>Example:<strong> if name: aman & mobile 8360811111</strong> <br>Then password is: <strong style=color:'blue'>ama836</strong></body></html>");
                lblConfirmPwdSts.setText(" ");
            
            }
    }//GEN-LAST:event_cmbUserTypeItemStateChanged

    private void txtMobileKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMobileKeyReleased
    
        if(txtMobile.getText().length()!=10 || !Pattern.matches("[0123456789]+", txtMobile.getText()))
        {
            mobileSts=false;
            lblMobileAuth.setText("<html><body><strong>*Error: Please enter a valid number (10 Digits only).</strong></body></html>");
        }
        else
        {
            
            mobileSts=true;
            lblMobileAuth.setText("");
            
        }
               
        
    }//GEN-LAST:event_txtMobileKeyReleased

    private void txtMobileKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMobileKeyPressed
           
                
    }//GEN-LAST:event_txtMobileKeyPressed

    private void txtMailIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMailIDKeyReleased
        if( !Pattern.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+.com$", txtMailID.getText()))
        {
            mailSts=false;
            lblEmailAuth.setText("<html><body><strong>*Error: Please enter a valid mail-ID.</strong></body></html>");
            
        }
        else
        {
            
            lblEmailAuth.setText("");
            mailSts=true;
            
        }
       // System.out.println("mail sts:"+mailSts);
    }//GEN-LAST:event_txtMailIDKeyReleased

    private void txtUsernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsernameKeyReleased
       if(txtUsername.getText().length()>0 && Pattern.matches("^[A-Za-z0-9+_.-]+", txtUsername.getText()))
       {
           usernameSts=true;
          
       }
       else
       {
           usernameSts=false;
            lblUsernameAuth.setText("<html><body><strong>*Error: Please enter only [A-Z],[a-z],[0-9],[_.-]</strong></body></html>");
       }
        
    }//GEN-LAST:event_txtUsernameKeyReleased

    private void txtConfirmPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConfirmPasswordKeyReleased
         if(cmbUserType.getSelectedItem().equals("Admin"))
         {
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
         }
         else
         {
             pwdCSts=true;
         }
    }//GEN-LAST:event_txtConfirmPasswordKeyReleased

    private void txtPassword2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassword2KeyReleased
         if(cmbUserType.getSelectedItem().equals("Admin") )
         {
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
             
            
         }
         else
         {
             pwdSts=true;
         }
    }//GEN-LAST:event_txtPassword2KeyReleased

    private void lblAddUserMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAddUserMouseEntered
        panelMouseEnter(pnlBtnAddUser,lblAddUser,"/Icons/addUser_W.png");        // TODO add your handling code here:
    }//GEN-LAST:event_lblAddUserMouseEntered

    private void lblAddUserMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAddUserMouseExited
        panelMouseExit(pnlBtnAddUser,lblAddUser,"/Icons/addUser_B.png");        // TODO add your handling code here:
    }//GEN-LAST:event_lblAddUserMouseExited

    private void lblAddUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAddUserMouseClicked
         correctSts=validateFields();
           if(correctSts==true)
           {
               if(thread==null)
               {
                   thread=new Thread(this);
                   thread.start();
               }
           }        // TODO add your handling code here:
    }//GEN-LAST:event_lblAddUserMouseClicked
    @Override
    public void run()
    {
        lblBuffring.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gif/blackFastLoading.gif")));
        if(!thread.interrupted())
        {
            pwd.delete(0, pwd.length());
               String gender="Male";
               String userType;
               
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
                   pwd.append(txtPassword2.getText());
               }
               else
               {
                   userType="User";
                   pwd.append(txtName.getText().substring(0,3));
                    pwd.append(txtMobile.getText().substring(0, 3));
               }
               correctSts=busLogic.addNewUser(txtName.getText(),txtUsername.getText(),pwd.toString(), userType , gender,txtMobile.getText(), txtMailID.getText(),"Good",fin,img);
               if(correctSts==true)
               {
                   lblBuffring.setIcon(null);
                   JOptionPane.showMessageDialog(this, txtName.getText()+" added successfully.");
                    pwd.delete(0, pwd.length());
                    fin=null;
                    img=null;
                    correctSts=false;
                    clearAllFields();
                    dashboard.addMenuPage();
               }
        }
        thread.interrupt();
        thread=null;
        
        
    }
    private void lblAllClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAllClearMouseClicked
     clearAllFields();        // TODO add your handling code here:
    }//GEN-LAST:event_lblAllClearMouseClicked

    private void lblAllClearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAllClearMouseEntered
            panelMouseEnter(pnlBtnAllClear,lblAllClear,"/Icons/allClear_W.png");        // TODO add your handling code here:
    }//GEN-LAST:event_lblAllClearMouseEntered

    private void lblAllClearMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAllClearMouseExited
            panelMouseExit(pnlBtnAllClear,lblAllClear,"/Icons/allClear_B.png");        // TODO add your handling code here:
    }//GEN-LAST:event_lblAllClearMouseExited

    private void radioOthersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioOthersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioOthersActionPerformed

    private void txtNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbUserType;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAddUser;
    private javax.swing.JLabel lblAllClear;
    private javax.swing.JLabel lblBuffring;
    private javax.swing.JLabel lblConfirmPwdSts;
    private javax.swing.JLabel lblEmailAuth;
    private javax.swing.JLabel lblMobileAuth;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblName1;
    private javax.swing.JLabel lblName2;
    private javax.swing.JLabel lblName3;
    private javax.swing.JLabel lblName4;
    private javax.swing.JLabel lblName5;
    private javax.swing.JLabel lblName6;
    private javax.swing.JLabel lblName7;
    private javax.swing.JLabel lblPasswordSts;
    private javax.swing.JLabel lblPasswordSts1;
    private javax.swing.JLabel lblSelectImage;
    private javax.swing.JLabel lblUserImg;
    private javax.swing.JLabel lblUsernameAuth;
    private javax.swing.JPanel pnlBtnAddUser;
    private javax.swing.JPanel pnlBtnAllClear;
    private javax.swing.JPanel pnlBtnSelectImage;
    private javax.swing.JPanel pnlControls;
    private javax.swing.JPanel pnlImgSec;
    private javax.swing.JPanel pnlPersonalDetailsSec;
    private javax.swing.JRadioButton radioFemale;
    private javax.swing.JRadioButton radioMale;
    private javax.swing.JRadioButton radioOthers;
    private javax.swing.JPasswordField txtConfirmPassword;
    private javax.swing.JTextField txtMailID;
    private javax.swing.JTextField txtMobile;
    private javax.swing.JTextField txtName;
    private javax.swing.JPasswordField txtPassword2;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
