
package grocerybag;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 *
 * @author MC
 */
public class FileParser {
    
    public static Problem parseFile(String fileName)
    {
        
        File file = new File(fileName);
        
        Problem problem = new Problem();
        try
        {
            List<ListItem> items = new ArrayList<>();
            List<Constraint> constraints = new ArrayList<>();
            Scanner input = new Scanner(file);
            
            // first line is num of bags
            String firstLine = input.nextLine();
            String[] firstLineSplit = firstLine.split(" ");
            int numBags = Integer.parseInt(firstLineSplit[0]);
            problem.setNumBags(numBags);
            
            // Second line is bag capacity
            String secondLine = input.nextLine();
            String[] secondLineSplit = secondLine.split(" ");
            int bagCapacity = Integer.parseInt(secondLineSplit[0]); 
            problem.setBagCapacity(bagCapacity);
            
            // items fill the rest of the file
            int itemNumber = 0;
            Map<String, Integer> reference = new HashMap<>();
            
            while (input.hasNextLine())
            {
                ListItem item;
                
                
                String line = input.nextLine();
                String[] lineSplit = line.split("\\s+");
                
                String itemName = lineSplit[0];
                int itemSize = Integer.parseInt(lineSplit[1]);
                
                // build item and add it to the list and reference for later use.
                item = new ListItem(itemSize, itemName, itemNumber);
                reference.put(itemName, itemNumber);
                itemNumber++;
                
                if(lineSplit.length == 2 || lineSplit[2].startsWith("//"))
                {
                    items.add(item);
                    continue;
                }
                
                ConstraintType type;
                if(lineSplit[2].startsWith("+"))
                {
                    type = ConstraintType.PLUS;
                    for(int i = 3; i < lineSplit.length; i++)
                    {
                        if(lineSplit[i].startsWith("//"))
                            break;
                        String constraintName = lineSplit[i];

                        item.addPlusConstraint(constraintName);
                        Constraint constraint = new Constraint(itemName, constraintName, type);
                        constraints.add(constraint);
                    }
                }
                else if(lineSplit[2].startsWith("-"))
                {
                    type = ConstraintType.MINUS;
                    for(int i = 3; i < lineSplit.length; i++)
                    {
                        if(lineSplit[i].startsWith("//"))
                            break;
                        String constraintName = lineSplit[i];

                        item.addConstraint(constraintName);
                        Constraint constraint = new Constraint(itemName, constraintName, type);
                        constraints.add(constraint);
                    }
                }
                items.add(item);
                
            }

            for(ListItem item : items)
            {
                item.initializeConstraints(itemNumber + 1);
                List<String> constraintNames = item.getConstraints();
                for(String name : constraintNames)
                {
                    int constraintNumber = reference.get(name);
                    item.addMinusConstraint(constraintNumber);
                }
                List<String> plusConstraintNames = item.getPlusConstraints();
                if(!plusConstraintNames.isEmpty())
                {
                    for(String name : plusConstraintNames)
                    {
                        item.reInitPlusConstraints();
                        int constraintNumber = reference.get(name);
                        item.addPlusConstraint(constraintNumber);
                    }
                }
                
            }
            
            problem.setList(items);
            problem.setConstraints(constraints);
            problem.setNumItems(itemNumber + 1);
            
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        return problem;
    }
    
    public static Problem parseFile2(String fileName)
    {
        File file = new File(fileName);
        
        Problem problem = new Problem();
        try
        {
            List<ListItem> items = new ArrayList<>();
            List<Constraint> constraints = new ArrayList<>();
            Scanner input = new Scanner(file);
            
            // first line is num of bags
            String firstLine = input.nextLine();
            String[] firstLineSplit = firstLine.split(" ");
            int numBags = Integer.parseInt(firstLineSplit[0]);
            problem.setNumBags(numBags);
            
            // Second line is bag capacity
            String secondLine = input.nextLine();
            String[] secondLineSplit = secondLine.split(" ");
            int bagCapacity = Integer.parseInt(secondLineSplit[0]); 
            problem.setBagCapacity(bagCapacity);
            
            // items fill the rest of the file
            int itemNumber = 0;
            Map<String, Integer> reference = new HashMap<>();
            Map<ListItem, List<String>> plusConstraints = new HashMap<>();
            Map<ListItem, List<String>> minusConstraints = new HashMap<>();
            
            while (input.hasNextLine())
            {
                ListItem item;
                itemNumber++;
                
                String line = input.nextLine();
                String[] lineSplit = line.split("\\s+");
                
                String itemName = lineSplit[0];
                int itemSize = Integer.parseInt(lineSplit[1]);
                
                // build item and add it to the list and reference for later use.
                item = new ListItem(itemSize, itemName, itemNumber);
                reference.put(itemName, itemNumber);
                items.add(item);
                
                if(lineSplit.length == 2 || lineSplit[2].startsWith("//"))
                {
                    items.add(item);
                    continue;
                }
                
                if(lineSplit[2].startsWith("+"))
                {
                    List<String> listOfConstraints = new ArrayList<>();
                    for(int i = 3; i < lineSplit.length; i++)
                    {
                        if(lineSplit[i].startsWith("//"))
                            break;
                        // make item2
                        String constraintName = lineSplit[i];
                        listOfConstraints.add(constraintName);
                    }
                    
                    plusConstraints.put(item, listOfConstraints);
                    // add all other combinations to the constraint list? crap I did not start on this early enough...
                }
                else if(lineSplit[2].startsWith("-"))
                {
                    List<String> listOfConstraints = new ArrayList<>();
                    for(int i = 3; i < lineSplit.length; i++)
                    {
                        if(lineSplit[i].startsWith("//"))
                            break;
                        String constraintName = lineSplit[i];
                        listOfConstraints.add(constraintName);
                    }
                    
                    minusConstraints.put(item, listOfConstraints);
                }
            }
            
            for(Entry<ListItem, List<String>> entry : plusConstraints.entrySet())
            {
                List<String> list = entry.getValue();
                for(String name : list)
                {
                    int itemNum = reference.get(name);
                    ListItem item2 = items.get(itemNum);
                    
                }
            }
            
            for(ListItem item : items)
            {
                item.initializeConstraints(itemNumber);
                List<String> constraintNames = item.getConstraints();
                for(String name : constraintNames)
                {
                    int constraintNumber = reference.get(name);
                    item.addMinusConstraint(constraintNumber);
                }
                List<String> plusConstraintNames = item.getPlusConstraints();
                if(!plusConstraintNames.isEmpty())
                {
                    for(String name : plusConstraintNames)
                    {
                        item.reInitPlusConstraints();
                        int constraintNumber = reference.get(name);
                        item.addPlusConstraint(constraintNumber);
                    }
                }
                
            }
            
            problem.setList(items);
            problem.setConstraints(constraints);
            problem.setNumItems(itemNumber);
            
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return problem;
    }
    
    public static void main(String[] args)
    {
        parseFile("example1.txt");
    }
}
