/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grocerybag;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author MC
 */
public class LocalSearch {
    
    private Random rand = new Random();
    
    public GraphNode search(GraphNode node)
    {
        // if stuck re-randomize
        Set<ListItem> conflicts = randomFillBags(node);
        List<Bag> bags = node.getBags();
        
        for(ListItem item : conflicts)
        {
            // a heuristic to stop the loop...
            
            // attempt to remove conflict
            Bag bag = node.getSolution().get(item);
            bag.takeItemOutOfbag(item);
            // if no longer in conflict remove from conflicts
            // check bag it's in, they should also be removed from conflicts
        }
        
        return null;
    }
    
    public Set<ListItem> randomFillBags(GraphNode node)
    {
        Set<ListItem> conflictedItems = new HashSet<>();
        List<Bag> bags = node.getBags();
        for(Bag bag : bags)
        {
            bag.resetBag();
        }
        for(ListItem item : node.getItems())
        {
            Bag bag = bags.get(rand.nextInt(bags.size()));
            boolean conflicted = bag.forcePut(item);
            node.getSolution().put(item, bag);
            if(conflicted)
            {
                // add to conflicted Set and check other items in bag for conflicts
                conflictedItems.add(item);
                for(ListItem inBag : bag.getListOfItems())
                {
                   // check for conflicts within what's left in the bag add them as well
                    if(!bag.canPutItemIn(inBag))
                        conflictedItems.add(inBag);
                }
                
            }
        }
        
        return conflictedItems;
    }
    
}
