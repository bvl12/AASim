package aasim;

import java.util.Arrays;

public class Units {
    int count;
    int[] units;
    
    public Units(int[] units){
        this.count = 0;
        this.units = Arrays.copyOf(units, units.length + 1);
        for(int i = 0; i < units.length-1; i++){
            this.count += units[i];
        }
    }
}
