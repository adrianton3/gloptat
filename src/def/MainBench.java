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

package def;

import gui.ConfigGUI;
import gui.MainBenchGUI;
import gui.MainVisGUI;
import gui.OutputGUI;

import java.util.HashMap;

import objfun.MOF;
import alg.ConfString;
import alg.OA;
import alg.OAParams;
import alg.ga.GA;
import alg.ga.GAParams;
import alg.pso.PSO;
import alg.pso.PSOParams;

public class MainBench extends MainGeneric {
 
 void setupOF() {
  of = new MOF();
  of.setFunc(0);
 }
 
 public void changeOF(int id) {
  of.setFunc(id);
 }
 
 void start() {
  se = new MainBenchGUI(this);
 	
  con = new OutputGUI();
  
  setupOF();
  
  /*
  oaFactory = new OAFactory[2];
  oaParams = new OAParams[2];
  conf = new ConfigGUI[2];
  
  setupOA(0,GA.nam);
  setupOA(1,PSO.nam);
  
  activeOAFactory = oaFactory[0];
  activeOAParams = oaParams[0];
  activeConf = conf[0];
  */
 }
    
 public static void main(String[] args) {
  MainBench instance = new MainBench();
  instance.start();
 }
}
