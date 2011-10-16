/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mariogenetic.game;

/**
 *
 * @author alice
 */
public abstract class Logic {

    public static final int COL_UP = 1;
    public static final int COL_DOWN = 2;
    public static final int COL_LEFT = 4;
    public static final int COL_RIGHT = 8;
    public static final int COL_X = 16;
    public static final int COL_Y = 32;
    
    
    public Controller parent;
    public Logic(){}
    public void doLogic(){}

}