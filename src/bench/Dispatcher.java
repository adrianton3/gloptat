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
import def.MainBench;
import def.MainGeneric;
import def.OAFactory;
import objfun.Domain;
import objfun.MOF;
import alg.OAParams;
import alg.SimResult;

public class Dispatcher {
	final MainGeneric outer;
	final String oanam;
 final OAParams oaparams;
 final MOF of;
 final Domain dom;
 final int nthreads = 4;
 final int trialsperthread;
 
 int threads_res = 0;
 
 SimResult sr[];
 
 public Dispatcher(MainGeneric outer, String oanam, OAParams oaparams, MOF of, Domain dom, int trialsperthread) {
  this.outer = outer;
  this.oanam = oanam;
  this.oaparams = oaparams;
  this.of = of;
  this.dom = dom;
  this.trialsperthread = trialsperthread;
 }
 
 synchronized void inc(int idnr, SimResult[] sr) {
 	int i;
  for(i=0;i<sr.length;i++) {
   this.sr[trialsperthread*idnr + i] = sr[i];
  }
	 
	 threads_res++;
  if(threads_res >= nthreads) notify();
 }
 
 public void dispatch() {
	sr = new SimResult[trialsperthread*nthreads]; 
	
 int i;
 for(i=0;i<nthreads;i++) {
  new Thread(new GOThread(this,i,OAFactory.get(oanam,oaparams,of,dom),trialsperthread)).start();
 }
  
 synchronized (this) {
  try {
   wait();
  } catch (InterruptedException e) { e.printStackTrace(); }
 }
  
  publishResults(sr);
 }

	private void publishResults(SimResult[] sr) {
		for(SimResult s: sr)
	  outer.con.add(s.toString());
		outer.con.separator();
	}
}