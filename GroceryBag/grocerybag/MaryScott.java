
package grocerybag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 *
 * @author MC
 */
public class MaryScott {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        if(args.length == 0 || args.length > 2)
            printStartError();
        
        String fileName = args[0];
        String runType;
        
        if(args.length == 1)
            runType = "";
        else
            runType = args[1];
       
        if(!(runType.equals("-slow") || runType.equals("-local") || runType.equals("")))
            printStartError();

        Problem problem = FileParser.parseFile(fileName);
        
        List<Bag> bags = new ArrayList<>();
        
        for(int i = 0; i < problem.numBags; i++)
        {
            Bag bag = new Bag(problem.bagCapacity(), problem.getNumItems());
            bags.add(bag);
        }
        
        List<ListItem> items = problem.getList();
        //set domain for all itmes
        for(ListItem item : items)
        {
            item.updateDomain(bags);
        }

        PriorityQueue<ListItem> queue = new PriorityQueue<>(1000, new MostRestrictedValueComparator());
        queue.addAll(items);
        Map<ListItem, Bag> solution = new HashMap<>();
        GraphNode root = new GraphNode(queue, solution, bags);
        
        GraphNode answer;
        
        switch (runType) {
            default:
            case "":
                {
                    SearchArcConsistent search = new SearchArcConsistent();
                    answer = search.search(root);
                    break;
                }
            case "-slow":
                {
                    SearchWithOutArcConsistency search = new SearchWithOutArcConsistency(queue);
                    answer = search.search(root);
                    break;
                }
            case "-local":   
                {
                    LocalSearch search = new LocalSearch();
                    answer = search.search(root);
                    break;
                }
        }
        
        if(answer.getIsSuccess())
            printSolution(answer.getBags());
        else
            System.out.println("failure");

        //searchWithArcConsistency();
        
        //searchWithOutArcConsistency();
        
        //localSearch();
        
        
    }
    
//    public static void normalRun(String fileName)
//    {
//        Problem problem = FileParser.parseFile(fileName);
//        
//        List<Bag> bags = new ArrayList<>();
//        
//        for(int i = 0; i < problem.numBags; i++)
//        {
//            Bag bag = new Bag(problem.bagCapacity(), problem.getNumItems());
//            bags.add(bag);
//        }
//        
//        List<ListItem> items = problem.getList();
//        //set domain for all itmes
//        for(ListItem item : items)
//        {
//            item.updateDomain(bags);
//        }
//
//        PriorityQueue<ListItem> queue = new PriorityQueue<>(1000, new MostRestrictedValueComparator());
//        queue.addAll(items);
//        
//        Map<ListItem, Bag> solution = new HashMap<>();
//        GraphNode root = new GraphNode(queue, solution, bags);
//        SearchArcConsistent search = new SearchArcConsistent();
//        
//        GraphNode answer = search.search(root);
//        
//        if(answer.getIsSuccess())
//            printSolution(answer.getBags());
//        else
//            System.out.println("failure");
//        System.exit(0);
//    }
//    
//    public static void slowRun(String fileName)
//    {
//        Problem problem = FileParser.parseFile(fileName);
//        
//        List<Bag> bags = new ArrayList<>();
//        
//        for(int i = 0; i < problem.numBags; i++)
//        {
//            Bag bag = new Bag(problem.bagCapacity(), problem.getNumItems());
//            bags.add(bag);
//        }
//        
//        List<ListItem> items = problem.getList();
//        //set domain for all itmes
//        for(ListItem item : items)
//        {
//            item.updateDomain(bags);
//        }
//
//        PriorityQueue<ListItem> queue = new PriorityQueue<>(1000, new MostRestrictedValueComparator());
//        queue.addAll(items);
//        
//        Map<ListItem, Bag> solution = new HashMap<>();
//        GraphNode root = new GraphNode(queue, solution, bags);
//        SearchWithOutArcConsistency search = new SearchWithOutArcConsistency(queue);
//        
//        GraphNode answer = search.search(root);
//        
//        if(answer.getIsSuccess())
//            printSolution(answer.getBags());
//        else
//            System.out.println("failure");
//        System.exit(0);
//    }
//    
//    public static void localRun(String fileName)
//    {
//        Problem problem = FileParser.parseFile(fileName);
//        
//        List<Bag> bags = new ArrayList<>();
//        
//        for(int i = 0; i < problem.numBags; i++)
//        {
//            Bag bag = new Bag(problem.bagCapacity(), problem.getNumItems());
//            bags.add(bag);
//        }
//        
//        List<ListItem> items = problem.getList();
//        //set domain for all itmes
//        for(ListItem item : items)
//        {
//            item.updateDomain(bags);
//        }
//
//        PriorityQueue<ListItem> queue = new PriorityQueue<>(1000, new MostRestrictedValueComparator());
//        queue.addAll(items);
//        
//        Map<ListItem, Bag> solution = new HashMap<>();
//        GraphNode root = new GraphNode(queue, solution, bags);
//        LocalSearch search = new LocalSearch();
//        
//        GraphNode answer = search.search(root);
//        
//        if(answer.getIsSuccess())
//            printSolution(answer.getBags());
//        else
//            System.out.println("failure");
//        
//        System.exit(0);
//    }
    
    public static void printSolution(List<Bag> bags)
    {
        //how to know it was a success???
        System.out.println("Success");
        for(Bag bag : bags)
        {
            System.out.println(bag.getNamesOfItemsInBag());
        }
        
    }
    
    public static void printStartError()
    {
        System.out.println("Usage: java MaryScott <fileName> [-slow | -local]");
        System.exit(1);
    }
}
