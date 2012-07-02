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

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import def.Dbo;

//still needs some work
public class Displayer 
{
 int rezx = 0, rezy = 0;
 double[][] dom;
 double[][] val;
 double[][] pointers;
 boolean[] pointerstatus;
 int[] pointerspec;
 float[] pointerscale;
 double[] best;
 public boolean pointersactive = false;
 public boolean bestactive = false;
 float pasx = 1, pasy = 1, pasz = 1;
 float rotz = 0, rotx = 0, roty = 0;
 float posz = -40, posx = 0, posy = 0;
 public double minc, maxc;
 
 boolean showAxis;
 boolean showBox;

 public boolean needsrepaint = false;
 public boolean needsresurface = false;
 
 public Displayer()
 {
  Display.setTitle("Displayer");
 }
//------------------------------------------------------------------------------
 public void incPas(float ip)
 {
  if(pasx+ip > 0.1 && pasy+ip > 0.1 && pasz+ip > 0.1)
  {
   pasx += ip; pasy += ip; pasz += ip;
  }
 }
//------------------------------------------------------------------------------
 public void mulPas(float ip)
 {
  if(pasx*ip > 0.1 && pasy*ip > 0.1 && pasz*ip > 0.1)
  {
   pasx *= ip; pasy *= ip; pasz *= ip;
  }
 }
//------------------------------------------------------------------------------
 public void incRotZ(float ir) { rotz += ir; }
 public void incRotY(float ir) { roty += ir; }
 public void incRotX(float ir) { rotx += ir; }
 
 public void incPos(float ax, float ay, float az) 
 { 
  posx += ax; 
  posy += ay;
  posz += az;
 }
//------------------------------------------------------------------------------
 public void setRotZ(float ir) { rotz = ir; }
 public void setRotY(float ir) { roty = ir; }
 public void setRotX(float ir) { rotx = ir; }
//------------------------------------------------------------------------------
 public void setBest(double[] ib) { best = ib; }
 public void setAxisState(boolean b) { showAxis = b; }
//------------------------------------------------------------------------------
 public void pointers(double[][] ip)
 {
  pointers = ip;
 }
//------------------------------------------------------------------------------
 public void pointersActive(boolean[] ipa)
 {
  pointerstatus = ipa;
 }
//------------------------------------------------------------------------------
 private void callSurface()
 {
  GL11.glLoadIdentity();
  
  GL11.glTranslatef(posx,posy,posz);
  
  GL11.glRotatef(roty, 1, 0, 0);
  GL11.glRotatef(rotz, 0, 0, 1);
  
  GL11.glScaled(0.3,0.3,0.3);
  GL11.glScaled(pasz,pasz,pasz);
  
  Surface.call();
 }
 
 public void suprafata()
 {
  callSurface();
 }
//------------------------------------------------------------------------------
 private void box() //convert to display list
 {
  float spx = -rezx/2f;
  float spy = -rezy/2f;
  float epx = spx+rezx;
  float epy = spy+rezy;

  GL11.glColor3f(1f, 1f, 1f);
  GL11.glLineWidth(2);
  GL11.glBegin(GL11.GL_LINES);
  
  GL11.glVertex3d(spx, spy, maxc); GL11.glVertex3d(epx, spy, maxc);
  GL11.glVertex3d(epx, spy, maxc); GL11.glVertex3d(epx, epy, maxc);
  GL11.glVertex3d(epx, epy, maxc); GL11.glVertex3d(spx, epy, maxc);
  GL11.glVertex3d(spx, epy, maxc); GL11.glVertex3d(spx, spy, maxc);
  
  GL11.glVertex3d(spx, spy, minc); GL11.glVertex3d(epx, spy, minc);
  GL11.glVertex3d(epx, spy, minc); GL11.glVertex3d(epx, epy, minc);
  GL11.glVertex3d(epx, epy, minc); GL11.glVertex3d(spx, epy, minc);
  GL11.glVertex3d(spx, epy, minc); GL11.glVertex3d(spx, spy, minc);
  
  GL11.glVertex3d(spx, spy, minc); GL11.glVertex3d(spx, spy, maxc);
  GL11.glVertex3d(epx, spy, minc); GL11.glVertex3d(epx, spy, maxc);
  GL11.glVertex3d(epx, epy, minc); GL11.glVertex3d(epx, epy, maxc);
  GL11.glVertex3d(spx, epy, minc); GL11.glVertex3d(spx, epy, maxc);
  
  GL11.glEnd();
 }
//------------------------------------------------------------------------------
 private void pointers()
 {
  if(pointers != null)
  {
  float spx = -rezx/2f;
  float spy = -rezy/2f;
  float tmpx, tmpy;
  float rapx, rapy;
  int tmpi, tmpj;
  
  rapx = rezx/(float)(dom[0][1]-dom[0][0]);
  rapy = rezy/(float)(dom[1][1]-dom[1][0]);
  
  int i;
  for(i=0;i<pointers.length;i++)
  {
   tmpx = spx + (float)(pointers[i][0] - dom[0][0]) * rapx;
   tmpy = spy + (float)(pointers[i][1] - dom[1][0]) * rapy;

   tmpi = (int)((pointers[i][0] - dom[0][0])*rapx); //ar trebui sa interpolez sau...
   tmpj = (int)((pointers[i][1] - dom[1][0])*rapy);
   
   tmpi = Math.min(Math.max(0,tmpi),rezx-1);
   tmpj = Math.min(Math.max(0,tmpj),rezy-1);
   
   if(pointers[i][0]>dom[0][0] && pointers[i][0]<dom[0][1] &&
      pointers[i][1]>dom[1][0] && pointers[i][1]<dom[1][1])
    
   GL11.glPushMatrix();
   GL11.glTranslatef(tmpx,tmpy,(float)(val[tmpi][tmpj]));
   GL11.glScaled(1.1,1.1,0.5);
   Pointer.call();
   GL11.glPopMatrix();
  }
  }
 }
 
 /*
 public void pointers2()
 {
  float tmpx, tmpy;
  float rapx, rapy;
  int tmpi, tmpj;
  
  if(pointersactive)
  {
   rapx = rezx/(float)(dom[0][1]-dom[0][0]);
   rapy = rezy/(float)(dom[1][1]-dom[1][0]);

   GL11.glColor3f(0.94f, 0.94f, 0.94f);
   int i;
   for(i=0;i<pointers.length;i++)
   {
    if(pointersstatus[i])
    {
     tmpx = spx + (float)(pointers[i][0] - dom[0][0]) * pasx * rapx;
     tmpy = spy + (float)(pointers[i][1] - dom[1][0]) * pasy * rapy;

     tmpi = (int)((pointers[i][0] - dom[0][0])*rapx);
     tmpj = (int)((pointers[i][1] - dom[1][0])*rapy);
     //System.out.println(tmpi+" "+tmpj);
     if(pointers[i][0]>dom[0][0] && pointers[i][0]<dom[0][1] &&
        pointers[i][1]>dom[1][0] && pointers[i][1]<dom[1][1])
      pointer(tmpx,tmpy,(float)(val[tmpi][tmpj]*pasz));
    }
   }
  }
  else
   if(bestactive)
   {
    rapx = rezx/(float)(dom[0][1]-dom[0][0]);
    rapy = rezy/(float)(dom[1][1]-dom[1][0]);

    tmpx = spx + (float)(best[0] - dom[0][0]) * pasx * rapx;
    tmpy = spy + (float)(best[1] - dom[1][0]) * pasy * rapy;
    //tmpx = spx+10*pasx; tmpy = spy+10*pasy;
    //System.out.println(best[0]+" "+best[1]); //corect
    GL11.glColor3f(1f, 1f, 1f);
    tmpi = (int)((best[0] - dom[0][0])*rapx);
    tmpj = (int)((best[1] - dom[1][0])*rapy);
    //if(tmpi>=0 && tmpi<rezx && tmpj>=0 && tmpj<rezy)
    if(best[0]>dom[0][0] && best[0]<dom[0][1] &&
       best[1]>dom[1][0] && best[1]<dom[1][1])
     pointer(tmpx,tmpy,(float)val[tmpi][tmpj]*pasz);
   }
 }*/
//------------------------------------------------------------------------------
 public void setDom(double[][] idom)
 {
  dom = idom;
 }
//------------------------------------------------------------------------------
 public void setVal(double[][] val)
 {
  this.val = val;
  this.rezx = val.length;
  this.rezy = val[0].length;
   
  needsresurface = true;
 }
 
 public void initGL() 
 {
  try 
  {
   //Display.setFullscreen(true);
   Display.setDisplayMode(new DisplayMode(800, 600));
   Display.create();
  } catch(Exception e) { Dbo.out("OpenGL Exception"); }
  
  //Display.setVSyncEnabled(true);
  
  final int dw = 800; //Display.getDisplayMode().getWidth();
  final int dh = 600; //Display.getDisplayMode().getHeight();
  
  glViewport (0, 0, dw, dh);
  //glViewport((Display.getDisplayMode().getWidth()-680)/2,(Display.getDisplayMode().getHeight()-680)/2,680,680);
  glMatrixMode (GL_PROJECTION);
  glLoadIdentity();
  
  //GL11.glOrtho(0, 20, 20, 0, 5, 500);
  //GL11.glOrtho(-4, 24, 24, -4, 5, 500);
  //GL11.glOrtho(-10, 30, 30, -10, 5, 500);
  
  GLU.gluPerspective (45.0f, (float)dw/(float)dh, 1.0f, 100.0f);
  //GL11.glTranslatef(-10,-10,0);
  
  glMatrixMode (GL_MODELVIEW);
  glLoadIdentity ();
 
  glClearColor (0.0f, 0.0f, 0.0f, 0.5f);
  glClearDepth (1.0f);
  glDepthFunc (GL_LEQUAL);
  glEnable (GL_DEPTH_TEST);
  glShadeModel (GL_SMOOTH);
  glHint (GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
 }
 
 private void exitProgram()
 {
  Dbo.out("Exiting...");
  Surface.release();
  Pointer.release();
  //releaseSurface(dlhandle);
  Display.destroy();
  System.exit(0);
 }
 
 private void render()
 {
  suprafata();
  pointers();
  //axe();
  box();
 }
 
 private boolean mouse()
 {
  boolean r = false;
  
  if(Mouse.isButtonDown(0))
  {
   incRotZ((float)(Mouse.getDX())/-2f);
   incRotY((float)(Mouse.getDY())/-2f);
   
   r = true;
  }
  else if(Mouse.isButtonDown(1))
  {
    incPos(0,(float)(Mouse.getDY())/5f,0);
   r = true;
  }
  
  double tmp = Mouse.getDWheel();
  if(tmp != 0)
  {
   incPas((float)tmp/1000f);
   r = true;
  }
  
  return r;
 }
 
 public void loop()
 {
  GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
  render();
  Display.update();
  GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
  render();
  Display.update();
  
  while(true)
  {
   if(needsresurface) { needsresurface = false; Surface.release(); Surface.assemble(this); } 
   //GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
   
   if(mouse() || needsrepaint) //needs refresh
   { 
    needsrepaint = false;
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); render(); Display.update(); Display.sync(40); 
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); render(); 
   } 
   //adv();
   //render();
   
   Display.update();
   Display.sync(40);

   if(Display.isCloseRequested()) exitProgram();
  }
 }
 
 public void start()
 {
  initGL();
  Surface.assemble(this);
  Pointer.assemble();
 }
}
