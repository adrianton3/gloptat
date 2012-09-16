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

import bench.Dispatcher;

import def.Dbo;
import def.Fasten;
import def.MainGeneric;

class ActBenchmarkMT implements ActionListener {
 MainGeneric outer;
 Dispatcher dispatcher;
 final int ntrials = 20;
 
 ActBenchmarkMT(MainGeneric outer) {
  this.outer = outer;
 }
 
 public void actionPerformed(ActionEvent e) {
 	outer.activeOAParams = outer.getOAParams();
  outer.activeOA.setParams(outer.activeOAParams);
  
  outer.con.add("Optimizing " + outer.of.getFunc().toString() + "\n" +
                //"Iterations: " + gaspar.niter + "\n" +
                "Using: " + outer.activeOA.getNam() + "\n" +
                "Parameters:\n" + outer.activeOAParams.toString());
  
  dispatcher = new Dispatcher(outer,outer.activeOA.getNam(),outer.activeOAParams,outer.of,outer.dom,5);
  dispatcher.dispatch();
 }
}