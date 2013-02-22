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

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import adrianton.gloptat.app.MainGeneric;

public class MainBenchGUI implements Runnable, MainGenericGUI {
	MainGeneric outer;
	JFrame frame;
	JComboBox<String> cmb_fun, cmb_oa;
	JButton but_config, but_benchmark, but_plot;

	public MainBenchGUI(MainGeneric outer) {
		this.outer = outer;
		SwingUtilities.invokeLater(this);
	}

	void assembleGUI() {
		frame = new JFrame("Global Optimization AT - Benchmark Mode");
		GridLayout experimentLayout = new GridLayout(8, 1, 4, 4);
		frame.setLayout(experimentLayout);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		// closing sequence

		String[] funNam = outer.of.ofNam();
		cmb_fun = new JComboBox<String>(funNam);

		String[] oaNam = outer.oaNam();
		cmb_oa = new JComboBox<String>(oaNam);

		but_config = new JButton("Config");
		but_benchmark = new JButton("Benchmark");
		but_plot = new JButton("PlotPerf");

		frame.getContentPane().add(cmb_fun);
		frame.getContentPane().add(cmb_oa);
		frame.getContentPane().add(but_config);
		frame.getContentPane().add(but_benchmark);
		frame.getContentPane().add(but_plot);

		but_config.addActionListener(new ActConf(outer));
		but_benchmark.addActionListener(new ActBenchmarkMT(outer));
		but_plot.addActionListener(new ActPlot(outer));

		cmb_fun.addActionListener(new ActFun(outer));
		cmb_oa.addActionListener(new ActOA(outer));

		frame.pack();
		frame.setVisible(true);
	}

	public void run() {
		assembleGUI();
	}

	public int getSelectedOAIndex() {
		return cmb_oa.getSelectedIndex();
	}

	public int getSelectedFunIndex() {
		return cmb_fun.getSelectedIndex();
	}
}