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

package alg.pso;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import alg.OAParams;

public class PSOParams implements OAParams
{
 public int niter = 20;
 public double target = 1000;
 
 public int np = 20;
 public int popmin = 10; //final
 public int ndrop = 0;   //final
 public int nnew = 0;    //final
 public double atenuator = 0.7; //final
 public double social = 0.7;    //final
 public double personal = 0.4;  //final
 public double random = 0.1;    //final
 public double speedmax = 10;   //final
 public int network_type = 4;   //final
 public int network_param = 5;  //final
//------------------------------------------------------------------------------
 @Override
 public String toString()
 {
  String ret;
  ret = "No particles: " + np + "\n" +
        "Popmin: " + popmin + "\n" +
        "Ndrop: " + ndrop + "\n" +
        "Nnew: " + nnew + "\n" +
        "Atenuator: " + atenuator + "\n" +
        "Personal: " + personal + "\n" +
        "Social: " + social + "\n" +
        "Random: " + random + "\n" +
        "Network type: " + network_type + "\n" +
        "Network parameter: " + network_param;
  return ret;
 }
 
 public static PSOParams fromMap(HashMap<String,Double> map)
 {
  PSOParams ret = new PSOParams();
  
  for(Entry<String, Double> entry : map.entrySet())
  {
   String key = entry.getKey();
   double val = entry.getValue();
   
   if(key.equals("np")) ret.np = (int)val;
   else if(key.equals("popmin")) ret.popmin = (int)val;
   else if(key.equals("ndrop")) ret.ndrop = (int)val;
   else if(key.equals("nnew")) ret.nnew = (int)val;
   else if(key.equals("atenuator")) ret.atenuator = (double)val;
   else if(key.equals("personal")) ret.personal = (double)val;
   else if(key.equals("social")) ret.social = (double)val;
   else if(key.equals("random")) ret.random = (double)val;
   else if(key.equals("network_type")) ret.network_type = (int)val;
   else if(key.equals("network_param")) ret.network_param = (int)val;
  }
  
  return ret;
 }
}