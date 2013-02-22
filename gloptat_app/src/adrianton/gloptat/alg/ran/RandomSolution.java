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

package adrianton.gloptat.alg.ran;

import adrianton.gloptat.objfun.Domain;

public class RandomSolution {
	final double[] pos;

	RandomSolution(double[] pos) {
		this.pos = pos;
	}

	static RandomSolution getRan(Domain dom) {
		double[] pos = new double[dom.nDim];

		for(int i = 0; i < dom.nDim; i++)
			pos[i] = Math.random() * (dom.d[i].r - dom.d[i].l) + dom.d[i].l;
		
		return new RandomSolution(pos);
	}
	
	RandomSolution nudge(Domain dom) {
		double[] pos = new double[this.pos.length];
		
		for(int i=0;i<pos.length;i++) {
			boolean nok = true;
			while(nok) {
				pos[i] = this.pos[i] + (Math.random()-0.5) * 1.1;
				nok = dom.d[i].l > pos[i] || dom.d[i].r < pos[i];
			}
		}
		
		return new RandomSolution(pos);
	}

	public double[] toArray() {
		double[] ret = new double[pos.length];
		for(int i = 0; i < pos.length; i++)
			ret[i] = pos[i];
		return ret;
	}
}
