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

public class Fasten 
{
 public static String toStr(double[] inum)
 {
  String ret = "";
  for(double a: inum) ret += a + " ";
  return ret;
 }
//------------------------------------------------------------------------------ 
 public static String toStr(int[] inum)
 {
  String ret = "";
  for(int a: inum) ret += a + " ";
  return ret;
 }
//------------------------------------------------------------------------------
 public static double avg(double[] inum)
 {
  double ret = 0;
  for(double a: inum) ret += a;
  return ret/inum.length;
 }
//------------------------------------------------------------------------------
 public static double sd(double[] inum)
 {
  double s = 0, av = avg(inum);
  for(double a: inum) s += (a-av)*(a-av);
  return Math.sqrt(s/inum.length);
 }
//------------------------------------------------------------------------------
 public static double min(double[] inum)
 {
  double ret = inum[0];
  for(double a: inum) ret = Math.min(a,ret);
  return ret;
 }
//------------------------------------------------------------------------------
 public static double max(double[] inum)
 {
  double ret = inum[0];
  for(double a: inum) ret = Math.max(a,ret);
  return ret;
 }
//------------------------------------------------------------------------------
 public static double round(double num, int p)
 {
  int z = (int)Math.pow(10,p);
  double result = num * z;
  result = Math.round(result);
  result = result / z;
  return result;
 }
}
