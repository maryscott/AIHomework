/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grocerybag;

/**
 *
 * @author MC
 */
public class Constraint {
    String item1;
    String item2;
    ConstraintType type;
    
    public Constraint(String item1, String item2, ConstraintType type)
    {
        this.item1 = item1;
        this.item2 = item2;
        this.type = type;
    }
    
    public boolean canTheseGoTogether(String item1, String item2)
    {
        if(type == ConstraintType.PLUS)
            return true;
        if(type == ConstraintType.MINUS)
            return false;
        
        return false;
    }
}
