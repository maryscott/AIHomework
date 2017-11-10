
package grocerybag;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MC
 */
public class Problem {
    List<ListItem> list;
    int numItems;
    int numBags;
    int bagCapacity;
    List<Constraint> constraints;
    
    public Problem()
    {
        list = new ArrayList<>();
        constraints = new ArrayList<>();
    }
    
    public Problem(List<ListItem> list, int numItems, int bagCapacity, int numBags)
    {
        this.list = list;
        this.numItems = numItems;
        this.numBags = numBags;
    }

    public List<ListItem> getList() {
        return list;
    }

    public void setList(List<ListItem> list) {
        this.list = list;
    }

    public int getNumItems() {
        return numItems;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public int bagCapacity() {
        return bagCapacity;
    }

    public void setBagCapacity(int bagCapacity) {
        this.bagCapacity = bagCapacity;
    }

    public int getNumBags() {
        return numBags;
    }

    public void setNumBags(int numBags) {
        this.numBags = numBags;
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }
    
    
}
