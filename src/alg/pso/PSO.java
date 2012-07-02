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

package alg.pso;

import alg.OA;
import alg.OAParams;
import alg.SimParams;
import objfun.ObjectiveFunction;

public class PSO implements OA
{
 public final String nam = "Particle swarm optimization";
 private PSOParams par = new PSOParams();
 private double dom[][];
 private Particle[] part;
 private Particle[] best;
 private double[] fit, bfit;
 private ObjectiveFunction ifit;
 private int nactive;
 private SocialNetwork sn;
//------------------------------------------------------------------------------
 public PSO(ObjectiveFunction iifit)
 {
  ifit = iifit;
 }
//------------------------------------------------------------------------------
 public void setDom(double[][] idom)
 {
  dom = idom;
 }
//------------------------------------------------------------------------------
 private void alloc()
 {
  part = new Particle[par.np];
  best = new Particle[par.np];
  fit = new double[par.np];
  bfit = new double[par.np];
  nactive = par.np;
 }
//------------------------------------------------------------------------------
 public void setParams(OAParams ipar)
 {
  par = (PSOParams) ipar;
  alloc();
  randomize();
 }
//------------------------------------------------------------------------------
 public void randomize()
 {
  int i;
  for(i=0;i<par.np;i++)
  {
   part[i] = new Particle(dom,par.atenuator,par.social,par.personal);
   best[i] = new Particle(dom,par.atenuator,par.social,par.personal);
   best[i] = part[i].copy();
  }
  calcFit();

  switch(par.network_type)
  {
   case 0:
    sn = SocialNetwork.Star(par.np);
    break;
   case 1:
    sn = SocialNetwork.Ring(par.np);
    break;
   case 2:
    sn = SocialNetwork.Total(par.np);
    break;
   case 3:
    sn = SocialNetwork.Islands(par.np);
    break;
   default:
    sn = null;
  }
 }
//------------------------------------------------------------------------------
public void setPop(double[][] ipop)
 {
  par.np = ipop.length;
  alloc();
  int i;
  for(i=0;i<par.np;i++)
   part[i].fromArray(ipop[i]);
 }
//------------------------------------------------------------------------------
 public double[][] getPop()
 {
  double[][] ret = new double[par.np][];
  int i;
  for(i=0;i<par.np;i++)
   ret[i] = part[i].toArray();
  return ret;
 }
//------------------------------------------------------------------------------
public boolean[] getActive()
 {
  boolean[] ret = new boolean[par.np];
  int i;
  for(i=0;i<par.np;i++)
   ret[i] = part[i].active;
  return ret;
 }
//------------------------------------------------------------------------------
 public double getBestFit()
 {
  int i;
  double ret = bfit[0];
  for(i=1;i<par.np;i++) if(bfit[i] > ret) ret = bfit[i];
  return ret;
 }
//------------------------------------------------------------------------------
 public double getMeanFit()
 {
  double acc = 0;
  int i, nact = 0;
  for(i=0;i<par.np;i++) if(part[i].active) { acc += bfit[i]; nact++; }
  return acc/(double)nact;
 }
//------------------------------------------------------------------------------
 public double[] getBest()
 {
  int i, besti;
  double ret;
  ret = bfit[0]; besti = 0;
  for(i=1;i<par.np;i++) if(bfit[i] > ret) { ret = bfit[i]; besti = i; }
  return best[besti].toArray();
 }
//------------------------------------------------------------------------------
 public int getNapel()
 {
  return ifit.getNCalls();
 }
//------------------------------------------------------------------------------
 public void resetNapel()
 {
  ifit.resetNCalls();
 }
//------------------------------------------------------------------------------
 private void calcFit(int i)
 {
  fit[i] = ifit.f(part[i].toArray());
  bfit[i] = fit[i];
 }
//------------------------------------------------------------------------------
 public void calcFit()
 {
  int i;
  for(i=0;i<par.np;i++)
   calcFit(i);
 }
//------------------------------------------------------------------------------
 private boolean[] markLast(int n)
 {
  boolean[] mark = new boolean[par.np];

  int i, j, mini;
  double min;
  for(i=0;i<par.np;i++) mark[i] = false;
  for(i=0;i<n;i++)
  {
   min = 1000; mini = -1;
   for(j=0;j<par.np;j++)
    if(part[j].active && !mark[j])
     if(fit[j] < min) { min = fit[j]; mini = j; }
   mark[mini] = true;
  }

  return mark;
 }
//------------------------------------------------------------------------------
 private int maxRand(int n)
 {
  int nsel = n;
  int i = 0, r, ret = -1;

  for(i=0;i<par.np;i++)
   if(part[i].active) { ret = i; break; }

  i = 0;
  while(i<nsel)
  {
   r = (int)(Math.random()*par.np);
   if(part[r].active)
   {
    i++;
    if(fit[r] > fit[ret]) ret = r;
   }
  }

  return ret;
 }
//------------------------------------------------------------------------------
 private int maxMark(boolean[] mark)
 {
  int i, maxi = -1;
  double max = -1000000;

  for(i=0;i<par.np;i++)
   if(part[i].active && mark[i])
    if(fit[i] > max)
    { max = fit[i]; maxi = i; }
  
  return maxi;
 }
//------------------------------------------------------------------------------
 private void renew()
 {
  boolean[] mark = markLast(par.nnew);
  int i;
  for(i=0;i<par.np;i++)
   if(mark[i])
   {
    part[i] = new Particle(dom,par.atenuator,par.social,par.personal);
    best[i] = part[i].copy();
    //evaluate fitness?
   }
 }
//------------------------------------------------------------------------------
 private void drop()
 {
  if(nactive > par.popmin)
  {
   nactive = Math.max(nactive-par.ndrop,par.popmin);
   boolean[] mark = markLast(par.ndrop); //nu atatea, ci pana la popmin
   int i;
   for(i=0;i<par.np;i++)
    if(mark[i])
     part[i].active = false;
  }
 }
//------------------------------------------------------------------------------
 public void step()
 {
  int i;
  for(i=0;i<par.np;i++)
  {
   if(part[i].active)
   {
    if(sn == null)
     part[i].updateV(part[maxRand(par.network_param)],best[i]);
    else
     part[i].updateV(part[maxMark(sn.getLine(i))],best[i]);

    fit[i] = ifit.f(part[i].toArray());
    if(fit[i] > bfit[i])
    {
     best[i] = part[i].copy();
     bfit[i] = fit[i];
    }
   }
  }

  for(i=0;i<par.np;i++)
  {
   if(part[i].active)
    part[i].updateP();
  }

  if(par.ndrop > 0) drop();
  else if(par.ndrop > 0)renew();
 }
//------------------------------------------------------------------------------
 public void alg()
 {
  int i;
  i = 0;
  while(getBestFit() < par.target && i < par.niter)
  {
   step();
   i++;
  }
 }
//------------------------------------------------------------------------------
 public String toString()
 {
  String ret = ""; int i;
  for(i=0;i<par.np;i++)
   ret += part[i].toString() + ";";
  return ret;
 }
//------------------------------------------------------------------------------
 public String getNam() 
 {
  return nam;
 }
}