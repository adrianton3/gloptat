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

public class Domain {
 public final Interval[] d;
 public final int nDim;
 
 public Domain(Interval[] d) {
 	this.d = d;
 	this.nDim = d.length;
 }
 
 public boolean in(double[] p) {
 	for(int i=0;i<d.length;i++)
 		if(!d[i].in(p[i])) return false;
 	
 	return true;
 }
 
 static Domain fromInterval(Interval pd, int nd) {
 	Interval[] dtmp = new Interval[nd];
 	
 	for(int i=0;i<nd;i++) {
 		dtmp[i] = pd; //no need to clone them since they're immutable
 	}
 	
 	return new Domain(dtmp);
 }
}
