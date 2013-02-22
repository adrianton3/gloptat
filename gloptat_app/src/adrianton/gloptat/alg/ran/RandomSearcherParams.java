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

import java.util.HashMap;

import adrianton.gloptat.alg.OAParams;

public class RandomSearcherParams implements OAParams {
 final int popSize;
 final int nIter;
 
 public RandomSearcherParams(int nIter, int popSize) {
		this.popSize = popSize;
		this.nIter = nIter;
	}
 
 static RandomSearcherParams fromMap(HashMap<String,Double> map) {
 	//...
 	return new RandomSearcherParams(20, 30);
 }
}
