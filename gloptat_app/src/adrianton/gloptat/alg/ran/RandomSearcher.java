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

public class RandomSearcher implements OA { //TODO: finish implementing this
	public final static String name = "Random Searcher";
	private final ObjectiveFunction of;
	private final RandomSearcherParams par;
	private final Domain dom;
	private RandomSolution[] pop;
	private double[] fit;
	private RandomSolution best;
	private double bestfit;

	RandomSearcher(ObjectiveFunction of, RandomSearcherParams par) {
		this.of = of;
		this.par = par;
		this.dom = of.getDom();
	}

	private void randomize() {
		pop = new RandomSolution[par.popSize];

		for(int i = 0; i < par.popSize; i++)
			pop[i] = RandomSolution.getRan(dom);

		fit = new double[par.popSize];
		for(int i = 0; i < par.popSize; i++)
			pop[i] = RandomSolution.getRan(dom);
	}

	private double[] getFit() {
		double[] ret = new double[par.popSize];

		for(int i = 0; i < par.popSize; i++)
			ret[i] = fit[i];

		return ret;
	}

	private double[][] getPop() {
		double[][] ret = new double[par.popSize][];
		for(int i = 0; i < par.popSize; i++)
			ret[i] = pop[i].toArray();
		return ret;
	}
/*
	private double getBestFit() {
		double ret = fit[0];
		for(int i = 1; i < par.popSize; i++)
			if(fit[i] > ret)
				ret = fit[i];
		return ret;
	}

	private double[] getBest() {
		int maxi = 0;
		double max = fit[0];
		for(int i = 1; i < par.popSize; i++)
			if(fit[i] > max) {
				max = fit[i];
				maxi = i;
			}
		return pop[maxi].toArray();
	}
*/
	private void calcFit() {
		for(int i=0;i<par.popSize;i++)
			fit[i] = of.f(pop[i].toArray());
	}

	private void updateBest() {
		int maxi = 0;

		for(int i = 1; i < par.popSize; i++)
			if(fit[i] > bestfit) {
				bestfit = fit[i];
				maxi = i;
			}
		
		best = pop[maxi];
	}
	
	private void step() {
		updateBest();
		calcFit();
		for(int i = 0; i < par.popSize; i++)
			pop[i] = pop[i].nudge(dom);
	}

	@Override
	public SimResults alg() {
		ArrayList<Snapshot> sr = new ArrayList<Snapshot>();
		
		randomize();
		bestfit = -1000;
		of.resetNCalls(); //this should be called from the outside
		
		calcFit();

		for(int i = 0; i < par.nIter; i++) {
			Snapshot ss = new Snapshot(getPop(), getFit(), of.getNCalls());
			sr.add(ss);

			step();
		}

		return new SimResults(sr);
	}

	@Override
	public String getName() {
		return name;
	}
}
