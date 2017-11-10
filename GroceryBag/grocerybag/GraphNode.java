
package grocerybag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author MC
 */
public class GraphNode {
    PriorityQueue<ListItem> items;
    List<ListItem> itemList;
    Map<ListItem, Bag> solution;
    List<Bag> bags;
    Boolean isSuccess;
    Set<ListItem> itemsInConflict;
    
    public GraphNode(PriorityQueue<ListItem> items, Map<ListItem, Bag> solution, List<Bag> bags)
    {
        this.items = items;
        itemList = new ArrayList<>(items);
        itemList.sort(new ItemNumberComparator());
        this.solution = solution;
        this.bags = bags;
        isSuccess = false;
//        itemsInConflict = new PriorityQueue(1000, new ListItemSizeComparator());
        itemsInConflict = new HashSet<>();
        
    }
    
    public GraphNode(GraphNode graph)
    {
        this.items = new PriorityQueue<>(new MostRestrictedValueComparator());
        this.items.addAll(graph.getItems());
        itemList = graph.getItemList();
        itemList.sort(new ItemNumberComparator());
        this.solution = new HashMap<>(graph.solution);
        this.bags = graph.bags;
//        this.bags = new ArrayList<>();
//        for(Bag bag : graph.getBags())
//        {
//            this.bags.add(new Bag(bag));
//        }
        isSuccess = graph.getIsSuccess();
//        itemsInConflict = new PriorityQueue(1000, new ListItemSizeComparator());
        itemsInConflict = new HashSet<>(graph.itemsInConflict);
    }

    public PriorityQueue<ListItem> getItems() {
        return items;
    }

    public void setItems(PriorityQueue<ListItem> items) {
        this.items = items;
    }
    
    public List<ListItem> getItemList()
    {
        return itemList;
    }

    public Map<ListItem, Bag> getSolution() {
        return solution;
    }

    public void setSolution(Map<ListItem, Bag> solution) {
        this.solution = solution;
    }

    public List<Bag> getBags() {
        return bags;
    }

    public void setBags(List<Bag> bags) {
        this.bags = bags;
    }
    
    public boolean getIsSuccess()
    {
        return isSuccess;
    }
    
    public void setIsSuccess(boolean isSuccess)
    {
        this.isSuccess = isSuccess;
    }
    
    public void addItemInConflict(ListItem item)
    {
        itemsInConflict.add(item);
    }
    
    public ListItem nextItemInConflict()
    {
        return null;
    }
}
