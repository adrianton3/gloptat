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

package adrianton.gloptat.alg;

import java.util.ArrayList;
import java.util.Iterator;

public class SimResults implements Iterable<Snapshot> {
	final ArrayList<Snapshot> simulation;

	public SimResults(ArrayList<Snapshot> simulation) {
		this.simulation = simulation;
	}
	
	public double getBestFit() {
		double max = simulation.get(0).getBestFit();
		
		for(Snapshot snapshot: simulation) {
			double tmp = snapshot.getBestFit();
			if(tmp > max)
				max = tmp;
		}
		
		return max;
	}

	public int getNIter() {
		return simulation.size();
	}

	public Snapshot getSnapShot(int iter) {
		return simulation.get(iter);
	}

	public Iterator<Snapshot> iterator() {
		return new SimResultsIterator(simulation);
	}
	
	@Override
	public String toString() {
		return getBestFit() + "";
	}
}