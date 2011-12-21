/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mariogenetic.objects;
import java.awt.Point;
import mariogenetic.main.GlobalVariables;
import mariogenetic.main.Vector;
import mariogenetic.game.GameState;
/**
 *
 * @author alice
 */
public class BonusLose extends Bonus {

    public BonusLose(Vector position, Point size,int value) {
        super(position,size,value);
     
        this.type = Bonus.TYPE_KILLS;

    }

    @Override
    public void tick()
    {

    }
    public void evalCollision()
    {
        super.evalCollision();
        GlobalVariables.main.gamestate.result=GameState.RESULT_LOST;
    }

}
