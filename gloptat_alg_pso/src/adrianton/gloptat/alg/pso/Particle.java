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

package adrianton.gloptat.alg.pso;

import adrianton.gloptat.objfun.Domain;

class Particle
{
 final int nv;
 boolean active = true;
 double[] pos;
 double[] vit;
 final double atenuator;
 final double social;
 final double personal;
 final Domain dom;
//------------------------------------------------------------------------------
 Particle(Domain dom, double iatenuator, double isocial, double ipersonal)
 {
  nv = dom.d.length;
  pos = new double[nv];
  vit = new double[nv];
  this.dom = dom;
  
  int i;
  for(i=0;i<nv;i++) {
   pos[i] = Math.random() * (dom.d[i].r - dom.d[i].l) + dom.d[i].l;
   vit[i] = Math.random() * ((dom.d[i].r - dom.d[i].l)/10d);
   if(Math.random()>0.5) vit[i] = -vit[i];
  }
  
  atenuator = iatenuator + Math.random()/5 - 0.1;
  social = isocial + Math.random()/5 - 0.1;
  personal = ipersonal + Math.random()/5 - 0.1;
 }
//------------------------------------------------------------------------------
 Particle(Domain dom, double[] pos, double[] vit, double atenuator, double social, double personal)
 {
  this.dom = dom;
  this.pos = pos; this.nv = pos.length;
  this.vit = vit;
  this.atenuator = atenuator;
  this.social = social;
  this.personal = personal;
 }
//------------------------------------------------------------------------------
 public void updateV(Particle ibbp, Particle ibp)
 {
  int i;
  for(i=0;i<nv;i++)
   vit[i] = Math.min((dom.d[i].r - dom.d[i].l)/3d,
           (vit[i]*atenuator) + ((ibbp.pos[i]-pos[i])*social) + ((ibp.pos[i]-pos[i])*personal));
 }
//------------------------------------------------------------------------------
 public void updateP()
 {
  int i;
  for(i=0;i<nv;i++)
   pos[i] += vit[i];
 }
//------------------------------------------------------------------------------
 public void similar(Particle ip, double inoise)
 {
  int i;
  for(i=0;i<nv;i++)
   pos[i] = ip.pos[i] + (((int)Math.random())*2-1)*Math.log(Math.min(Math.random()*(dom.d[i].r - dom.d[i].l)*0.3,1));
 }
//------------------------------------------------------------------------------
 public Particle copy()
 {
  double[] npos, nvit;
  npos = new double[nv];
  nvit = new double[nv];

  int i;
  for(i=0;i<nv;i++) npos[i] = pos[i];
  for(i=0;i<nv;i++) nvit[i] = vit[i];

  return new Particle(dom,npos,nvit,atenuator,social,personal);
 }
//------------------------------------------------------------------------------
 public double getVar(int i)
 {
  return pos[i];
 }
//------------------------------------------------------------------------------
 @Override
 public String toString()
 {
  String ret = "["; int i;
  for(i=0;i<nv;i++)
   ret += pos[i] + " ";
  ret += "] - [";
  for(i=0;i<nv;i++)
   ret += vit[i] + " ";
  ret += "]";
  return ret;
 }
//------------------------------------------------------------------------------
 public double[] toArray()
 {
  double[] ret = new double[nv];
  int i;
  for(i=0;i<nv;i++)
   ret[i] = pos[i];
  return ret;
 }
//------------------------------------------------------------------------------
 public void fromArray(double[] iar)
 {
  int i;
  for(i=0;i<nv;i++)
   pos[i] = iar[i];
 }
}