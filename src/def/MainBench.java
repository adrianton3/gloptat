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
import gui.OutputGUI;

import java.util.HashMap;

import objfun.MOF;
import alg.ConfString;
import alg.OAParams;
import alg.ga.GAParams;
import alg.pso.PSOParams;

public class MainBench extends MainGeneric {
 //GUI
 //public OutputGUI con;
 //public ConfigGUI[] conf;
 
 //Objective function
 //public MOF of;
 
 //Optimization algorithms 
 //public OAParams[] oaparams;
 
 //Search space
 double[][] dom = {{-5.12,5.12},{-5.12,5.12}};
 
 //...
 //public OAParams activeOAParams;
 //public ConfigGUI activeConf;
 
 String oanam = "GA";
 
 void setupOF()
 {
  of = new MOF();
  of.setFunc(0);
  of.setDom(dom[0][0],dom[0][1],dom[1][0],dom[1][1]); //not ok
 }
 
 void setupGA() //no need
 {
  oaparams[0] = new GAParams();
  
  conf[0] = new ConfigGUI("Config GA","");
 }
 
 void setupPSO() //no need
 {
  oaparams[1] = new PSOParams();
  
  conf[1] = new ConfigGUI("Config PSO","");
 }
 
 public void changeOF(int id)
 {
  of.setFunc(id);
  dom[0][0] = of.getFunc().psdomx;
  dom[0][1] = of.getFunc().pedomx;
  dom[1][0] = of.getFunc().psdomy;
  dom[1][1] = of.getFunc().pedomy;
 }
 
 void start()
 {
  con = new OutputGUI();
  
  setupOF();
  
  oaparams = new OAParams[2];
  conf = new ConfigGUI[2];
  
  setupGA();
  setupPSO();
  
  activeOAParams = oaparams[0];
  activeConf = conf[0];
 }
 
 public OAParams getOAParams() 
 {
  HashMap<String,Double> map;
  map = new ConfString(activeConf.getString()).toMap();
  return OAFactory.getParams(oanam, map);
  
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
 }
    
 public static void main(String[] args) {
  MainBench instance = new MainBench();
  instance.start();
 }
}
