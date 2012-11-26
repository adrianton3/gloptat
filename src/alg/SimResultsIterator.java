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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimResultsIterator implements Iterator<Snapshot> {
	private ArrayList<Snapshot> simulation;
	private int currentPosition;

	public SimResultsIterator(ArrayList<Snapshot> simulation) {
		this.simulation = simulation;
		currentPosition = 0;
	}

	public boolean hasNext() {
		return currentPosition < simulation.size();
	}

	public Snapshot next() {
		if(hasNext()) {
			Snapshot snapshot = simulation.get(currentPosition);
			currentPosition++;
			return snapshot;
		} 
		else throw new NoSuchElementException();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}
