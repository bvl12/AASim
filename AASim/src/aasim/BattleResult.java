package aasim;

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
        if(this.winner != br.winner || this.ATKIPC != br.ATKIPC || this.DEFIPC != br.DEFIPC)
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