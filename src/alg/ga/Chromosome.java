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

public class Chromosome
{
 static final int nb = 20;
 private final byte[] inf;
//------------------------------------------------------------------------------
 Chromosome()
 {
  inf = new byte[nb];
  int i;
  for(i=0;i<nb;i++)
   if(Math.random() < 0.5d) inf[i] = 0;
   else inf[i] = 1;
 }
//------------------------------------------------------------------------------
 Chromosome(byte iinf)
 {
  inf = new byte[nb];
  int i;
  for(i=0;i<nb;i++)
   inf[i] = iinf;
 }
//------------------------------------------------------------------------------
 Chromosome(byte[] iinf)
 {
  inf = new byte[nb];
  int i;
  for(i=0;i<nb;i++)
   inf[i] = iinf[i];
 }
//------------------------------------------------------------------------------
 public Chromosome copy()
 {
  byte[] ret = new byte[nb];
  int i;
  for(i=0;i<nb;i++)
   ret[i] = inf[i];
  return new Chromosome(ret);
 }
//------------------------------------------------------------------------------
 public Chromosome mutate(double imutch)
 {
  byte[] ret = new byte[nb];
  int i;
  for(i=0;i<nb;i++)
   if(Math.random() < imutch)
    ret[i] = (byte)(1-inf[i]);
   else
    ret[i] = inf[i];
  return new Chromosome(ret);
 }
//------------------------------------------------------------------------------
 public Chromosome mutate(double mean, double lat)
 {
  byte[] ret = new byte[nb];
  mean = Math.max(Math.min(mean,nb),0);
  int i;
  for(i=0;i<nb;i++)
  {
   if(Math.random() < Math.exp(-(i-mean)*(i-mean) / lat))
    ret[i] = (byte)(1-inf[i]);
   else
    ret[i] = inf[i];
  }
  return new Chromosome(ret);
 }
//------------------------------------------------------------------------------
 public Chromosome crossover1(Chromosome ig, double bias)
 {
  byte[] ret = new byte[nb];

  int i, cut;
  cut = (int)(Math.random() * (nb-2) + 1);
  if(Math.random() < bias && false)
  {
   for(i=0;i<cut;i++) ret[i] = inf[i];
   for(i=cut;i<nb;i++) ret[i] = ig.inf[i];
  }
  else
  {
   for(i=0;i<cut;i++) ret[i] = ig.inf[i];
   for(i=cut;i<nb;i++) ret[i] = inf[i];
  }
  
  return new Chromosome(ret);
 }
//------------------------------------------------------------------------------
 public Chromosome crossover2(Chromosome ig, double bias)
 {
  byte[] ret = new byte[nb];

  int i, cut1, cut2;
  cut1 = (int)(Math.random() * (nb/3*2));
  cut2 = (int)(Math.random() * (nb-cut1));
  if(Math.random() < bias && false)
  {
   for(i=0;i<cut1;i++) ret[i] = inf[i];
   for(i=cut1;i<cut2;i++) ret[i] = ig.inf[i];
   for(i=cut2;i<nb;i++) ret[i] = inf[i];
  }
  else
  {
   for(i=0;i<cut1;i++) ret[i] = ig.inf[i];
   for(i=cut1;i<cut2;i++) ret[i] = inf[i];
   for(i=cut2;i<nb;i++) ret[i] = ig.inf[i];
  }
  
  return new Chromosome(ret);
 }
//------------------------------------------------------------------------------
 public Chromosome crossoveru(Chromosome ig, double bias)
 {
  byte[] ret = new byte[nb];

  int i;
  for(i=0;i<nb;i++)
   if(Math.random() < bias && false)
    ret[i] = inf[i];
   else
    ret[i] = ig.inf[i];

  return new Chromosome(ret);
 }
//------------------------------------------------------------------------------
 public long toLong() 
 {
  int i;
  long ret, pow;
  ret = 0; pow = 1;

  for(i=0;i<nb;i++)
   { if(inf[i] == 1) ret+=pow; pow<<=1; }

  return ret;
 }
//------------------------------------------------------------------------------
 public void fromLong(long n)
 {
  int i;
  long p;
  for(p=1,i=0;i<nb;i++,p<<=1)
   if((p & n) > 0) inf[i] = 1; else inf[i] = 0;
 }
//------------------------------------------------------------------------------
 @Override
 public String toString() { return inf.toString(); }
//------------------------------------------------------------------------------
 public int length() { return nb; }
}
