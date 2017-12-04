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
public class PrintItem implements Comparable<PrintItem>{
    int parent1;
    int parent2;
    int child;
    int priority;
    
    public PrintItem(int parent1, int parent2, int child)
    {
        this.parent1 = parent1;
        this.parent2 = parent2;
        this.child = child;
    }

    public int getParent1() {
        return parent1;
    }

    public void setParent1(int parent1) {
        this.parent1 = parent1;
    }

    public int getParent2() {
        return parent2;
    }

    public void setParent2(int parent2) {
        this.parent2 = parent2;
    }

    public int getChild() {
        return child;
    }

    public void setChild(int child) {
        this.child = child;
    }

    @Override
    public int compareTo(PrintItem o) {
        return 1;
    }
    
}
