/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
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
public class AddBook extends javax.swing.JPanel implements Runnable{

   FileInputStream fin;
    File img;
    BusMethodInterface busLogic;
    Dashboard dashboard;
    Thread thread;
    ResultSet rs;
    DefaultComboBoxModel cmbGenreModel;
    private String isbn,title,author,edition,publisher,cat,language,price,quantity,summary;
    private StringBuilder pub_date;
    boolean btnAddClick=false,btnRefreshClick=false;
    boolean fieldSts=false,sts=false;
    
    public AddBook(Dashboard dashboard) {
        this.dashboard=dashboard;
        initComponents();
        thread=null;
        busLogic=new BusinessLogic();
        pub_date=new StringBuilder("");
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(40);
        cmbGenreModel = (DefaultComboBoxModel) cmbGenre.getModel();
    }
    
    void refreshGenreList()
    {
        if(thread==null)
        {
            btnRefreshClick=true;
            thread=new Thread(this);
            thread.start();
        }
    }
    void addNewBook()
    {
        if(thread==null)
        {
            btnAddClick=true;
            thread=new Thread(this);
            thread.start();
        }
    }
    
    
    @Override
    public void run()
    {
       
            if(btnRefreshClick==true)
            {
                btnRefreshGenre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gif/blackFastLoading.gif")));
                refreshGenreListNow();
                btnRefreshGenre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/refresh_B.png")));
            }
            
            if(btnAddClick==true)
            {
                lblBuffering.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gif/blackFastLoading.gif")));
                addBookNow();
                
            }
    }
    void refreshGenreListNow()
    {
        btnRefreshClick=false;
        if(!thread.interrupted())
        {
            try
            {
                     rs=busLogic.fetchBookGenre();
                     cmbGenreModel.removeAllElements();
                     cmbGenreModel.addElement("-Select-");
                    while(rs.next())
                    {
                        cmbGenreModel.addElement(rs.getString(2));
                    }
                    busLogic.disconnectNow();
                    cmbGenre.setModel(cmbGenreModel);
                    
            }
            catch(Exception e)
            {

            }
        }
        thread.interrupt();
        thread=null;  
    }
    
    void addBookNow()
    {
                    
        try
        {
            btnAddClick=false;
            if(!thread.interrupted())
            {
                if(busLogic.checkValidISBN(txtIsbn.getText())==false)
                    {
                        JOptionPane.showMessageDialog(this, "The book with this ISBN-number. is already exists.");
                        throw new MyExpection("");
                    }
                     if(add()==true)
                     {
                         lblBuffering.setIcon(null);
                         JOptionPane.showMessageDialog(this, title+" book added successfully.");
                         
                         
                     }
            }
            thread.interrupt();
            thread=null;
            resetAll();
            
        }
        catch(MyExpection e)
        {
            
        }
        
    }
    boolean add()
    {
                     sts=busLogic.addNewBook(isbn, title, author, edition, publisher, pub_date.toString(), cat, language, price, quantity, summary, this.fin, this.img);
                    return sts;
    }
    void resetAll()
    {
        txtTitle.setText(null);
          txtIsbn.setText(null);
          cmbGenre.setSelectedIndex(0);
          cmbLanguage.setSelectedIndex(0);
          txtPrice.setText(null);
          spinerQuantity.setValue(0);
          txtAuthor.setText(null);
          txtPublisher.setText(null);
          cmbDay.setSelectedIndex(0);
          cmbMonth.setSelectedIndex(0);
          cmbYear.setSelectedIndex(30);
          cmbEddition.setSelectedIndex(0);
          txtSummary.setText(null);
          lblUserImg.setIcon(null);
         //fin=null;
         //img=null;
          //other values
          btnRefreshClick=false;
          btnAddClick=false;
          
    }
    boolean isAllFieldFilled()
    {
        fieldSts=true;
        pub_date.delete(0,pub_date.length());
        try
        {
            title=txtTitle.getText();
            if(title.length()<=0)
            {
                fieldSts=false;
                txtTitle.grabFocus();
                JOptionPane.showMessageDialog(this,"Please enter Title of the book.");
                throw new MyExpection("");
            }
       
            isbn=txtIsbn.getText();
            if(isbn.length()<=0)
            {
                fieldSts=false;
                txtIsbn.grabFocus();
                JOptionPane.showMessageDialog(this,"Please enter ISBN number.");
                throw new MyExpection("");
            }
            
            cat=(String) cmbGenre.getSelectedItem();
            if(cat.equals("-Select-") || cat.length()<=0)
            {
                fieldSts=false;
                cmbGenre.grabFocus();
                JOptionPane.showMessageDialog(this,"Please select book category.");
                throw new MyExpection("");
            }
            
            language=(String) cmbLanguage.getSelectedItem();
            if(language.equals("-Select-"))
            {
                fieldSts=false;
                cmbLanguage.grabFocus();
                JOptionPane.showMessageDialog(this,"Please select book language.");
                throw new MyExpection("");
            }
            
            price=txtPrice.getText();
            if(price.length()<=0 || Integer.parseInt(price)<0)
            {
                fieldSts=false;
                txtPrice.grabFocus();
                JOptionPane.showMessageDialog(this,"Please enter valid book price.");
                throw new MyExpection("");
            }
            
            quantity=spinerQuantity.getValue().toString();
            if(spinerQuantity.getValue().toString().length()<=0 || Integer.parseInt(quantity)<0)
            {
                fieldSts=false;
                spinerQuantity.grabFocus();
                JOptionPane.showMessageDialog(this,"Please enter valid book quantity.");
                throw new MyExpection("");
            }
            
             if(lblUserImg.getIcon()==null)
            {
                fieldSts=false;
                lblSelectImage.grabFocus();
                JOptionPane.showMessageDialog(this,"Please select a book image.");
                throw new MyExpection("");
            }
           
            
            author=txtAuthor.getText();
            if(author.length()<=0)
            {
                fieldSts=false;
                txtAuthor.grabFocus();
                JOptionPane.showMessageDialog(this,"Please enter author name.");
                throw new MyExpection("");
            }
            
           
            publisher=txtPublisher.getText();
            if(publisher.length()<=0)
            {
                fieldSts=false;
                txtPublisher.grabFocus();
                JOptionPane.showMessageDialog(this,"Please enter publisher name.");
                throw new MyExpection("");
            }
            
            
            if(cmbDay.getSelectedItem().equals("-Day-"))
            {
                fieldSts=false;
                cmbDay.grabFocus();
                JOptionPane.showMessageDialog(this,"Please select day.");
                throw new MyExpection("");
            }
            if(cmbMonth.getSelectedItem().equals("-Month-"))
            {
                fieldSts=false;
                cmbMonth.grabFocus();
                JOptionPane.showMessageDialog(this,"Please select month.");
                throw new MyExpection("");
            }
            if(cmbYear.getSelectedItem().equals("-Year-"))
            {
                fieldSts=false;
                cmbYear.grabFocus();
                JOptionPane.showMessageDialog(this,"Please select year.");
                throw new MyExpection("");
            }
            edition=(String) cmbEddition.getSelectedItem();
            if(edition.equals("-Select-"))
            {
                fieldSts=false;
                cmbEddition.grabFocus();
                JOptionPane.showMessageDialog(this,"Please select book eddition.");
                throw new MyExpection("");
            }
            
            pub_date.append(cmbDay.getSelectedItem()).append("/").append(cmbMonth.getSelectedItem()).append("/").append(cmbYear.getSelectedItem());
            
            summary=txtSummary.getText();
            if(summary.length()<=0)
            {
                fieldSts=false;
                txtSummary.grabFocus();
                JOptionPane.showMessageDialog(this,"Please write somthing about book.");
                throw new MyExpection("");
            }
            if(summary.length()>=500)
            {
                fieldSts=false;
                txtSummary.grabFocus();
                JOptionPane.showMessageDialog(this,"Please enter 150 characters only.");
                throw new MyExpection("");
            }
            
        }
        catch(NumberFormatException e)
        {
            fieldSts=false;
            JOptionPane.showMessageDialog(this, "Please enter numeric value in price.");
        }
        catch(MyExpection e)
        {
            fieldSts=false;
        }
        return fieldSts;
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
    
      
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlMainParent = new javax.swing.JPanel();
        pnlImgSec = new javax.swing.JPanel();
        lblUserImg = new javax.swing.JLabel();
        pnlBtnSelectImage = new javax.swing.JPanel();
        lblSelectImage = new javax.swing.JLabel();
        pnlPersonalDetailsSec = new javax.swing.JPanel();
        lblName4 = new javax.swing.JLabel();
        txtTitle = new javax.swing.JTextField();
        lblName6 = new javax.swing.JLabel();
        txtIsbn = new javax.swing.JTextField();
        lblIsbnAuth = new javax.swing.JLabel();
        lblTitleAuth = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cmbGenre = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        spinerQuantity = new javax.swing.JSpinner();
        cmbLanguage = new javax.swing.JComboBox<>();
        btnRefreshGenre = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtAuthor = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPublisher = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cmbDay = new javax.swing.JComboBox<>();
        cmbMonth = new javax.swing.JComboBox<>();
        cmbYear = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cmbEddition = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtSummary = new javax.swing.JTextArea();
        pnlBtnCancel = new javax.swing.JPanel();
        lblCancel = new javax.swing.JLabel();
        pnlBtnAddBook = new javax.swing.JPanel();
        lblAddBook = new javax.swing.JLabel();
        lblBuffering = new javax.swing.JLabel();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setBackground(new java.awt.Color(254, 254, 254));
        setPreferredSize(new java.awt.Dimension(590, 352));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setAutoscrolls(true);

        pnlMainParent.setBackground(new java.awt.Color(254, 254, 254));

        pnlImgSec.setBackground(new java.awt.Color(254, 254, 254));
        pnlImgSec.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select Picture", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

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
        lblSelectImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSelectImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/selectImage_B.png"))); // NOI18N
        lblSelectImage.setText("Add Image...");

        javax.swing.GroupLayout pnlBtnSelectImageLayout = new javax.swing.GroupLayout(pnlBtnSelectImage);
        pnlBtnSelectImage.setLayout(pnlBtnSelectImageLayout);
        pnlBtnSelectImageLayout.setHorizontalGroup(
            pnlBtnSelectImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSelectImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBtnSelectImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pnlPersonalDetailsSec.setBackground(new java.awt.Color(254, 254, 254));
        pnlPersonalDetailsSec.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "General Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        pnlPersonalDetailsSec.setMaximumSize(new java.awt.Dimension(100, 100));

        lblName4.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblName4.setText("Title");

        txtTitle.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        txtTitle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTitleKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTitleKeyReleased(evt);
            }
        });

        lblName6.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblName6.setText("ISBN-No.");

        txtIsbn.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        txtIsbn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIsbnKeyReleased(evt);
            }
        });

        lblIsbnAuth.setForeground(new java.awt.Color(241, 43, 21));

        lblTitleAuth.setForeground(new java.awt.Color(241, 43, 21));

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        jLabel1.setText("Genre");

        cmbGenre.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        cmbGenre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        jLabel2.setText("Language");

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        jLabel3.setText("Price");

        txtPrice.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        jLabel4.setText("Quantity");

        spinerQuantity.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N

        cmbLanguage.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        cmbLanguage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Select-", "English", "Hindi", "Punjabi" }));

        btnRefreshGenre.setBackground(new java.awt.Color(248, 247, 247));
        btnRefreshGenre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/refresh_B.png"))); // NOI18N
        btnRefreshGenre.setToolTipText("Click here to refresh Genre list.");
        btnRefreshGenre.setBorderPainted(false);
        btnRefreshGenre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshGenreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPersonalDetailsSecLayout = new javax.swing.GroupLayout(pnlPersonalDetailsSec);
        pnlPersonalDetailsSec.setLayout(pnlPersonalDetailsSecLayout);
        pnlPersonalDetailsSecLayout.setHorizontalGroup(
            pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPersonalDetailsSecLayout.createSequentialGroup()
                .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPersonalDetailsSecLayout.createSequentialGroup()
                        .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblName6)
                            .addComponent(jLabel1)
                            .addComponent(lblName4)
                            .addComponent(jLabel3))
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPersonalDetailsSecLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTitle)
                    .addGroup(pnlPersonalDetailsSecLayout.createSequentialGroup()
                        .addComponent(txtPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinerQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
                    .addComponent(cmbLanguage, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPersonalDetailsSecLayout.createSequentialGroup()
                        .addComponent(cmbGenre, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefreshGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtIsbn)
                    .addGroup(pnlPersonalDetailsSecLayout.createSequentialGroup()
                        .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitleAuth)
                            .addComponent(lblIsbnAuth))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlPersonalDetailsSecLayout.setVerticalGroup(
            pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPersonalDetailsSecLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName4)
                    .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitleAuth)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName6)
                    .addComponent(txtIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblIsbnAuth)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefreshGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addComponent(spinerQuantity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        jPanel1.setBackground(new java.awt.Color(254, 254, 254));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Other Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        jLabel5.setText("Author");

        txtAuthor.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        jLabel6.setText("Publisher Name");

        txtPublisher.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        jLabel7.setText("Publication Date");

        cmbDay.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        cmbDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Day-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        cmbMonth.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        cmbMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Month-", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec" }));

        cmbYear.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        cmbYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "-Year-", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040" }));
        cmbYear.setSelectedIndex(30);

        jLabel8.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        jLabel8.setText("Eddition");

        cmbEddition.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        cmbEddition.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Select-", "First", "Second", "Third", "Forth", "Fifth", "Sixth", "Seventh", "Eighth", "Ninth", "Tenth", "Other" }));

        jLabel9.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        jLabel9.setText("Brief Summary about Book (500 Character only)");

        txtSummary.setColumns(20);
        txtSummary.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        txtSummary.setLineWrap(true);
        txtSummary.setRows(5);
        txtSummary.setToolTipText("Write a brief summary about the book .");
        txtSummary.setWrapStyleWord(true);
        txtSummary.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSummaryKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(txtSummary);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAuthor, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPublisher, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel6))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cmbDay, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(0, 0, 0)
                                        .addComponent(cmbMonth, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(0, 0, 0)
                                        .addComponent(cmbYear, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE))
                                    .addComponent(cmbEddition, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(42, 42, 42))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPublisher, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbYear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbDay, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbEddition, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlBtnCancel.setBackground(new java.awt.Color(248, 247, 247));
        pnlBtnCancel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlBtnCancel.setToolTipText("Add User");
        pnlBtnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnCancelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlBtnCancelMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlBtnCancelMouseEntered(evt);
            }
        });

        lblCancel.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblCancel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/cancel_B.png"))); // NOI18N
        lblCancel.setText("Cancel");
        lblCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCancelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCancelMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCancelMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout pnlBtnCancelLayout = new javax.swing.GroupLayout(pnlBtnCancel);
        pnlBtnCancel.setLayout(pnlBtnCancelLayout);
        pnlBtnCancelLayout.setHorizontalGroup(
            pnlBtnCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlBtnCancelLayout.setVerticalGroup(
            pnlBtnCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCancel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
        );

        pnlBtnAddBook.setBackground(new java.awt.Color(248, 247, 247));
        pnlBtnAddBook.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlBtnAddBook.setToolTipText("Add User");
        pnlBtnAddBook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnAddBookMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlBtnAddBookMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlBtnAddBookMouseEntered(evt);
            }
        });

        lblAddBook.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblAddBook.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAddBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/addBook_B.png"))); // NOI18N
        lblAddBook.setText("Add  Book");
        lblAddBook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAddBookMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAddBookMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAddBookMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout pnlBtnAddBookLayout = new javax.swing.GroupLayout(pnlBtnAddBook);
        pnlBtnAddBook.setLayout(pnlBtnAddBookLayout);
        pnlBtnAddBookLayout.setHorizontalGroup(
            pnlBtnAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAddBook, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlBtnAddBookLayout.setVerticalGroup(
            pnlBtnAddBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAddBook, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlMainParentLayout = new javax.swing.GroupLayout(pnlMainParent);
        pnlMainParent.setLayout(pnlMainParentLayout);
        pnlMainParentLayout.setHorizontalGroup(
            pnlMainParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainParentLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(pnlMainParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlMainParentLayout.createSequentialGroup()
                        .addComponent(pnlPersonalDetailsSec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlImgSec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(pnlMainParentLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(pnlBtnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(140, 140, 140)
                .addComponent(lblBuffering, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBtnAddBook, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(27, 27, 27))
        );
        pnlMainParentLayout.setVerticalGroup(
            pnlMainParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainParentLayout.createSequentialGroup()
                .addGroup(pnlMainParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlImgSec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPersonalDetailsSec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlMainParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainParentLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlMainParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pnlBtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlBtnAddBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlMainParentLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(lblBuffering)))
                .addGap(29, 29, 29))
        );

        jScrollPane1.setViewportView(pnlMainParent);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void pnlBtnSelectImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnSelectImageMouseClicked
        try {
            
            selectImage();
            fin=fin;
            img=img;
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

    private void txtTitleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTitleKeyPressed
        
    }//GEN-LAST:event_txtTitleKeyPressed

    private void txtTitleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTitleKeyReleased

       

    }//GEN-LAST:event_txtTitleKeyReleased

    private void txtIsbnKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIsbnKeyReleased
       
    }//GEN-LAST:event_txtIsbnKeyReleased

    private void pnlBtnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnCancelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnCancelMouseClicked

    private void pnlBtnCancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnCancelMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnCancelMouseExited

    private void pnlBtnCancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnCancelMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnCancelMouseEntered

    private void pnlBtnAddBookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAddBookMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnAddBookMouseClicked

    private void pnlBtnAddBookMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAddBookMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnAddBookMouseExited

    private void pnlBtnAddBookMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAddBookMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnAddBookMouseEntered

    private void lblCancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCancelMouseEntered
        panelMouseEnter(pnlBtnCancel, lblCancel,"/Icons/cancel_W.png");                // TODO add your handling code here:
    }//GEN-LAST:event_lblCancelMouseEntered

    private void lblCancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCancelMouseExited
        panelMouseExit(pnlBtnCancel, lblCancel,"/Icons/cancel_B.png");          // TODO add your handling code here:
    }//GEN-LAST:event_lblCancelMouseExited

    private void lblAddBookMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAddBookMouseEntered
        panelMouseEnter(pnlBtnAddBook, lblAddBook,"/Icons/addBook_W.png");          // TODO add your handling code here:
    }//GEN-LAST:event_lblAddBookMouseEntered

    private void lblAddBookMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAddBookMouseExited
        panelMouseExit(pnlBtnAddBook, lblAddBook,"/Icons/addBook_B.png");        // TODO add your handling code here:
    }//GEN-LAST:event_lblAddBookMouseExited

    private void btnRefreshGenreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshGenreActionPerformed
        refreshGenreList();        // TODO add your handling code here:
    }//GEN-LAST:event_btnRefreshGenreActionPerformed

    private void lblAddBookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAddBookMouseClicked
        if(isAllFieldFilled()==true)
        {
            addNewBook();
            resetAll();
        }
    }//GEN-LAST:event_lblAddBookMouseClicked

    private void lblCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCancelMouseClicked
        if(JOptionPane.showConfirmDialog(this, "Do you really want to cancel ?")==0)
        {
              resetAll();
        }
    }//GEN-LAST:event_lblCancelMouseClicked

    private void txtSummaryKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSummaryKeyReleased
                // TODO add your handling code here:
    }//GEN-LAST:event_txtSummaryKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefreshGenre;
    private javax.swing.JComboBox<String> cmbDay;
    private javax.swing.JComboBox<String> cmbEddition;
    private javax.swing.JComboBox<String> cmbGenre;
    private javax.swing.JComboBox<String> cmbLanguage;
    private javax.swing.JComboBox<String> cmbMonth;
    private javax.swing.JComboBox<String> cmbYear;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAddBook;
    private javax.swing.JLabel lblBuffering;
    private javax.swing.JLabel lblCancel;
    private javax.swing.JLabel lblIsbnAuth;
    private javax.swing.JLabel lblName4;
    private javax.swing.JLabel lblName6;
    private javax.swing.JLabel lblSelectImage;
    private javax.swing.JLabel lblTitleAuth;
    private javax.swing.JLabel lblUserImg;
    private javax.swing.JPanel pnlBtnAddBook;
    private javax.swing.JPanel pnlBtnCancel;
    private javax.swing.JPanel pnlBtnSelectImage;
    private javax.swing.JPanel pnlImgSec;
    private javax.swing.JPanel pnlMainParent;
    private javax.swing.JPanel pnlPersonalDetailsSec;
    private javax.swing.JSpinner spinerQuantity;
    private javax.swing.JTextField txtAuthor;
    private javax.swing.JTextField txtIsbn;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtPublisher;
    private javax.swing.JTextArea txtSummary;
    private javax.swing.JTextField txtTitle;
    // End of variables declaration//GEN-END:variables
}
