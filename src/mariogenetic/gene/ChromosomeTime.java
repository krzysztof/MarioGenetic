/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mariogenetic.gene;

import java.util.Random;
import mariogenetic.Global;
import mariogenetic.Global.Keys;

/**
 *
 * @author alice
 */
public class ChromosomeTime extends Chromosome{

    public Global.Keys[] moves; //30 sekund
    public Global.Keys[] special;
    
    public ChromosomeTime()
    {     
        GeneticsConfig gc = GeneticsConfig.getInstance();
        Integer moves_per_sec = (Integer) GeneticsConfig.getInstance().get_parameter(GeneticsConfig.Param.MOVES_PER_SECOND);
        Integer max_time = (Integer) GeneticsConfig.getInstance().get_parameter(GeneticsConfig.Param.MAXIMUM_TIME);
        Integer size = (max_time*moves_per_sec)/1000;        
        
        //this.arr_length = size;
        arr_length = 100;
        moves = new Global.Keys[arr_length]; 
        special = new Global.Keys[arr_length];

        for (int i = 0; i < moves.length; i++) {            
            moves[i] = gc.getRandomMove();
        }

        for (int i = 0; i < special.length; i++) {
            special[i] = gc.getRandomSpecial();
        }
    }

    public static void main(String[] args)
    {
        ChromosomeTime ct = new ChromosomeTime();
        for(Keys k: ct.special)
            System.out.print(k+" ");

        Chromosome[] par = {ct};
        ChromosomeTime c2 = new ChromosomeTime(par);
        System.out.println("");
        for(Keys k: c2.special)
            System.out.print(k+" ");
    }

    public void mutateMoves(Double breadth)
    {
        Random rand = new Random();        
        for (int i = 0; i < moves.length; i++) {
            Double d = rand.nextDouble();
            if(d<breadth)
            {
                moves[i]=GeneticsConfig.getInstance().getRandomMove();
            }            
        }
        
    }
    public void mutateSpecial(Double breadth)
    {
        Random rand = new Random();  
        for (int i = 0; i < moves.length; i++) {
            Double d = rand.nextDouble();
            if(d<breadth)
            {
                moves[i]=GeneticsConfig.getInstance().getRandomSpecial();
            }            
        }
    }    

    public ChromosomeTime(Chromosome[] parents)
    {
        this();
        GeneticsConfig gc = GeneticsConfig.getInstance();        
        for (int i = 0; i < moves.length; i++) {
            Global.Keys[] modifiers = new Global.Keys[parents.length];
            for (int j = 0; j < modifiers.length; j++) {
                modifiers[j]=parents[j].getMovesArray()[i];
            }
            moves[i] = gc.getRandomMove(modifiers);
        }

        for (int i = 0; i < special.length; i++) {
            Global.Keys[] modifiers = new Global.Keys[parents.length];
            for (int j = 0; j < modifiers.length; j++) {
                modifiers[j]=parents[j].getSpecialArray()[i];
            }
            special[i] = gc.getRandomSpecial(modifiers);
        }
    }

    Keys[] getMovesArray() {
        return moves;
    }
    Keys[] getSpecialArray() {
        return special;
    }

    public boolean isEnd(long time)
    {
        return time>=moves.length;
    }
    public Global.Keys getCurrentMove(long time)
    {
        return moves[(int)time%moves.length];
    }
    public Global.Keys getSpecial(long time)
    {
        return special[(int)time%special.length];
    }
//    public void calcFunc()
//    {
//        final_score=0;
//        if(final_state.result==GameState.RESULT_DEAD)
//        {
//            final_score-=40;
//        }
//        else if(final_state.result==GameState.RESULT_WON)
//        {
//            final_score+=30;
//        }
//        final_score += final_state.score*2;
//        final_score += 15*((Chromosome.arr_length*100)/final_state.timeElapsed());
//    }

    @Override
    public String toString()
    {
        String s = "State not Set";
        if(resultData!=null)
            s=resultData.toString();
        return s;
    }

    public int compareTo(Object o) {
        ChromosomeTime ct = (ChromosomeTime)o;
       return (resultData.getFinalScore()<ct.resultData.getFinalScore()?-1:(resultData.getFinalScore()>ct.resultData.getFinalScore()?1:0));
    }
}
