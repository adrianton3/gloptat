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

package adrianton.gloptat.app.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import adrianton.gloptat.alg.OA;
import adrianton.gloptat.alg.OAParams;
import adrianton.gloptat.alg.SimResults;
import adrianton.gloptat.app.Fasten;
import adrianton.gloptat.app.MainGeneric;

class ActBenchmarkLight implements ActionListener {
	MainGeneric outer;
	final int ntrials = 20;

	ActBenchmarkLight(MainGeneric outer) {
		this.outer = outer;
	}

	public void actionPerformed(ActionEvent e) {
		OAParams oaParams = outer.getOAParams();

		outer.con.add("Optimizing " + outer.of.getFunc().toString() + "\n"
				+ "Using: " + outer.activeOAFactory.getName() + "\n" + "Parameters:\n"
				+ oaParams.toString());

		outer.con.add("Running " + ntrials + " instances...");
		
		double[] rez = new double[ntrials];
		for(int i = 0; i < ntrials; i++) {
			OA oa = outer.getOA(oaParams);
			SimResults simResults = oa.alg();
			rez[i] = simResults.getBestFit();
		}
		
		for(int i = 0; i < ntrials; i++)
			outer.con.add("{ run: " + i + "; bestfit: " + Fasten.round(rez[i], 2) + " }");

		double max = Fasten.round(Fasten.max(rez), 2);
		double min = Fasten.round(Fasten.min(rez), 2);
		double avg = Fasten.round(Fasten.avg(rez), 2);

		outer.con.add("Maximum: " + max);
		outer.con.add("Minimum: " + min);
		outer.con.add("Average: " + avg);

		outer.con.separator();
	}
}