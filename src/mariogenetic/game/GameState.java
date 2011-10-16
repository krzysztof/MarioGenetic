/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mariogenetic.game;

import java.sql.Time;
import java.util.Date;
import mariogenetic.Global;


/**
 *
 * @author alice
 */
public class GameState {
    
    public static int RESULT_NONE = 0;
    public static int RESULT_TIMEOUT = 1;
    public static int RESULT_WON = 2;
    public static int RESULT_DEAD = 3;
    public static String[] result_strings = {"No result","Timeout","Won","Death"};

    public long started;
    public long now;
    public long finished;
    public int score;
    public int result;
    boolean game_over = false;
    public GameState()
    {
        now = started = new Date().getTime();
        score = 0;
        game_over=false;
        result = RESULT_NONE;
    }

    public GameState(GameState gs) {
        this();
        this.now = gs.now;
        this.score = gs.score;
        this.finished = gs.finished;
        this.result = gs.result;
        this.game_over = gs.game_over;
        this.started = gs.started;
    }
    public void reset()
    {
        now = started = new Date().getTime();
        score = 0;
        game_over=false;
        result = RESULT_NONE;
        Global.frame_main.resources.reset();
        Global.frame_main.renderer.reset();
    }
    public void updateTime(long d_time)
    {
        now+=d_time;
    }
    public void setGameOver(int reason)
    {
//        finished = new Date().getTime();
        finished = now;
        result = reason;
        game_over=true;
    }
    public String toString()
    {
//        long now = new Date().getTime();
        return String.format("Result: %s Score: %d Time: %d",result_strings[result] ,score,(now-started));
    }

    public void check() {

        //zmiana trybu
        if(Global.MODE_CURRENT!=Global.MODE_NEXT)
        {
            Global.MODE_CURRENT = Global.MODE_NEXT;
            switch(Global.MODE_CURRENT)
            {
                case Global.MODE_USER:
                {
                    Global.frame_main.logic=new LogicHuman_Temporary();
                    Global.frame_main.controller = new ControllerHuman();
                    break;
                }
                case Global.MODE_TIME:
                {
                    Global.frame_main.logic=new LogicTime();
                    Global.frame_main.controller = new ControllerTime();
                    break;
                }
            }
        }
        else if(result != RESULT_NONE)
        {
            if(Global.MODE_CURRENT == Global.MODE_USER)
            {
                reset();
            }
            else if(Global.MODE_CURRENT == Global.MODE_TIME)
            {
                
            }
        }
    }
    public long timeElapsed()
    {
//        long now = new Date().getTime();
        return now-started;
    }
    


}