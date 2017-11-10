/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grocerybag;

import java.util.Comparator;

/**
 *
 * @author MC
 */
public class leastContainingValueComparator implements Comparator<Bag>{

    @Override
    public int compare(Bag a, Bag b) {
        
        return a.getConstraintValue() < b.getConstraintValue() 
                ? -1 
                : a.getConstraintValue() == b.getConstraintValue() ? 0 : 1;
    }
    
}
