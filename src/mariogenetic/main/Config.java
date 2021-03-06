/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mariogenetic.main;

import mariogenetic.gene.GeneticsConfig;
import mariogenetic.GUI.LabeledTextBox;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import mariogenetic.GUI.Locals;
import mariogenetic.game.Main;
import mariogenetic.gene.GeneticsConfig.Parameter;

/**
 *
 * @author alice
 */
public class Config extends JPanel {
        public static final Dimension window_main_size = new Dimension(500,500);
        public static final Dimension window_settings_size = new Dimension(380,750);
        public static final Dimension window_editor_size = new Dimension(870,500);
//	public static final int TILE_SIZE = 30;
        public static final Color color_grid_light = new Color(210,210,210);
        public static final Color color_grid_dark = new Color(170,170,170);

        public static final Color color_actor = Color.blue;
        public static final Color color_terrain = Color.darkGray;
        public static final Color color_type_value = Color.orange;
        public static final Color color_type_wins = Color.green;
        public static final Color color_type_kills = Color.red;

        public static final Color color_map_selected = Color.GREEN;
        public static final Color color_map_not_selected = (new JButton()).getBackground();
        public static final Color color_map_object_selected = Color.MAGENTA;

        public static boolean show_coords = false;
        public static boolean mouse_cam = false;
        public static boolean show_debug = false;

        private static String coords_yes = "Coordinates:ON";
        private static String coords_no = "Coordinates:OFF";

        private static String debug_yes = "Debug:ON";
        private static String debug_no = "Debug:OFF";
        

        private void revertBack(ArrayList<Parameter> params,LabeledTextBox[] ltb_tab)
        {
            GeneticsConfig gc = GeneticsConfig.getInstance();
            ArrayList<Parameter> needs_reset = this.requiresReset(ltb_tab);
            for(LabeledTextBox label : ltb_tab)
            {
                Parameter p = Parameter.valueOf(label.getLabel());
                if (p == Parameter.MAXIMUM_TIME)
                {
                    label.setValue(String.valueOf((Integer)gc.getParameter(p)));
                }
                else if (p == Parameter.MOVES_PER_SECOND)
                {
                    label.setValue(String.valueOf((Integer)gc.getParameter(p)));
                }
            }
        }

        private ArrayList<Parameter> requiresReset(LabeledTextBox[] ltb_tab)
        {
//            Param[] resetingParams = {Param.MAXIMUM_TIME,Param.MOVES_PER_SECOND};

            GeneticsConfig gc = GeneticsConfig.getInstance();
            ArrayList<Parameter> out_params = new ArrayList<Parameter>();
            for(LabeledTextBox label : ltb_tab)
            {
                Parameter p = Parameter.valueOf(label.getLabel());
                if(p==Parameter.MAXIMUM_TIME)
                {
                    Integer max_time = (Integer) gc.getParameter(p);
                    Integer max_time2 = Integer.valueOf(label.getValue());                    
                    if(!max_time.equals(max_time2))
                    {                        
                        out_params.add(p);
                    }
                }
                else if (p == Parameter.MOVES_PER_SECOND)
                {
                    Integer val1 = (Integer) gc.getParameter(p);
                    Integer val2 = Integer.valueOf(label.getValue());
                    if(!val1.equals(val2))
                    {
                        out_params.add(p);
                    }
                }
            }

            return out_params;
        }

        public static void main(String[] args) {
            JFrame frame = new JFrame("Settings");

            final Config conf_main = new Config();
            frame.getContentPane().add(conf_main);
            frame.setLayout(new BorderLayout());
            frame.pack();
            frame.setSize(Config.window_settings_size);
            frame.setVisible(true);

            JToolBar toolbar = new JToolBar("Tools", JToolBar.HORIZONTAL);


            final JButton btn_coords = new JButton(show_coords?coords_yes:coords_no);
            btn_coords.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                
                Config.show_coords = !Config.show_coords;                
                btn_coords.setText(show_coords?coords_yes:coords_no);
                }
            });

            final JButton btn_debug = new JButton(show_debug?debug_yes:debug_no);
            btn_debug.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                
                Config.show_debug = !Config.show_debug;
                btn_debug.setText(show_debug?debug_yes:debug_no);}            
            });

            toolbar.setFloatable(false);
            toolbar.add(btn_coords);
            toolbar.add(btn_debug);

            JPanel container = new JPanel();



            container.setLayout(new FlowLayout(FlowLayout.CENTER,3,3));


            GeneticsConfig gc = GeneticsConfig.getInstance();
            final LabeledTextBox[] lbtx = {
                new LabeledTextBox(GeneticsConfig.Keys.UP, String.valueOf(gc.getMoveKeyProbability(GeneticsConfig.Keys.UP)),"Move up probability"),
                new LabeledTextBox(GeneticsConfig.Keys.DOWN, String.valueOf(gc.getMoveKeyProbability(GeneticsConfig.Keys.DOWN)),"Move down probability"),
                new LabeledTextBox(GeneticsConfig.Keys.LEFT, String.valueOf(gc.getMoveKeyProbability(GeneticsConfig.Keys.LEFT)),"Move left probability"),
                new LabeledTextBox(GeneticsConfig.Keys.RIGHT, String.valueOf(gc.getMoveKeyProbability(GeneticsConfig.Keys.RIGHT)),"Move right probability"),
                new LabeledTextBox(GeneticsConfig.Keys.NONE, String.valueOf(gc.getMoveKeyProbability(GeneticsConfig.Keys.NONE)),"No-action probability"),
                new LabeledTextBox(GeneticsConfig.Keys.A, String.valueOf(gc.getSpecialKeyProbability(GeneticsConfig.Keys.A)),"A action probability"),
                new LabeledTextBox(GeneticsConfig.Keys.B, String.valueOf(gc.getSpecialKeyProbability(GeneticsConfig.Keys.B)),"B action probability"),
                new LabeledTextBox(GeneticsConfig.Keys.C, String.valueOf(gc.getSpecialKeyProbability(GeneticsConfig.Keys.C)),"C action probability"),
                new LabeledTextBox(GeneticsConfig.Keys.D, String.valueOf(gc.getSpecialKeyProbability(GeneticsConfig.Keys.D)),"D action probability"),
                new LabeledTextBox(GeneticsConfig.Keys.NONE, String.valueOf(gc.getSpecialKeyProbability(GeneticsConfig.Keys.NONE)),"No-action probability")
            };

            JButton btn_ok = new JButton("Apply");

            JPanel move_group = new JPanel();
            move_group.setBorder(BorderFactory.createTitledBorder("Movement key probabilities"));

            Main m = GlobalVariables.main;
            String[] lstr = new String[m.logic_list.size()];
            for (int i = 0; i < lstr.length; i++) {
                lstr[i]=m.logic_list.get(i).getClass().getSimpleName();
            }
            final JComboBox cbx_logic = new JComboBox(lstr);
            container.add(cbx_logic);
            container.add(move_group);
            move_group.setLayout(new BoxLayout(move_group,BoxLayout.Y_AXIS));

            JPanel special_group = new JPanel();
            special_group.setBorder(BorderFactory.createTitledBorder("Special key probabilities"));
            container.add(special_group);
            special_group.setLayout(new BoxLayout(special_group,BoxLayout.Y_AXIS));

            JPanel genetic_group = new JPanel();
            genetic_group.setBorder(BorderFactory.createTitledBorder("Genetic Settings"));
            container.add(genetic_group);
            genetic_group.setLayout(new BoxLayout(genetic_group,BoxLayout.Y_AXIS));

            Double crossing_parameter = (Double)GeneticsConfig.getInstance().getParameter(GeneticsConfig.Parameter.CROSSING_PARAMETER);

            
            HashMap<GeneticsConfig.Parameter,Object> hash_map = gc.getParamsMap();
            
                        
            Parameter[] param_values = hash_map.keySet().toArray(new Parameter[hash_map.size()]);
            
            ArrayList<Parameter> arr_par = new ArrayList<Parameter>();
            for(Parameter p : param_values)
            {
                arr_par.add(p);
            }
            Collections.sort(arr_par, new Comparator<Parameter>(){
                public int compare(Parameter o1, Parameter o2) {
                    String s1 = o1.name();
                    String s2=  o2.name();
                    return s1.compareTo(s2);
                }
            });
            param_values = arr_par.toArray(new Parameter[arr_par.size()]);
            
            final LabeledTextBox[] lbtx_params = new LabeledTextBox[param_values.length];
            Locals locals = Locals.getInstance();
            for(int i=0;i<lbtx_params.length;++i)
            {
                lbtx_params[i] = new LabeledTextBox(param_values[i].toString(), String.valueOf(gc.getParameter(param_values[i])),locals.getString(param_values[i].toString()));
                
                genetic_group.add(lbtx_params[i]);

            }

            for (int i = 0; i < 5; i++) {
                move_group.add(lbtx[i]);
            }
            for (int i = 5; i < lbtx.length; i++) {
                special_group.add(lbtx[i]);
            }
            container.add(btn_ok);

            btn_ok.addActionListener(new ActionListener(){

                
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < 5; i++) {
                        GeneticsConfig.getInstance().setMoveKeyProbability(lbtx[i].getKey(), Double.valueOf(lbtx[i].getValue()));
                    }
                    for (int i = 5; i < lbtx.length; i++) {
                        GeneticsConfig.getInstance().setSpecialKeyProbability(lbtx[i].getKey(), Double.valueOf(lbtx[i].getValue()));
                    }
                    ArrayList<Parameter> modified = conf_main.requiresReset(lbtx_params);
                    boolean logic_changed = false;
                    
                    if(cbx_logic.getSelectedIndex() != GlobalVariables.main.selected_logic)
                        logic_changed=true;
                    
                    if(modified.size()>0 || logic_changed)
                    {
                        String params = "";
                        for(Parameter p : modified)
                        {
                            params+= p.name()+", ";
                        }
                        if(logic_changed)
                            params+="Logic changed";
                        String msg = String.format("Some parameters need to reset the population\nin order to apply the changes: %s\n\nPress YES to reset the population.\nPress NO to revert the values and do nothing.\n\n",params);

                        int chosen = JOptionPane.showConfirmDialog(conf_main,msg , "Population reset is needed in order to apply." , JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
                        if(chosen==JOptionPane.YES_OPTION)
                        {
                            for(int i=0;i<lbtx_params.length;i++) {
                                    Parameter p = GeneticsConfig.Parameter.valueOf(lbtx_params[i].getLabel());
                                    GeneticsConfig.getInstance().updateParameter(p, lbtx_params[i].getValue());
                                }
                            GlobalVariables.main.resetAll();
                            GlobalVariables.main.setLogic(cbx_logic.getSelectedIndex());
                            GlobalVariables.main.selected_logic = cbx_logic.getSelectedIndex();

                        }
                        else if(chosen == JOptionPane.NO_OPTION)
                        {
                            conf_main.revertBack(modified, lbtx_params);
                            conf_main.repaint();
                            cbx_logic.setSelectedIndex(GlobalVariables.main.selected_logic);

                        }
                        else if(chosen == JOptionPane.CANCEL_OPTION)
                        {

                        }

                    }
                     else{
                        for(int i=0;i<lbtx_params.length;i++) {
                                    Parameter p = GeneticsConfig.Parameter.valueOf(lbtx_params[i].getLabel());
                                    GeneticsConfig.getInstance().updateParameter(p, lbtx_params[i].getValue());
                                }
                     }
                    
                    conf_main.repaint();
                    
                }
            });

            frame.add(toolbar,BorderLayout.NORTH);
            frame.add(container,BorderLayout.CENTER);

        }

}
