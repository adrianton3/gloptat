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

public class Ivan
{
 final int ng;
 boolean active = true;
 final Domain dom;
 private final Chromosome[] gene;
//------------------------------------------------------------------------------
 Ivan(Domain dom)
 {
  this.dom = dom; ng = dom.d.length;
  gene = new Chromosome[ng];
  int i;
  for(i=0;i<ng;i++)
   gene[i] = new Chromosome();
 }
//------------------------------------------------------------------------------
 Ivan(Domain dom, Chromosome[] iag)
 {
  this.dom = dom; ng = dom.d.length;
  gene = new Chromosome[ng];
  int i;
  for(i=0;i<ng;i++)
   gene[i] = iag[i];
 }
//------------------------------------------------------------------------------
 public Ivan mutate(double imutch)
 {
  Chromosome[] ret = new Chromosome[ng];
  int i;
  for(i=0;i<ng;i++)
   ret[i] = gene[i].mutate(imutch);
  return new Ivan(dom,ret);
 }
//------------------------------------------------------------------------------
 public Ivan mutate(double mean, double lat)
 {
  Chromosome[] ret = new Chromosome[ng];
  int i;
  for(i=0;i<ng;i++)
   ret[i] = gene[i].mutate(mean,lat);
  return new Ivan(dom,ret);
 }
//------------------------------------------------------------------------------
 public Ivan crossover1(Ivan iv, double bias)
 {
  Chromosome[] retg = new Chromosome[ng];
  int i;
  for(i=0;i<ng;i++)
   retg[i] = gene[i].crossover1(iv.getGena(i),bias);
  return new Ivan(dom,retg);
 }
//------------------------------------------------------------------------------
 public Ivan crossover2(Ivan iv, double bias)
 {
  Chromosome[] ret = new Chromosome[ng];
  int i;
  for(i=0;i<ng;i++)
   ret[i] = gene[i].crossover2(iv.getGena(i),bias);
  return new Ivan(dom,ret);
 }
//------------------------------------------------------------------------------
 public Ivan crossoveru(Ivan iv, double bias)
 {
  Chromosome[] ret = new Chromosome[ng];
  int i;
  for(i=0;i<ng;i++)
   ret[i] = gene[i].crossoveru(iv.getGena(i),bias);
  return new Ivan(dom,ret);
 }
//------------------------------------------------------------------------------
 public Ivan copy()
 {
  Chromosome[] ret = new Chromosome[ng];
  int i;
  for(i=0;i<ng;i++)
   ret[i] = gene[i].copy();
  return new Ivan(dom,ret);
 }
//------------------------------------------------------------------------------
 public double[] toArray()
 {
  double[] ret = new double[ng];
  int i;
  for(i=0;i<ng;i++)
   ret[i] = dom.d[i].l + (dom.d[i].r - dom.d[i].l) / Math.pow(2,Chromosome.nb) * gene[i].toLong();
  return ret;
 }
//------------------------------------------------------------------------------
 public void fromArray(double[] iar)
 {
  int i;
  for(i=0;i<ng;i++)
   gene[i].fromLong((long)((iar[i] - dom.d[i].l)*(Math.pow(2,Chromosome.nb) / (dom.d[i].r - dom.d[i].l))));
 }
//------------------------------------------------------------------------------
 public Chromosome getGena(int ii) { return gene[ii]; }
 public void setGena(int ii, Chromosome iv) { gene[ii] = iv; }
}
