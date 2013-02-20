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

package adrianton.gloptat.alg.pso;

public class SocialNetwork
{
 private boolean[][] m;

 SocialNetwork(int n)
 {
  int i, j;
  m = new boolean[n][];
  for(i=0;i<n;i++)
   m[i] = new boolean[n];
 }

 void set(int i, int j)
 {
  m[i][j] = true;
  m[j][i] = true;
 }

 void unSet(int i, int j)
 {
  m[i][j] = false;
  m[j][i] = false;
 }

 boolean get(int i, int j)
 {
  return m[i][j];
 }

 int size()
 {
  return m.length;
 }

 int[] getFriends(int i)
 {
  int nf = 0;

  int j;
  for(j=0;j<m.length;j++)
   if(m[i][j]) nf++;
  
  int[] ret = new int[nf];
  
  int p = 0;
  for(j=0;j<m.length;j++)
   if(m[i][j]) { ret[p] = j; p++; }

  return ret;
 }

 boolean[] getLine(int i)
 {
  return m[i];
 }

 SocialNetwork join(SocialNetwork that)
 {
  SocialNetwork ret = new SocialNetwork(m.length);

  return ret;
 }

 static SocialNetwork Star(int n)
 {
  SocialNetwork ret = new SocialNetwork(n);

  int i;
  for(i=0;i<n;i++)
   ret.set(0,i);

  return ret;
 }

 static SocialNetwork Ring(int n)
 {
  SocialNetwork ret = new SocialNetwork(n);

  int i;
  for(i=0;i<n-1;i++)
   ret.set(i,i+1);

  ret.set(n-1,0);

  return ret;
 }

 static SocialNetwork Total(int n)
 {
  SocialNetwork ret = new SocialNetwork(n);

  int i, j;
  for(i=0;i<n;i++)
   for(j=i;j<n;j++)
    ret.set(i,j);

  return ret;
 }

 static SocialNetwork Random(int n, int grad)
 {
  SocialNetwork ret = new SocialNetwork(n);

  int i, j, tmp;
  for(i=0;i<n;i++)
  {
   j = 0;
   while(j < grad)
   {
    tmp = (int)(Math.random() * n);
    if(!ret.get(i,tmp))
    { ret.set(i,tmp); grad++; }
   }
  }
  
  return ret;
 }

 static SocialNetwork Islands(int n)
 {
  SocialNetwork ret = new SocialNetwork(n);

  int st, en;
  int i, j;

  st = 0; en = n/2;
  for(i=st;i<en;i++)
   for(j=i;j<en;j++)
    ret.set(i,j);

  st = n/2; en = n;
  for(i=st;i<en;i++)
   for(j=i;j<en;j++)
    ret.set(i,j);

  ret.set(0,n-1);

  return ret;
 }
}