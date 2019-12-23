package aasim;

import java.util.Arrays;

public class Units {
    int count;
    int[] units;
    
    public Units(int[] units){
        this.count = 0;
        this.units = Arrays.copyOf(units, units.length + 1);
        for(int i:units){
            this.count += i;
        }
    }
}
