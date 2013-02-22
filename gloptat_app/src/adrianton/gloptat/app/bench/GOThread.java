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

import adrianton.gloptat.alg.OA;
import adrianton.gloptat.alg.OAFactory;
import adrianton.gloptat.alg.OAParams;
import adrianton.gloptat.alg.SimResults;
import adrianton.gloptat.objfun.ObjectiveFunction;

public class GOThread implements Runnable {
	final Dispatcher outer;
	final int idnr;
	final OAFactory oaFactory;
	final ObjectiveFunction of;
	final OAParams oaParams;
	final int nTrials;

	GOThread(Dispatcher outer, int idnr, OAFactory oaFactory,
			ObjectiveFunction of, OAParams oaParams, int nTrials) {
		this.outer = outer;
		this.idnr = idnr;
		this.oaFactory = oaFactory;
		this.of = of;
		this.oaParams = oaParams;
		this.nTrials = nTrials;
	}

	public void run() {
		SimResults[] rez = new SimResults[nTrials];

		for(int i = 0; i < nTrials; i++) {
			OA oa = oaFactory.getOA(of, oaParams);
			rez[i] =  oa.alg();
		}

		outer.addResultSet(idnr, rez);
	}
}