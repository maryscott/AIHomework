/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theoremprover;

import java.util.Objects;

/**
 *
 * @author MC
 */
public class Parameter {
    private String con;
    private int var;
    private boolean isCon;

    public Parameter(Parameter param)
    {
        this.con = param.getCon();
        this.var = param.getVar();
        this.isCon = param.getIsCon();
    }
    
    public Parameter(String con, boolean isConstant)
    {
        this.con = con;
        this.isCon = isConstant;
        this.var = -1;
    }
    
    public Parameter(int var)
    {
        this.var = var;
    }
    
    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public int getVar() {
        return var;
    }

    public void setVar(int var) {
        this.var = var;
    }
    
    public boolean getIsCon()
    {
        return isCon;
    }
    
    public void switchToVar(int var)
    {
        con = null;
        this.var = var;
        isCon = false;
    }
    
    public void setIsCon(boolean isCon)
    {
        this.isCon = isCon;
    }
    
    @Override
    public String toString()
    {
        if(con == null)
            return Integer.toString(var);
        else
            return con;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.con);
        hash = 59 * hash + this.var;
        hash = 59 * hash + (this.isCon ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Parameter other = (Parameter) obj;
        if (this.var != other.var) {
            return false;
        }
        if (this.isCon != other.isCon) {
            return false;
        }
        if (!Objects.equals(this.con, other.con)) {
            return false;
        }
        return true;
    }
    
    
}
