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
