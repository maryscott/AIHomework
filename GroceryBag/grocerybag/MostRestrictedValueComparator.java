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
public class MostRestrictedValueComparator implements Comparator<ListItem>{

    @Override
    public int compare(ListItem a, ListItem b) {
        return a.getConstraints().size() > b.getConstraints().size() 
                ? -1 
                : a.getConstraints().size() == b.getConstraints().size() ? 0 : 1;
    }
    
}
