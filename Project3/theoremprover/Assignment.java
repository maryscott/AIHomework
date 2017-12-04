/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theoremprover;

/**
 *
 * @author MC
 */
public class Assignment {
    Parameter var;
    Parameter val;

    public Assignment(Parameter var, Parameter val)
    {
        this.var = var;
        this.val = val;
    }
    
    public Parameter getVar() {
        return var;
    }

    public void setVar(Parameter var) {
        this.var = var;
    }

    public Parameter getVal() {
        return val;
    }

    public void setVal(Parameter val) {
        this.val = val;
    }
    
    
}
