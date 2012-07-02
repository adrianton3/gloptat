package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import def.Main;

public class ActPlot implements ActionListener 
{
 Main outer;
 
 ActPlot(Main outer)
 {
  this.outer = outer;
 }
 
 public void actionPerformed(ActionEvent e) 
 {
  outer.con.frame.setVisible(true);
 }
}
