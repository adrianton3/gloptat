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

package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

import alg.OA;

import def.Fasten;
import def.Main;

class ActBenchmark implements ActionListener
{
 Main outer;
 final int ntrials = 20;
 
 ActBenchmark(Main outer)
 {
  this.outer = outer;
 }
 
 public void actionPerformed(ActionEvent e) 
 {
  outer.activeOA.setParams(outer.activeOAParams);
  
  outer.con.add("Optimizing " + outer.of.getFunc().toString() + "\n" +
                //"Iterations: " + gaspar.niter + "\n" +
                "Using: " + outer.activeOA.getNam() + "\n" +
                "Parameters:\n" + outer.activeOAParams.toString());

  double[] rez = new double[ntrials];
  int i;
  for(i=0;i<ntrials;i++)
  {
   outer.activeOA.resetNapel();
   outer.activeOA.alg();
   rez[i] = outer.activeOA.getBestFit();
  }

  outer.con.add("Running "+ntrials+" instances of GA...");
  for(i=0;i<ntrials;i++)
   outer.con.add(i+": "+Fasten.round(rez[i],2));

  double min, max, avg;

  max = Fasten.round(Fasten.max(rez),2);
  min = Fasten.round(Fasten.min(rez),2);
  avg = Fasten.round(Fasten.avg(rez),2);

  outer.con.add("Maximum: " + max);
  outer.con.add("Minimum: " + min);
  outer.con.add("Average: " + avg);
  
  outer.con.separator();
 }
}