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

package adrianton.gloptat.alg.ga;

import adrianton.gloptat.objfun.Domain;
//should be parametrized to accept other types of chromosomes as well
public class Individual
{
	final Domain dom;
	final int nChromosome;
 private final Chromosome[] chromosome;
 boolean active = true;
//------------------------------------------------------------------------------
 Individual(Domain dom)
 {
  this.dom = dom; nChromosome = dom.d.length;
  chromosome = new Chromosome[nChromosome];
  int i;
  for(i=0;i<nChromosome;i++)
   chromosome[i] = new Chromosome();
 }
//------------------------------------------------------------------------------
 Individual(Domain dom, Chromosome[] iag)
 {
  this.dom = dom; nChromosome = dom.d.length;
  chromosome = new Chromosome[nChromosome];
  int i;
  for(i=0;i<nChromosome;i++)
   chromosome[i] = iag[i];
 }
//------------------------------------------------------------------------------
 public Individual mutate(double imutch)
 {
  Chromosome[] ret = new Chromosome[nChromosome];
  int i;
  for(i=0;i<nChromosome;i++)
   ret[i] = chromosome[i].mutate(imutch);
  return new Individual(dom,ret);
 }
//------------------------------------------------------------------------------
 public Individual mutate(double mean, double lat)
 {
  Chromosome[] ret = new Chromosome[nChromosome];
  int i;
  for(i=0;i<nChromosome;i++)
   ret[i] = chromosome[i].mutate(mean,lat);
  return new Individual(dom,ret);
 }
//------------------------------------------------------------------------------
 public Individual crossover1(Individual iv, double bias)
 {
  Chromosome[] retg = new Chromosome[nChromosome];
  int i;
  for(i=0;i<nChromosome;i++)
   retg[i] = chromosome[i].crossover1(iv.getGene(i),bias);
  return new Individual(dom,retg);
 }
//------------------------------------------------------------------------------
 public Individual crossover2(Individual iv, double bias)
 {
  Chromosome[] ret = new Chromosome[nChromosome];
  int i;
  for(i=0;i<nChromosome;i++)
   ret[i] = chromosome[i].crossover2(iv.getGene(i),bias);
  return new Individual(dom,ret);
 }
//------------------------------------------------------------------------------
 public Individual crossoveru(Individual iv, double bias)
 {
  Chromosome[] ret = new Chromosome[nChromosome];
  int i;
  for(i=0;i<nChromosome;i++)
   ret[i] = chromosome[i].crossoveru(iv.getGene(i),bias);
  return new Individual(dom,ret);
 }
//------------------------------------------------------------------------------
 public Individual copy()
 {
  Chromosome[] ret = new Chromosome[nChromosome];
  int i;
  for(i=0;i<nChromosome;i++)
   ret[i] = chromosome[i].copy();
  return new Individual(dom,ret);
 }
//------------------------------------------------------------------------------
 public double[] toArray()
 {
  double[] ret = new double[nChromosome];
  int i;
  for(i=0;i<nChromosome;i++)
   ret[i] = dom.d[i].l + (dom.d[i].r - dom.d[i].l) / Math.pow(2,Chromosome.nb) * chromosome[i].toLong();
  return ret;
 }
//------------------------------------------------------------------------------
 public void fromArray(double[] iar)
 {
  int i;
  for(i=0;i<nChromosome;i++)
   chromosome[i].fromLong((long)((iar[i] - dom.d[i].l)*(Math.pow(2,Chromosome.nb) / (dom.d[i].r - dom.d[i].l))));
 }
//------------------------------------------------------------------------------
 public Chromosome getGene(int ii) { return chromosome[ii]; }
 public void setGene(int ii, Chromosome iv) { chromosome[ii] = iv; }
}
