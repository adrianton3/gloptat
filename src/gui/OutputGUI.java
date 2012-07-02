/*
 * Copyright 2012 Adrian Toncean
 * 
 * This file is part of Global Optimization AT.
 *
 * Global Optimization AT is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Global Optimization AT is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Global Optimization AT. If not, see <http://www.gnu.org/licenses/>.
 */

package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import def.Main;

public class OutputGUI implements Runnable 
{
 Main outer;
 JFrame frame;
 JTextArea out_out;
 JScrollPane scroll_pane;
 
 public OutputGUI(Main outer)
 {
  this.outer = outer;
  SwingUtilities.invokeLater(this);
 }
 
 void assembleGUI()
 {
  frame = new JFrame ("Output");
  GridLayout experimentLayout = new GridLayout(1,1,4,4);
  frame.setLayout(experimentLayout);
  frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
  frame.setPreferredSize(new Dimension(300,600));
  
  out_out = new JTextArea();
  out_out.setFont(new Font("monospaced", Font.PLAIN, 12) );
  out_out.setEditable(false);
  
  scroll_pane = new JScrollPane(out_out);
  frame.getContentPane().add(scroll_pane);
  frame.pack();
 }
 
 public void run() 
 {
  assembleGUI();
 }
 
 public String get()
 {
  return out_out.getText();
 }
 
 public void set(String s)
 {
  out_out.setText(s);
 }
 
 public void add(String s)
 {
  set(get() + s + "\n");
 }
 
 public void clear()
 {
  set("");
 }
 
 public void separator()
 {
  add("//---------------------------------");
 }
}