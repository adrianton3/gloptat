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

import java.util.HashMap;

import gui.ConfigGUI;
import gui.MainGenericGUI;
import gui.OutputGUI;
import objfun.Domain;
import objfun.Interval;
import objfun.MOF;
import alg.ConfString;
import alg.OA;
import alg.OAParams;
import alg.ga.GA;
import alg.ga.GAParams;
import alg.pso.PSO;
import alg.pso.PSOParams;

public abstract class MainGeneric {
 //GUI
	public MainGenericGUI se;
	public OutputGUI con;
	public ConfigGUI[] conf; 
	public ConfigGUI activeConf;
	
	//Objective function
	public MOF of;
	
	//Search space
	public Domain dom = new Domain(new Interval[] {new Interval(-5.12,5.12), new Interval(-5.12,5.12)});
	
	//Optimization algorithms
	public OA[] oa;
	public OAParams[] oaparams;
	public OA activeOA;
	public OAParams activeOAParams;
	
	public abstract void changeOF(int tmp);
	
	public OAParams getOAParams() {
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
}
