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

package bench;

import def.Dbo;
import alg.OA;
import alg.SimResult;

public class GOThread implements Runnable {
 final Dispatcher outer;
 final int idnr;
 final OA oa;
 final int ntrials;

 GOThread(Dispatcher outer, int idnr, OA oa, int ntrials) {
  this.outer = outer;
  this.idnr = idnr;
  this.oa = oa;
  this.ntrials = ntrials;
 }

 public void run() {
  SimResult[] rez = new SimResult[ntrials];
  int i;
  for(i=0;i<ntrials;i++) {
   oa.resetNapel();
   oa.randomize();
   oa.alg();
   rez[i] = new SimResult(oa.getBestFit());
  }

  outer.inc(idnr,rez);
 } 
}