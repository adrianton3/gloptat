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

class F_DeJong extends WOF
{
 F_DeJong()
 {
  psdomx = -5.12;
  psdomy = -5.12;
  pedomx =  5.12;
  pedomy =  5.12;
 }

 public String toString() { return "DeJong"; }

 double f(double[] inp)
 {
  return -(inp[0]*inp[0] + inp[1]*inp[1]);
 }
}
//==============================================================================
class F_Rosenbrock extends WOF
{
 F_Rosenbrock()
 {
  psdomx = -2.048;
  psdomy = -2.048;
  pedomx =  2.048;
  pedomy =  2.048;
 }

 public String toString() { return "Rosenbrock"; }

 double f(double[] inp)
 {
  return -(5*Math.pow(inp[1] - inp[0]*inp[0],2) + Math.pow(1 - inp[0],2));
 }
}
//==============================================================================
class F_Rastrigin extends WOF
{
 F_Rastrigin()
 {
  psdomx = -5.12;
  psdomy = -5.12;
  pedomx =  5.12;
  pedomy =  5.12;
 }

 public String toString() { return "Rastrigin"; }

 double f(double[] inp)
 {
  return -(20 + inp[0]*inp[0] - 10 * Math.cos(2*Math.PI*inp[0]) +
                inp[1]*inp[1] - 10 * Math.cos(2*Math.PI*inp[1]));
 }
}
//==============================================================================
class F_Michalewicz extends WOF
{
 F_Michalewicz()
 {
  psdomx =  0;
  psdomy =  0;
  pedomx =  Math.PI;
  pedomy =  Math.PI;
 }

 public String toString() { return "Michalewicz"; }

 double f(double[] inp)
 {
  return (Math.sin(inp[0]) * Math.pow(Math.sin(1*inp[0]*inp[0] / Math.PI), 20) +
          Math.sin(inp[1]) * Math.pow(Math.sin(2*inp[1]*inp[1] / Math.PI), 20));
 }
}