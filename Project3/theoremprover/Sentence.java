/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theoremprover;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MC
 */
public class Sentence {
    
    List<Predicate> predlist;
    String comments;
    boolean refutePart;
    int num_pred;
    int parentIndex1;
    int parentIndex2;

    public Sentence()
    {
        predlist = new ArrayList<>();
        refutePart = false;
        num_pred = 0;
    }
    
    public Sentence(List<Predicate> predlist, boolean refutePart, int num_pred, String comments)
    {
        this.predlist = predlist;
        this.refutePart = refutePart;
        this.num_pred = num_pred;
        this.comments = comments;
        this.parentIndex1 = -1;
        this.parentIndex2 = -1;
    }
    
    public Sentence(List<Predicate> predlist, boolean refutePart, int num_pred, String comments, int parentIndex1, int parentIndex2)
    {
        this.predlist = predlist;
        this.refutePart = refutePart;
        this.num_pred = num_pred;
        this.comments = comments;
        this.parentIndex1 = parentIndex1;
        this.parentIndex2 = parentIndex2;
    }
    
   public void AddPredicate(Predicate pred, boolean refutePart)
   {
       predlist.add(pred);
       num_pred++;
       this.refutePart = refutePart;
   }
   
   public List<Predicate> getPredList()
   {
       return predlist;
   }
    
   public int getParent1()
   {
       return parentIndex1;
   }
   
   public int getParent2()
   {
       return parentIndex2;
   }
   
}
