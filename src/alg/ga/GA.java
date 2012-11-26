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

import java.util.ArrayList;

import objfun.Domain;
import objfun.ObjectiveFunction;
import alg.OA;
import alg.OAParams;
import alg.SimParams;
import alg.SimResults;
import alg.Snapshot;
/* lots of work here:
 * + need to parametrize everything
 */
public class GA implements OA {
 public final static String name = "Genetic Algorithm";
 private final ObjectiveFunction of;
 private final GAParams par;
 private final Domain dom;
 
 private Individual[] gv;
 private Individual[] gn;
 private double[] fit;
 private int nactive; //will probably disappear
 private double genn;

 public GA(ObjectiveFunction of, GAParams par) {
  this.of = of;
  this.par = par;
  this.dom = of.getDom();
  
  alloc();
  randomize();
 }

 private void alloc() {
  gv = new Individual[par.niv];
  gn = new Individual[par.niv];
  fit = new double[par.niv];
 }

 private void init() {
  genn = par.mutation_start;
  nactive = par.niv;
 }

 private void randomize() {
  init();

  int i;
  for(i=0;i<par.niv;i++) {
   gv[i] = new Individual(dom);
   gn[i] = null; //new Ivan(dom);
  } //?
  calcFit();
 }

	private double[] getFit() {
		double[] ret = new double[par.niv];
		
		for(int i = 0; i < par.niv; i++)
			ret[i] = fit[i];
		
		return ret;
	}

 private double[][] getPop() {
  double[][] ret = new double[par.niv][];
  int i;
  for(i=0;i<par.niv;i++)
   ret[i] = gv[i].toArray();
  return ret;
 }

 private boolean[] getActive()
 {
  boolean[] ret = new boolean[par.niv];
  int i;
  for(i=0;i<par.niv;i++)
   ret[i] = gv[i].active;
  return ret;
 }

 private double getBestFit()
 {
  int i;
  double ret = fit[0];
  for(i=1;i<par.niv;i++) if(fit[i] > ret) ret = fit[i];
  return ret;
 }

 private double[] getBestForce()
 {
  calcFit();
  return getBest();
 }

 private double[] getBest()
 {
  int i, besti;
  double ret;
  ret = fit[0]; besti = 0;
  for(i=1;i<par.niv;i++) if(fit[i] > ret) { ret = fit[i]; besti = i; }
  return gv[besti].toArray();
 }

 private int getBestI()
 {
  int i, besti;
  double ret;
  ret = -10000; besti = -1;
  /*
  for(i=0;i<par.niv;i++)
   Dbo.out(" "+gv[i].toArray()[0]);*/
  
  for(i=0;i<par.niv;i++)
   if(gv[i].active) if(fit[i] > ret) { ret = fit[i]; besti = i; } 
  return besti;
 }

 private void calcFit(int i)
 {
  if(gv[i].active)
   fit[i] = of.f(gv[i].toArray());
 }

 private void calcFit()
 {
  int i;
  for(i=0;i<par.niv;i++)
   calcFit(i);
 }

 private void elitism() //this should be given as a parameter
 {
  gn[0] = gv[getBestI()];//.copy();
 }

 private int select_tournament(double p) //this should be given as a parameter
 {
  int nsel = (int)(p*par.niv);
  int i = 0, r, ret = -1;

  for(i=0;i<par.niv;i++)
   if(gv[i].active) { ret = i; break; }

  i = 0;
  while(i<nsel)
  {
   r = (int)(Math.random()*par.niv);
   if(gv[r].active)
   {
    i++;
    if(fit[r] > fit[ret]) ret = r;
   }
  }

  return ret;
 }

 private double[] procFit()
 {
  double[] afit = new double[par.niv];
  double worstfit = 10000;
  int i;

  for(i=0;i<par.niv;i++)
   if(gv[i].active)
    if(fit[i] < worstfit) worstfit = fit[i];

  for(i=0;i<par.niv;i++)
   if(gv[i].active)
   {
    afit[i] = fit[i]-worstfit+1.1;

    switch(par.dampening_type)
    {
     case 0:
      afit[i] = Math.sqrt(afit[i]);
      break;
     case 2:
      afit[i] = afit[i]*afit[i];
      break;
    }
   }

  return afit;
 }

 private int select_roulette() //this should be given as a parameter
 {
  int i;
  double sum = 0, ran, tmp=0;
  double[] afit = procFit();

  for(i=0;i<par.niv;i++)
   if(gv[i].active)
    sum += afit[i];

  ran = Math.random()*sum;
 
  for(i=0;i<par.niv;i++)
   if(gv[i].active)
   {
    tmp += afit[i];
    if(tmp >= ran) return i;
   }
  
  return -1;
 }

 private int select() //this shouldn't exist
 {
  switch(par.selection_type)
  {
   case 0: return select_roulette();
   case 1: return select_tournament(par.selection_pressure);
  }
  return -1;
 }

 private boolean[] markLast(int n)
 {
  boolean[] mark = new boolean[par.niv];

  int i, j, mini;
  double min;
  for(i=0;i<mark.length;i++) mark[i] = false;
  for(i=0;i<n;i++)
  {
   min = 10000; mini = -1;
   for(j=0;j<mark.length;j++)
    if(gv[j].active && !mark[j])
     if(fit[j] < min) { min = fit[j]; mini = j; }
   mark[mini] = true;
  }

  return mark;
 }

 private void renew() //this should be given as a parameter
 {
  boolean[] mark = markLast(par.nnew);
  int i;
  for(i=0;i<par.niv;i++)
   if(mark[i])
   {
    gv[i] = new Individual(dom);
    calcFit(i);
   }
 }

 private void drop() //this should be given as a parameter
 {
  if(nactive > par.popmin)
  {
   nactive = Math.max(nactive-par.ndrop,par.popmin);
   boolean[] mark = markLast(par.ndrop);
   int i;
   for(i=0;i<par.niv;i++)
    if(mark[i])
     gv[i].active = false;
  }
 }

 private void invert() //this should be given as a parameter
 {
  boolean[] mark = markLast(par.ndrop);
  int i;
  for(i=0;i<par.niv;i++)
   if(mark[i])
   {
    gv[i].mutate(1);
    calcFit(i);
   }
 }

 private Individual crossover(int i1, int i2)
 {
  if(Math.random() < par.crossover_bias)
  {
  	//this is wrong on so many levels; should be given as a parameter
   if(fit[i1] > fit[i2])
    switch(par.crossover_type)
    {
     case 0: return gv[i1].crossover1(gv[i2],par.crossover_bias);
     case 1: return gv[i1].crossover2(gv[i2],par.crossover_bias);
     case 2: return gv[i1].crossoveru(gv[i2],par.crossover_bias);
    }
   else
    switch(par.crossover_type)
    {
     case 0: return gv[i2].crossover1(gv[i1],par.crossover_bias);
     case 1: return gv[i2].crossover2(gv[i1],par.crossover_bias);
     case 2: return gv[i2].crossoveru(gv[i1],par.crossover_bias);
    }
  }
  else
  {
   if(fit[i1] < fit[i2])
    switch(par.crossover_type)
    {
     case 0: return gv[i1].crossover1(gv[i2],par.crossover_bias);
     case 1: return gv[i1].crossover2(gv[i2],par.crossover_bias);
     case 2: return gv[i1].crossoveru(gv[i2],par.crossover_bias);
    }
   else
    switch(par.crossover_type)
    {
     case 0: return gv[i2].crossover1(gv[i1],par.crossover_bias);
     case 1: return gv[i2].crossover2(gv[i1],par.crossover_bias);
     case 2: return gv[i2].crossoveru(gv[i1],par.crossover_bias);
    }
  }
  return null;
 }

 private void grow(int i) //this should be given as a parameter
 {
  fit[i] = of.f(gn[i].toArray());
  Individual iv = mutate(i);
  double ivf = of.f(iv.toArray());
  
  if(ivf > fit[i])
  {
   gn[i] = iv;
   fit[i] = ivf;
  }
 }

 private Individual mutate(int i) //this should be given as a parameter
 {
  switch(par.mutation_type)
  {
   case 0: return gn[i].mutate(par.mutation_chance);
   case 1: return gn[i].mutate(genn,par.mutation_lat);
  }
  return null;
 }

 public void step()
 {
  int i;
  int i1, i2;
  //every call here should actually call functions given as parameters
  if(!par.grow) calcFit();

  if(par.ndrop > 0) drop();
  else if(par.nnew > 0) renew();
  else if(par.ninvert > 0) invert();

  elitism();

  procFit();

  if(par.grow) grow(0);

  for(i=1;i<par.niv;i++)
   if(gv[i].active)
   {
    i1 = select();
    i2 = select();

    gn[i] = crossover(i1,i2);

    gn[i] = mutate(i);

    if(par.grow) grow(i);
   }

  for(i=0;i<par.niv;i++) if(gv[i].active) gv[i] = gn[i];

  genn += par.mutation_inc;
 }
 
	public SimResults alg() {
		ArrayList<Snapshot> sr = new ArrayList<Snapshot>();
		
		int i = 0;
		init();
		while(/*getBestFit() < par.target && */i < par.ngen) {
			Snapshot snapshot = new Snapshot(getPop(), getFit(), of.getNCalls());
			sr.add(snapshot);

			step();
			i++;
		}

		return new SimResults(sr);
	}

 public String getName() {
  return name;
 }
}