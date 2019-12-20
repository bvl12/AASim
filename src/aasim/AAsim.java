/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aasim;

import java.awt.GridLayout;
import static java.lang.Math.round;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Andrew
 */
public class AAsim extends javax.swing.JFrame {
    //init all vars
    Random rand = new Random();
    int Ainf = 0;
    int Aart = 0;
    int Atank = 0;
    int Afight = 0;
    int Abomb = 0;
    int Acarr = 0;
    int Abattle = 0;
    int Acruise = 0;
    int Adest = 0;
    int Asub = 0;
    int Atrans = 0;
    int Dinf = 0;
    int Dart = 0;
    int Dtank = 0;
    int Dfight = 0;
    int Dbomb = 0;
    int Dcarr = 0;
    int Dbattle = 0;
    int Dcruise = 0;
    int Ddest = 0;
    int Dsub = 0;
    int Dtrans = 0;
    boolean hasAAgun = false;
    boolean isAAssault = false;
    int itercount = 10000;
    int[] defvals = {2, 2, 3, 4, 1, 2, 4, 3, 2, 1, 0, 4};
    int[] atkvals = {1, 2, 3, 3, 4, 1, 4, 3, 2, 2, 0, 4};
    int[] IPCvals = {3, 4, 5, 10, 12, 14, 20, 12, 8, 6, 7, 20};
    int[] defaultlossorder = {0, 1, 9, 2, 10, 8, 3, 7, 4, 5, 11};
    int[] lossorder = Arrays.copyOf(defaultlossorder, defaultlossorder.length);
    HashMap<BattleResult, Integer> results = new HashMap<BattleResult, Integer>();
    /**
     * Creates new form AAsimUI
     */
    public AAsim() {
        initComponents();
    }
    
    public boolean hasGroundUnits(int[] units){
        if(units[0] > 0 || units[1] > 0 || units[2] > 0)
            return true;
        else return false;
    }
    
    public void sim(int iterations){
        DefaultTableModel model = (DefaultTableModel)resulttable.getModel();
        model.setRowCount(0);
        int[] a = {Ainf, Aart, Atank, Afight, Abomb, Acarr, Abattle, Acruise, Adest, Asub, Atrans};
        int[] d = {Dinf, Dart, Dtank, Dfight, Dbomb, Dcarr, Dbattle, Dcruise, Ddest, Dsub, Dtrans};
        boolean inMap;
        results.clear();
        for(int n = 0; n < iterations; n++){
            BattleResult test = simbat(a,d);
            inMap = false;
            for(BattleResult br:results.keySet()){
                if(br.equals(test)){
                    results.replace(br, results.get(br)+1);
                    inMap = true;
                    break;
                }
            }
            if(!inMap){
                results.put(test, 1);
            }

        }
        BattleResult[] br = {};
        
        results  = sortByValue(results);
        int ATKwin = 0;
        int DEFwin = 0;
        int takenWground = 0;
        int AIPCcost = 0;
        int DIPCcost = 0;
        BattleResult[] keys = results.keySet().toArray(br);
        model.setRowCount(results.size());
        for(int i = 0; i < results.size(); i++){
            DIPCcost += results.get(keys[i]) * keys[i].DEFIPC;
            AIPCcost += results.get(keys[i]) * keys[i].ATKIPC;
            switch (keys[i].winner) {
                case 1:
                    resulttable.setValueAt("ATK", i, 0);
                    ATKwin += results.get(keys[i]);
                    if(hasGroundUnits(keys[i].remainingunits.units)){
                        takenWground += results.get(keys[i]);
                    }
                    break;
                case 0:
                    resulttable.setValueAt("DEF", i, 0);
                    DEFwin += results.get(keys[i]);
                    break;
                default:
                    resulttable.setValueAt("DRAW", i, 0);
                    break;
            }
            for(int j = 0; j < 11; j++)
                resulttable.setValueAt(keys[i].remainingunits.units[j], i, j+1);
            
            resulttable.setValueAt((float)round(results.get(keys[i])*1000/iterations)/10,i,12);
            resulttable.setValueAt(keys[i].ATKIPC,i,13);
            resulttable.setValueAt(keys[i].DEFIPC,i,14);
        }
        ATKWinrate.setText(Integer.toString(round((ATKwin * 100 / iterations))));
        DEFWinrate.setText(Integer.toString(round((DEFwin * 100 / iterations))));
        ATKIPCcost.setText(Double.toString((double)((double)round(AIPCcost * 10 / iterations) / 10)));
        DEFIPCcost.setText(Double.toString((double)((double)round(DIPCcost * 10 / iterations) / 10)));
        TakenWGroundrate.setText(Integer.toString(round(takenWground * 100 / iterations)));
    }
    
    public BattleResult simbat(int[] aarr, int[] darr){
        Units a = new Units(aarr);
        Units d = new Units(darr);
        int Aloss = 0;
        int Dloss = 0;
        int AIPC = 0;
        int DIPC = 0;
        if(hasAAgun){
            for(int i = 0; i < aarr[3]; i++){
                if(rollDie()==1)
                    Aloss++;
            }
            a.units[3] -= Aloss;
            a.count -= Aloss;
            AIPC += IPCvals[3] * Aloss;
           
            Aloss = 0;
            for(int i = 0; i < aarr[4]; i++)
                if(rollDie()==1)
                    Aloss++;
            a.units[4] -= Aloss;
            a.count -= Aloss;
            AIPC += IPCvals[4] * Aloss;
            Aloss = 0;
        }
        if(isAAssault){
            for(int i = 0; i < a.units[6]; i++){
                if(rollDie()<=atkvals[6]){
                    if(d.units[6] > 0){
                        d.units[6]--;
                        d.units[lossIndex(lossorder,11)]++;
                    }
                    else if(d.units[lossorder[0]] > 0){
                        d.units[lossorder[0]]--;
                        d.count--;
                        DIPC += IPCvals[lossorder[0]];
                    }
                    else if(d.units[lossorder[1]] > 0){
                        d.units[lossorder[1]]--;
                        d.count--;
                        DIPC += IPCvals[lossorder[1]];
                    }
                    else if(d.units[lossorder[2]] > 0){
                        d.units[lossorder[2]]--;
                        d.count--;
                        DIPC += IPCvals[lossorder[2]];
                    }
                    else if(d.units[lossorder[3]] > 0){
                        d.units[lossorder[3]]--;
                        d.count--;
                        DIPC += IPCvals[lossorder[3]];
                    }
                    else if(d.units[lossorder[4]] > 0){
                        d.units[lossorder[4]]--;
                        d.count--;
                        DIPC += IPCvals[lossorder[4]];
                    }
                    else if(d.units[lossorder[5]] > 0){
                        d.units[lossorder[5]]--;
                        d.count--;
                        DIPC += IPCvals[lossorder[5]];
                    }
                    else if(d.units[lossorder[6]] > 0){
                        d.units[lossorder[6]]--;
                        d.count--;
                        DIPC += IPCvals[lossorder[6]];
                    }
                    else if(d.units[lossorder[7]] > 0){
                        d.units[lossorder[7]]--;
                        d.count--;
                        DIPC += IPCvals[lossorder[7]];
                    }
                    else if(d.units[lossorder[8]] > 0){
                        d.units[lossorder[8]]--;
                        d.count--;
                        DIPC += IPCvals[lossorder[8]];
                    }
                    else if(d.units[lossorder[9]] > 0){
                        d.units[lossorder[9]]--;
                        d.count--;
                        DIPC += IPCvals[lossorder[9]];
                    }
                    else if(d.units[lossorder[10]] > 0){
                        d.units[lossorder[10]]--;
                        d.count--;
                        DIPC += IPCvals[lossorder[10]];
                    }
                }
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
                    if(rollDie() < atkvals[i])
                        Dloss++;
                }
                for(int j = 0; j < d.units[i]; j++){
                    if(rollDie() < defvals[i])
                        Aloss++;
                }
            }
            
            for(int i = 0; i < Aloss; i++){
                if(a.units[6] > 0){
                    a.units[6]--;
                    a.units[lossIndex(lossorder,11)]++;
                }
                else if(a.units[lossorder[0]] > 0){
                    a.units[lossorder[0]]--;
                    a.count--;
                    AIPC += IPCvals[lossorder[0]];
                }
                else if(a.units[lossorder[1]] > 0){
                    a.units[lossorder[1]]--;
                    a.count--;
                    AIPC += IPCvals[lossorder[1]];
                }
                else if(a.units[lossorder[2]] > 0){
                    a.units[lossorder[2]]--;
                    a.count--;
                    AIPC += IPCvals[lossorder[2]];
                }
                else if(a.units[lossorder[3]] > 0){
                    a.units[lossorder[3]]--;
                    a.count--;
                    AIPC += IPCvals[lossorder[3]];
                }
                else if(a.units[lossorder[4]] > 0){
                    a.units[lossorder[4]]--;
                    a.count--;
                    AIPC += IPCvals[lossorder[4]];
                }
                else if(a.units[lossorder[5]] > 0){
                    a.units[lossorder[5]]--;
                    a.count--;
                    AIPC += IPCvals[lossorder[5]];
                }
                else if(a.units[lossorder[6]] > 0){
                    a.units[lossorder[6]]--;
                    a.count--;
                    AIPC += IPCvals[lossorder[6]];
                }
                else if(a.units[lossorder[7]] > 0){
                    a.units[lossorder[7]]--;
                    a.count--;
                    AIPC += IPCvals[lossorder[7]];
                }
                else if(a.units[lossorder[8]] > 0){
                    a.units[lossorder[8]]--;
                    a.count--;
                    AIPC += IPCvals[lossorder[8]];
                }
                else if(a.units[lossorder[9]] > 0){
                    a.units[lossorder[9]]--;
                    a.count--;
                    AIPC += IPCvals[lossorder[9]];
                }
                else if(a.units[lossorder[10]] > 0){
                    a.units[lossorder[10]]--;
                    a.count--;
                    AIPC += IPCvals[lossorder[10]];
                }
            }
            for(int i = 0; i < Dloss; i++){
                if(d.units[6] > 0){
                    d.units[6]--;
                    d.units[lossIndex(lossorder,11)]++;
                }
                else if(d.units[lossorder[0]] > 0){
                    d.units[lossorder[0]]--;
                    d.count--;
                    DIPC += IPCvals[lossorder[0]];
                }
                else if(d.units[lossorder[1]] > 0){
                    d.units[lossorder[1]]--;
                    d.count--;
                    DIPC += IPCvals[lossorder[1]];
                }
                else if(d.units[lossorder[2]] > 0){
                    d.units[lossorder[2]]--;
                    d.count--;
                    DIPC += IPCvals[lossorder[2]];
                }
                else if(d.units[lossorder[3]] > 0){
                    d.units[lossorder[3]]--;
                    d.count--;
                    DIPC += IPCvals[lossorder[3]];
                }
                else if(d.units[lossorder[4]] > 0){
                    d.units[lossorder[4]]--;
                    d.count--;
                    DIPC += IPCvals[lossorder[4]];
                }
                else if(d.units[lossorder[5]] > 0){
                    d.units[lossorder[5]]--;
                    d.count--;
                    DIPC += IPCvals[lossorder[5]];
                }
                else if(d.units[lossorder[6]] > 0){
                    d.units[lossorder[6]]--;
                    d.count--;
                    DIPC += IPCvals[lossorder[6]];
                }
                else if(d.units[lossorder[7]] > 0){
                    d.units[lossorder[7]]--;
                    d.count--;
                    DIPC += IPCvals[lossorder[7]];
                }
                else if(d.units[lossorder[8]] > 0){
                    d.units[lossorder[8]]--;
                    d.count--;
                    DIPC += IPCvals[lossorder[8]];
                }
                else if(d.units[lossorder[9]] > 0){
                    d.units[lossorder[9]]--;
                    d.count--;
                    DIPC += IPCvals[lossorder[9]];
                }
                else if(d.units[lossorder[10]] > 0){
                    d.units[lossorder[10]]--;
                    d.count--;
                    DIPC += IPCvals[lossorder[10]];
                }
            }
            Aloss = 0;
            Dloss = 0;
            if(a.count <= 0){
                d.units[6]+= d.units[lossIndex(lossorder,11)];
                d.units[lossIndex(lossorder,11)] = 0;
                if(d.count <= 0)
                    return new BattleResult(-1,d, AIPC, DIPC);
                return new BattleResult(0, d, AIPC, DIPC);
            }
            if(d.count <= 0){
                a.units[6]+= d.units[lossIndex(lossorder,11)];
                a.units[lossIndex(lossorder,11)] = 0;
                if(a.count <= 0)
                    return new BattleResult(-1,d, AIPC, DIPC);
                return new BattleResult(1, a, AIPC, DIPC);
            }
        }
        
    }
    
    public int rollDie(){
        return rand.nextInt(6);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    UpdateOOLWindow = new javax.swing.JDialog();
    jLabel6 = new javax.swing.JLabel();
    jLabel7 = new javax.swing.JLabel();
    jLabel8 = new javax.swing.JLabel();
    jLabel9 = new javax.swing.JLabel();
    jLabel10 = new javax.swing.JLabel();
    jLabel11 = new javax.swing.JLabel();
    jLabel12 = new javax.swing.JLabel();
    jLabel13 = new javax.swing.JLabel();
    jLabel14 = new javax.swing.JLabel();
    jLabel15 = new javax.swing.JLabel();
    jLabel16 = new javax.swing.JLabel();
    jLabel17 = new javax.swing.JLabel();
    UpdateLossOrder = new javax.swing.JButton();
    InfOOL = new javax.swing.JSpinner();
    ArtOOL = new javax.swing.JSpinner();
    SubOOL = new javax.swing.JSpinner();
    TankOOL = new javax.swing.JSpinner();
    TransOOL = new javax.swing.JSpinner();
    DestOOL = new javax.swing.JSpinner();
    FightOOL = new javax.swing.JSpinner();
    CruiseOOL = new javax.swing.JSpinner();
    BombOOL = new javax.swing.JSpinner();
    CarrOOL = new javax.swing.JSpinner();
    BattleOOL = new javax.swing.JSpinner();
    ATKpanel = new javax.swing.JPanel();
    ATKinftxt = new javax.swing.JLabel();
    ATKarttxt = new javax.swing.JLabel();
    ATKtanktxt = new javax.swing.JLabel();
    ATKfighttxt = new javax.swing.JLabel();
    ATKbombtxt = new javax.swing.JLabel();
    ATKcarrtxt = new javax.swing.JLabel();
    ATKbattletxt = new javax.swing.JLabel();
    ATKcruisetxt = new javax.swing.JLabel();
    ATKdesttxt = new javax.swing.JLabel();
    ATKsubtxt = new javax.swing.JLabel();
    ATKtranstxt = new javax.swing.JLabel();
    ATKsub = new javax.swing.JTextField();
    ATKdest = new javax.swing.JTextField();
    ATKtrans = new javax.swing.JTextField();
    ATKcruise = new javax.swing.JTextField();
    ATKbattle = new javax.swing.JTextField();
    ATKcarr = new javax.swing.JTextField();
    ATKbomb = new javax.swing.JTextField();
    ATKfight = new javax.swing.JTextField();
    ATKtank = new javax.swing.JTextField();
    ATKart = new javax.swing.JTextField();
    ATKinf = new javax.swing.JTextField();
    Ainfnum = new javax.swing.JLabel();
    Aartnum = new javax.swing.JLabel();
    Atanknum = new javax.swing.JLabel();
    Afightnum = new javax.swing.JLabel();
    Abombnum = new javax.swing.JLabel();
    Acarrnum = new javax.swing.JLabel();
    Abattlenum = new javax.swing.JLabel();
    Acruisenum = new javax.swing.JLabel();
    Adestnum = new javax.swing.JLabel();
    Asubnum = new javax.swing.JLabel();
    Atransnum = new javax.swing.JLabel();
    AmphibiousAssault = new javax.swing.JCheckBox();
    DEFpanel = new javax.swing.JPanel();
    ATKinftxt1 = new javax.swing.JLabel();
    ATKarttxt1 = new javax.swing.JLabel();
    ATKtanktxt1 = new javax.swing.JLabel();
    ATKfighttxt1 = new javax.swing.JLabel();
    ATKbombtxt1 = new javax.swing.JLabel();
    ATKcarrtxt1 = new javax.swing.JLabel();
    ATKbattletxt1 = new javax.swing.JLabel();
    ATKcruisetxt1 = new javax.swing.JLabel();
    ATKdesttxt1 = new javax.swing.JLabel();
    ATKsubtxt1 = new javax.swing.JLabel();
    ATKtranstxt1 = new javax.swing.JLabel();
    DEFsub = new javax.swing.JTextField();
    DEFdest = new javax.swing.JTextField();
    DEFtrans = new javax.swing.JTextField();
    DEFcruise = new javax.swing.JTextField();
    DEFbattle = new javax.swing.JTextField();
    DEFcarr = new javax.swing.JTextField();
    DEFbomb = new javax.swing.JTextField();
    DEFfight = new javax.swing.JTextField();
    DEFtank = new javax.swing.JTextField();
    DEFart = new javax.swing.JTextField();
    DEFinf = new javax.swing.JTextField();
    AAgun = new javax.swing.JCheckBox();
    Dinfnum = new javax.swing.JLabel();
    Dartnum = new javax.swing.JLabel();
    Dtanknum = new javax.swing.JLabel();
    Dfightnum = new javax.swing.JLabel();
    Dbombnum = new javax.swing.JLabel();
    Dcarrnum = new javax.swing.JLabel();
    Dbattlenum = new javax.swing.JLabel();
    Dcruisenum = new javax.swing.JLabel();
    Ddestnum = new javax.swing.JLabel();
    Dsubnum = new javax.swing.JLabel();
    Dtransnum = new javax.swing.JLabel();
    Settings = new javax.swing.JPanel();
    ResetButton = new javax.swing.JButton();
    SingleTurn = new javax.swing.JCheckBox();
    CustomOOL = new javax.swing.JCheckBox();
    jPanel1 = new javax.swing.JPanel();
    jScrollPane2 = new javax.swing.JScrollPane();
    resulttable = new javax.swing.JTable();
    Summary = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    ATKWinrate = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    DEFWinrate = new javax.swing.JLabel();
    TakenWGroundrate = new javax.swing.JLabel();
    ATKIPCcost = new javax.swing.JLabel();
    DEFIPCcost = new javax.swing.JLabel();

    UpdateOOLWindow.setMinimumSize(new java.awt.Dimension(114, 386));
    UpdateOOLWindow.setResizable(false);

    jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    jLabel6.setText("Custom OOL");

    jLabel7.setText("Infantry");

    jLabel8.setText("Artillery");

    jLabel9.setText("Tank");

    jLabel10.setText("Fighter");

    jLabel11.setText("Bomber");

    jLabel12.setText("Carrier");

    jLabel13.setText("Cruiser");

    jLabel14.setText("Destroyer");

    jLabel15.setText("Submarine");

    jLabel16.setText("Transport");

    jLabel17.setText("Battleship");

    UpdateLossOrder.setText("Update");
    UpdateLossOrder.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        UpdateLossOrderActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout UpdateOOLWindowLayout = new javax.swing.GroupLayout(UpdateOOLWindow.getContentPane());
    UpdateOOLWindow.getContentPane().setLayout(UpdateOOLWindowLayout);
    UpdateOOLWindowLayout.setHorizontalGroup(
      UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(UpdateOOLWindowLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(UpdateOOLWindowLayout.createSequentialGroup()
            .addGap(10, 10, 10)
            .addComponent(UpdateLossOrder)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          .addGroup(UpdateOOLWindowLayout.createSequentialGroup()
            .addGroup(UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(UpdateOOLWindowLayout.createSequentialGroup()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BattleOOL, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addGroup(UpdateOOLWindowLayout.createSequentialGroup()
                .addGroup(UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                  .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(DestOOL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(FightOOL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(CruiseOOL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(BombOOL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(CarrOOL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
              .addGroup(UpdateOOLWindowLayout.createSequentialGroup()
                .addGroup(UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(jLabel9)
                  .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(SubOOL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(TankOOL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
              .addGroup(UpdateOOLWindowLayout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(0, 0, Short.MAX_VALUE))
              .addGroup(UpdateOOLWindowLayout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TransOOL, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addGroup(UpdateOOLWindowLayout.createSequentialGroup()
                .addGroup(UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(jLabel7)
                  .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(ArtOOL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(InfOOL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
    );
    UpdateOOLWindowLayout.setVerticalGroup(
      UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(UpdateOOLWindowLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jLabel6)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel7)
          .addComponent(InfOOL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel8)
          .addComponent(ArtOOL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel9)
          .addComponent(SubOOL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel10)
          .addComponent(TankOOL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel11)
          .addComponent(TransOOL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel12)
          .addComponent(DestOOL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel13)
          .addComponent(FightOOL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel14)
          .addComponent(CruiseOOL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel15)
          .addComponent(BombOOL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(CarrOOL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(UpdateOOLWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel17)
          .addComponent(BattleOOL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(UpdateLossOrder)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("AA Battle Simulator");
    setAlwaysOnTop(true);

    ATKpanel.setBorder(javax.swing.BorderFactory.createTitledBorder("ATK"));

    ATKinftxt.setText("Infantry");

    ATKarttxt.setText("Artillery");

    ATKtanktxt.setText("Tanks");

    ATKfighttxt.setText("Fighters");

    ATKbombtxt.setText("Bombers");

    ATKcarrtxt.setText("Carriers");

    ATKbattletxt.setText("Battleships");

    ATKcruisetxt.setText("Cruisers");

    ATKdesttxt.setText("Destroyers");

    ATKsubtxt.setText("Submarines");

    ATKtranstxt.setText("Transports");

    ATKsub.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ATKsubActionPerformed(evt);
      }
    });

    ATKdest.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ATKdestActionPerformed(evt);
      }
    });

    ATKtrans.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ATKtransActionPerformed(evt);
      }
    });

    ATKcruise.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ATKcruiseActionPerformed(evt);
      }
    });

    ATKbattle.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ATKbattleActionPerformed(evt);
      }
    });

    ATKcarr.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ATKcarrActionPerformed(evt);
      }
    });

    ATKbomb.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ATKbombActionPerformed(evt);
      }
    });

    ATKfight.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ATKfightActionPerformed(evt);
      }
    });

    ATKtank.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ATKtankActionPerformed(evt);
      }
    });

    ATKart.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ATKartActionPerformed(evt);
      }
    });

    ATKinf.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ATKinfActionPerformed(evt);
      }
    });

    Ainfnum.setText("0");

    Aartnum.setText("0");

    Atanknum.setText("0");

    Afightnum.setText("0");

    Abombnum.setText("0");

    Acarrnum.setText("0");

    Abattlenum.setText("0");

    Acruisenum.setText("0");

    Adestnum.setText("0");

    Asubnum.setText("0");

    Atransnum.setText("0");

    AmphibiousAssault.setText("Amphibious Assault");
    AmphibiousAssault.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        AmphibiousAssaultActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout ATKpanelLayout = new javax.swing.GroupLayout(ATKpanel);
    ATKpanel.setLayout(ATKpanelLayout);
    ATKpanelLayout.setHorizontalGroup(
      ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(ATKpanelLayout.createSequentialGroup()
        .addGroup(ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(ATKpanelLayout.createSequentialGroup()
            .addGroup(ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(ATKinftxt)
              .addComponent(ATKarttxt)
              .addComponent(ATKtanktxt)
              .addComponent(ATKfighttxt)
              .addComponent(ATKbombtxt)
              .addComponent(ATKcarrtxt)
              .addComponent(ATKbattletxt)
              .addComponent(ATKcruisetxt)
              .addComponent(ATKdesttxt)
              .addComponent(ATKsubtxt)
              .addComponent(ATKtranstxt))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
              .addComponent(ATKsub, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
              .addComponent(ATKdest, javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(ATKcruise, javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(ATKbattle, javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(ATKcarr, javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(ATKbomb, javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(ATKfight, javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(ATKtank, javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(ATKart, javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(ATKinf, javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(ATKtrans))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(Ainfnum)
              .addComponent(Aartnum)
              .addComponent(Atanknum)
              .addComponent(Afightnum)
              .addComponent(Abombnum)
              .addComponent(Acarrnum)
              .addComponent(Abattlenum)
              .addComponent(Acruisenum)
              .addComponent(Adestnum)
              .addComponent(Asubnum)
              .addComponent(Atransnum)))
          .addComponent(AmphibiousAssault))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    ATKpanelLayout.setVerticalGroup(
      ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(ATKpanelLayout.createSequentialGroup()
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKinftxt)
          .addComponent(ATKinf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Ainfnum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKarttxt)
          .addComponent(ATKart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Aartnum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKtanktxt)
          .addComponent(ATKtank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Atanknum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKfighttxt)
          .addComponent(ATKfight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Afightnum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKbombtxt)
          .addComponent(ATKbomb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Abombnum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKcarrtxt)
          .addComponent(ATKcarr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Acarrnum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKbattletxt)
          .addComponent(ATKbattle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Abattlenum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKcruisetxt)
          .addComponent(ATKcruise, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Acruisenum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKdesttxt)
          .addComponent(ATKdest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Adestnum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKsubtxt)
          .addComponent(ATKsub, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Asubnum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(ATKpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKtranstxt)
          .addComponent(ATKtrans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Atransnum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(AmphibiousAssault)
        .addGap(338, 338, 338))
    );

    DEFpanel.setBorder(javax.swing.BorderFactory.createTitledBorder("DEF"));

    ATKinftxt1.setText("Infantry");

    ATKarttxt1.setText("Artillery");

    ATKtanktxt1.setText("Tanks");

    ATKfighttxt1.setText("Fighters");

    ATKbombtxt1.setText("Bombers");

    ATKcarrtxt1.setText("Carriers");

    ATKbattletxt1.setText("Battleships");

    ATKcruisetxt1.setText("Cruisers");

    ATKdesttxt1.setText("Destroyers");

    ATKsubtxt1.setText("Submarines");

    ATKtranstxt1.setText("Transports");

    DEFsub.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        DEFsubActionPerformed(evt);
      }
    });

    DEFdest.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        DEFdestActionPerformed(evt);
      }
    });

    DEFtrans.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        DEFtransActionPerformed(evt);
      }
    });

    DEFcruise.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        DEFcruiseActionPerformed(evt);
      }
    });

    DEFbattle.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        DEFbattleActionPerformed(evt);
      }
    });

    DEFcarr.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        DEFcarrActionPerformed(evt);
      }
    });

    DEFbomb.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        DEFbombActionPerformed(evt);
      }
    });

    DEFfight.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        DEFfightActionPerformed(evt);
      }
    });

    DEFtank.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        DEFtankActionPerformed(evt);
      }
    });

    DEFart.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        DEFartActionPerformed(evt);
      }
    });

    DEFinf.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        DEFinfActionPerformed(evt);
      }
    });

    AAgun.setText("AA Gun");
    AAgun.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        AAgunActionPerformed(evt);
      }
    });

    Dinfnum.setText("0");

    Dartnum.setText("0");

    Dtanknum.setText("0");

    Dfightnum.setText("0");

    Dbombnum.setText("0");

    Dcarrnum.setText("0");

    Dbattlenum.setText("0");

    Dcruisenum.setText("0");

    Ddestnum.setText("0");

    Dsubnum.setText("0");

    Dtransnum.setText("0");

    javax.swing.GroupLayout DEFpanelLayout = new javax.swing.GroupLayout(DEFpanel);
    DEFpanel.setLayout(DEFpanelLayout);
    DEFpanelLayout.setHorizontalGroup(
      DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(DEFpanelLayout.createSequentialGroup()
        .addGroup(DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(DEFpanelLayout.createSequentialGroup()
            .addGroup(DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(ATKarttxt1)
              .addComponent(ATKtanktxt1)
              .addComponent(ATKinftxt1)
              .addComponent(ATKbattletxt1)
              .addComponent(ATKfighttxt1)
              .addComponent(ATKcarrtxt1)
              .addComponent(ATKbombtxt1)
              .addComponent(ATKcruisetxt1)
              .addComponent(ATKdesttxt1)
              .addComponent(ATKsubtxt1)
              .addComponent(ATKtranstxt1))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addComponent(DEFtrans, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(DEFsub, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(DEFdest, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(DEFcruise, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(DEFbattle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(DEFcarr, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(DEFbomb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(DEFfight, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(DEFtank, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(DEFart, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(DEFinf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(5, 5, 5)
            .addGroup(DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(Dinfnum)
              .addComponent(Dtransnum)
              .addComponent(Dsubnum)
              .addComponent(Ddestnum)
              .addComponent(Dcruisenum)
              .addComponent(Dbattlenum)
              .addComponent(Dcarrnum)
              .addComponent(Dbombnum)
              .addComponent(Dfightnum)
              .addComponent(Dtanknum)
              .addComponent(Dartnum)))
          .addComponent(AAgun))
        .addContainerGap(19, Short.MAX_VALUE))
    );
    DEFpanelLayout.setVerticalGroup(
      DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(DEFpanelLayout.createSequentialGroup()
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKinftxt1)
          .addComponent(DEFinf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Dinfnum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKarttxt1)
          .addComponent(DEFart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Dartnum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKtanktxt1)
          .addComponent(DEFtank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Dtanknum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKfighttxt1)
          .addComponent(DEFfight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Dfightnum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKbombtxt1)
          .addComponent(DEFbomb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Dbombnum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKcarrtxt1)
          .addComponent(DEFcarr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Dcarrnum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKbattletxt1)
          .addComponent(DEFbattle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Dbattlenum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKcruisetxt1)
          .addComponent(DEFcruise, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Dcruisenum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKdesttxt1)
          .addComponent(DEFdest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Ddestnum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKsubtxt1)
          .addComponent(DEFsub, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Dsubnum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(DEFpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKtranstxt1)
          .addComponent(DEFtrans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(Dtransnum))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(AAgun))
    );

    Settings.setBorder(javax.swing.BorderFactory.createTitledBorder("Settings"));

    ResetButton.setText("Reset All Units");
    ResetButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ResetButtonActionPerformed(evt);
      }
    });

    SingleTurn.setText("Single Turn of Attack (Nonfunctional)");
    SingleTurn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        SingleTurnActionPerformed(evt);
      }
    });

    CustomOOL.setText("Custom OOL");
    CustomOOL.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        CustomOOLActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout SettingsLayout = new javax.swing.GroupLayout(Settings);
    Settings.setLayout(SettingsLayout);
    SettingsLayout.setHorizontalGroup(
      SettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(SettingsLayout.createSequentialGroup()
        .addGroup(SettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(ResetButton)
          .addComponent(SingleTurn)
          .addComponent(CustomOOL))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    SettingsLayout.setVerticalGroup(
      SettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(SettingsLayout.createSequentialGroup()
        .addComponent(ResetButton)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(SingleTurn)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(CustomOOL)
        .addContainerGap(62, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 1347, Short.MAX_VALUE)
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 337, Short.MAX_VALUE)
    );

    resulttable.setAutoCreateRowSorter(true);
    resulttable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {

      },
      new String [] {
        "Winner", "INF", "ART", "TANK", "FTR", "BMBR", "AC", "BS", "CRSR", "DEST", "SUB", "TRAN", "%", "A IPC", "D IPC"
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
    resulttable.setName(""); // NOI18N
    jScrollPane2.setViewportView(resulttable);

    Summary.setBorder(javax.swing.BorderFactory.createTitledBorder("SUMMARY"));

    jLabel1.setText("ATK Win %");

    ATKWinrate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    ATKWinrate.setText("0");

    jLabel2.setText("DEF Win %");

    jLabel3.setText("Taken w/ Ground %");

    jLabel4.setText("Avg ATK IPC Cost");

    jLabel5.setText("Avg DEF IPC Cost");

    DEFWinrate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    DEFWinrate.setText("0");

    TakenWGroundrate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    TakenWGroundrate.setText("0");

    ATKIPCcost.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    ATKIPCcost.setText("0");

    DEFIPCcost.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    DEFIPCcost.setText("0");

    javax.swing.GroupLayout SummaryLayout = new javax.swing.GroupLayout(Summary);
    Summary.setLayout(SummaryLayout);
    SummaryLayout.setHorizontalGroup(
      SummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(SummaryLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(SummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabel1)
          .addGroup(SummaryLayout.createSequentialGroup()
            .addGap(10, 10, 10)
            .addComponent(ATKWinrate)))
        .addGap(18, 18, 18)
        .addGroup(SummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabel2)
          .addGroup(SummaryLayout.createSequentialGroup()
            .addGap(10, 10, 10)
            .addComponent(DEFWinrate)))
        .addGap(18, 18, 18)
        .addGroup(SummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabel3)
          .addGroup(SummaryLayout.createSequentialGroup()
            .addGap(10, 10, 10)
            .addComponent(TakenWGroundrate)))
        .addGap(18, 18, 18)
        .addGroup(SummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabel4)
          .addGroup(SummaryLayout.createSequentialGroup()
            .addGap(10, 10, 10)
            .addComponent(ATKIPCcost)))
        .addGap(18, 18, 18)
        .addGroup(SummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(SummaryLayout.createSequentialGroup()
            .addGap(10, 10, 10)
            .addComponent(DEFIPCcost))
          .addComponent(jLabel5))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    SummaryLayout.setVerticalGroup(
      SummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(SummaryLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(SummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(jLabel2)
          .addComponent(jLabel3)
          .addComponent(jLabel4)
          .addComponent(jLabel5))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(SummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ATKWinrate)
          .addComponent(DEFWinrate)
          .addComponent(TakenWGroundrate)
          .addComponent(ATKIPCcost)
          .addComponent(DEFIPCcost))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(ATKpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(DEFpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                .addComponent(Summary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(835, 835, 835))
              .addComponent(Settings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGap(495, 495, 495))
          .addGroup(layout.createSequentialGroup()
            .addComponent(jScrollPane2)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addComponent(ATKpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
          .addComponent(DEFpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(Settings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(Summary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))))
    );

    Settings.getAccessibleContext().setAccessibleDescription("");

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void ATKsubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ATKsubActionPerformed
        try{
            Asub = Integer.parseInt(ATKsub.getText());
        }catch(NumberFormatException e){
            Asub = 0;
        }
        Asubnum.setText(Integer.toString(Asub));
        ATKtrans.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_ATKsubActionPerformed

    private void ATKdestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ATKdestActionPerformed
        try{
            Adest = Integer.parseInt(ATKdest.getText());
        }catch(NumberFormatException e){
            Adest = 0;
        }
        Adestnum.setText(Integer.toString(Adest));
        ATKsub.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_ATKdestActionPerformed

    private void ATKtransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ATKtransActionPerformed
        try{
            Atrans = Integer.parseInt(ATKtrans.getText());
        }catch(NumberFormatException e){
            Atrans = 0;
        }
        Atransnum.setText(Integer.toString(Atrans));
        DEFinf.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_ATKtransActionPerformed

    private void ATKcruiseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ATKcruiseActionPerformed
        try{
            Acruise = Integer.parseInt(ATKcruise.getText());
        }catch(NumberFormatException e){
            Acruise = 0;
        }
        Acruisenum.setText(Integer.toString(Acruise));
        ATKdest.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_ATKcruiseActionPerformed

    private void ATKartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ATKartActionPerformed
        try{
            Aart = Integer.parseInt(ATKart.getText());
        }catch(NumberFormatException e){
            Aart = 0;
        }
        Aartnum.setText(Integer.toString(Aart));
        ATKtank.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_ATKartActionPerformed

    private void ATKinfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ATKinfActionPerformed
        try{
            Ainf = Integer.parseInt(ATKinf.getText());
        }catch(NumberFormatException e){
            Ainf = 0;
        }
        Ainfnum.setText(Integer.toString(Ainf));
        ATKart.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_ATKinfActionPerformed

    private void ATKbattleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ATKbattleActionPerformed
        try{
            Abattle = Integer.parseInt(ATKbattle.getText());
        }catch(NumberFormatException e){
            Abattle = 0;
        }
        Abattlenum.setText(Integer.toString(Abattle));
        ATKcruise.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_ATKbattleActionPerformed

    private void ATKbombActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ATKbombActionPerformed
        try{
            Abomb = Integer.parseInt(ATKbomb.getText());
        }catch(NumberFormatException e){
            Abomb = 0;
        }
        Abombnum.setText(Integer.toString(Abomb));
        ATKcarr.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_ATKbombActionPerformed

    private void ATKtankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ATKtankActionPerformed
        try{
            Atank = Integer.parseInt(ATKtank.getText());
        }catch(NumberFormatException e){
            Atank = 0;
        }
        Atanknum.setText(Integer.toString(Atank));
        ATKfight.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_ATKtankActionPerformed

    private void ATKfightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ATKfightActionPerformed
        try{
            Afight = Integer.parseInt(ATKfight.getText());
        }catch(NumberFormatException e){
            Afight = 0;
        }
        Afightnum.setText(Integer.toString(Afight));
        ATKbomb.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_ATKfightActionPerformed

    private void ATKcarrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ATKcarrActionPerformed
        try{
            Acarr = Integer.parseInt(ATKcarr.getText());
        }catch(NumberFormatException e){
            Acarr = 0;
        }
        Acarrnum.setText(Integer.toString(Acarr));
        ATKbattle.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_ATKcarrActionPerformed

    private void AmphibiousAssaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AmphibiousAssaultActionPerformed
        isAAssault = AmphibiousAssault.isSelected();
        sim(itercount);
    }//GEN-LAST:event_AmphibiousAssaultActionPerformed

    private void AAgunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AAgunActionPerformed
        hasAAgun = AAgun.isSelected();
        sim(itercount);
    }//GEN-LAST:event_AAgunActionPerformed

    private void DEFinfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DEFinfActionPerformed
        try{
            Dinf = Integer.parseInt(DEFinf.getText());
        }catch(NumberFormatException e){
            Dinf = 0;
        }
        Dinfnum.setText(Integer.toString(Dinf));
        DEFart.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_DEFinfActionPerformed

    private void DEFartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DEFartActionPerformed
        try{
            Dart = Integer.parseInt(DEFart.getText());
        }catch(NumberFormatException e){
            Dart = 0;
        }
        Dartnum.setText(Integer.toString(Dart));
        DEFtank.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_DEFartActionPerformed

    private void DEFtankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DEFtankActionPerformed
        try{
            Dtank = Integer.parseInt(DEFtank.getText());
        }catch(NumberFormatException e){
            Dtank = 0;
        }
        Dtanknum.setText(Integer.toString(Dtank));
        DEFfight.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_DEFtankActionPerformed

    private void DEFfightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DEFfightActionPerformed
        try{
            Dfight = Integer.parseInt(DEFfight.getText());
        }catch(NumberFormatException e){
            Dfight = 0;
        }
        Dfightnum.setText(Integer.toString(Dfight));
        DEFbomb.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_DEFfightActionPerformed

    private void DEFbombActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DEFbombActionPerformed
        try{
            Dbomb = Integer.parseInt(DEFbomb.getText());
        }catch(NumberFormatException e){
            Dbomb = 0;
        }
        Dbombnum.setText(Integer.toString(Dbomb));
        DEFcarr.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_DEFbombActionPerformed

    private void DEFcarrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DEFcarrActionPerformed
        try{
            Dcarr = Integer.parseInt(DEFcarr.getText());
        }catch(NumberFormatException e){
            Dcarr = 0;
        }
        Dcarrnum.setText(Integer.toString(Dcarr));
        DEFbattle.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_DEFcarrActionPerformed

    private void DEFbattleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DEFbattleActionPerformed
        try{
            Dbattle = Integer.parseInt(DEFbattle.getText());
        }catch(NumberFormatException e){
            Dbattle = 0;
        }
        Dbattlenum.setText(Integer.toString(Dbattle));
        DEFcruise.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_DEFbattleActionPerformed

    private void DEFcruiseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DEFcruiseActionPerformed
        try{
            Dcruise = Integer.parseInt(DEFcruise.getText());
        }catch(NumberFormatException e){
            Dcruise = 0;
        }
        Dcruisenum.setText(Integer.toString(Dcruise));
        DEFdest.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_DEFcruiseActionPerformed

    private void DEFtransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DEFtransActionPerformed
        try{
            Dtrans = Integer.parseInt(DEFtrans.getText());
        }catch(NumberFormatException e){
            Dtrans = 0;
        }
        Dtransnum.setText(Integer.toString(Dtrans));
        AAgun.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_DEFtransActionPerformed

    private void DEFdestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DEFdestActionPerformed
        try{
            Ddest = Integer.parseInt(DEFdest.getText());
        }catch(NumberFormatException e){
            Ddest = 0;
        }
        Ddestnum.setText(Integer.toString(Ddest));
        DEFsub.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_DEFdestActionPerformed

    private void DEFsubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DEFsubActionPerformed
        try{
            Dsub = Integer.parseInt(DEFsub.getText());
        }catch(NumberFormatException e){
            Dsub = 0;
        }
        Dsubnum.setText(Integer.toString(Dsub));
        DEFtrans.requestFocusInWindow();
        sim(itercount);
    }//GEN-LAST:event_DEFsubActionPerformed

    private void UpdateLossOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateLossOrderActionPerformed
        lossorder[(int)InfOOL.getValue()-1] = 0;
        lossorder[(int)ArtOOL.getValue()-1] = 1;
        lossorder[(int)SubOOL.getValue()-1] = 2;
        lossorder[(int)TankOOL.getValue()-1] = 3;
        lossorder[(int)TransOOL.getValue()-1] = 4;
        lossorder[(int)DestOOL.getValue()-1] = 5;
        lossorder[(int)FightOOL.getValue()-1] = 7;
        lossorder[(int)CruiseOOL.getValue()-1] = 8;
        lossorder[(int)BombOOL.getValue()-1] = 9;
        lossorder[(int)CarrOOL.getValue()-1] = 10;
        lossorder[(int)BattleOOL.getValue()-1] = 11;
        UpdateOOLWindow.setEnabled(false);
        UpdateOOLWindow.setVisible(false);
        sim(itercount);
    }//GEN-LAST:event_UpdateLossOrderActionPerformed

  private void CustomOOLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CustomOOLActionPerformed
    if(CustomOOL.isSelected()){
      UpdateOOLWindow.setEnabled(true);
      UpdateOOLWindow.setVisible(true);
      UpdateOOLWindow.setAlwaysOnTop(true);
      InfOOL.setValue(lossIndex(lossorder, 0)+1);
      ArtOOL.setValue(lossIndex(lossorder, 1)+1);
      SubOOL.setValue(lossIndex(lossorder, 2)+1);
      TankOOL.setValue(lossIndex(lossorder, 3)+1);
      TransOOL.setValue(lossIndex(lossorder, 4)+1);
      DestOOL.setValue(lossIndex(lossorder, 5)+1);
      FightOOL.setValue(lossIndex(lossorder, 7)+1);
      CruiseOOL.setValue(lossIndex(lossorder, 8)+1);
      BombOOL.setValue(lossIndex(lossorder, 9)+1);
      CarrOOL.setValue(lossIndex(lossorder, 10)+1);
      BattleOOL.setValue(lossIndex(lossorder, 11)+1);
    }
    else{
      lossorder = Arrays.copyOf(defaultlossorder, defaultlossorder.length);
      sim(itercount);
    }
  }//GEN-LAST:event_CustomOOLActionPerformed

  private void SingleTurnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SingleTurnActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_SingleTurnActionPerformed

  private void ResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetButtonActionPerformed
    resetUnits();
  }//GEN-LAST:event_ResetButtonActionPerformed

    public static void main(String[] args) {
        AAsim frame = new AAsim();
        frame.setSize(890,670);
        frame.setVisible(true);
        
    }
    
    public int lossIndex(int[] order, int val){
        for(int i = 0; i < order.length; i++){
            if(order[i] == val)
                return i;
        }
        return -1;
    }
    
    public void resetUnits(){
        Ainf = 0;
        Aart = 0;
        Atank = 0;
        Afight = 0;
        Abomb = 0;
        Acarr = 0;
        Abattle = 0;
        Acruise = 0;
        Adest = 0;
        Asub = 0;
        Atrans = 0;
        Dinf = 0;
        Dart = 0;
        Dtank = 0;
        Dfight = 0;
        Dbomb = 0;
        Dcarr = 0;
        Dbattle = 0;
        Dcruise = 0;
        Ddest = 0;
        Dsub = 0;
        Dtrans = 0;
        hasAAgun = false;
        isAAssault = false;
        ATKinf.setText("");
        ATKart.setText("");
        ATKbomb.setText("");
        ATKbattle.setText("");
        ATKcarr.setText("");
        ATKcruise.setText("");
        ATKdest.setText("");
        ATKfight.setText("");
        ATKsub.setText("");
        ATKtank.setText("");
        ATKtrans.setText("");
        
        Ainfnum.setText("0");
        Aartnum.setText("0");
        Abombnum.setText("0");
        Abattlenum.setText("0");
        Acarrnum.setText("0");
        Acruisenum.setText("0");
        Adestnum.setText("0");
        Afightnum.setText("0");
        Asubnum.setText("0");
        Atanknum.setText("0");
        Atransnum.setText("0");
        
        DEFinf.setText("");
        DEFart.setText("");
        DEFbomb.setText("");
        DEFbattle.setText("");
        DEFcarr.setText("");
        DEFcruise.setText("");
        DEFdest.setText("");
        DEFfight.setText("");
        DEFsub.setText("");
        DEFtank.setText("");
        DEFtrans.setText("");
        
        Dinfnum.setText("0");
        Dartnum.setText("0");
        Dbombnum.setText("0");
        Dbattlenum.setText("0");
        Dcarrnum.setText("0");
        Dcruisenum.setText("0");
        Ddestnum.setText("0");
        Dfightnum.setText("0");
        Dsubnum.setText("0");
        Dtanknum.setText("0");
        Dtransnum.setText("0");
        
    }
    
        // function to sort hashmap by values 
    public static HashMap<BattleResult, Integer> sortByValue(HashMap<BattleResult, Integer> hm) 
    { 
        // Create a list from elements of HashMap 
        List<Map.Entry<BattleResult, Integer> > list = 
               new LinkedList<Map.Entry<BattleResult, Integer> >(hm.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<BattleResult, Integer> >() { 
            public int compare(Map.Entry<BattleResult, Integer> o1,  
                               Map.Entry<BattleResult, Integer> o2) 
            { 
                return (o2.getValue()).compareTo(o1.getValue()); 
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<BattleResult, Integer> temp = new LinkedHashMap<BattleResult, Integer>(); 
        for (Map.Entry<BattleResult, Integer> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    } 
    
        // function to sort hashmap by values 
    public static HashMap<BattleResult, Integer> sortByATK(HashMap<BattleResult, Integer> hm) 
    { 
        // Create a list from elements of HashMap 
        List<Map.Entry<BattleResult, Integer> > list = 
               new LinkedList<Map.Entry<BattleResult, Integer> >(hm.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<BattleResult, Integer> >() { 
            public int compare(Map.Entry<BattleResult, Integer> o1,  
                               Map.Entry<BattleResult, Integer> o2) 
            { 
                if(o1.getKey().winner == 1){
                    if(o2.getKey().winner == 1){
                        if(o1.getKey().remainingunits.count > o2.getKey().remainingunits.count)
                            return -1;
                        else
                            return 1;
                    }
                    else
                        return -1;
                }
                else if(o1.getKey().winner == -1){
                     if(o2.getKey().winner == 0)
                        return -1;
                     else if(o2.getKey().winner == 1)
                         return 1;
                }

                else if(o1.getKey().winner == 0){
                    if(o2.getKey().winner != 0)
                        return 1;
                    else{
                        if(o1.getKey().remainingunits.count < o2.getKey().remainingunits.count)
                            return -1;
                        else
                            return 1;
                    }
                }
                return 0;
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<BattleResult, Integer> temp = new LinkedHashMap<BattleResult, Integer>(); 
        for (Map.Entry<BattleResult, Integer> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    } 
    
    
        // function to sort hashmap by values 
    public static HashMap<BattleResult, Integer> sortByDEF(HashMap<BattleResult, Integer> hm) 
    { 
        // Create a list from elements of HashMap 
        List<Map.Entry<BattleResult, Integer> > list = 
               new LinkedList<Map.Entry<BattleResult, Integer> >(hm.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<BattleResult, Integer> >() { 
            public int compare(Map.Entry<BattleResult, Integer> o1,  
                               Map.Entry<BattleResult, Integer> o2) 
            { 
                if(o1.getKey().winner == 1){
                    if(o2.getKey().winner == 1){
                        if(o1.getKey().remainingunits.count > o2.getKey().remainingunits.count)
                            return 1;
                        else
                            return -1;
                    }
                    else
                        return 1;
                }
                else if(o1.getKey().winner == -1){
                     if(o2.getKey().winner == 0)
                        return 1;
                     else if(o2.getKey().winner == 1)
                         return -1;
                }

                else if(o1.getKey().winner == 0){
                    if(o2.getKey().winner != 0)
                        return -1;
                    else{
                        if(o1.getKey().remainingunits.count < o2.getKey().remainingunits.count)
                            return 1;
                        else
                            return -1;
                    }
                }
                return 0;
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<BattleResult, Integer> temp = new LinkedHashMap<BattleResult, Integer>(); 
        for (Map.Entry<BattleResult, Integer> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    } 
   
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JCheckBox AAgun;
  private javax.swing.JLabel ATKIPCcost;
  private javax.swing.JLabel ATKWinrate;
  private javax.swing.JTextField ATKart;
  private javax.swing.JLabel ATKarttxt;
  private javax.swing.JLabel ATKarttxt1;
  private javax.swing.JTextField ATKbattle;
  private javax.swing.JLabel ATKbattletxt;
  private javax.swing.JLabel ATKbattletxt1;
  private javax.swing.JTextField ATKbomb;
  private javax.swing.JLabel ATKbombtxt;
  private javax.swing.JLabel ATKbombtxt1;
  private javax.swing.JTextField ATKcarr;
  private javax.swing.JLabel ATKcarrtxt;
  private javax.swing.JLabel ATKcarrtxt1;
  private javax.swing.JTextField ATKcruise;
  private javax.swing.JLabel ATKcruisetxt;
  private javax.swing.JLabel ATKcruisetxt1;
  private javax.swing.JTextField ATKdest;
  private javax.swing.JLabel ATKdesttxt;
  private javax.swing.JLabel ATKdesttxt1;
  private javax.swing.JTextField ATKfight;
  private javax.swing.JLabel ATKfighttxt;
  private javax.swing.JLabel ATKfighttxt1;
  private javax.swing.JTextField ATKinf;
  private javax.swing.JLabel ATKinftxt;
  private javax.swing.JLabel ATKinftxt1;
  private javax.swing.JPanel ATKpanel;
  private javax.swing.JTextField ATKsub;
  private javax.swing.JLabel ATKsubtxt;
  private javax.swing.JLabel ATKsubtxt1;
  private javax.swing.JTextField ATKtank;
  private javax.swing.JLabel ATKtanktxt;
  private javax.swing.JLabel ATKtanktxt1;
  private javax.swing.JTextField ATKtrans;
  private javax.swing.JLabel ATKtranstxt;
  private javax.swing.JLabel ATKtranstxt1;
  private javax.swing.JLabel Aartnum;
  private javax.swing.JLabel Abattlenum;
  private javax.swing.JLabel Abombnum;
  private javax.swing.JLabel Acarrnum;
  private javax.swing.JLabel Acruisenum;
  private javax.swing.JLabel Adestnum;
  private javax.swing.JLabel Afightnum;
  private javax.swing.JLabel Ainfnum;
  private javax.swing.JCheckBox AmphibiousAssault;
  private javax.swing.JSpinner ArtOOL;
  private javax.swing.JLabel Asubnum;
  private javax.swing.JLabel Atanknum;
  private javax.swing.JLabel Atransnum;
  private javax.swing.JSpinner BattleOOL;
  private javax.swing.JSpinner BombOOL;
  private javax.swing.JSpinner CarrOOL;
  private javax.swing.JSpinner CruiseOOL;
  private javax.swing.JCheckBox CustomOOL;
  private javax.swing.JLabel DEFIPCcost;
  private javax.swing.JLabel DEFWinrate;
  private javax.swing.JTextField DEFart;
  private javax.swing.JTextField DEFbattle;
  private javax.swing.JTextField DEFbomb;
  private javax.swing.JTextField DEFcarr;
  private javax.swing.JTextField DEFcruise;
  private javax.swing.JTextField DEFdest;
  private javax.swing.JTextField DEFfight;
  private javax.swing.JTextField DEFinf;
  private javax.swing.JPanel DEFpanel;
  private javax.swing.JTextField DEFsub;
  private javax.swing.JTextField DEFtank;
  private javax.swing.JTextField DEFtrans;
  private javax.swing.JLabel Dartnum;
  private javax.swing.JLabel Dbattlenum;
  private javax.swing.JLabel Dbombnum;
  private javax.swing.JLabel Dcarrnum;
  private javax.swing.JLabel Dcruisenum;
  private javax.swing.JLabel Ddestnum;
  private javax.swing.JSpinner DestOOL;
  private javax.swing.JLabel Dfightnum;
  private javax.swing.JLabel Dinfnum;
  private javax.swing.JLabel Dsubnum;
  private javax.swing.JLabel Dtanknum;
  private javax.swing.JLabel Dtransnum;
  private javax.swing.JSpinner FightOOL;
  private javax.swing.JSpinner InfOOL;
  private javax.swing.JButton ResetButton;
  private javax.swing.JPanel Settings;
  private javax.swing.JCheckBox SingleTurn;
  private javax.swing.JSpinner SubOOL;
  private javax.swing.JPanel Summary;
  private javax.swing.JLabel TakenWGroundrate;
  private javax.swing.JSpinner TankOOL;
  private javax.swing.JSpinner TransOOL;
  private javax.swing.JButton UpdateLossOrder;
  private javax.swing.JDialog UpdateOOLWindow;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel10;
  private javax.swing.JLabel jLabel11;
  private javax.swing.JLabel jLabel12;
  private javax.swing.JLabel jLabel13;
  private javax.swing.JLabel jLabel14;
  private javax.swing.JLabel jLabel15;
  private javax.swing.JLabel jLabel16;
  private javax.swing.JLabel jLabel17;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JTable resulttable;
  // End of variables declaration//GEN-END:variables
}
