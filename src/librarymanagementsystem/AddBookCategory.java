/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author thakur-huni
 */
public class AddBookCategory extends javax.swing.JPanel implements Runnable{

    /**
     * Creates new form AddBookCategory
     */
    BusMethodInterface busLogic;
    Dashboard dashboard;
    String bookId=null;
    boolean correctSts=false,updateGenreSts=false,genreSts=false;
    DefaultTableModel model;
    ResultSet rs;
    int rows;
    int cols;
    Thread threadReresh,threadAdd,threadDelete,threadUpdate,threadSearch;
    boolean btnRefreshClick=false,btnAddClick=false,btnDeleteClick=false,btnUpdateClick=false,btnSearchClick=false;
    public AddBookCategory(Dashboard dashboard) throws SQLException {
        initComponents();
        this.dashboard=dashboard;
        busLogic=new BusinessLogic();
        threadReresh=null;
        threadDelete=null;
        threadAdd=null;
        threadUpdate=null;
        threadSearch=null;
        
        jScrollPane2.getVerticalScrollBar().setUnitIncrement(40);
    }
    void fetchBookCatToTable() throws SQLException
    {
        if(threadReresh==null)
        {
            threadReresh=new Thread(this);
            threadReresh.start();
        }
               
    }
    void addNewGenre()
    {
        if(threadAdd==null)
        {
            threadAdd=new Thread(this);
            threadAdd.start();
        }
    }
    void updateGenre()
    {
        if(threadUpdate==null)
        {
            threadUpdate=new Thread(this);
            threadUpdate.start();
        }
    }
    void deleteGenre()
    {
        if(threadDelete==null)
        {
            threadDelete=new Thread(this);
            threadDelete.start();
        }
    }
    void searchGenre()
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
      if(btnAddClick==true)
      {
          lblBuffering.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gif/blackFastLoading.gif")));
          addGenreNow();
      }
      if(btnUpdateClick==true)
      {
          lblBuffering.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gif/blackFastLoading.gif")));
          updateGenreNow();
      }
      if(btnDeleteClick==true)
      {
          lblBuffering.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gif/blackFastLoading.gif")));
          deleteGenreNow();
      }
      if(btnSearchClick==true)
      {
          btnSearching.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gif/blackFastLoading.gif")));
          btnSearching.setBackground(Color.white);
          searchGenreNow();
      }
      
      
    }
    void searchGenreNow()
    {
        btnSearchClick=false;
        if(!threadSearch.interrupted())
        {
            try 
            {
                rs=busLogic.loadSearchBookGenre(txtSearch.getText());
                model.setRowCount(0);
                while (rs.next()) {
                    Object o[] = {rs.getInt(1), rs.getString(2)};
                    model.addRow(o);
                }
                
                busLogic.disconnectNow();
                btnSearching.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search.png")));
                   btnSearching.setBackground(new Color(45,118,232));
                
            } catch (SQLException ex) {
                Logger.getLogger(ManageUsers.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
        threadSearch.interrupt();
        threadSearch=null;
    }
    void addGenreNow()
    {
        btnAddClick=false;
        if(!threadAdd.interrupted())
        {
           
                    if(busLogic.addNewBookGenre(txtGenere.getText())==true)
                    {
                        correctSts=false;
                        lblBuffering.setIcon(null);
                        JOptionPane.showMessageDialog(this, txtGenere.getText()+" added Successfully.");
                        allClear();
                    }
           
            threadAdd.interrupt();
                threadAdd=null;
                
        }
    }
    void deleteGenreNow()
    {
        btnDeleteClick=false;
        if(!threadDelete.interrupted())
        {
             if(busLogic.deleteBookGenre(bookId)==true);
                            {
                                correctSts=false;
                                lblBuffering.setIcon(null);
                                allClear();
                                JOptionPane.showMessageDialog(this, txtGenere.getText()+" deleted Successfully.");
                                
                            }
                            
                       
        }
        threadDelete.interrupt();
        threadDelete=null;
        
        btnRefreshClick=true;
        try {
            fetchBookCatToTable();
        } catch (SQLException ex) {
            Logger.getLogger(AddBookCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void updateGenreNow()
    {
        btnUpdateClick=false;
        if(!threadUpdate.interrupted())
        {
            validateFields();
                  if(updateGenreSts==true)
                    {
                        if(busLogic.updateBookGenre(bookId,txtGenere.getText())==true)
                        {
                            correctSts=false;
                            lblBuffering.setIcon(null);
                            JOptionPane.showMessageDialog(this, txtGenere.getText()+" updated Successfully.");
                            allClear();
                       
                        }
                    }
                    else
                    {
                       JOptionPane.showMessageDialog(this, txtGenere.getText()+" is already exists."); 
                    }
        }
        threadUpdate.interrupt();
        threadUpdate=null;
        
        
         btnRefreshClick=true;
        try {
            fetchBookCatToTable();
        } catch (SQLException ex) {
            Logger.getLogger(AddBookCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void refreshTable()
    {
        btnRefreshClick=false;
        if(!threadReresh.interrupted())
         {
          lblLoading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gif/blackFastLoading.gif")));
          try {
            rs=busLogic.fetchBookGenre();
            model=(DefaultTableModel)tableGenreBooks.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                Object o[] = {rs.getInt(1), rs.getString(2)};
                model.addRow(o);
            }
            busLogic.disconnectNow();
                threadReresh.interrupt();
                threadReresh=null;
                lblLoading.setIcon(null);
                
        } catch (SQLException ex) {
            Logger.getLogger(AddBookCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
    boolean validateFields()
    {
        if(txtGenere.getText().length()<=0)
        {
            correctSts=false;
            JOptionPane.showMessageDialog(this,"Please enter genre name." );
        }
        else
        {
            if(bookId==null)
            {
                
                if(busLogic.checkValidBookGenre(txtGenere.getText())==false)
                {
                    correctSts=false;
                    lblAuthGenre.setText("Error: Genre already exist.");
                }
                else
                {
                    correctSts=true;
                    lblAuthGenre.setText("");
                }
            }
            else
            {
                if(busLogic.checkValidBookGenre(bookId,txtGenere.getText())==false)
                {
                    updateGenreSts=false;
                    lblAuthGenre.setText("Error: Genre already exist.");
                }
                else
                {
                    updateGenreSts=true;
                    lblAuthGenre.setText("");
                }
            }
        }
        return correctSts;
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
    void allClear()
    {
        txtGenere.setText(null);
        bookId=null;
        lblBuffering.setIcon(null);
        correctSts=false;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        pnlBtnRefresh = new javax.swing.JPanel();
        lblRefresh = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lblLoading = new javax.swing.JLabel();
        btnSearching = new javax.swing.JButton();
        pnlPersonalDetailsSec = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        txtGenere = new javax.swing.JTextField();
        pnlBtnAdd = new javax.swing.JPanel();
        lblAddGenre = new javax.swing.JLabel();
        pnlBtnCancel = new javax.swing.JPanel();
        lblCancel = new javax.swing.JLabel();
        lblAuthGenre = new javax.swing.JLabel();
        pnlBtnDelete = new javax.swing.JPanel();
        lblDelete = new javax.swing.JLabel();
        pnlBtnUpdate = new javax.swing.JPanel();
        lblUpdate = new javax.swing.JLabel();
        lblBuffering = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableGenreBooks = new javax.swing.JTable();

        setBackground(new java.awt.Color(254, 254, 254));
        setPreferredSize(new java.awt.Dimension(590, 352));

        jScrollPane2.setAutoscrolls(true);

        jPanel2.setBackground(new java.awt.Color(254, 254, 254));

        jPanel1.setBackground(new java.awt.Color(254, 254, 254));

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

        lblRefresh.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblRefresh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/refresh_B.png"))); // NOI18N
        lblRefresh.setText("Refresh Table Data");
        lblRefresh.setToolTipText("Click here to refresh table data");
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
            .addComponent(lblRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        pnlBtnRefreshLayout.setVerticalGroup(
            pnlBtnRefreshLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBtnRefreshLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblRefresh))
        );

        txtSearch.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        txtSearch.setToolTipText("Search Genre by name");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jLabel1.setText("Search by Name :");

        btnSearching.setBackground(new java.awt.Color(45, 118, 232));
        btnSearching.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/search.png"))); // NOI18N
        btnSearching.setBorderPainted(false);
        btnSearching.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBtnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(lblLoading)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnSearching, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSearching, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlBtnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(lblLoading, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlPersonalDetailsSec.setBackground(new java.awt.Color(254, 254, 254));
        pnlPersonalDetailsSec.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add Genre", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N
        pnlPersonalDetailsSec.setMaximumSize(new java.awt.Dimension(100, 100));

        lblName.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        lblName.setText("Name");

        txtGenere.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        txtGenere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGenereActionPerformed(evt);
            }
        });
        txtGenere.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGenereKeyReleased(evt);
            }
        });

        pnlBtnAdd.setBackground(new java.awt.Color(248, 247, 247));
        pnlBtnAdd.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlBtnAdd.setToolTipText("Add User");
        pnlBtnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnAddMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlBtnAddMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlBtnAddMouseEntered(evt);
            }
        });

        lblAddGenre.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblAddGenre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAddGenre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/addGenre_B.png"))); // NOI18N
        lblAddGenre.setText("Add Genre");
        lblAddGenre.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        lblAddGenre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAddGenreMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAddGenreMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAddGenreMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout pnlBtnAddLayout = new javax.swing.GroupLayout(pnlBtnAdd);
        pnlBtnAdd.setLayout(pnlBtnAddLayout);
        pnlBtnAddLayout.setHorizontalGroup(
            pnlBtnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAddGenre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
        );
        pnlBtnAddLayout.setVerticalGroup(
            pnlBtnAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBtnAddLayout.createSequentialGroup()
                .addComponent(lblAddGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
        lblCancel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
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
            .addComponent(lblCancel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlBtnCancelLayout.setVerticalGroup(
            pnlBtnCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCancel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        lblAuthGenre.setForeground(new java.awt.Color(248, 11, 11));

        pnlBtnDelete.setBackground(new java.awt.Color(248, 247, 247));
        pnlBtnDelete.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlBtnDelete.setToolTipText("Add User");
        pnlBtnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnDeleteMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlBtnDeleteMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlBtnDeleteMouseEntered(evt);
            }
        });

        lblDelete.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblDelete.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/delete_B.png"))); // NOI18N
        lblDelete.setText("Delete");
        lblDelete.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        lblDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDeleteMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDeleteMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDeleteMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout pnlBtnDeleteLayout = new javax.swing.GroupLayout(pnlBtnDelete);
        pnlBtnDelete.setLayout(pnlBtnDeleteLayout);
        pnlBtnDeleteLayout.setHorizontalGroup(
            pnlBtnDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
        );
        pnlBtnDeleteLayout.setVerticalGroup(
            pnlBtnDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pnlBtnUpdate.setBackground(new java.awt.Color(248, 247, 247));
        pnlBtnUpdate.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlBtnUpdate.setToolTipText("Add User");
        pnlBtnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnUpdateMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlBtnUpdateMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlBtnUpdateMouseEntered(evt);
            }
        });

        lblUpdate.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblUpdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/update_B.png"))); // NOI18N
        lblUpdate.setText("Update");
        lblUpdate.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        lblUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUpdateMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblUpdateMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblUpdateMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout pnlBtnUpdateLayout = new javax.swing.GroupLayout(pnlBtnUpdate);
        pnlBtnUpdate.setLayout(pnlBtnUpdateLayout);
        pnlBtnUpdateLayout.setHorizontalGroup(
            pnlBtnUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlBtnUpdateLayout.setVerticalGroup(
            pnlBtnUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        lblBuffering.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBuffering.setToolTipText("Please wait...");

        javax.swing.GroupLayout pnlPersonalDetailsSecLayout = new javax.swing.GroupLayout(pnlPersonalDetailsSec);
        pnlPersonalDetailsSec.setLayout(pnlPersonalDetailsSecLayout);
        pnlPersonalDetailsSecLayout.setHorizontalGroup(
            pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPersonalDetailsSecLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPersonalDetailsSecLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lblBuffering, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPersonalDetailsSecLayout.createSequentialGroup()
                        .addComponent(lblName)
                        .addGap(18, 18, 18)
                        .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlPersonalDetailsSecLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pnlBtnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pnlBtnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pnlBtnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pnlBtnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(16, 16, 16))
                            .addComponent(txtGenere)
                            .addComponent(lblAuthGenre))
                        .addGap(27, 27, 27))))
        );
        pnlPersonalDetailsSecLayout.setVerticalGroup(
            pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPersonalDetailsSecLayout.createSequentialGroup()
                .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGenere, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBuffering)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAuthGenre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPersonalDetailsSecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnlPersonalDetailsSecLayout.createSequentialGroup()
                        .addComponent(pnlBtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(pnlBtnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlPersonalDetailsSecLayout.createSequentialGroup()
                        .addComponent(pnlBtnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlBtnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableGenreBooks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name"
            }
        ));
        tableGenreBooks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableGenreBooksMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableGenreBooks);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlPersonalDetailsSec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPersonalDetailsSec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScrollPane2.setViewportView(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtGenereKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGenereKeyReleased
            lblAuthGenre.setText(null);
    }//GEN-LAST:event_txtGenereKeyReleased

    private void pnlBtnAddMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAddMouseEntered
        
    }//GEN-LAST:event_pnlBtnAddMouseEntered

    private void pnlBtnAddMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAddMouseExited
       
    }//GEN-LAST:event_pnlBtnAddMouseExited

    private void pnlBtnAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAddMouseClicked
    
    }//GEN-LAST:event_pnlBtnAddMouseClicked

    private void pnlBtnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnCancelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnCancelMouseClicked

    private void pnlBtnCancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnCancelMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnCancelMouseExited

    private void pnlBtnCancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnCancelMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnCancelMouseEntered

    private void lblCancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCancelMouseEntered
                panelMouseEnter(pnlBtnCancel, lblCancel,"/Icons/cancel_W.png");
    }//GEN-LAST:event_lblCancelMouseEntered

    private void lblCancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCancelMouseExited
                 panelMouseExit(pnlBtnCancel, lblCancel,"/Icons/cancel_B.png");        // TODO add your handling code here:
    }//GEN-LAST:event_lblCancelMouseExited

    private void lblCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCancelMouseClicked
                allClear();        // TODO add your handling code here:
    }//GEN-LAST:event_lblCancelMouseClicked

    private void lblAddGenreMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAddGenreMouseEntered
            panelMouseEnter(pnlBtnAdd, lblAddGenre,"/Icons/addGenre_W.png");        // TODO add your handling code here:
    }//GEN-LAST:event_lblAddGenreMouseEntered

    private void lblAddGenreMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAddGenreMouseExited
            panelMouseExit(pnlBtnAdd, lblAddGenre,"/Icons/addGenre_B.png");         // TODO add your handling code here:
    }//GEN-LAST:event_lblAddGenreMouseExited

    private void lblAddGenreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAddGenreMouseClicked
                if(validateFields()==true)
                {
                    btnAddClick=true;
                    addNewGenre();
                }
                   
                
    }//GEN-LAST:event_lblAddGenreMouseClicked

    private void lblDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDeleteMouseClicked
               if(bookId!=null)
                {
              
                    if(JOptionPane.showConfirmDialog(this, "Do you really want to delete "+txtGenere.getText())==0)
                    {
                        btnDeleteClick=true;
                        deleteGenre();
                           
                    }
                    else
                    {
                        allClear();
                    }
                
            }
            else
            {
               correctSts=false;
               JOptionPane.showMessageDialog(this, "Please select genre from bellow table."); 
            }       
        
                
    }//GEN-LAST:event_lblDeleteMouseClicked

    private void lblDeleteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDeleteMouseExited
        panelMouseExit(pnlBtnDelete, lblDelete,"/Icons/delete_B.png");
    }//GEN-LAST:event_lblDeleteMouseExited

    private void lblDeleteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDeleteMouseEntered
        panelMouseEnter(pnlBtnDelete, lblDelete,"/Icons/delete_W.png");
    }//GEN-LAST:event_lblDeleteMouseEntered

    private void pnlBtnDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnDeleteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnDeleteMouseClicked

    private void pnlBtnDeleteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnDeleteMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnDeleteMouseExited

    private void pnlBtnDeleteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnDeleteMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnDeleteMouseEntered

    private void lblUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUpdateMouseClicked
          if(bookId!=null)
          {
                  btnUpdateClick=true;
                  updateGenre();
              
          }
          else
          {
              JOptionPane.showMessageDialog(this, "Please select category first from bellow table.");
          }
    }//GEN-LAST:event_lblUpdateMouseClicked

    private void lblUpdateMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUpdateMouseExited
        panelMouseExit(pnlBtnUpdate, lblUpdate,"/Icons/update_B.png");
    }//GEN-LAST:event_lblUpdateMouseExited

    private void lblUpdateMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUpdateMouseEntered
        panelMouseEnter(pnlBtnUpdate, lblUpdate,"/Icons/update_W.png");
    }//GEN-LAST:event_lblUpdateMouseEntered

    private void pnlBtnUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnUpdateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnUpdateMouseClicked

    private void pnlBtnUpdateMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnUpdateMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnUpdateMouseExited

    private void pnlBtnUpdateMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnUpdateMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnUpdateMouseEntered

    private void lblRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRefreshMouseClicked
        try {
            btnRefreshClick=true;
            fetchBookCatToTable();        // TODO add your handling code here:
        } catch (SQLException ex) {
            Logger.getLogger(AddBookCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lblRefreshMouseClicked

    private void lblRefreshMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRefreshMouseExited
        panelMouseExit(pnlBtnRefresh, lblRefresh,"/Icons/refresh_B.png");
    }//GEN-LAST:event_lblRefreshMouseExited

    private void lblRefreshMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRefreshMouseEntered
        panelMouseEnter(pnlBtnRefresh, lblRefresh,"/Icons/refresh_W.png");        // TODO add your handling code here:
    }//GEN-LAST:event_lblRefreshMouseEntered

    private void pnlBtnRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnRefreshMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnRefreshMouseClicked

    private void pnlBtnRefreshMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnRefreshMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnRefreshMouseExited

    private void pnlBtnRefreshMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnRefreshMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnRefreshMouseEntered

    private void txtGenereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGenereActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGenereActionPerformed

    private void tableGenreBooksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableGenreBooksMouseClicked
        rows = tableGenreBooks.getSelectedRow();
        cols = tableGenreBooks.getColumnCount();
        Object[] val = new Object[cols];
        for(int i = 0; i < cols; i++) {
            val[i]=tableGenreBooks.getValueAt(rows, i);
        }
        bookId=String.valueOf(val[0]);
        txtGenere.setText(val[1].toString());
    }//GEN-LAST:event_tableGenreBooksMouseClicked

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
            // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnSearchingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchingActionPerformed
        if(txtSearch.getText().length()>0)
        {
            searchGenre();  
        }
        else
        {
            txtSearch.grabFocus();
            JOptionPane.showMessageDialog(this, "Please enter username to search.");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchingActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearching;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAddGenre;
    private javax.swing.JLabel lblAuthGenre;
    private javax.swing.JLabel lblBuffering;
    private javax.swing.JLabel lblCancel;
    private javax.swing.JLabel lblDelete;
    private javax.swing.JLabel lblLoading;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblRefresh;
    private javax.swing.JLabel lblUpdate;
    private javax.swing.JPanel pnlBtnAdd;
    private javax.swing.JPanel pnlBtnCancel;
    private javax.swing.JPanel pnlBtnDelete;
    private javax.swing.JPanel pnlBtnRefresh;
    private javax.swing.JPanel pnlBtnUpdate;
    private javax.swing.JPanel pnlPersonalDetailsSec;
    private javax.swing.JTable tableGenreBooks;
    private javax.swing.JTextField txtGenere;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
