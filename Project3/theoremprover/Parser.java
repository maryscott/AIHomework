/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theoremprover;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author MC
 */
public class Parser {
    
    private Scanner readCommand = new Scanner(System.in);
    private Scanner readFile;
    private final int MAXPRED = 50;
    private final int MAXPARAM = 10;
    
    //private ArrayList<Predicate> predlist = new ArrayList<>();
    
    private int nextVar = 0;
    
    public List<Sentence> LoadKB(String fileName)
    {
       List<Sentence> kb = ReadKB(fileName);
       
       return kb;
    }
    
    public List<Sentence> ReadKB(String filename)
    {
        File file = new File(filename);
        String line;
        boolean refuteFlag = false;
        List<Sentence> kb = new ArrayList<>();
        
        try
        {
            readFile = new Scanner(file);
            while (readFile.hasNextLine())
            {
                line = readFile.nextLine();
                if(line.equals(""))
                {
                    refuteFlag = true;
                    continue;
                }
                Sentence sent = StringToSentence(line, refuteFlag);
                if (sent == null)
                {
                    System.out.println("Unable to parse line: " + line);
                    return new ArrayList<>();
                }
                else
                    kb.add(sent);
            }
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("You gave a bad filename, shame on you!");
            return new ArrayList<>();
        }
        
        StandardizeKB(kb);
        return kb;
    }
    
    public Sentence StringToSentence(String line, boolean refuteFlag) {
        ArrayList<Predicate> predlist = new ArrayList<>();
        Parameter param;
        Sentence sent;
        int i = 0;
        int j = 0;
        int numParams = 0;
        int numPreds = 0;
        boolean done;
        boolean neg = false;
        boolean isConstant = false;
        Predicate pred;
        String comments = "";
        
        while (i < line.length() && line.charAt(i) != '\n' && line.charAt(i) != '.')
        {
            while (i < line.length() && line.charAt(i) == ' ')
                i++;
            if(i >= line.length())
                break;
            if(line.charAt(i) == '\n' || line.charAt(i) == '.')
            {
                comments = line.substring(i + 1);
                break;
            }
            if(line.charAt(i) == '!')
            {
                neg = true;
                i++;
                while(line.charAt(i) == ' ')
                    i++;
            }
            // start of predicate name
            j = i;
            // find end of predicate name
            while(((line.charAt(j) >= 'a') && (line.charAt(j) <= 'z')) || ((line.charAt(j)>='A') && (line.charAt(j)<='Z')))
                j++;
            // make sure next char is what it should be
            if(line.charAt(j) != '(')
                return null;
            // make sure we have something to put as the name
            if(j == i)
                return null;
            // grab the name
            String pname = line.substring(i, j);
            pred = new Predicate(pname, neg);
            numPreds++;
            done = false;
            numParams = 0;
            while(!done)
            {
                i = j+1;
                while(line.charAt(i) == ' ')
                    i++;
                j = i;
                if((line.charAt(j) >= 'A' && line.charAt(j) <= 'Z'))
                    isConstant = true;
                while((line.charAt(j) >= 'a' && line.charAt(j) <= 'z') || (line.charAt(j) >= 'A' && line.charAt(j) <= 'Z'))
                    j++;
                switch(line.charAt(j))
                {
                    case ' ':
                    case ')':
                    case ',': // param[snum][p],&line[i],j-i
                        String paramString = line.substring(i, j);
                        param = new Parameter(paramString, isConstant);
                        pred.AddParameter(param);
                        numParams++;
                        break;
                    default: return null;
                }
                while(line.charAt(j) == ' ')
                    j++;
                switch(line.charAt(j))
                {
                    case ')': 
                        done = true; 
                        break;
                    case ',': 
                        break;
                    default: 
                        return null;
                }
            }
            predlist.add(pred);
            i = j+1;
            
        }
        sent = new Sentence(predlist, refuteFlag, numPreds, comments);
        return sent;   
    }
    
    void StandardizeKB(List<Sentence> kb) {
        // go through each sentence, all lowercase parameters are variables
        // same "params" in same sentence stay the same, but if x is in sent 1 and sent 2 then 
        // sent1 will get 1 and sent2 will get 2
        String varToBeChanged;
        for( Sentence sent : kb)
        {
            varToBeChanged = "";
            for(int i = 0; i < sent.getPredList().size(); i++)
            {
                Predicate a = sent.getPredList().get(i);
                for (Parameter paramA : a.getParameters())
                {
                    if(!paramA.getIsCon() && paramA.getCon() != null)
                    {
                        varToBeChanged = paramA.getCon();
                        paramA.switchToVar(nextVar);
                    }
                    else // no need to compare if its a constant
                        continue;
                    // check the rest of the preds
                    for(int j = 1; j < sent.getPredList().size(); j++)
                    {
                        Predicate b = sent.getPredList().get(j);
                        // check all of their parameters
                        for (Parameter paramB : b.getParameters())
                        {
                            if(!paramB.getIsCon() && paramB.getCon() != null)
                            {
                                if(varToBeChanged.equals(paramB.getCon()))
                                {
                                    paramB.switchToVar(nextVar);
                                }
                            }
                        }
                    }
                    nextVar++;
                }
            }
        }
    }
    
    public void StandaradizeNewSentence(Sentence sent)
    {
        int varThisOrBigger = nextVar;
        int varToBeChanged;
        for(int i = 0; i < sent.getPredList().size(); i++)
        {
            Predicate a = sent.getPredList().get(i);
            for (Parameter paramA : a.getParameters())
            {
                if(!paramA.getIsCon() && paramA.getVar() < varThisOrBigger && paramA.getVar() >= 0)
                {
                    varToBeChanged = paramA.getVar();
                    paramA.switchToVar(nextVar);
                }
                else // no need to compare if its a constant
                    continue;
                // check the rest of the preds
                for(int j = 1; j < sent.getPredList().size(); j++)
                {
                    Predicate b = sent.getPredList().get(j);
                    // check all of their parameters
                    for (Parameter paramB : b.getParameters())
                    {
                        if(varToBeChanged == paramB.getVar())
                        {
                            paramB.switchToVar(nextVar);
                        }
                    }
                }
                nextVar++;
            }
        }
    }
    
}


