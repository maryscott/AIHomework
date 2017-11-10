
package grocerybag;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author MC
 */
public class ListItem implements Comparator<ListItem>{
    private int size;
    private String name;
    private int itemNumber;
    private BitSet cannotGoWith;
    private List<String> constraints;
    private List<String> plusConstraints;
    private boolean inConflict = false;
    private List<Bag> domain;
    
    public ListItem(int size, String name, int itemNumber)
    {
        this.size = size;
        this.name = name;
        this.itemNumber = itemNumber;
        constraints = new ArrayList<>();
        plusConstraints = new ArrayList<>();
    }
    
    public void initializeConstraints(int numTotalItems)
    {
        cannotGoWith = new BitSet(numTotalItems);
    }
    
    public void addMinusConstraint(int itemNumber)
    {
        cannotGoWith.set(itemNumber);
    }
    
    public void reInitPlusConstraints()
    {
        cannotGoWith.set(0, cannotGoWith.size());
    }
    
    public void addPlusConstraint(int itemNumber)
    {
        if (cannotGoWith.get(itemNumber))
            cannotGoWith.flip(itemNumber);
    }
    
   public void addConstraint(String itemName)
    {
        constraints.add(itemName);
    }
   
   public void addPlusConstraint(String itemName)
   {
       plusConstraints.add(itemName);
   }
    
    public int getSize()
    {
        return size;
    }
    
    public int getItemNumber()
    {
        return itemNumber;
    }
    
    public BitSet getItemConstraints()
    {
        return cannotGoWith;
    }
    
    public List<String> getConstraints()
    {
        return constraints;
    }
    
    public List<String> getPlusConstraints()
    {
        return plusConstraints;
    }
    
    public String getName()
    {
        return name;
    }
    
    public boolean canGoInSameBag(int itemNumber)
    {
        return !cannotGoWith.get(itemNumber);
    }
    
    public void setInConflict(boolean inConflict)
    {
        this.inConflict = inConflict;
    }
    
    public boolean getInConflict()
    {
        return inConflict;
    }
    
    public List<Bag> getDomain(){
        return domain;
    }
    
    public void updateDomain(List<Bag> domain)
    {
        this.domain = domain;
    }
    
    public boolean removeFromDomain(Bag bag)
    {
        return this.domain.remove(bag);
    }
    
    public int getDomainSize()
    {
        return this.domain.size();
    }

    @Override
    public int compare(ListItem a, ListItem b) {
        return a.getSize() < b.getSize() ? -1 : a.getSize() == b.getSize() ? 0 : 1;
    }
    
}
