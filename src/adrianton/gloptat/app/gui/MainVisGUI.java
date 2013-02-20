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

package adrianton.gloptat.app.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import adrianton.gloptat.app.MainVis;


public class MainVisGUI implements Runnable, MainGenericGUI {
 MainVis outer;
 JFrame frame;
 JComboBox cmb_fun, cmb_oa;
 JButton but_config, but_init, but_step, but_run, but_benchmark, but_plot;
 
 public MainVisGUI(MainVis outer)
 {
  this.outer = outer;
  SwingUtilities.invokeLater(this);
 }
 
 void assembleGUI()
 {
  frame = new JFrame ("Global Optimization AT");
  GridLayout experimentLayout = new GridLayout(8,1,4,4);
  frame.setLayout(experimentLayout);
  
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closing sequence //should close the display properly
  
  String[] funNam = {"DeJong","Rastrigin","Michalewicz"};
  cmb_fun = new JComboBox(funNam);
  
  String[] oaNam = {"GA","PSO"};
  cmb_oa = new JComboBox(oaNam);
  
  but_config = new JButton("Config");
  but_init = new JButton("Init");
  but_step = new JButton("Step");
  but_run = new JButton("Run");
  but_benchmark = new JButton("Benchmark");
  but_plot = new JButton("PlotPerf");
  
  //f.getContentPane().add(new JLabel("Hello, world!"));
  frame.getContentPane().add(cmb_fun);
  frame.getContentPane().add(cmb_oa);
  frame.getContentPane().add(but_config);
  frame.getContentPane().add(but_init);
  frame.getContentPane().add(but_step);
  frame.getContentPane().add(but_run);
  frame.getContentPane().add(but_benchmark);
  frame.getContentPane().add(but_plot);
  
  but_config.addActionListener(new ActConf(outer));
  but_init.addActionListener(new ActInit(outer));
  but_step.addActionListener(new ActStep(outer));
  but_run.addActionListener(new ActRun(outer));
  but_benchmark.addActionListener(new ActBenchmarkLight(outer));
  but_plot.addActionListener(new ActPlot(outer));
  
  cmb_fun.addActionListener(new ActFun(outer));
  cmb_oa.addActionListener(new ActOA(outer));
  
  frame.pack();
  frame.setVisible(true);
 }
 
 public void run() {
  assembleGUI();
 }
 
 public int getSelectedOAIndex() {
	return cmb_oa.getSelectedIndex();
 }

 public int getSelectedFunIndex() {
	return cmb_fun.getSelectedIndex();
 }
}