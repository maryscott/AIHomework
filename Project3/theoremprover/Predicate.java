/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theoremprover;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MC
 */
public class Predicate {
    private boolean neg;
    private String name;
    private List<Parameter> paramlist;
    private int numParam;
    
    public Predicate(String name, boolean neg)
    {
        this.name = name;
        this.neg = neg;
        paramlist = new ArrayList<>();
        numParam = 0;
    }
    
    public Predicate(String name, List<Parameter> paramlist, int numParam)
    {
        this.name = name;
        this.numParam = numParam;
        this.paramlist = paramlist;
    }
    
    public Predicate(Predicate pred)
    {
        this.neg = pred.getNeg();
        this.name = pred.getName();
        this.paramlist = new ArrayList<>();
        for(Parameter param : pred.getParameters())
        {
            Parameter addParam = new Parameter(param);
            paramlist.add(addParam);
        }
        
    }

    public void AddParameter(Parameter param)
    {
        paramlist.add(param);
        numParam++;
    }
    
    public List<Parameter> getParameters()
    {
        return this.paramlist;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public boolean getNeg()
    {
        return neg;
    }
}
