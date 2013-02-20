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

package adrianton.gloptat.objfun;

class F_Rosenbrock extends WOF {
 F_Rosenbrock() {
 	dom = Domain.fromInterval(new Interval(-2.048,2.048),2);
 }

 public String toString() { return "Rosenbrock"; }

 double f(double[] inp) {
  return -(5*Math.pow(inp[1] - inp[0]*inp[0],2) + Math.pow(1 - inp[0],2));
 }
}