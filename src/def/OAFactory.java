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

import objfun.MOF;
import alg.OA;
import alg.OAParams;
import alg.ga.GA;
import alg.ga.GAParams;
import alg.pso.PSO;
import alg.pso.PSOParams;

public class OAFactory {
 public static OA get(String oanam, OAParams oaparams, MOF of, double[][] dom) {
  OA oa = null;
  
  if(oanam.equals("GA") || oanam.equals("Genetic Algorithm")) oa = new GA(of);
  else if(oanam.equals("PSO") || oanam.equals("Particle Swarm Optimization")) oa = new PSO(of);
  else Dbo.out("OAFactory: Unrecognized OA: " + oanam);
  
  oa.setDom(dom);
  oa.setParams(oaparams);
  
  return oa;
 }
 
 public static OAParams getParams(String oanam, HashMap<String,Double> map) {
  OAParams oaparams = null;
  
  if(oanam.equals("GA")) oaparams = GAParams.fromMap(map);
  else if(oanam.equals("PSO")) oaparams = PSOParams.fromMap(map);
  
  return oaparams;
 }
}
