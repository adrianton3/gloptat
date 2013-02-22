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

package adrianton.gloptat.app;

import java.util.Iterator;

import adrianton.gloptat.alg.Snapshot;
import adrianton.gloptat.app.gui.MainVisGUI;
import adrianton.gloptat.app.gui.OutputGUI;
import adrianton.gloptat.objfun.Domain;
import adrianton.gloptat.objfun.MOF;
import adrianton.gloptat.plotter.Displayer;
import adrianton.gloptat.plotter.DisplayerThread;

public class MainVis extends MainGeneric {
	public Displayer displayer;
	Domain dom;

	public Iterator<Snapshot> iterator;

	void setupDisplayer() {
		Domain dom = of.getDom();
		displayer = new Displayer();

		int tmprx, tmpry;
		double dx, dy;

		dx = dom.d[0].r - dom.d[0].l;
		dy = dom.d[1].r - dom.d[1].l;

		if(dx < dy) {
			tmprx = 120;
			tmpry = (int) (tmprx * (dy / dx));
		} else {
			tmpry = 120;
			tmprx = (int) (tmpry * (dx / dy));
		}

		displayer.setDom(dom);
		displayer.setVal(of.compute(tmprx, tmpry));
		displayer.minc = of.minc;
		displayer.maxc = of.maxc;
	}

	void setupOF() {
		of = new MOF();
		of.setFunc(0);
	}

	public void changeOF(int id) {
		of.setFunc(id);
		dom = of.getDom();

		int tmprx, tmpry;
		double dx, dy;

		dx = dom.d[0].r - dom.d[0].l;
		dy = dom.d[1].r - dom.d[1].l;

		if(dx < dy) {
			tmprx = 120;
			tmpry = (int) (tmprx * (dy / dx));
		} else {
			tmpry = 120;
			tmprx = (int) (tmpry * (dx / dy));
		}

		displayer.setDom(dom);
		displayer.setVal(of.compute(tmprx, tmpry));
		displayer.minc = of.minc;
		displayer.maxc = of.maxc;

		displayer.needsRepaint();
	}

	void start() {
		con = new OutputGUI();

		setupOF();

		loadFactories();

		se = new MainVisGUI(this);

		setupDisplayer();

		new Thread(new DisplayerThread(displayer)).start();
	}

	public static void main(String[] args) {
		MainVis instance = new MainVis();
		instance.start();
	}
}
