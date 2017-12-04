/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theoremprover;

/**
 *
 * @author MC
 */
public class QueueItem implements Comparable<QueueItem>{
    private Sentence sent1;
    private Sentence sent2;
    private int priority;
    
    public QueueItem(Sentence sent1, Sentence sent2, int priority)
    {
        this.sent1 = sent1;
        this.sent2 = sent2;
        this.priority = priority;
    }

    public Sentence getSent1() {
        return sent1;
    }

    public Sentence getSent2() {
        return sent2;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(QueueItem o) {
        if(priority < o.getPriority())
            return -1;
        else if(priority == o.getPriority())
            return 0;
        else 
            return 1;
    }

   
}
