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

package def;

import gui.ConfigGUI;
import gui.MainGenericGUI;
import gui.OutputGUI;

import java.util.ArrayList;

import objfun.MOF;
import alg.ConfString;
import alg.OA;
import alg.OAParams;
import alg.ga.GAFactory;
import alg.pso.PSOFactory;

public abstract class MainGeneric {
	// GUI
	public MainGenericGUI se;
	public OutputGUI con;
	public ConfigGUI[] conf;
	public ConfigGUI activeConf;

	// Objective function
	public MOF of;

	// Optimization algorithms
	public OAFactory[] oaFactory;
	public OAFactory activeOAFactory;

	public abstract void changeOF(int tmp);

	public OAParams getOAParams() {
		return activeOAFactory.getOAParams(new ConfString(activeConf.getString()));
	}

	public OA getOA(OAParams oaParams) {
		return activeOAFactory.getOA(of, oaParams);
	}

	protected void tmp() { //temporary solution
		oaFactory = new OAFactory[]{new GAFactory(), new PSOFactory() };
		
		for(int i=0;i<oaFactory.length;i++)
			conf[i] = new ConfigGUI("Config " + oaFactory[i].getName(), 
				oaFactory[i].getOAParams(new ConfString("")).toString());
		
		activeConf = conf[0];
		activeOAFactory = oaFactory[0];
	}
	
	private void loadFactories() {
		ArrayList<OAFactory> factory = FactoryLoader.getFactories("factories/",
				OAFactory.class);

		int i = 0;
		for(OAFactory oaf : factory) {
			oaFactory[i] = oaf;
			conf[i] = new ConfigGUI("Config " + oaFactory[i].getName(), 
					oaFactory[i].getOAParams(new ConfString("")).toString());
			i++;
		}
	}
}
