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

import objfun.Domain;
import objfun.ObjectiveFunction;
import alg.OA;
import alg.OAParams;
import alg.SimParams;
/* lots of work here:
 * + need to parametrize everything
 */
public class GA implements OA
{
 public final static String nam = "Genetic Algorithm";
 private GAParams par = new GAParams(); //final
 private Individual[] gv;
 private Individual[] gn;
 private double[] fit;
 private Domain dom; //final
 private ObjectiveFunction ifit; //final
 private int nactive;
 private double genn;
//------------------------------------------------------------------------------
 public GA(ObjectiveFunction iifit)
 {
  ifit = iifit;
 }
//------------------------------------------------------------------------------
 private void alloc()
 {
  gv = new Individual[par.niv];
  gn = new Individual[par.niv];
  fit = new double[par.niv];
 }
//------------------------------------------------------------------------------
 public void setDom(Domain dom) {
  this.dom = dom;
 }
//------------------------------------------------------------------------------
 public void setParams(OAParams ipar)
 {
  par = (GAParams)ipar;
  alloc();
  randomize();
 }
//------------------------------------------------------------------------------
 public void init() {
  genn = par.mutation_start;
  nactive = par.niv;
 }
//------------------------------------------------------------------------------
 public void randomize() {
  init();

  int i;
  for(i=0;i<par.niv;i++) {
   gv[i] = new Individual(dom);
   gn[i] = null; //new Ivan(dom);
  } //?
  calcFit();
 }
//------------------------------------------------------------------------------
 public void setPop(double[][] ipop) {
  par.niv = ipop.length;
  alloc();
  int i;
  for(i=0;i<par.niv;i++) {
   gv[i] = new Individual(dom);
   gv[i].fromArray(ipop[i]);
  }
 }
//------------------------------------------------------------------------------
 public double[][] getPop() {
  double[][] ret = new double[par.niv][];
  int i;
  for(i=0;i<par.niv;i++)
   ret[i] = gv[i].toArray();
  return ret;
 }
//------------------------------------------------------------------------------
 public boolean[] getActive()
 {
  boolean[] ret = new boolean[par.niv];
  int i;
  for(i=0;i<par.niv;i++)
   ret[i] = gv[i].active;
  return ret;
 }
//------------------------------------------------------------------------------
 public double getBestFit()
 {
  int i;
  double ret = fit[0];
  for(i=1;i<par.niv;i++) if(fit[i] > ret) ret = fit[i];
  return ret;
 }
//------------------------------------------------------------------------------
 public double getMeanFit()
 {
  double acc = 0;
  int i, nact = 0;
  for(i=0;i<par.niv;i++) if(gv[i].active) { acc += fit[i]; nact++; }
  return acc/(double)nact;
 }
//------------------------------------------------------------------------------
 public double[] getBestForce()
 {
  calcFit();
  return getBest();
 }
//------------------------------------------------------------------------------
 public double[] getBest()
 {
  int i, besti;
  double ret;
  ret = fit[0]; besti = 0;
  for(i=1;i<par.niv;i++) if(fit[i] > ret) { ret = fit[i]; besti = i; }
  return gv[besti].toArray();
 }
//------------------------------------------------------------------------------
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
//------------------------------------------------------------------------------
 public int getNapel()
 {
  return ifit.getNCalls();
 }
//------------------------------------------------------------------------------
 public void resetNapel() {
  ifit.resetNCalls();
 }
//------------------------------------------------------------------------------
 private void calcFit(int i)
 {
  if(gv[i].active)
   fit[i] = ifit.f(gv[i].toArray());
 }
//------------------------------------------------------------------------------
 public void calcFit()
 {
  int i;
  for(i=0;i<par.niv;i++)
   calcFit(i);
 }
//------------------------------------------------------------------------------
 private void elitism() //this should be given as a parameter
 {
  gn[0] = gv[getBestI()];//.copy();
 }
//------------------------------------------------------------------------------
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
//------------------------------------------------------------------------------
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
//------------------------------------------------------------------------------
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
//------------------------------------------------------------------------------
 private int select() //this shouldn't exist
 {
  switch(par.selection_type)
  {
   case 0: return select_roulette();
   case 1: return select_tournament(par.selection_pressure);
  }
  return -1;
 }
//------------------------------------------------------------------------------
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
//------------------------------------------------------------------------------
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
//------------------------------------------------------------------------------
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
//------------------------------------------------------------------------------
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
//------------------------------------------------------------------------------
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
//------------------------------------------------------------------------------
 private void grow(int i) //this should be given as a parameter
 {
  fit[i] = ifit.f(gn[i].toArray());
  Individual iv = mutate(i);
  double ivf = ifit.f(iv.toArray());
  
  if(ivf > fit[i])
  {
   gn[i] = iv;
   fit[i] = ivf;
  }
 }
//------------------------------------------------------------------------------
 private Individual mutate(int i) //this should be given as a parameter
 {
  switch(par.mutation_type)
  {
   case 0: return gn[i].mutate(par.mutation_chance);
   case 1: return gn[i].mutate(genn,par.mutation_lat);
  }
  return null;
 }
//------------------------------------------------------------------------------
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
//------------------------------------------------------------------------------
 public void alg()
 {
  int i;
  i = 0;
  init();
  while(getBestFit() < par.target && i < par.ngen)
  {
   step();
   i++;
  }
 }
//------------------------------------------------------------------------------
 public String fitToString()
 {
  String ret = ""; int i;
  for(i=0;i<par.niv;i++)
   ret += i+": " + (((int)(fit[i]*1000))/1000d) + ", ";
  return ret;
 }
//------------------------------------------------------------------------------
 public String toString()
 {
  String ret = ""; int i;
  for(i=0;i<par.niv;i++)
   ret += gn[i].toString() + ";";
  return ret;
 }
 @Override
//------------------------------------------------------------------------------
 public String getNam() 
 {
  return nam;
 }
}