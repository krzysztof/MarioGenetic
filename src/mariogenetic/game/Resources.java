/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mariogenetic.game;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import mariogenetic.main.*;
import mariogenetic.objects.*;


/**
 *
 * @author alice
 */
public class Resources {

    
    public ArrayList<Actor> actors;
    public ArrayList<Terrain> terrain;
    public ArrayList<Bonus> bonus;
    public String current_map_file;
//    public Actor main_actor;

    public Resources(String file){
        GlobalVariables.resources_mutex=true;
        actors = new ArrayList<Actor>();
        terrain = new ArrayList<Terrain>();
        bonus = new ArrayList<Bonus>();
        GlobalVariables.resources_mutex=false;
        current_map_file = file;
        loadResources(file);
    }
    public Actor getMainActor()
    {
        if(actors.size()>0)
        {
            return actors.get(0);
        }
        return null;
    }
    public void loadResources(String file)
    {
        if(GlobalVariables.resources_mutex)
        {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Resources.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        GlobalVariables.resources_mutex=true;

        actors = new ArrayList<Actor>();
        terrain = new ArrayList<Terrain>();
        bonus = new ArrayList<Bonus>();
        
        this.current_map_file = file;
           try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str;
            while ((str = in.readLine()) != null) {
                String[] arr = str.split(" ");
                if(arr[0].equals(Actor.class.getSimpleName()))
                {
                    actors.add(new Actor(
                            new Vector(Integer.parseInt(arr[1]),
                                       Integer.parseInt(arr[2])),
                            new Point(Integer.parseInt(arr[3]),
                                      Integer.parseInt(arr[4]))
                                      ));                    
                }
                else if(arr[0].equals(Terrain.class.getSimpleName()))
                {
                    terrain.add(new Terrain(
                            new Vector(Integer.parseInt(arr[1]),
                                       Integer.parseInt(arr[2])),
                            new Point(Integer.parseInt(arr[3]),
                                      Integer.parseInt(arr[4]))
                                      ));
                }
                else if(arr[0].equals(BonusCoin.class.getSimpleName()))
                {
                    bonus.add(new BonusCoin(
                            new Vector(Integer.parseInt(arr[1]),
                                       Integer.parseInt(arr[2])),
                            new Point(Integer.parseInt(arr[3]),
                                      Integer.parseInt(arr[4])),
                            Integer.parseInt(arr[5])));
                }
                else if(arr[0].equals(BonusWin.class.getSimpleName()))
                {
                    bonus.add(new BonusWin(
                            new Vector(Integer.parseInt(arr[1]),
                                       Integer.parseInt(arr[2])),
                            new Point(Integer.parseInt(arr[3]),
                                      Integer.parseInt(arr[4])),Integer.parseInt(arr[5])));
                }

                else if(arr[0].equals(BonusLose.class.getSimpleName()))
                {
                    bonus.add(new BonusLose(
                            new Vector(Integer.parseInt(arr[1]),
                                       Integer.parseInt(arr[2])),
                            new Point(Integer.parseInt(arr[3]),
                                      Integer.parseInt(arr[4])),Integer.parseInt(arr[5])));
                }

            }
            in.close();
        } catch (IOException e) {
        }

        GlobalVariables.resources_mutex=false;
    }

    public ArrayList<WorldObject> getReopenedResource()
    {
        Resources r = new Resources(current_map_file);
        return r.getAsWorldObjects();
    }

    public ArrayList<WorldObject> getAsWorldObjects()
    {
        ArrayList<WorldObject> al = new ArrayList<WorldObject>();
        al.addAll(actors);
        al.addAll(terrain);
        al.addAll(bonus);
        return al;
    }
    public void reset()
    {
        loadResources(current_map_file);
    }

}
