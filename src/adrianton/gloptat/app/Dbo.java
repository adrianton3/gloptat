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

public class Dbo {
 public static boolean on = true;
 public static boolean err = true;
 static long lasttime = System.nanoTime();

 public static void out(String istr) {
  //if(on) //bypass? why?
   System.out.println(istr);
  //and maybe some log in some file
 }
 
 public static void err(String istr) {
  if(err)
   System.out.println("Err: " + istr);
  //and maybe some error log
 }

 public static void start() {
  lasttime = System.nanoTime();
 }

 public static long elapsed() {
  return System.nanoTime() - lasttime;
 }
}