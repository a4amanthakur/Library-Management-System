/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.sql.ResultSet;
import java.awt.Color;
import java.awt.Image;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author thakur-huni
 */
public class ManageBook extends javax.swing.JPanel implements Runnable{

    /**
     * Creates new form ManageBook
     */
    LinkedList<BookImageObject> idAndImage;
    boolean btnRefreshClick=false,btnFetchRowCLick=false,btnDeleteClick=false,colorFlag=false;
    BusinessLogic busLogic;
    ResultSet rs;
    int totalBooks=0,row,col,i;
    Thread threadRefresh,threadFetchRow,threadDelete;
    DefaultTableModel model;
    String bookId;
   
    
    
    public ManageBook(Dashboard dashboard) {
        initComponents();
        threadRefresh=null;
        threadFetchRow=null;
        busLogic=new BusinessLogic();
        idAndImage=new LinkedList<>();
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
    void fetchBooks()
    {
        if(threadRefresh==null)
        {
            System.out.println("fetch:");
            btnRefreshClick=true;
            threadRefresh=new Thread(this);
            threadRefresh.start();
        }
    }
    
    void deleteOneBook(String id)
    {
        if(threadDelete==null)
        {        System.out.println("delete:");

            bookId=id;
            btnDeleteClick=true;
            threadDelete=new Thread(this);
            threadDelete.start();
        }
    }
    
    @Override
    public void run()
    {
        if(btnRefreshClick==true);
        {
            System.out.println("fetch running:");
            btnRefreshClick=false;
            pnlBtnRefresh2.setEnabled(false);
            lblLoading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gif/blackFastLoading.gif")));
            fetchBooksToTable();
            pnlBtnRefresh2.setEnabled(true);
        }
        if(btnDeleteClick==true)
        {
            System.out.println("delete running:");
            btnDeleteClick=false;
            pnlBtnDelete.setEnabled(false);
            lblBuffering.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gif/blackFastLoading.gif")));
            deleteBookNow(bookId);
            pnlBtnDelete.setEnabled(true);
        }
       
    }
    
    void deleteBookNow(String id)
    {
        btnDeleteClick=false;
        if(!threadDelete.interrupted())
        {
            if(busLogic.deleteBook(id)==true)
            {
                lblBuffering.setIcon(null);
                JOptionPane.showMessageDialog(this, "Deleted Successfully.");
            }
        }
        threadDelete.interrupt();
        threadDelete=null;
        btnDeleteClick=false;
        btnRefreshClick=false;
        fetchBooks();
    }
    void fetchBooksToTable()
    {
        btnRefreshClick=false;
           if(!threadRefresh.interrupted())
           {
                model=(DefaultTableModel)bookTable.getModel();
                    model.setRowCount(0);
                    idAndImage.clear();
                    try
                    {
                        rs=busLogic.fetchBooks();
                        while (rs.next())
                        {
                            Object o[]={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13)};
                            model.addRow(o);
                            BookImageObject obj=new BookImageObject();
                            obj.setId(rs.getString(1));
                            obj.setImg(rs.getBytes(14));
                            idAndImage.add(obj);
                        }
                        busLogic.disconnectNow();
                        lblLoading.setIcon(null);
                    }
                    catch(SQLException e)
                    {
                        
                    }
           }
           threadRefresh.interrupt();
           threadRefresh=null;
           btnRefreshClick=false;
                    
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
        jScrollPane2 = new javax.swing.JScrollPane();
        bookTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lblImg = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        lblAuthor = new javax.swing.JLabel();
        lblCat = new javax.swing.JLabel();
        lblLanguage = new javax.swing.JLabel();
        lblIsbn = new javax.swing.JLabel();
        lblPublisher = new javax.swing.JLabel();
        lblEdition = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        pnlBtnDelete = new javax.swing.JPanel();
        lblDelete = new javax.swing.JLabel();
        pnlBtnEdit = new javax.swing.JPanel();
        lblEdit = new javax.swing.JLabel();
        pnlBtnCancel = new javax.swing.JPanel();
        lblCancel = new javax.swing.JLabel();
        lblBuffering = new javax.swing.JLabel();

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        bookTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Title", "Author", "Edition", "Category", "Language", "ISBN", "Publisher", "Publication Date", "Price", "Total Books", "Rest Books", "Summary"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        bookTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bookTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(bookTable);
        if (bookTable.getColumnModel().getColumnCount() > 0) {
            bookTable.getColumnModel().getColumn(0).setResizable(false);
            bookTable.getColumnModel().getColumn(9).setResizable(false);
            bookTable.getColumnModel().getColumn(10).setResizable(false);
            bookTable.getColumnModel().getColumn(11).setResizable(false);
            bookTable.getColumnModel().getColumn(12).setResizable(false);
        }

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 17)); // NOI18N
        jLabel1.setText("Book Details");

        lblTitle.setFont(new java.awt.Font("Ubuntu", 0, 23)); // NOI18N
        lblTitle.setText("Title");

        lblAuthor.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        lblAuthor.setText("Author      : ");

        lblCat.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        lblCat.setText("Category : ");

        lblLanguage.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        lblLanguage.setText("Language :");

        lblIsbn.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        lblIsbn.setText("ISBN           :");

        lblPublisher.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        lblPublisher.setText("Publisher  :");

        lblEdition.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        lblEdition.setText("Edition       :");

        lblPrice.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        lblPrice.setText("Price          :");

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
        lblDelete.setText("Delete Selected");
        lblDelete.setToolTipText("Click here to refresh table data");
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
            .addComponent(lblDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlBtnDeleteLayout.setVerticalGroup(
            pnlBtnDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBtnDeleteLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblDelete))
        );

        pnlBtnEdit.setBackground(new java.awt.Color(248, 247, 247));
        pnlBtnEdit.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnlBtnEdit.setToolTipText("Add User");
        pnlBtnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnEditMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlBtnEditMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlBtnEditMouseEntered(evt);
            }
        });

        lblEdit.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblEdit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/update_B.png"))); // NOI18N
        lblEdit.setText("Edit Selected");
        lblEdit.setToolTipText("Click here to refresh table data");
        lblEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEditMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblEditMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblEditMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout pnlBtnEditLayout = new javax.swing.GroupLayout(pnlBtnEdit);
        pnlBtnEdit.setLayout(pnlBtnEditLayout);
        pnlBtnEditLayout.setHorizontalGroup(
            pnlBtnEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlBtnEditLayout.setVerticalGroup(
            pnlBtnEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBtnEditLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblEdit))
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
        lblCancel.setToolTipText("Click here to refresh table data");
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBtnCancelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblCancel))
        );

        javax.swing.GroupLayout pnlMainContentLayout = new javax.swing.GroupLayout(pnlMainContent);
        pnlMainContent.setLayout(pnlMainContentLayout);
        pnlMainContentLayout.setHorizontalGroup(
            pnlMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jLabel1)
                    .addGroup(pnlMainContentLayout.createSequentialGroup()
                        .addComponent(lblImg, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlMainContentLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(pnlMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlMainContentLayout.createSequentialGroup()
                                        .addComponent(lblIsbn)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(pnlMainContentLayout.createSequentialGroup()
                                        .addGroup(pnlMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblCat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(pnlMainContentLayout.createSequentialGroup()
                                                .addGroup(pnlMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblPublisher, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(lblEdition, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                                                    .addComponent(lblPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(6, 6, 6))
                                            .addComponent(lblAuthor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblLanguage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(79, 79, 79)))
                                .addGroup(pnlMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(pnlBtnEdit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pnlBtnDelete, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pnlBtnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(pnlMainContentLayout.createSequentialGroup()
                                        .addGap(48, 48, 48)
                                        .addComponent(lblBuffering)))
                                .addGap(20, 20, 20)))))
                .addContainerGap())
        );
        pnlMainContentLayout.setVerticalGroup(
            pnlMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainContentLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainContentLayout.createSequentialGroup()
                        .addComponent(lblTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlMainContentLayout.createSequentialGroup()
                                .addComponent(lblAuthor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblCat)
                                    .addComponent(lblBuffering, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblLanguage)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblIsbn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPublisher)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblEdition)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPrice))
                            .addGroup(pnlMainContentLayout.createSequentialGroup()
                                .addComponent(pnlBtnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlBtnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pnlBtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(lblImg, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 21, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(pnlMainContent);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHead, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlHead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lblRefresh2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRefresh2MouseClicked
            fetchBooks();
    }//GEN-LAST:event_lblRefresh2MouseClicked

    private void lblRefresh2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRefresh2MouseExited
        panelMouseExit(pnlBtnRefresh2, lblRefresh2,"/Icons/refresh_B.png");
    }//GEN-LAST:event_lblRefresh2MouseExited

    private void lblRefresh2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRefresh2MouseEntered
        panelMouseEnter(pnlBtnRefresh2, lblRefresh2,"/Icons/refresh_W.png");        // TODO add your handling code here:
    }//GEN-LAST:event_lblRefresh2MouseEntered

    private void pnlBtnRefresh2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnRefresh2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnRefresh2MouseClicked

    private void pnlBtnRefresh2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnRefresh2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnRefresh2MouseExited

    private void pnlBtnRefresh2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnRefresh2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnRefresh2MouseEntered

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnSearchingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchingActionPerformed
          // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchingActionPerformed

    private void bookTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookTableMouseClicked
           row = bookTable.getSelectedRow();
            col =bookTable.getColumnCount();
            Object[] val = new Object[col];
            for( i = 0; i < col; i++) {
                val[i]=bookTable.getValueAt(row, i);
            }
                    
                    //set data
                    bookId=(String) val[0];
                    lblTitle.setText((String) val[1]);
                    lblAuthor.setText("Author      : "+(String) val[2]);
                    lblCat.setText("Category : "+(String) val[4]);
                    lblLanguage.setText("Language : "+(String) val[5]);
                    lblIsbn.setText("ISBN           : "+(String) val[6]);
                    lblPublisher.setText("Publisher  : "+(String) val[7]);
                    lblEdition.setText("Edition       : "+(String) val[3]);
                    lblPrice.setText("Price          : "+(String) val[9]);
                    
                    for(BookImageObject obj:idAndImage)
                    {
                        if(obj.getId().equals(bookId))
                        {
                            ImageIcon image = new ImageIcon(obj.getImg());
                            Image im = image.getImage();
                            Image myImg = im.getScaledInstance(204,233,Image.SCALE_SMOOTH);
                            ImageIcon newImage = new ImageIcon(myImg);
                            lblImg.setIcon(newImage);
                            break;
                        }
                    }
                    
    }//GEN-LAST:event_bookTableMouseClicked
    void resetAll()
    {
        bookId=null;
        lblTitle.setText("Title");
        lblAuthor.setText("Author      : ");
        lblCat.setText("Category : ");
        lblLanguage.setText("Language : ");
        lblIsbn.setText("ISBN           : ");
        lblPublisher.setText("Publisher  : ");
        lblEdition.setText("Edition       : ");
        lblPrice.setText("Price          : ");
    }
    private void lblDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDeleteMouseClicked
        if(bookId!=null)
        {
            if(JOptionPane.showConfirmDialog(this,"Do you really want to delete ?")==0 )
            {
                deleteOneBook(bookId);
                resetAll();
                //fetchBooks();
            }
            else
            {
                bookId=null;
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Please select book from table.");
        }
        
    }//GEN-LAST:event_lblDeleteMouseClicked

    private void lblDeleteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDeleteMouseExited
        panelMouseExit(pnlBtnDelete, lblDelete,"/Icons/delete_B.png");
    }//GEN-LAST:event_lblDeleteMouseExited

    private void lblDeleteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDeleteMouseEntered
       
        panelMouseEnter(pnlBtnDelete, lblDelete,"/Icons/delete_W.png");             // TODO add your handling code here:
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

    private void lblEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEditMouseClicked

    private void lblEditMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditMouseExited
       panelMouseExit(pnlBtnEdit, lblEdit,"/Icons/update_B.png");
    }//GEN-LAST:event_lblEditMouseExited

    private void lblEditMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditMouseEntered
       panelMouseEnter(pnlBtnEdit, lblEdit,"/Icons/update_W.png");
    }//GEN-LAST:event_lblEditMouseEntered

    private void pnlBtnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnEditMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnEditMouseClicked

    private void pnlBtnEditMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnEditMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnEditMouseExited

    private void pnlBtnEditMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnEditMouseEntered
        resetAll();
    }//GEN-LAST:event_pnlBtnEditMouseEntered

    private void lblCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCancelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblCancelMouseClicked

    private void lblCancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCancelMouseExited
       panelMouseExit(pnlBtnCancel, lblCancel,"/Icons/cancel_W.png");
    }//GEN-LAST:event_lblCancelMouseExited

    private void lblCancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCancelMouseEntered
        panelMouseEnter(pnlBtnCancel, lblCancel,"/Icons/cancel_B.png");
    }//GEN-LAST:event_lblCancelMouseEntered

    private void pnlBtnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnCancelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnCancelMouseClicked

    private void pnlBtnCancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnCancelMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnCancelMouseExited

    private void pnlBtnCancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnCancelMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlBtnCancelMouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable bookTable;
    private javax.swing.JButton btnSearching;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAuthor;
    private javax.swing.JLabel lblBuffering;
    private javax.swing.JLabel lblCancel;
    private javax.swing.JLabel lblCat;
    private javax.swing.JLabel lblDelete;
    private javax.swing.JLabel lblEdit;
    private javax.swing.JLabel lblEdition;
    private javax.swing.JLabel lblImg;
    private javax.swing.JLabel lblIsbn;
    private javax.swing.JLabel lblLanguage;
    private javax.swing.JLabel lblLoading;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblPublisher;
    private javax.swing.JLabel lblRefresh2;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlBtnCancel;
    private javax.swing.JPanel pnlBtnDelete;
    private javax.swing.JPanel pnlBtnEdit;
    private javax.swing.JPanel pnlBtnRefresh2;
    private javax.swing.JPanel pnlHead;
    private javax.swing.JPanel pnlMainContent;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
