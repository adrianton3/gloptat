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
import gui.MainVisGUI;
import gui.OutputGUI;

import java.util.HashMap;

import objfun.MOF;
import plotter.Displayer;
import plotter.DisplayerThread;
import alg.ConfString;
import alg.OA;
import alg.OAParams;
import alg.ga.GA;
import alg.ga.GAParams;
import alg.pso.PSO;
import alg.pso.PSOParams;

public class MainVis extends MainGeneric {
 public Displayer d;

 void setupDisplayer() {
  d = new Displayer();
  
  int tmprx, tmpry;
  double dx, dy;

  dx = dom.d[0].r - dom.d[0].l;
  dy = dom.d[1].r - dom.d[1].l;

  if(dx < dy) {
   tmprx = 120;
   tmpry = (int)(tmprx * (dy/dx));
  }
  else {
   tmpry = 120;
   tmprx = (int)(tmpry * (dx/dy));
  }
  
  d.setDom(dom);
  d.setVal(of.compute(tmprx,tmpry));
  d.minc = of.minc;
  d.maxc = of.maxc;
 }
 
 void setupOF() {
  of = new MOF();
  of.setFunc(0);
  of.setDom(dom);
 }

 public void changeOF(int id) {
  of.setFunc(id);
  dom = of.getFunc().dom;
  
  int tmprx, tmpry;
  double dx, dy;

  dx = dom.d[0].r - dom.d[0].l;
  dy = dom.d[1].r - dom.d[1].l;

  if(dx < dy) {
   tmprx = 120;
   tmpry = (int)(tmprx * (dy/dx));
  }
  else {
   tmpry = 120;
   tmprx = (int)(tmpry * (dx/dy));
  }
  
  d.setDom(dom);
  d.setVal(of.compute(tmprx,tmpry));
  d.minc = of.minc;
  d.maxc = of.maxc;
  
  d.needsrepaint = true;
 }
 
 void start() {
  se = new MainVisGUI(this);

  con = new OutputGUI();
  
  setupOF();
  
  oa = new OA[2];
  oaparams = new OAParams[2];
  conf = new ConfigGUI[2];
  
  setupOA(0,GA.nam);
  setupOA(1,PSO.nam);
    
  activeOA = oa[0];
  activeOAParams = oaparams[0];
  activeConf = conf[0];
  
  setupDisplayer();
  
  new Thread(new DisplayerThread(d)).start();
 }

 public static void main(String[] args) {
  MainVis instance = new MainVis();
  instance.start();
 }
}
