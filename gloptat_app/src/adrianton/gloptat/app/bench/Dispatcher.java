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

package adrianton.gloptat.app.bench;

import adrianton.gloptat.alg.OAFactory;
import adrianton.gloptat.alg.OAParams;
import adrianton.gloptat.alg.SimResults;
import adrianton.gloptat.app.MainGeneric;
import adrianton.gloptat.objfun.ObjectiveFunction;

public class Dispatcher {
	final MainGeneric outer;
	final OAFactory oaFactory;
	final OAParams oaParams;
	final ObjectiveFunction of;
	final int nThreads;
	final int trialsPerThread;

	int threadsResponses = 0;
	SimResults[] sr;

	public Dispatcher(MainGeneric outer, OAFactory oaFactory, OAParams oaparams, ObjectiveFunction of,
			int nThreads, int trialsPerThread) {
		this.outer = outer;
		this.oaFactory = oaFactory;
		this.oaParams = oaparams;
		this.of = of;
		this.nThreads = nThreads;
		this.trialsPerThread = trialsPerThread;
	}

	synchronized void addResultSet(int idnr, SimResults[] sr) {
		int i;
		for(i = 0; i < sr.length; i++) {
			this.sr[trialsPerThread * idnr + i] = sr[i];
		}

		threadsResponses++;
		if(threadsResponses >= nThreads)
			notify();
	}

	public void dispatch() {
		sr = new SimResults[trialsPerThread * nThreads];

		int i;
		for(i = 0; i < nThreads; i++) {
			new Thread(new GOThread(this, i, oaFactory, of, oaParams, trialsPerThread)).start();
		}

		synchronized(this) {
			try {
				wait();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}

		publishResults(sr);
	}

	private void publishResults(SimResults[] sr) {
		for(SimResults s : sr)
			outer.con.add(s.toString());
		outer.con.separator();
	}
}