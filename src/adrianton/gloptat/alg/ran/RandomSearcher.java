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

import java.util.ArrayList;

import adrianton.gloptat.alg.OA;
import adrianton.gloptat.alg.SimResults;
import adrianton.gloptat.alg.Snapshot;
import adrianton.gloptat.objfun.Domain;
import adrianton.gloptat.objfun.ObjectiveFunction;

public class RandomSearcher implements OA { //unfinished
	public final static String name = "Random Searcher";
	private final ObjectiveFunction of;
	private final RandomSearcherParams par;
	private final Domain dom;
	private RandomSolution[] pop;
	private double[] fit;
	
	RandomSearcher(ObjectiveFunction of, RandomSearcherParams par) {
		this.of = of;
		this.par = par;
		this.dom = of.getDom();
	}
	
	public void randomize() {
		pop = new RandomSolution[par.popSize];
		
		for(int i=0;i<par.popSize;i++)
			pop[i] = new RandomSolution(dom);
	}

	private double[] getFit() {
		double[] ret = new double[par.popSize];
		
		for(int i = 0; i < par.popSize; i++)
			ret[i] = fit[i];
		
		return ret;
	}

	public double[][] getPop() {
		return null;
	}

	public double getBestFit() {
		return 0;
	}

	public double[] getBest() {
		return null;
	}

	public int getNCalls() {
		return 0;
	}

	public void step() {
		
	}

	public SimResults alg() {
		ArrayList<Snapshot> sr = new ArrayList<Snapshot>();
		
		for(int i=0;i<par.nIter;i++) {
			Snapshot ss = new Snapshot(getPop(), getFit(), of.getNCalls());
			sr.add(ss);

			step();
		}

		return new SimResults(sr);
	}

	public String getName() {
		return name;
	}
}
