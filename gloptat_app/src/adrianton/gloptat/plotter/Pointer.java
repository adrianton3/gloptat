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

package adrianton.gloptat.plotter;

import org.lwjgl.opengl.GL11;

public class Pointer 
{
 static int handle;
 
 static void assemble()
 {
  final float tmpm = 1;
  final float tmpm2 = 1/3;
  final float tmpm3 = 7;
  
  handle = GL11.glGenLists(1);
  GL11.glNewList(handle,GL11.GL_COMPILE);

  GL11.glBegin(GL11.GL_TRIANGLES);
  GL11.glColor3f(1,1,1);
  
  GL11.glVertex3f(0, 0, tmpm2);
  GL11.glVertex3f(-tmpm,-tmpm, tmpm3);
  GL11.glVertex3f(-tmpm, tmpm, tmpm3);

  GL11.glVertex3f(0, 0, tmpm2);
  GL11.glVertex3f(-tmpm, tmpm, tmpm3);
  GL11.glVertex3f( tmpm, tmpm, tmpm3);
  
  GL11.glVertex3f(0, 0, tmpm2);
  GL11.glVertex3f(tmpm, tmpm, tmpm3);
  GL11.glVertex3f(tmpm,-tmpm, tmpm3);
  
  GL11.glVertex3f(0, 0, tmpm2);
  GL11.glVertex3f( tmpm, -tmpm, tmpm3);
  GL11.glVertex3f(-tmpm, -tmpm, tmpm3);
  GL11.glEnd();

  GL11.glBegin(GL11.GL_QUADS);
  GL11.glVertex3f( tmpm, tmpm, tmpm3);
  GL11.glVertex3f( tmpm,-tmpm, tmpm3);
  GL11.glVertex3f(-tmpm,-tmpm, tmpm3);
  GL11.glVertex3f(-tmpm, tmpm, tmpm3);
  GL11.glEnd(); 
  
  GL11.glEndList();
 }
 
 static void call()
 {
  GL11.glCallList(handle);
 }
 
 static void release()
 {
  GL11.glDeleteLists(handle,1);
 }
}