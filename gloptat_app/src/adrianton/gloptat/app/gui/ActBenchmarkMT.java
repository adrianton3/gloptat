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

package adrianton.gloptat.app.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import adrianton.gloptat.alg.OAParams;
import adrianton.gloptat.app.MainGeneric;
import adrianton.gloptat.app.bench.Dispatcher;

class ActBenchmarkMT implements ActionListener {
 MainGeneric outer;
 Dispatcher dispatcher;
 final int trialsPerThread = 5;
 final int nThreads = 4;
 
 ActBenchmarkMT(MainGeneric outer) {
  this.outer = outer;
 }
 
 public void actionPerformed(ActionEvent e) {
	 OAParams oaParams = outer.getOAParams();
  
  outer.con.add("Optimizing " + outer.of.getFunc().toString() + "\n" +
                "Using: " + outer.activeOAFactory.getName() + "\n" +
                "Parameters:\n" + oaParams.toString());
  
  dispatcher = new Dispatcher(outer,outer.activeOAFactory,oaParams,outer.of,nThreads,trialsPerThread);
  dispatcher.dispatch();
 }
}