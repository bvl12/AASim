package aasim;

public class SingleTurnResult {
    Units remainingATK;
    Units remainingDEF;
    int ATKIPC;
    int DEFIPC;
    
    public SingleTurnResult(Units remainingATK, Units remainingDEF, int AIPC, int DIPC){
        this.remainingATK = remainingATK;
        this.remainingDEF = remainingDEF;
        this.ATKIPC = AIPC;
        this.DEFIPC = DIPC;
    }
    
    public boolean equals(SingleTurnResult br){
        for(int i = 0; i<br.remainingATK.units.length; i++){
          if(this.remainingATK.units[i] != br.remainingATK.units[i])
            return false;
        }
        for(int i = 0; i < br.remainingDEF.units.length; i++){
          if(this.remainingDEF.units[i] != br.remainingDEF.units[i])
            return false;
        }
        return true;
    }
}
