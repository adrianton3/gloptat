package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import def.MainVis;

public class ActPlot implements ActionListener 
{
 MainVis outer;
 
 ActPlot(MainVis outer)
 {
  this.outer = outer;
 }
 
 public void actionPerformed(ActionEvent e) 
 {
  outer.con.frame.setVisible(true);
 }
}
