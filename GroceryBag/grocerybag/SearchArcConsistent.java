/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grocerybag;

import java.util.BitSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author MC
 */
public class SearchArcConsistent {
    
     public GraphNode search(GraphNode node)
    {
        GraphNode copy = new GraphNode(node);
        
        if(node.getItems().isEmpty())
        {
            node.setIsSuccess(true);
            return node;
        } 
        ListItem item = node.getItems().remove();
        
        PriorityQueue<Bag> bagQueue = new PriorityQueue(new leastContainingValueComparator());
        bagQueue.addAll(node.getBags());
        
        while (!bagQueue.isEmpty())
        {
            // Assign a bag
            Bag bag = bagQueue.poll();
            if(bag.putItemInBag(item))
            {
                // update arc consistency
                boolean canGoOn = updateArcConsistency(node, item, bag);
                // if failure??? like a domain == 0
                if(!canGoOn)
                {
                    //undo arc consistency, move to next bag to try
                    node = new GraphNode(copy);
                    node.getItems().remove();
                    continue;
                }
                
                GraphNode retNode = search(node);
                if(retNode.getIsSuccess())
                    return retNode;
                else
                {
                    // we messed with arc consistency so reverse it
                    node = new GraphNode(copy);
                    node.getItems().remove();
                    
                    bag.takeItemOutOfbag(item);
                    node.setItems(retNode.getItems());
                }
            }
        }
        
        return copy;
    }
    
    private boolean updateArcConsistency(GraphNode node, ListItem item, Bag bag)
    {
        // go to each of my constrainted items and take out this bag from their domain
        if (node.getItems().isEmpty())
            return true;
        List<ListItem> allItems = node.getItemList();
        // regular Q put item in it
        Queue<ListItem> queue = new PriorityQueue<>();
        queue.offer(item);
        while (!queue.isEmpty())
        {
            ListItem item3 = queue.poll();
            BitSet bit = item3.getItemConstraints();
            for (int i = bit.nextSetBit(0); i >= 0; i = bit.nextSetBit(i+1)) 
            {
                if (i >= allItems.size()) {
                    break; // or (i+1) would overflow
                }
                // operate on index i here\
                ListItem item2 = allItems.get(i);
                if(revise(node, item3, item2))
                {
                    if (item.getDomain().isEmpty())
                            return false;
                    queue.offer(item2);
                }
                
            }
        }
        return true;
    }
     
    private static boolean revise(GraphNode problem, ListItem item1, ListItem item2)
    {
        boolean revised = false;
        for(Bag bag : item1.getDomain())
        {
            List<Bag> item2Bags = item2.getDomain();
            if(item2Bags.contains(bag) && item2Bags.size() == 1)
            {
                item1.getDomain().remove(bag);
                revised = true;
            }
        }
        return revised;
    }
}
