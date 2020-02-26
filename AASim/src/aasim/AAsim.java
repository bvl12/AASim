package aasim;

import static java.lang.Math.round;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import javax.swing.DropMode;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;

public class AAsim extends javax.swing.JFrame implements TableModelListener{
  private static final int INF = 0;
  private static final int ART = 1;
  private static final int TANK = 2;
  private static final int FTR = 3;
  private static final int BMBR = 4;
  private static final int AC = 5;
  private static final int BS = 6;
  private static final int CRSR = 7;
  private static final int DEST = 8;
  private static final int SUB = 9;
  private static final int TRAN = 10;
  private static final int WBS = 11;

  public AAsim() {
    initComponents();
    ATKModel = (DefaultTableModel) ATKtable.getModel();
    DEFModel = (DefaultTableModel) DEFtable.getModel();
    ResultsModel = (DefaultTableModel) ResultsTable.getModel();
    SummaryModel = (DefaultTableModel) SummaryTable.getModel();
    SingleTOAModel = (DefaultTableModel) SingleTOATable.getModel();
    
    ATKModel.addTableModelListener(this);
    DEFModel.addTableModelListener(this);
    ResultsModel.addTableModelListener(this);

    OoLSetter.setDragEnabled(true);
    OoLSetter.setDropMode(DropMode.INSERT);
    
    SummaryModel.setValueAt("ATK", 0, 0);
    SummaryModel.setValueAt("DEF", 1, 0);
    
    setUpTable(ATKModel);
    setUpTable(DEFModel);
  }
  
  @Override public void tableChanged(TableModelEvent e){
    if(e.getColumn() == 0)
      return;
    
    try{
      if(e.getSource() == ATKModel){
        for(int i = 0; i < atkUnits.length - 1; i++){
          if(ATKModel.getValueAt(i, 1) instanceof Integer)
            atkUnits[i] = (int)ATKModel.getValueAt(i, 1);
          else
            atkUnits[i] = 0;
          sim(itercount);
          simsingle(itercount);
        }
      } else if(e.getSource() == DEFModel){
        for(int i = 0; i < defUnits.length - 1; i++){
          if(DEFModel.getValueAt(i, 1) instanceof Integer)
            defUnits[i] = (int)DEFModel.getValueAt(i, 1);
          else
            defUnits[i] = 0;
          sim(itercount);
          simsingle(itercount);
        }
      }
    } catch(NumberFormatException ex){}
  }
  
  public void setUpTable(DefaultTableModel model){
    model.setValueAt("INF", 0, 0);
    model.setValueAt("ART", 1, 0);
    model.setValueAt("TANK", 2, 0);
    model.setValueAt("FTR", 3, 0);
    model.setValueAt("BMBR", 4, 0);
    model.setValueAt("AC", 5, 0);
    model.setValueAt("BS", 6, 0);
    model.setValueAt("CRSR", 7, 0);
    model.setValueAt("DEST", 8, 0);
    model.setValueAt("SUB", 9, 0);
    model.setValueAt("TRAN", 10, 0);    
  }

  public boolean hasGroundUnits(int[] units){
    return units[INF] > 0 || units[ART] > 0 || units[FTR] > 0;
  }
  
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jScrollPane5 = new javax.swing.JScrollPane();
    jTable1 = new javax.swing.JTable();
    jScrollPane6 = new javax.swing.JScrollPane();
    jTable2 = new javax.swing.JTable();
    ATKpanel = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    ATKtable = new javax.swing.JTable();
    isAmphibiousAssault = new javax.swing.JCheckBox();
    DEFpanel = new javax.swing.JPanel();
    jScrollPane2 = new javax.swing.JScrollPane();
    DEFtable = new javax.swing.JTable();
    hasAAGun = new javax.swing.JCheckBox();
    ResultsPanel = new javax.swing.JPanel();
    jTabbedPane1 = new javax.swing.JTabbedPane();
    jScrollPane3 = new javax.swing.JScrollPane();
    ResultsTable = new javax.swing.JTable();
    jScrollPane8 = new javax.swing.JScrollPane();
    SingleTOATable = new javax.swing.JTable();
    SettingsPanel = new javax.swing.JPanel();
    resetButton = new javax.swing.JButton();
    jScrollPane7 = new javax.swing.JScrollPane();
    OoLSetter = new javax.swing.JList<>();
    jPanel1 = new javax.swing.JPanel();
    jScrollPane4 = new javax.swing.JScrollPane();
    SummaryTable = new javax.swing.JTable();

    jTable1.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
      },
      new String [] {
        "Title 1", "Title 2", "Title 3", "Title 4"
      }
    ));
    jScrollPane5.setViewportView(jTable1);

    jTable2.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
      },
      new String [] {
        "Title 1", "Title 2", "Title 3", "Title 4"
      }
    ));
    jScrollPane6.setViewportView(jTable2);

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

    isAmphibiousAssault.setText("Amphibious Assault");
    isAmphibiousAssault.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        isAmphibiousAssaultActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout ATKpanelLayout = new javax.swing.GroupLayout(ATKpanel);
    ATKpanel.setLayout(ATKpanelLayout);
    ATKpanelLayout.setHorizontalGroup(
      ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ATKpanelLayout.createSequentialGroup()
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(391, 391, 391))
      .addGroup(ATKpanelLayout.createSequentialGroup()
        .addComponent(isAmphibiousAssault)
        .addGap(0, 0, Short.MAX_VALUE))
    );
    ATKpanelLayout.setVerticalGroup(
      ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ATKpanelLayout.createSequentialGroup()
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(isAmphibiousAssault)
        .addGap(23, 23, 23))
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

    hasAAGun.setText("AA Gun");
    hasAAGun.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        hasAAGunActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout DEFpanelLayout = new javax.swing.GroupLayout(DEFpanel);
    DEFpanel.setLayout(DEFpanelLayout);
    DEFpanelLayout.setHorizontalGroup(
      DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(DEFpanelLayout.createSequentialGroup()
        .addGroup(DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(DEFpanelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(DEFpanelLayout.createSequentialGroup()
            .addGap(36, 36, 36)
            .addComponent(hasAAGun)))
        .addContainerGap(15, Short.MAX_VALUE))
    );
    DEFpanelLayout.setVerticalGroup(
      DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(DEFpanelLayout.createSequentialGroup()
        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(hasAAGun))
    );

    ResultsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Results"));

    ResultsTable.setAutoCreateRowSorter(true);
    ResultsTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {

      },
      new String [] {
        "Winner", "INF", "ART", "TANK", "FTR", "BMBR", "AC", "BS", "CRSR", "DEST", "SUB", "TRAN", "%", "C %", "A IPC", "D IPC"
      }
    ) {
      Class[] types = new Class [] {
        java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Integer.class
      };
      boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
      };

      public Class getColumnClass(int columnIndex) {
        return types [columnIndex];
      }

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
      }
    });
    jScrollPane3.setViewportView(ResultsTable);

    jTabbedPane1.addTab("Overall Results", jScrollPane3);

    SingleTOATable.setAutoCreateRowSorter(true);
    SingleTOATable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {

      },
      new String [] {
        "%", "C %", "A IPC", "D IPC"
      }
    ) {
      Class[] types = new Class [] {
        java.lang.Float.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Integer.class
      };
      boolean[] canEdit = new boolean [] {
        false, false, false, false
      };

      public Class getColumnClass(int columnIndex) {
        return types [columnIndex];
      }

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
      }
    });
    jScrollPane8.setViewportView(SingleTOATable);

    jTabbedPane1.addTab("Single Turn of Attack", jScrollPane8);

    javax.swing.GroupLayout ResultsPanelLayout = new javax.swing.GroupLayout(ResultsPanel);
    ResultsPanel.setLayout(ResultsPanelLayout);
    ResultsPanelLayout.setHorizontalGroup(
      ResultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ResultsPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jTabbedPane1)
        .addContainerGap())
    );
    ResultsPanelLayout.setVerticalGroup(
      ResultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(ResultsPanelLayout.createSequentialGroup()
        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
        .addContainerGap())
    );

    SettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Settings"));

    resetButton.setText("Reset All Units");
    resetButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        resetButtonActionPerformed(evt);
      }
    });

    OoLSetter.setModel(new javax.swing.AbstractListModel<String>() {
      String[] strings = { "INF", "ART", "TANK", "FTR", "BMBR", "AC", "BS", "CRSR", "DEST", "SUB", "TRAN" };
      public int getSize() { return strings.length; }
      public String getElementAt(int i) { return strings[i]; }
    });
    OoLSetter.setToolTipText("");
    jScrollPane7.setViewportView(OoLSetter);

    javax.swing.GroupLayout SettingsPanelLayout = new javax.swing.GroupLayout(SettingsPanel);
    SettingsPanel.setLayout(SettingsPanelLayout);
    SettingsPanelLayout.setHorizontalGroup(
      SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(SettingsPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(resetButton)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );
    SettingsPanelLayout.setVerticalGroup(
      SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(SettingsPanelLayout.createSequentialGroup()
        .addGroup(SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(SettingsPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(resetButton)
            .addGap(0, 67, Short.MAX_VALUE))
          .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        .addContainerGap())
    );

    jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Summary"));

    SummaryTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null, null, null},
        {null, null, null, null}
      },
      new String [] {
        "", "Win %", "Avg. IPC Cost", "Ground Units %"
      }
    ) {
      Class[] types = new Class [] {
        java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class
      };
      boolean[] canEdit = new boolean [] {
        false, false, false, false
      };

      public Class getColumnClass(int columnIndex) {
        return types [columnIndex];
      }

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
      }
    });
    jScrollPane4.setViewportView(SummaryTable);

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(ResultsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(ATKpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(DEFpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(SettingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
              .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(ATKpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(SettingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addComponent(DEFpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(ResultsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void isAmphibiousAssaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isAmphibiousAssaultActionPerformed
    amphibiousAssault = isAmphibiousAssault.isSelected();
    sim(itercount);
    simsingle(itercount);
  }//GEN-LAST:event_isAmphibiousAssaultActionPerformed

  private void hasAAGunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hasAAGunActionPerformed
    antiAir = hasAAGun.isSelected();
    sim(itercount);
    simsingle(itercount);
  }//GEN-LAST:event_hasAAGunActionPerformed

  private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
    ATKtable.changeSelection(0, 1, false, false);
    DEFtable.changeSelection(0, 1, false, false);
    isAmphibiousAssault.setSelected(false);
    hasAAGun.setSelected(false);
    amphibiousAssault = false;
    antiAir = false;
    for(int i = 0; i < atkUnits.length - 1; i++){
      ATKModel.setValueAt(null, i, 1);
      DEFModel.setValueAt(null, i, 1);
    }
  }//GEN-LAST:event_resetButtonActionPerformed

  public static void main(String[] args) {
        AAsim frame = new AAsim();
        frame.setSize(765,575);
        frame.setVisible(true);
        
  }
  
  public BattleResult simbat(int[] aarr, int[] darr){
        Units a = new Units(aarr);
        Units d = new Units(darr);
        int Aloss = 0;
        int Dloss = 0;
        int AIPC = 0;
        int DIPC = 0;
        boolean AAdestroyed = false;
        if(antiAir){
            for(int i = 0; i < a.units[FTR]; i++){
                if(rollDie()==1)
                    Aloss++;
            }
            a.units[FTR] -= Aloss;
            a.count -= Aloss;
            AIPC += IPCvals[FTR] * Aloss;
           
            Aloss = 0;
            for(int i = 0; i < a.units[BMBR]; i++)
                if(rollDie()==1)
                    Aloss++;
            a.units[BMBR] -= Aloss;
            a.count -= Aloss;
            AIPC += IPCvals[BMBR] * Aloss;
            Aloss = 0;
        }
        if(amphibiousAssault){
            for(int i = 0; i < a.units[BS] + a.units[WBS]; i++){
                if(rollDie() < atkvals[BS])
                    Dloss++;
            }
            for(int i = 0; i < a.units[CRSR]; i++){
                if(rollDie() < atkvals[CRSR])
                    Dloss++;
            }
            //sanitize results by removing sea units because this is a land battle
            for(int i = 5; i < 11; i++){
                a.count -= a.units[i];
                a.units[i] = 0;
            }
        }
        while(true){
            for(int i = 0; i < 12; i++){
                for(int j = 0; j < a.units[i]; j++){
                    if(i == INF && j < a.units[ART]) {//INF get +1 if they're supported by artillery on ATK
                        if(rollDie() < atkvals[i] + 1)
                            Dloss++;
                } else if(rollDie() < atkvals[i])
                    Dloss++;
                }
                for(int j = 0; j < d.units[i]; j++){
                    if(rollDie() < defvals[i])
                        Aloss++;
                }
            }
            
            for(int i = 0; i < Aloss; i++){
                if(a.units[BS] > 0){
                    a.units[BS]--;
                    a.units[lossIndex(OoL, WBS)]++;
                }
                else if(a.units[OoL[INF]] > 0){
                    a.units[OoL[INF]]--;
                    a.count--;
                    AIPC += IPCvals[OoL[INF]];
                }
                else if(a.units[OoL[ART]] > 0){
                    a.units[OoL[ART]]--;
                    a.count--;
                    AIPC += IPCvals[OoL[ART]];
                }
                else if(a.units[OoL[TANK]] > 0){
                    a.units[OoL[TANK]]--;
                    a.count--;
                    AIPC += IPCvals[OoL[TANK]];
                }
                else if(a.units[OoL[FTR]] > 0){
                    a.units[OoL[FTR]]--;
                    a.count--;
                    AIPC += IPCvals[OoL[FTR]];
                }
                else if(a.units[OoL[BMBR]] > 0){
                    a.units[OoL[BMBR]]--;
                    a.count--;
                    AIPC += IPCvals[OoL[BMBR]];
                }
                else if(a.units[OoL[AC]] > 0){
                    a.units[OoL[AC]]--;
                    a.count--;
                    AIPC += IPCvals[OoL[AC]];
                }
                else if(a.units[OoL[BS]] > 0){
                    a.units[OoL[BS]]--;
                    a.count--;
                    AIPC += IPCvals[OoL[BS]];
                }
                else if(a.units[OoL[CRSR]] > 0){
                    a.units[OoL[CRSR]]--;
                    a.count--;
                    AIPC += IPCvals[OoL[CRSR]];
                }
                else if(a.units[OoL[DEST]] > 0){
                    a.units[OoL[DEST]]--;
                    a.count--;
                    AIPC += IPCvals[OoL[DEST]];
                }
                else if(a.units[OoL[SUB]] > 0){
                    a.units[OoL[SUB]]--;
                    a.count--;
                    AIPC += IPCvals[OoL[SUB]];
                }
                else if(a.units[OoL[TRAN]] > 0){
                    a.units[OoL[TRAN]]--;
                    a.count--;
                    AIPC += IPCvals[OoL[TRAN]];
                }
            }
            for(int i = 0; i < Dloss; i++){
                if(d.units[BS] > 0){
                    d.units[BS]--;
                    d.units[lossIndex(OoL,WBS)]++;
                }
                else if(antiAir && !AAdestroyed && d.count == 1){
                  AAdestroyed = true;
                  DIPC += AAIPC;
                }
                else if(d.units[OoL[INF]] > 0){
                    d.units[OoL[INF]]--;
                    d.count--;
                    DIPC += IPCvals[OoL[INF]];
                }
                else if(d.units[OoL[ART]] > 0){
                    d.units[OoL[ART]]--;
                    d.count--;
                    DIPC += IPCvals[OoL[ART]];
                }
                else if(d.units[OoL[TANK]] > 0){
                    d.units[OoL[TANK]]--;
                    d.count--;
                    DIPC += IPCvals[OoL[TANK]];
                }
                else if(d.units[OoL[FTR]] > 0){
                    d.units[OoL[FTR]]--;
                    d.count--;
                    DIPC += IPCvals[OoL[FTR]];
                }
                else if(d.units[OoL[BMBR]] > 0){
                    d.units[OoL[BMBR]]--;
                    d.count--;
                    DIPC += IPCvals[OoL[BMBR]];
                }
                else if(d.units[OoL[AC]] > 0){
                    d.units[OoL[AC]]--;
                    d.count--;
                    DIPC += IPCvals[OoL[AC]];
                }
                else if(d.units[OoL[BS]] > 0){
                    d.units[OoL[BS]]--;
                    d.count--;
                    DIPC += IPCvals[OoL[BS]];
                }
                else if(d.units[OoL[CRSR]] > 0){
                    d.units[OoL[CRSR]]--;
                    d.count--;
                    DIPC += IPCvals[OoL[CRSR]];
                }
                else if(d.units[OoL[DEST]] > 0){
                    d.units[OoL[DEST]]--;
                    d.count--;
                    DIPC += IPCvals[OoL[DEST]];
                }
                else if(d.units[OoL[SUB]] > 0){
                    d.units[OoL[SUB]]--;
                    d.count--;
                    DIPC += IPCvals[OoL[SUB]];
                }
                else if(d.units[OoL[TRAN]] > 0){
                    d.units[OoL[TRAN]]--;
                    d.count--;
                    DIPC += IPCvals[OoL[TRAN]];
                }
            }
            Aloss = 0;
            Dloss = 0;
            if(a.count <= 0){
                d.units[BS]+= d.units[lossIndex(OoL, WBS)];
                d.units[lossIndex(OoL, WBS)] = 0;
                if(d.count <= 0)
                    return new BattleResult(-1,d, AIPC, DIPC);
                return new BattleResult(0, d, AIPC, DIPC);
            }
            if(d.count <= 0){
                a.units[BS]+= d.units[lossIndex(OoL, WBS)];
                a.units[lossIndex(OoL, WBS)] = 0;
                if(a.count <= 0)
                    return new BattleResult(-1,d, AIPC, DIPC);
                return new BattleResult(1, a, AIPC, DIPC);
            }
        }
        
    }
  
  public SingleTurnResult simsinglebat(int[] aarr, int[] darr){
    Units a = new Units(aarr);
    Units d = new Units(darr);
    int Aloss = 0;
    int Dloss = 0;
    int AIPC = 0;
    int DIPC = 0;
    boolean AAdestroyed = false;
    if(antiAir){
      for(int i = 0; i < a.units[FTR]; i++){
        if(rollDie()==1)
          Aloss++;
      }
      a.units[FTR] -= Aloss;
      a.count -= Aloss;
      AIPC += IPCvals[FTR] * Aloss;

      Aloss = 0;
      for(int i = 0; i < a.units[BMBR]; i++)
        if(rollDie()==1)
          Aloss++;
      a.units[BMBR] -= Aloss;
      a.count -= Aloss;
      AIPC += IPCvals[BMBR] * Aloss;
      Aloss = 0;
    }
    if(amphibiousAssault){
        for(int i = 0; i < a.units[BS] + a.units[WBS]; i++){
          if(rollDie() < atkvals[BS])
              Dloss++;
      }
      for(int i = 0; i < a.units[CRSR]; i++){
          if(rollDie() < atkvals[CRSR])
              Dloss++;
      }
      //sanitize results by removing sea units because this is a land battle
      for(int i = 5; i < 11; i++){
          a.count -= a.units[i];
          a.units[i] = 0;
      }
    }
    for(int i = 0; i < 12; i++){
      for(int j = 0; j < a.units[i]; j++){
            if(i == INF && j < a.units[ART]) {//INF get +1 if they're supported by artillery on ATK
                if(rollDie() < atkvals[i] + 1)
                    Dloss++;
        } else if(rollDie() < atkvals[i])
            Dloss++;
        }
            for(int j = 0; j < d.units[i]; j++){
                if(rollDie() < defvals[i])
                    Aloss++;
            }
        }

        for(int i = 0; i < Aloss; i++){
            if(a.units[BS] > 0){
                a.units[BS]--;
                a.units[lossIndex(OoL, WBS)]++;
            }
            else if(a.units[OoL[INF]] > 0){
                a.units[OoL[INF]]--;
                a.count--;
                AIPC += IPCvals[OoL[INF]];
            }
            else if(a.units[OoL[ART]] > 0){
                a.units[OoL[ART]]--;
                a.count--;
                AIPC += IPCvals[OoL[ART]];
            }
            else if(a.units[OoL[TANK]] > 0){
                a.units[OoL[TANK]]--;
                a.count--;
                AIPC += IPCvals[OoL[TANK]];
            }
            else if(a.units[OoL[FTR]] > 0){
                a.units[OoL[FTR]]--;
                a.count--;
                AIPC += IPCvals[OoL[FTR]];
            }
            else if(a.units[OoL[BMBR]] > 0){
                a.units[OoL[BMBR]]--;
                a.count--;
                AIPC += IPCvals[OoL[BMBR]];
            }
            else if(a.units[OoL[AC]] > 0){
                a.units[OoL[AC]]--;
                a.count--;
                AIPC += IPCvals[OoL[AC]];
            }
            else if(a.units[OoL[BS]] > 0){
                a.units[OoL[BS]]--;
                a.count--;
                AIPC += IPCvals[OoL[BS]];
            }
            else if(a.units[OoL[CRSR]] > 0){
                a.units[OoL[CRSR]]--;
                a.count--;
                AIPC += IPCvals[OoL[CRSR]];
            }
            else if(a.units[OoL[DEST]] > 0){
                a.units[OoL[DEST]]--;
                a.count--;
                AIPC += IPCvals[OoL[DEST]];
            }
            else if(a.units[OoL[SUB]] > 0){
                a.units[OoL[SUB]]--;
                a.count--;
                AIPC += IPCvals[OoL[SUB]];
            }
            else if(a.units[OoL[TRAN]] > 0){
                a.units[OoL[TRAN]]--;
                a.count--;
                AIPC += IPCvals[OoL[TRAN]];
            }
        }
        for(int i = 0; i < Dloss; i++){
            if(d.units[BS] > 0){
                d.units[BS]--;
                d.units[lossIndex(OoL, WBS)]++;
            }
            else if(antiAir && !AAdestroyed && d.count == 1){
              AAdestroyed = true;
              DIPC += AAIPC;
            }
            else if(d.units[OoL[INF]] > 0){
                d.units[OoL[INF]]--;
                d.count--;
                DIPC += IPCvals[OoL[INF]];
            }
            else if(d.units[OoL[ART]] > 0){
                d.units[OoL[ART]]--;
                d.count--;
                DIPC += IPCvals[OoL[ART]];
            }
            else if(d.units[OoL[TANK]] > 0){
                d.units[OoL[TANK]]--;
                d.count--;
                DIPC += IPCvals[OoL[TANK]];
            }
            else if(d.units[OoL[FTR]] > 0){
                d.units[OoL[FTR]]--;
                d.count--;
                DIPC += IPCvals[OoL[FTR]];
            }
            else if(d.units[OoL[BMBR]] > 0){
                d.units[OoL[BMBR]]--;
                d.count--;
                DIPC += IPCvals[OoL[BMBR]];
            }
            else if(d.units[OoL[AC]] > 0){
                d.units[OoL[AC]]--;
                d.count--;
                DIPC += IPCvals[OoL[AC]];
            }
            else if(d.units[OoL[BS]] > 0){
                d.units[OoL[BS]]--;
                d.count--;
                DIPC += IPCvals[OoL[BS]];
            }
            else if(d.units[OoL[CRSR]] > 0){
                d.units[OoL[CRSR]]--;
                d.count--;
                DIPC += IPCvals[OoL[CRSR]];
            }
            else if(d.units[OoL[DEST]] > 0){
                d.units[OoL[DEST]]--;
                d.count--;
                DIPC += IPCvals[OoL[DEST]];
            }
            else if(d.units[OoL[SUB]] > 0){
                d.units[OoL[SUB]]--;
                d.count--;
                DIPC += IPCvals[OoL[SUB]];
            }
            else if(d.units[OoL[TRAN]] > 0){
                d.units[OoL[TRAN]]--;
                d.count--;
                DIPC += IPCvals[OoL[TRAN]];
            }
        }

        d.units[BS]+= d.units[lossIndex(OoL, WBS)];
        d.units[lossIndex(OoL, WBS)] = 0;

        a.units[BS]+= d.units[lossIndex(OoL, WBS)];
        a.units[lossIndex(OoL, WBS)] = 0;
        return new SingleTurnResult(a, d, AIPC, DIPC);
  }
  
  public int lossIndex(int[] order, int val){
    for(int i = 0; i < order.length; i++){
        if(order[i] == val)
            return i;
    }
    return -1;
  }
    
  public int rollDie(){
    return rand.nextInt(6);
  }
  
  public void sim(int iterations){
    ResultsModel.setRowCount(0);
    int[] a = Arrays.copyOf(atkUnits, atkUnits.length);
    int[] d = Arrays.copyOf(defUnits, defUnits.length);
    boolean inMap;
    results.clear();
    for(int n = 0; n < iterations; n++){
        BattleResult recentResult = simbat(a,d);
        inMap = false;
        for(BattleResult br:results.keySet()){
            if(br.equals(recentResult)){
                results.replace(br, results.get(br)+1);
                inMap = true;
                break;
            }
        }
        if(!inMap){
            results.put(recentResult, 1);
        }
    }
    BattleResult[] br = {};

    results = sortMap(results);

    int ATKwin = 0;
    int DEFwin = 0;
    int takenWground = 0;
    int heldWground = 0;
    int AIPCcost = 0;
    int DIPCcost = 0;
    BattleResult[] keys = results.keySet().toArray(br);
    ResultsModel.setRowCount(results.size());
    float cumulative_pct = 0;
    for(int i = 0; i < results.size(); i++){
        DIPCcost += results.get(keys[i]) * keys[i].DEFIPC;
        AIPCcost += results.get(keys[i]) * keys[i].ATKIPC;
        switch (keys[i].winner) {
            case 1:
                ResultsModel.setValueAt("ATK", i, 0);
                ATKwin += results.get(keys[i]);
                if(hasGroundUnits(keys[i].remainingunits.units)){
                    takenWground += results.get(keys[i]);
                }
                break;
            case 0:
                ResultsModel.setValueAt("DEF", i, 0);
                DEFwin += results.get(keys[i]);
                if(hasGroundUnits(keys[i].remainingunits.units)){
                    heldWground += results.get(keys[i]);
                }
                break;
            default:
                ResultsModel.setValueAt("DRAW", i, 0);
                break;
        }
        for(int j = 0; j < 11; j++)
            ResultsModel.setValueAt(keys[i].remainingunits.units[j], i, j+1);

        ResultsModel.setValueAt((float)round(results.get(keys[i])*1000/iterations)/10,i,12);
        cumulative_pct += (float)round(results.get(keys[i])*1000/iterations)/10;
        ResultsModel.setValueAt(cumulative_pct,i,13);
        ResultsModel.setValueAt(keys[i].ATKIPC,i,14);
        ResultsModel.setValueAt(keys[i].DEFIPC,i,15);
    }
    SummaryModel.setValueAt(round((ATKwin * 100 / iterations)), 0, 1);
    SummaryModel.setValueAt(round((DEFwin * 100 / iterations)), 1, 1);
    SummaryModel.setValueAt(((float)round(AIPCcost * 10 / iterations) / 10), 0, 2);
    SummaryModel.setValueAt((float)round(DIPCcost * 10 / iterations) / 10, 1, 2);
    SummaryModel.setValueAt(round(takenWground * 100 / iterations), 0, 3);
    SummaryModel.setValueAt(round(heldWground * 100 / iterations), 1, 3);
  }
  
  public void simsingle(int iterations){
    SingleTOAModel.setRowCount(0);
    int[] a = Arrays.copyOf(atkUnits, atkUnits.length);
    int[] d = Arrays.copyOf(defUnits, defUnits.length);
    boolean inMap;
    singleresults.clear();
    for(int n = 0; n < iterations; n++){
        SingleTurnResult recentResult = simsinglebat(a,d);
        inMap = false;
        for(SingleTurnResult br:singleresults.keySet()){
            if(br.equals(recentResult)){
                singleresults.replace(br, singleresults.get(br)+1);
                inMap = true;
                break;
            }
        }
        if(!inMap){
            singleresults.put(recentResult, 1);
        }
    }
    SingleTurnResult[] br = {};

    singleresults = sortMap(singleresults);

    int ATKwin = 0;
    int DEFwin = 0;
    int takenWground = 0;
    int heldWground = 0;
    int AIPCcost = 0;
    int DIPCcost = 0;
    SingleTurnResult[] keys = singleresults.keySet().toArray(br);
    SingleTOAModel.setRowCount(singleresults.size());
    float cumulative_pct = 0;
    for(int i = 0; i < singleresults.size(); i++){
        DIPCcost += singleresults.get(keys[i]) * keys[i].DEFIPC;
        AIPCcost += singleresults.get(keys[i]) * keys[i].ATKIPC;
        

        SingleTOAModel.setValueAt((float)round(singleresults.get(keys[i])*1000/iterations)/10,i,0);
        cumulative_pct += (float)round(singleresults.get(keys[i])*1000/iterations)/10;
        SingleTOAModel.setValueAt(cumulative_pct,i,1);
        SingleTOAModel.setValueAt(keys[i].ATKIPC,i,2);
        SingleTOAModel.setValueAt(keys[i].DEFIPC,i,3);
    }
  }
  
  public static <T> HashMap<T, Integer> sortMap(HashMap<T, Integer> hm){ 
        // Create a list from elements of HashMap 
        List<Map.Entry<T, Integer> > list = 
               new LinkedList<Map.Entry<T, Integer> >(hm.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<T, Integer> >() { 
            public int compare(Map.Entry<T, Integer> o1,  
                               Map.Entry<T, Integer> o2) 
            { 
                return (o2.getValue()).compareTo(o1.getValue()); 
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<T, Integer> temp = new LinkedHashMap<T, Integer>(); 
        for (Map.Entry<T, Integer> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    }
    

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel ATKpanel;
  private javax.swing.JTable ATKtable;
  private javax.swing.JPanel DEFpanel;
  private javax.swing.JTable DEFtable;
  private javax.swing.JList<String> OoLSetter;
  private javax.swing.JPanel ResultsPanel;
  private javax.swing.JTable ResultsTable;
  private javax.swing.JPanel SettingsPanel;
  private javax.swing.JTable SingleTOATable;
  private javax.swing.JTable SummaryTable;
  private javax.swing.JCheckBox hasAAGun;
  private javax.swing.JCheckBox isAmphibiousAssault;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JScrollPane jScrollPane3;
  private javax.swing.JScrollPane jScrollPane4;
  private javax.swing.JScrollPane jScrollPane5;
  private javax.swing.JScrollPane jScrollPane6;
  private javax.swing.JScrollPane jScrollPane7;
  private javax.swing.JScrollPane jScrollPane8;
  private javax.swing.JTabbedPane jTabbedPane1;
  private javax.swing.JTable jTable1;
  private javax.swing.JTable jTable2;
  private javax.swing.JButton resetButton;
  // End of variables declaration//GEN-END:variables
  private DefaultTableModel ATKModel;
  private DefaultTableModel DEFModel;
  private DefaultTableModel ResultsModel;
  private DefaultTableModel SummaryModel;
  private DefaultTableModel SingleTOAModel;
  private boolean amphibiousAssault = false;
  private boolean antiAir = false;
  private int itercount = 10000;
  private int[] atkvals = {1, 2, 3, 3, 4, 1, 4, 3, 2, 2, 0, 4};
  private int[] defvals = {2, 2, 3, 4, 1, 2, 4, 3, 2, 1, 0, 4};
  private int[] IPCvals = {3, 4, 5, 10, 12, 14, 20, 12, 8, 6, 7, 20};
  private int AAIPC = 6;
  private int[] defaultOoL = {0, 1, 9, 2, 10, 8, 3, 7, 4, 5, 11};
  private int[] OoL = Arrays.copyOf(defaultOoL, defaultOoL.length);
  private int[] atkUnits = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  private int[] defUnits = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  private HashMap<BattleResult, Integer> results = new HashMap<BattleResult, Integer>();
  private HashMap<SingleTurnResult, Integer> singleresults = new HashMap<SingleTurnResult, Integer>();
  Random rand = new Random();
}
