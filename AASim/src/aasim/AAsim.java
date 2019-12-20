package aasim;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;

public class AAsim extends javax.swing.JFrame implements TableModelListener{
  public AAsim() {
    List ATKTableVals;
    initComponents();
    ATKModel = (DefaultTableModel) ATKtable.getModel();
    DEFModel = (DefaultTableModel) DEFtable.getModel();
    ResultsModel = (DefaultTableModel) ResultsTable.getModel();
    
    ATKModel.addTableModelListener(this);
    DEFModel.addTableModelListener(this);
    ResultsModel.addTableModelListener(this);
    
    setUpTable(ATKModel);
    setUpTable(DEFModel);
  }
  
  @Override public void tableChanged(TableModelEvent e){
    if(e.getSource() == ATKModel){
      System.out.println("(" + e.getFirstRow() + ", " + e.getColumn() + ") -> " + ATKModel.getValueAt(e.getFirstRow(), e.getColumn()));
    } else if(e.getSource() == DEFModel){
      System.out.println("(" + e.getFirstRow() + ", " + e.getColumn() + ") -> " + ATKModel.getValueAt(e.getFirstRow(), e.getColumn()));
    }
  }
  
  public void setUpTable(DefaultTableModel model){
    model.setValueAt("INF", 0, 0);
    model.setValueAt("ART", 1, 0);
    model.setValueAt("TANK", 2, 0);
    model.setValueAt("FTR", 3, 0);
    model.setValueAt("BMBR", 4, 0);
    model.setValueAt("AC", 5, 0);
    model.setValueAt("BS", 6, 0);
    model.setValueAt("Cruise", 7, 0);
    model.setValueAt("DEST", 8, 0);
    model.setValueAt("SUB", 9, 0);
    model.setValueAt("TRAN", 10, 0);    
  }

  
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    ATKpanel = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    ATKtable = new javax.swing.JTable();
    DEFpanel = new javax.swing.JPanel();
    jScrollPane2 = new javax.swing.JScrollPane();
    DEFtable = new javax.swing.JTable();
    ResultsPanel = new javax.swing.JPanel();
    jScrollPane3 = new javax.swing.JScrollPane();
    ResultsTable = new javax.swing.JTable();
    SettingsPanel = new javax.swing.JPanel();
    jPanel1 = new javax.swing.JPanel();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("AA Battle Simulator");
    setPreferredSize(new java.awt.Dimension(765, 670));

    ATKpanel.setBorder(javax.swing.BorderFactory.createTitledBorder("ATK"));
    ATKpanel.setName("ATKpanel"); // NOI18N

    ATKtable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null}
      },
      new String [] {
        "Unit", "Count"
      }
    ) {
      Class[] types = new Class [] {
        java.lang.String.class, java.lang.Integer.class
      };
      boolean[] canEdit = new boolean [] {
        false, true
      };

      public Class getColumnClass(int columnIndex) {
        return types [columnIndex];
      }

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
      }
    });
    jScrollPane1.setViewportView(ATKtable);
    ATKtable.getAccessibleContext().setAccessibleName("ATKTable");

    javax.swing.GroupLayout ATKpanelLayout = new javax.swing.GroupLayout(ATKpanel);
    ATKpanel.setLayout(ATKpanelLayout);
    ATKpanelLayout.setHorizontalGroup(
      ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ATKpanelLayout.createSequentialGroup()
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(352, 352, 352))
    );
    ATKpanelLayout.setVerticalGroup(
      ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(ATKpanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(58, Short.MAX_VALUE))
    );

    DEFpanel.setBorder(javax.swing.BorderFactory.createTitledBorder("DEF"));
    DEFpanel.setName(""); // NOI18N

    DEFtable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null}
      },
      new String [] {
        "Unit", "Count"
      }
    ) {
      Class[] types = new Class [] {
        java.lang.String.class, java.lang.Integer.class
      };
      boolean[] canEdit = new boolean [] {
        false, true
      };

      public Class getColumnClass(int columnIndex) {
        return types [columnIndex];
      }

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
      }
    });
    jScrollPane2.setViewportView(DEFtable);

    javax.swing.GroupLayout DEFpanelLayout = new javax.swing.GroupLayout(DEFpanel);
    DEFpanel.setLayout(DEFpanelLayout);
    DEFpanelLayout.setHorizontalGroup(
      DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(DEFpanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(15, Short.MAX_VALUE))
    );
    DEFpanelLayout.setVerticalGroup(
      DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(DEFpanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    ResultsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Results"));

    ResultsTable.setAutoCreateRowSorter(true);
    ResultsTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
      },
      new String [] {
        "Winner", "INF", "ART", "TANK", "FTR", "BMBR", "AC", "BS", "CRUISE", "DEST", "SUB", "TRAN", "%", "A IPC", "D IPC"
      }
    ) {
      Class[] types = new Class [] {
        java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Integer.class
      };
      boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
      };

      public Class getColumnClass(int columnIndex) {
        return types [columnIndex];
      }

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
      }
    });
    jScrollPane3.setViewportView(ResultsTable);

    javax.swing.GroupLayout ResultsPanelLayout = new javax.swing.GroupLayout(ResultsPanel);
    ResultsPanel.setLayout(ResultsPanelLayout);
    ResultsPanelLayout.setHorizontalGroup(
      ResultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(ResultsPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
        .addContainerGap())
    );
    ResultsPanelLayout.setVerticalGroup(
      ResultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ResultsPanelLayout.createSequentialGroup()
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(210, 210, 210))
    );

    SettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Settings"));

    javax.swing.GroupLayout SettingsPanelLayout = new javax.swing.GroupLayout(SettingsPanel);
    SettingsPanel.setLayout(SettingsPanelLayout);
    SettingsPanelLayout.setHorizontalGroup(
      SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 0, Short.MAX_VALUE)
    );
    SettingsPanelLayout.setVerticalGroup(
      SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 111, Short.MAX_VALUE)
    );

    jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Summary"));

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 0, Short.MAX_VALUE)
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 0, Short.MAX_VALUE)
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addComponent(ResultsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(ATKpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(DEFpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(SettingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        .addContainerGap(27, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addComponent(ATKpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(DEFpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(SettingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(ResultsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(96, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  
  public static void main(String[] args) {
        AAsim frame = new AAsim();
        frame.setSize(765,620);
        frame.setVisible(true);
        
    }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel ATKpanel;
  private javax.swing.JTable ATKtable;
  private javax.swing.JPanel DEFpanel;
  private javax.swing.JTable DEFtable;
  private javax.swing.JPanel ResultsPanel;
  private javax.swing.JTable ResultsTable;
  private javax.swing.JPanel SettingsPanel;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JScrollPane jScrollPane3;
  // End of variables declaration//GEN-END:variables
  private DefaultTableModel ATKModel;
  private DefaultTableModel DEFModel;
  private DefaultTableModel ResultsModel;
  private List ATK
}
