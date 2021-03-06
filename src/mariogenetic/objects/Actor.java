/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mariogenetic.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import mariogenetic.main.Config;
import mariogenetic.main.GlobalVariables;
import mariogenetic.main.Vector;

/**
 *
 * @author alice
 */
public class Actor extends WorldObject{
	
//    public boolean falling;
//    public boolean jumping;

    public Vector velocity;
    public Vector previous_pos;
    
    public Actor(Vector position, Point size)
    {
        super(position,size);
//        falling = true;
//        jumping = false;        
        previous_pos = position;
        velocity = new Vector(0.0,0.0);       
    }
    public void paint(Graphics2D g2)
    {
        super.paint(g2);
        Color c = g2.getColor();
        g2.setColor(Config.color_actor);
        g2.fillRect((int)position.x, (int)position.y, size.x, size.y);
        g2.setColor(c);
    }
    public Rectangle previewX()
    {
        return new Rectangle((int)(position.x+velocity.x),(int)position.y,size.x,size.y);
    }
    public Rectangle previewY()
    {
        return new Rectangle((int)(position.x),(int)(position.y+velocity.y),size.x,size.y);
    }
    public void tickX()
    {
        previous_pos.x=position.x;
        position.x+=velocity.x;
    }
    public void tickY()
    {
        previous_pos.y = position.y;
        position.y+=velocity.y;                
    }
    public void moveLeft(double vx)
    {
        velocity.x = -vx;
    }
    public void moveRight(double vx)
    {
        velocity.x = vx;
    }
    public void moveDown(double vy)
    {
        velocity.y = vy;
    }
    public void moveUp(double vy)
    {
        velocity.y = -vy;
    }

    public void stopX()
    {
        velocity.x=0;
    }
    public void stopY()
    {
        velocity.y=0;
    }

}
