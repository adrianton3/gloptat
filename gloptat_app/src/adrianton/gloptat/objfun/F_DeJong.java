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

class F_DeJong extends WOF {
 F_DeJong() {
 	dom = Domain.fromInterval(new Interval(-5.12,5.12), 2);
 }

 public String toString() { return "DeJong"; }
 
 double f(double[] inp) {
  double tmp = 0;
  
  for(int i=0;i<inp.length;i++) {
  	tmp += inp[i]*inp[i];
  }
  
 	return -tmp;
 }
}