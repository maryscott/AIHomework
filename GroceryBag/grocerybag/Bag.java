
package grocerybag;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 *
 * @author MC
 */
public class Bag {
    private int maxCapacity;
    private int currentSize;
    private List<ListItem> items;
    private BitSet itemsInBag;
    private BitSet cannotGoIn;
    private BitSet resetBitSet;
    
    public Bag(int maxCapacity, int totalNumItems)
    {
        this.maxCapacity = maxCapacity;
        currentSize = 0;
        items = new ArrayList<>();
        cannotGoIn = new BitSet(totalNumItems);
        itemsInBag = new BitSet(totalNumItems);
        resetBitSet = new BitSet(totalNumItems);
    }
    
    public Bag(Bag bag)
    {
        this.maxCapacity = bag.maxCapacity;
        this.currentSize = bag.currentSize;
        this.items = new ArrayList<>(bag.items);
        this.cannotGoIn = (BitSet)bag.cannotGoIn.clone();
        this.itemsInBag = (BitSet)bag.itemsInBag.clone();
        this.resetBitSet = (BitSet)bag.resetBitSet.clone();
    }
    
    public boolean putItemInBag(ListItem item)
    {
        int newSize = currentSize + item.getSize();
        
        // check this items constraints agains items already in bag
        for(ListItem itemInBag : items)
        {
            if(!item.canGoInSameBag(itemInBag.getItemNumber()))
                return false;
        }
        
        if (cannotGoIn.get(item.getItemNumber()))
        {
            return false;
        }
        else if (newSize > maxCapacity)
        {
            return false;
        }
        else
        {
           items.add(item);
           currentSize += item.getSize();
           itemsInBag.set(item.getItemNumber());
           BitSet itemConstraints = item.getItemConstraints();
           cannotGoIn.or(itemConstraints);
           return true;
        }
    }
    
    public String getNamesOfItemsInBag()
    {
        String namesOfItems = "";
        for(ListItem item : items)
        {
            namesOfItems += item.getName() + " ";
        }
        
        return namesOfItems;
    }
    
    // return true if causes a conflict
    public boolean forcePut(ListItem item)
    {
        boolean causesConflict = false;
        items.add(item);
        currentSize += item.getSize();
        if(currentSize > maxCapacity)
            causesConflict = true;
        itemsInBag.set(item.getItemNumber());
        if(cannotGoIn.get(item.getItemNumber()))
            causesConflict = true;
        BitSet itemConstraints = item.getItemConstraints();
        cannotGoIn.or(itemConstraints);
        
        return causesConflict;
    }
    
    public boolean canPutItemIn(ListItem item)
    {
        return !cannotGoIn.get(item.getItemNumber());
    }
    
    public void takeItemOutOfbag(ListItem item)
    {
        items.remove(item);
        currentSize -= item.getSize();
        itemsInBag.flip(item.getItemNumber());
        cannotGoIn.and(resetBitSet);
        for(ListItem itemInBag : items)
        {
            cannotGoIn.or(itemInBag.getItemConstraints());
        }
    }
    
    public void resetBag()
    {
        currentSize = 0;
        items.clear();
        cannotGoIn.and(resetBitSet);
        itemsInBag.and(resetBitSet);
    }
    
    public int getConstraintValue()
    {
        return cannotGoIn.cardinality();
    }
    
    public BitSet getCannotGoIn()
    {
        return cannotGoIn;
    }
    
    public List<ListItem> getListOfItems()
    {
        return items;
    }
    
}
