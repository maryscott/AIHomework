/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grocerybag;

import java.util.PriorityQueue;

/**
 *
 * @author MC
 */
public class SearchWithOutArcConsistency {
    
    private PriorityQueue<ListItem> queue;
    
    public SearchWithOutArcConsistency(PriorityQueue<ListItem> queue)
    {
        this.queue = queue;
    }
    
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
                GraphNode retNode = search(node);
                if(retNode.getIsSuccess())
                    return retNode;
                else
                {
                    bag.takeItemOutOfbag(item);
                    node.setItems(retNode.getItems());
                }
            }
        }
        
        return copy;
    }
}
