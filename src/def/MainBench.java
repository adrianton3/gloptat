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
 String oanam = "GA";
 
 void setupOF() {
  of = new MOF();
  of.setFunc(0);
  of.setDom(dom);
 }
 
 void setupGA() { // to be converted to OAFactory
  oaparams[0] = new GAParams();
  
  oa[0] = new GA(of);
  oa[0].setDom(dom);
  oa[0].setParams(oaparams[0]);
  
  //oa[0].init();
  
  conf[0] = new ConfigGUI("Config GA",oaparams[0].toString());
 }
 
 void setupPSO() { // to be converted to OAFactory
  oaparams[1] = new PSOParams();
  
  oa[1] = new PSO(of);
  oa[1].setDom(dom);
  oa[1].setParams(oaparams[1]);
  
  //oa[1].init();
  
  conf[1] = new ConfigGUI("Config PSO",oaparams[1].toString());
 }
 
 public void changeOF(int id) {
  of.setFunc(id);
  dom = of.getFunc().dom;
 }
 
 void start() {
  se = new MainBenchGUI(this);
 	
  con = new OutputGUI();
  
  setupOF();
  
  oa = new OA[2];
  oaparams = new OAParams[2];
  conf = new ConfigGUI[2];
  
  setupGA();
  setupPSO();
  
  activeOA = oa[0];
  activeOAParams = oaparams[0];
  activeConf = conf[0];
 }
 
 public OAParams getOAParams() {
  HashMap<String,Double> map;
  map = new ConfString(activeConf.getString()).toMap();
  return OAFactory.getParams(oanam, map);
 }
    
 public static void main(String[] args) {
  MainBench instance = new MainBench();
  instance.start();
 }
}
