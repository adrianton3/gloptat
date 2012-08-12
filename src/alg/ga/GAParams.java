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

package alg.ga;

import java.util.HashMap;
import java.util.Map.Entry;

import alg.OAParams;
import alg.pso.PSOParams;

public class GAParams implements OAParams
{
 public final static int CROSSOVER_1 = 0;
 public final static int CROSSOVER_2 = 1;
 public final static int CROSSOVER_U = 2;

 public int ngen = 20;
 public double target = 1000;
 
 public int niv = 30;
 public int popmin = 10; //final
 public int ndrop = 0;   //final
 public int nnew = 0;    //final
 public int ninvert = 0; //final
 public int selection_type = 0;          //final
 public double selection_pressure = 0.5; //final
 public int dampening_type = 0;          //final
 public int crossover_type = 0;          //final
 public double crossover_bias = 0.5;     //final
 public int mutation_type = 0;           //final
 public double mutation_chance = 0.01;   //final
 public double mutation_lat = 2;         //final
 public double mutation_start = 20;      //final
 public double mutation_inc = -1;        //final
 public boolean grow = false;            //final
//------------------------------------------------------------------------------
 public String toString()
 {
  String ret = "";
  ret = "Niv: " + niv + "\n" +
        "Popmin: " + popmin + "\n" +
        "Ndrop: " + ndrop + "\n" +
        "Nnew: " + nnew + "\n" +
        "Ninvert: " + ninvert + "\n" +
        "Selection type: " + selection_type + "\n" +
        "Selection pressure: " + selection_pressure + "\n" +
        "Damping function: " + dampening_type + "\n" +
        "Crossover type: " + crossover_type + "\n" +
        "Crossover bias: " + crossover_bias + "\n" +
        "Mutation type: " + mutation_type + "\n" +
        "Mutation chance: " + mutation_chance + "\n" +
        "Mutation lat: " + mutation_lat + "\n" +
        "Mutation start: " + mutation_start + "\n" +
        "Mutation inc: " + mutation_inc + "\n" +
        "Grow: " + grow;
  return ret;
 }
 
 public static GAParams fromMap(HashMap<String,Double> map)
 {
  GAParams ret = new GAParams();
  
  for(Entry<String, Double> entry : map.entrySet())
  {
   String key = entry.getKey();
   double val = entry.getValue();
   
   if(key.equals("np")) ret.niv = (int)val;
   else if(key.equals("popmin")) ret.popmin = (int)val;
   else if(key.equals("ndrop")) ret.ndrop = (int)val;
   else if(key.equals("nnew")) ret.nnew = (int)val;
   else if(key.equals("ninvert")) ret.ninvert = (int)val;
   else if(key.equals("selection_type")) ret.selection_type = (int)val;
   else if(key.equals("selection_pressure")) ret.selection_pressure = (double)val;
   else if(key.equals("damping_function")) ret.dampening_type = (int)val;
   else if(key.equals("crossover_type")) ret.crossover_type = (int)val;
   else if(key.equals("crossover_bias")) ret.crossover_bias = (double)val;
   else if(key.equals("mutation_type")) ret.mutation_type = (int)val;
   else if(key.equals("mutation_chance")) ret.mutation_chance = (double)val;
   else if(key.equals("mutation_lat")) ret.mutation_lat = (double)val;
   else if(key.equals("mutation_start")) ret.mutation_start = (double)val;
   else if(key.equals("mutation_inc")) ret.mutation_inc = (double)val;
   else if(key.equals("grow")) { if(val==0) ret.grow = false; else ret.grow = true; }
  }
  
  return ret;
 }
}