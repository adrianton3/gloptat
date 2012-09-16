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

import def.MainVis;

public class ConfigGUI implements Runnable {
 String title;
 String deftext;
 JFrame frame;
 JScrollPane scroll_pane;
 JTextArea inp_conf;
 
 public ConfigGUI(String title, String deftext) {
  this.title = title;
  this.deftext = deftext;
  SwingUtilities.invokeLater(this);
 }
 
 void assembleGUI() {
  frame = new JFrame(title);
  GridLayout experimentLayout = new GridLayout(1,1,4,4);
  frame.setLayout(experimentLayout);
  frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
  frame.setPreferredSize(new Dimension(300,600));
  
  inp_conf = new JTextArea();
  inp_conf.setFont(new Font("monospaced", Font.PLAIN, 12) );
  inp_conf.setText(deftext);
  
  scroll_pane = new JScrollPane(inp_conf);
  frame.getContentPane().add(scroll_pane);
  frame.pack();
 }
 
 public void run() {
  assembleGUI();
 }
 
 public String getString() {
  return inp_conf.getText();
 }
 
 public void setString(String s) {
  inp_conf.setText(s);
 }
}