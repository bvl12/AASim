/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aasim;

import java.util.Arrays;

/**
 *
 * @author Andrew
 */
public class BattleResult {
    int winner;
    Units remainingunits;
    int ATKIPC;
    int DEFIPC;
    
    public BattleResult(int winner, Units remainingunits, int AIPC, int DIPC){
        this.winner = winner;
        this.remainingunits = remainingunits;
        this.ATKIPC = AIPC;
        this.DEFIPC = DIPC;
    }
    
    public boolean equals(BattleResult br){
        if(this.winner != br.winner)
            return false;
        for(int i = 0; i<br.remainingunits.units.length; i++){
            if(this.remainingunits.units[i] != br.remainingunits.units[i])
                return false;
        }
        return true;
    }
    
    public void print(){
        System.out.print(this.winner + "\t");
        for(int i = 0; i < this.remainingunits.units.length; i++){
            System.out.print(this.remainingunits.units[i] + "\t");
        }
        System.out.println();
    }
}
