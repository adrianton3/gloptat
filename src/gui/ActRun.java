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

import alg.ConfString;
import alg.OA;
import alg.ga.GA;
import alg.ga.GAParams;
import alg.pso.PSOParams;

import def.Dbo;
import def.Fasten;
import def.MainVis;

class ActRun implements ActionListener
{
 MainVis outer;
 
 ActRun(MainVis outer)
 {
  this.outer = outer;
 }
 
 public void actionPerformed(ActionEvent e) 
 {
  Timer timer = new Timer();

  outer.activeOAParams = outer.getOAParams();  
  outer.activeOA.setParams(outer.activeOAParams);
  timer.scheduleAtFixedRate(new AutoStep(outer.activeOA,outer.d,30), 0, 100);
 }
}