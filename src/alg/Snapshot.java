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

package alg;

public class Snapshot {
	final double[][] pos;
	final double[] fit;
	final int nCalls;

	public Snapshot(double[][] pos, double[] fit, int nCalls) {
		this.pos = pos;
		this.fit = fit;
		this.nCalls = nCalls;
	}

	public double getBestFit() {
		double max = fit[0];
		
		for(double f: fit)
			if(f > max)
				max = f;
		
		return max;
	}

	public double[][] getPop() {
		return pos;
	}
}
