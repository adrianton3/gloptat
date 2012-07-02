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

package objfun;

public class MOF implements ObjectiveFunction //mega objective function?
{
 double sdomx, sdomy, edomx, edomy;
 public double minc, maxc;
 private WOF func;
 private int ncalls;
//------------------------------------------------------------------------------
 public void setFunc(int idnr)
 {
  switch(idnr)
  {
   case 0: func = new F_DeJong(); break;
   case 1: func = new F_Rastrigin(); break;
   case 2: func = new F_Michalewicz(); break;
  }
 }
//------------------------------------------------------------------------------
 public WOF getFunc()
 {
  return func;
 }
//------------------------------------------------------------------------------
 public void setDom(double sdomx, double edomx, double sdomy, double edomy)
 {
  this.sdomx = sdomx;
  this.edomx = edomx;
  this.sdomy = sdomy;
  this.edomy = edomy;
 }
//------------------------------------------------------------------------------
 private double vod(double x, double y)
 { 
  return func.f(new double[] {x,y});
 }
//------------------------------------------------------------------------------
 public double[][] compute(int rezx, int rezy)
 {
  double[][] ret = new double[rezx][rezy];
  double pasx, pasy;
  double px, py;
  pasx = (edomx - sdomx) / rezx;
  pasy = (edomy - sdomy) / rezy;
  maxc = -10000; minc = 10000;
  int i, j;

  px = sdomx;
  for(i=0;i<rezx;i++)
  {
   py = sdomy;
   for(j=0;j<rezy;j++)
   {
    ret[i][j] = vod(px,py);
    
    if(ret[i][j] < minc) minc = ret[i][j];
    else if(ret[i][j] > maxc) maxc = ret[i][j];
    
    py += pasy;
   }
   px += pasx;
  }
  
  return ret;
 }
//------------------------------------------------------------------------------
 public void resetNCalls()
 {
  ncalls = 0;
 }
//------------------------------------------------------------------------------
 public int getNCalls()
 {
  return ncalls;
 }
//------------------------------------------------------------------------------
 public double f(double[] x)
 {
  ncalls++;
  
  if((x[0] >= sdomx && x[0] <= edomx) && (x[1] >= sdomy && x[1] <= edomy))
   return func.f(x);
  else return -10000;
 }
}
