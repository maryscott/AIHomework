/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theoremprover;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Timer;
import javafx.util.Pair;

/**
 *
 * @author MC
 */
public class TheoremProver {

    private static List<Sentence> kb;
    private static Parser parser = new Parser();
    private static List<Assignment> theta = new ArrayList<>();
    private static PriorityQueue<QueueItem> queue = new PriorityQueue<>();
    private static Random rand = new Random();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if (args.length != 1)
            closeWithUsage();
        
        String fileName = args[0];
       
        kb = parser.ReadKB(fileName);
        
        System.out.println("Given Knowledge base: ");
        printKB(kb);
        
        System.out.println("Time to start doing stuff to the KB");
        System.out.println("\nLet's start with Random Solver");
                
        RandomResolve();
        
        // need to reset the KB for differest search method
        
        System.out.println("\nNow for a Heuristic Solver");
        
        kb = parser.ReadKB(fileName);
        theta = new ArrayList<>();
        queue = new PriorityQueue<>();
        HeuristicResolve();
        
//        System.out.println("\n Assignments:");
//        printAssignments(theta);
        
        
    }
    
    public static void closeWithUsage()
    {
        System.err.println("Usage: TheoremProver <FileName>");
        System.exit(0);
    }
    
    public static void printKB(List<Sentence> kb)
    {
        for(Sentence sent : kb)
        {
            String sentence = "";
            for (Predicate pred: sent.getPredList())
            {
                if(pred.getNeg())
                    sentence += "!";
                sentence += pred.getName();
                sentence += "(";
                for(int i = 0; i < pred.getParameters().size(); i++)
                {
                    sentence += pred.getParameters().get(i).toString();
                    if(i < pred.getParameters().size() - 1)
                        sentence += ",";
                }
                sentence += ") ";
            }
//            sentence += "parent 1 = ";
//            sentence += sent.getParent1();
//            sentence += " parent 2 = ";
//            sentence += sent.getParent2();
            System.out.println(sentence);
        }
    }
    
    public static String sentenceString(Sentence sent)
    {
        String sentence = "";
            for (Predicate pred: sent.getPredList())
            {
                if(pred.getNeg())
                    sentence += "!";
                sentence += pred.getName();
                sentence += "(";
                for(int i = 0; i < pred.getParameters().size(); i++)
                {
                    sentence += pred.getParameters().get(i).toString();
                    if(i < pred.getParameters().size() - 1)
                        sentence += ",";
                }
                sentence += ") ";
            }
            return sentence;
    }
    
    public static void printAssignments(List<Assignment> theta)
    {
        for (Assignment assign : theta)
        {
            System.out.println("Var: " + assign.getVar() + " and Val: " + assign.getVal());
        }
    }
    
    public static void printStepsToResolution(Sentence sent)
    {
        System.out.println("Contradiction Found");
        // Use a queue? to fill in the sentences, that way its a breadth first search
        // just pre-pend rather than append
        PriorityQueue<PrintItem> printQueue = new PriorityQueue<>();
        PrintItem item = new PrintItem(sent.getParent1(), sent.getParent2(), -1);
        printQueue.add(item);
      
        String printThis = "Thus we have a contradiction";
        while(!printQueue.isEmpty())
        {
            PrintItem parents = printQueue.poll();
            if(parents.getParent1() == -1)
                continue;
            Sentence parent1 = kb.get(parents.getParent1());
            Sentence parent2 = kb.get(parents.getParent2());
            
            if(parent1.getParent1() < 0 && parent1.getParent2() < 0)
            {
                PrintItem parentItem1 = new PrintItem(parent1.getParent1(), parent1.getParent2(), parents.getParent1());
                printQueue.add(parentItem1);
            }
            
            if(parent1.getParent1() < 0 && parent1.getParent2() < 0)
            {
                PrintItem parentItem2 = new PrintItem(parent2.getParent1(), parent2.getParent2(), parents.getParent2());
                printQueue.add(parentItem2);
            }
            
            String newLine;
            if(parents.getChild() == -1)
                newLine = sentenceString(parent1) + " AND " + sentenceString(parent2) + " RESOLVE TO " + "\n";
            else
            {
                Sentence child = kb.get(parents.getChild());
                newLine = sentenceString(parent1) + " AND " + sentenceString(parent2) + " RESOLVE TO " + sentenceString(child) + "\n";
            }
            printThis = newLine + printThis;
        }
        
        System.out.println(printThis);
    }
    
    // Random Solver
    public static void RandomResolve()
    {
        long startTime = System.currentTimeMillis();
        int numSteps = 0;
        Boolean done = false;
        
        // Initial filling of the queue
        fillRandomQueue();
        // start running things through the queue
        while(!done && !queue.isEmpty())
        {
            QueueItem item = queue.poll();
            numSteps++;
//            System.out.println("trying to Resolve: " + sentenceString(item.getSent1()) + " AND " + sentenceString(item.getSent2()));
            List<Sentence> newSentences = tryResolution(item.getSent1(), item.getSent2());
            for(Sentence sent : newSentences)
            {
                if(sent.getPredList().isEmpty())
                {
                    // we have found the end of the line and should stop!
                    printStepsToResolution(sent);
                    done = true;
                }
            }
            addToRandomQueue(newSentences);
        }
        
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime; // multiply by something to get to seconds?
        System.out.println("RandomResolve: #steps = " + numSteps + ", time = " + timeTaken);
    }
    
    public static void fillRandomQueue()
    {
        for(int i = 0; i < kb.size(); i++)
        {
            for(int j = i+1; j < kb.size(); j++)
            {
                QueueItem item = new QueueItem(kb.get(i), kb.get(j), rand.nextInt(100));
                queue.add(item);
            }
        }
    }
    
    public static void addToRandomQueue(List<Sentence> newSentences)
    {
        for(Sentence sent : newSentences)
        {
            int end = kb.indexOf(sent);
            for(int i = 0; i < end; i++)
            {
                QueueItem item = new QueueItem(kb.get(i), sent, rand.nextInt());
                queue.add(item);
            }
        }
    }
    
    // Heuristic Solver
    public static void HeuristicResolve()
    {
        long startTime = System.currentTimeMillis();
        int numSteps = 0;
        Boolean done = false;
        
        // Initial filling of the queue
        fillHeuristicQueue();
        // start running things through the queue
        while(!done && !queue.isEmpty())
        {
            QueueItem item = queue.poll();
            numSteps++;
//            System.out.println("trying to Resolve: " + sentenceString(item.getSent1()) + " AND " + sentenceString(item.getSent2()));
            List<Sentence> newSentences = tryResolution(item.getSent1(), item.getSent2());
            for(Sentence sent : newSentences)
            {
                if(sent.getPredList().isEmpty())
                {
                    // we have found the end of the line and should stop!
                    printStepsToResolution(sent);
                    done = true;
                }
            }
            addToHeuristicQueue(newSentences);
        }
        
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime; // multiply by something to get to seconds?
        System.out.println("RandomResolve: #steps = " + numSteps + ", time = " + timeTaken);
    }
    
    public static void fillHeuristicQueue()
    {
        for(int i = 0; i < kb.size(); i++)
        {
            for(int j = i+1; j < kb.size(); j++)
            {
                QueueItem item = new QueueItem(kb.get(i), kb.get(j), determinePriority(kb.get(i), kb.get(j)));
                queue.add(item);
            }
        }
    }
    
    public static void addToHeuristicQueue(List<Sentence> newSentences)
    {
        for(Sentence sent : newSentences)
        {
            int end = kb.indexOf(sent);
            for(int i = 0; i < end; i++)
            {
                QueueItem item = new QueueItem(kb.get(i), sent, determinePriority(kb.get(i), sent));
                queue.add(item);
            }
        }
    }
    
    public static int determinePriority(Sentence sent1, Sentence sent2)
    {
        // Simplest Heuristic I can think of
        int priority;
        if(sent1.getPredList().size() == sent2.getPredList().size())
        {
            priority = sent1.getPredList().size() + 10;
        }
        else if (sent1.getPredList().size() <= sent2.getPredList().size())
        {
            priority = sent2.getPredList().size() + 10;
        }
        else
        {
            priority = sent1.getPredList().size() + 10;
        }
        
//        for(Predicate pred1 : sent1.getPredList())
//        {
//            for(Predicate pred2 : sent2.getPredList())
//            {
//                if(pred1.getName().equals(pred2.getName()))
//                    priority--;
//                if(pred1.getNeg() != pred2.getNeg())
//                    priority--;
//            }
//        }
        
        return priority;
    }
    
    // Returns new sentences
    public static List<Sentence> tryResolution(Sentence sent1, Sentence sent2)
    {
        //sentlist[sent1]
        //sentlist[sent2]
        List<Sentence> newSentences = new ArrayList<>();
        int p1,p2;
        for(p1 = 0; p1 < sent1.getPredList().size(); p1++)
        {
            for(p2 = 0;p2 < sent2.getPredList().size(); p2++)
            {
                int numAssign = unifyPred(sent1.getPredList().get(p1), sent2.getPredList().get(p2), theta);
                if(numAssign >= 0)
                {
                    Sentence sent = AddSentenceFromResolution(sent1, sent2, p1, p2, theta, numAssign);
                    newSentences.add(sent);
                }
            }
        }
        return newSentences;
    }
    
    // return number of assignments
    public static int unifyPred(Predicate p1, Predicate p2, List<Assignment> theta)
    {
        int numAssign = 0;
        if(!p1.getName().equals(p2.getName()))
            return -1;
        
        if(p1.getNeg() == p2.getNeg())
            return -1;
        
        for(int i = 0; i < p1.getParameters().size(); i++)
        {
//            System.out.print(p1.getName() + p1.getParameters().size());
//            System.out.print(p2.getName() + p2.getParameters().size() + "\n");
            Parameter param1 = new Parameter(p1.getParameters().get(i));
            Parameter param2 = new Parameter(p2.getParameters().get(i));
            // need a way to update without actually writing over things...
            for(int j = 0; j < theta.size(); j++)
            {
                // update params to proper updated stuff...
                Assignment assign = theta.get(j);
                if(param1.equals(assign.getVar()))
                    param1 = assign.getVal();
                if(param2.equals(assign.getVar()))
                    param2 = assign.getVal();
            }
            
            if (!param1.getIsCon())
                numAssign += unifyVar(param1, param2);
            else if (!param2.getIsCon())
                numAssign += unifyVar(param2, param1);
            else if (param1.getIsCon() && param2.getIsCon())
            {
                if(param1.getCon().equals(param2.getCon()))
                    return 0;
                else
                    return -1;
            }
        }
        
        return numAssign;
    }
    
    public static int unifyVar(Parameter param1, Parameter param2)
    {
        int numAssign = 0;
        //so the first assignment can get in...
        if(param1 == param2)
        {
            return -1;
        }
        
        if(theta.isEmpty())
        {
            Assignment newAssign = new Assignment(param1, param2);
            theta.add(newAssign);
            numAssign++;
        }
        else
        {
            for(int i = 0; i < theta.size(); i++)
            {
                Assignment assign = theta.get(i);
                if(assign.getVar().equals(param1) && assign.getVal().equals(param2))
                    continue;
                if(assign.getVar().equals(param1))
                    numAssign += unifyVar(assign.getVal(), param2);
                else if(param2.equals(assign.getVar()))
                    numAssign += unifyVar(param1, assign.getVal());
                else if(!AlreadyInTheta(param1,param2))
                {
                    
                    Assignment newAssign = new Assignment(param1, param2);
                    theta.add(newAssign);
                    numAssign++;
                }
            }
        }
        return numAssign;
    }
    
    public static boolean AlreadyInTheta(Parameter param1, Parameter param2)
    {
        boolean inTheta = false;
        
        for(Assignment assign : theta)
        {
            if(assign.getVar().equals(param1) && assign.getVal().equals(param2))
                inTheta = true;
        }
        return inTheta;
    }
    
    // return new sentence
    public static Sentence AddSentenceFromResolution(Sentence sent1, Sentence sent2, int p1, int p2, List<Assignment> theta, int numAssign)
    {
        List<Predicate> predlist1 = sent1.getPredList();
        List<Predicate> predlist2 = sent2.getPredList();
        List<Predicate> predlist = new ArrayList<>();
        int numPreds = 0;
        
        for(int i = 0; i < predlist1.size(); i++)
        {
            if(i == p1)
                continue;
            predlist.add(new Predicate(predlist1.get(i)));
            numPreds++;
        }
        for(int i = 0; i < predlist2.size(); i++)
        {
            if(i == p2)
                continue;
            predlist.add(new Predicate(predlist2.get(i)));
            numPreds++;
        }
        // Perform substitutions
        
        Sentence sent = new Sentence(predlist, false, numPreds, "", kb.indexOf(sent1), kb.indexOf(sent2));
        // Standardize Apart
        parser.StandaradizeNewSentence(sent);
        
        kb.add(sent);
        return sent;
    }
    
}
