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

import java.util.TimerTask;

import alg.OA;

import plotter.Displayer;

import def.Main;

public class AutoStep extends TimerTask
{
 Displayer d;
 OA ao;
 
 int titer = 0;
 int titerlim = 30;
 
 AutoStep(OA ao, Displayer d, int titerlim)
 {
  this.ao = ao;
  this.d = d;
  this.titerlim = titerlim;
 }
 
 public void run()
 {
  ao.step();
  d.pointers(ao.getPop());
  d.pointersActive(ao.getActive());
  
  d.needsrepaint = true;
  
  titer++;
  if(titer > titerlim) this.cancel();
 }
}