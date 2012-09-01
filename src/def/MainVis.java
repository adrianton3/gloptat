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
 //GUI
 //public MainVisGUI se;
 //public OutputGUI con;
 //public ConfigGUI[] conf;
 
 //Plotter
 public Displayer d;
 
 //Objective function
 //public MOF of;
 
 
 //Optimization algorithms 
 //public OA[] oa;
 //public OAParams[] oaparams;
 
 //Search space
 //double[][] dom = {{-5.12,5.12},{-5.12,5.12}};
 
 //...
 //public OA activeOA;
 //public OAParams activeOAParams;
 //public ConfigGUI activeConf;
 
 void setupDisplayer() {
  d = new Displayer();
  
  int tmprx, tmpry;
  double dx, dy;

  dx = dom[0][1]-dom[0][0];
  dy = dom[1][1]-dom[1][0];

  if(dx < dy)
  {
   tmprx = 120;
   tmpry = (int)(tmprx * (dy/dx));
  }
  else
  {
   tmpry = 120;
   tmprx = (int)(tmpry * (dx/dy));
  }
  
  d.setDom(dom);
  d.setVal(of.compute(tmprx,tmpry));
  d.minc = of.minc;
  d.maxc = of.maxc;
 }
 
 void setupOF()
 {
  of = new MOF();
  of.setFunc(0);
  of.setDom(dom[0][0],dom[0][1],dom[1][0],dom[1][1]); //not ok
 }
 
 void setupGA()
 {
  oaparams[0] = new GAParams();
  
  oa[0] = new GA(of);
  oa[0].setDom(dom);
  oa[0].setParams(oaparams[0]);
  
  //oa[0].init();
  
  conf[0] = new ConfigGUI("Config GA","");
 }
 
 void setupPSO()
 {
  oaparams[1] = new PSOParams();
  
  oa[1] = new PSO(of);
  oa[1].setDom(dom);
  oa[1].setParams(oaparams[1]);
  
  //oa[1].init();
  
  conf[1] = new ConfigGUI("Config PSO","");
 }
 
 public void changeOF(int id)
 {
  of.setFunc(id);
  dom[0][0] = of.getFunc().psdomx;
  dom[0][1] = of.getFunc().pedomx;
  dom[1][0] = of.getFunc().psdomy;
  dom[1][1] = of.getFunc().pedomy;
  
  int tmprx, tmpry;
  double dx, dy;

  dx = dom[0][1]-dom[0][0];
  dy = dom[1][1]-dom[1][0];

  if(dx < dy)
  {
   tmprx = 120;
   tmpry = (int)(tmprx * (dy/dx));
  }
  else
  {
   tmpry = 120;
   tmprx = (int)(tmpry * (dx/dy));
  }
  
  d.setDom(dom);
  d.setVal(of.compute(tmprx,tmpry));
  d.minc = of.minc;
  d.maxc = of.maxc;
  
  d.needsrepaint = true;
 }
 
 void start()
 {
  se = new MainVisGUI(this);

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
  
  setupDisplayer();
  
  new Thread(new DisplayerThread(d)).start();
 }

 public OAParams getOAParams() 
 {
  HashMap<String,Double> map;
  map = new ConfString(activeConf.getString()).toMap();
  
  /*
  Class[] c = {GAParams.class,PSOParams.class};
  try {
   return (OAParams) c[0].getMethod("fromMap").invoke(new Object[] {});
  } catch (IllegalArgumentException e) {
   e.printStackTrace();
  } catch (SecurityException e) {
   e.printStackTrace();
  } catch (IllegalAccessException e) {
   e.printStackTrace();
  } catch (InvocationTargetException e) {
   e.printStackTrace();
  } catch (NoSuchMethodException e) {
   e.printStackTrace();
  } */
  
  //must find a better way of doing this
  if(activeOA instanceof GA) return GAParams.fromMap(map);
  else if(activeOA instanceof PSO) return PSOParams.fromMap(map);
  
  return null;
 }
 
 public static void main(String[] args) 
 {
  MainVis instance = new MainVis();
  instance.start();
 }
}
