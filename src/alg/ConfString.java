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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import def.Dbo;

public class ConfString {
 String s;
 
 public ConfString(String s) {
  this.s = s;
 }
 
 public HashMap<String,Double> toMap() {
  HashMap<String,Double> ret = new HashMap<String,Double>();
  
  String[] tmp = s.split("\n");
  for(String s: tmp) {
   s += ";";
   s = s.substring(0,s.indexOf(';'));
   
   int pe = s.indexOf(':');
   if(pe > -1) {
    String key, val;
    key = s.substring(0,pe).trim();
    val = s.substring(pe+1).trim();
    
    try {
     double tmpv = Double.parseDouble(val);
     ret.put(key, tmpv);
    } catch (NumberFormatException e) {
    	double tmpv = 0;
    	if(val.equals("true")) tmpv = 1;
    	ret.put(key, tmpv);
    }
   }
  }
  return ret;
 }
 
 public void toFile(String fnam) {
  FileWriter fstream;
  try {
   fstream = new FileWriter(fnam);
   BufferedWriter out = new BufferedWriter(fstream);
   out.write(s);
  } 
  catch (IOException e) {
   e.printStackTrace();
  }
 }
 
 public static ConfString fromFile(String fnam) {
  try {
   return new ConfString(new Scanner(new File(fnam),"UTF-8").useDelimiter("\\A").next());
  } 
  catch (FileNotFoundException e) {
   e.printStackTrace();
   return null;
  }
 }
}
