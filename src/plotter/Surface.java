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

package plotter;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import def.Dbo;

public class Surface 
{
 public static int handle;
 
 public static void assemble(Displayer outer)
 {
  int i, j;
  float px, py;
  float z1, z2, z3, z4;
  float tmp;

  Color col;

  float spx = -outer.rezx/2;
  float spy = -outer.rezy/2;

  handle = GL11.glGenLists(1);
  GL11.glNewList(handle,GL11.GL_COMPILE);
  
  GL11.glBegin(GL11.GL_QUADS);
  
  px = spx;
  for(i=0;i<outer.rezx-1;i++)
  {
   py = spy;
   for(j=0;j<outer.rezy-1;j++)
   {
    z1 = (float)outer.val[i][j];
    z2 = (float)outer.val[i+1][j];
    z3 = (float)outer.val[i][j+1];
    z4 = (float)outer.val[i+1][j+1];
    
    tmp = (float)(1-((outer.val[i][j]-outer.minc) / (outer.maxc-outer.minc)));
    tmp = tmp * 66/100 + 34/100;
    col = new Color(Color.HSBtoRGB(tmp, 0.7f, 0.85f));
    GL11.glColor3f((float)(col.getRed()/255f),(float)(col.getGreen()/255f),(float)(col.getBlue()/255f));

    GL11.glVertex3f(px,   py,   z1);
    
    tmp = (float)(1-((outer.val[i][j+1]-outer.minc) / (outer.maxc-outer.minc)));
    tmp = tmp * 66/100 + 34/100;
    col = new Color(Color.HSBtoRGB(tmp, 0.7f, 0.85f));
    GL11.glColor3f((float)(col.getRed()/255f),(float)(col.getGreen()/255f),(float)(col.getBlue()/255f));
    
    GL11.glVertex3f(px,   py+1, z3);
    
    tmp = (float)(1-((outer.val[i+1][j+1]-outer.minc) / (outer.maxc-outer.minc)));
    tmp = tmp * 66/100 + 34/100;
    col = new Color(Color.HSBtoRGB(tmp, 0.7f, 0.85f));
    GL11.glColor3f((float)(col.getRed()/255f),(float)(col.getGreen()/255f),(float)(col.getBlue()/255f));
    
    GL11.glVertex3f(px+1, py+1, z4);
    
    tmp = (float)(1-((outer.val[i+1][j]-outer.minc) / (outer.maxc-outer.minc)));
    tmp = tmp * 66/100 + 34/100;
    col = new Color(Color.HSBtoRGB(tmp, 0.7f, 0.85f));
    GL11.glColor3f((float)(col.getRed()/255f),(float)(col.getGreen()/255f),(float)(col.getBlue()/255f));
    
    GL11.glVertex3f(px+1, py,   z2);

    py += 1;
   }
   px += 1;
  }

  GL11.glEnd();
  GL11.glEndList(); 
 }
 
 static void call()
 {
  GL11.glCallList(handle);
 }
 
 public static void release()
 {
  GL11.glDeleteLists(handle,1);
 }
}
