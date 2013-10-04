import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class comets extends PApplet {

/*
For comets to spawn randomly, set startMode to 0.
 For all comets to spawn at center, set startMode to 1.
 For wandering origin, set startMode to 2
 */

int startMode =2;

/*
For rainbow(all comets simultaneously), set coloration to 0
 For rainbow(individual comets), set coloration to 1
 To set color, set coloration to 2 and set your values
 */

int coloration = 1;
int makeItThisHue = 20;
int makeItThisSat = 70;
int makeItThisBright = 100;
int makeItThisAlpha = 10;

/*
Set the length of the comet tail here
 */
int cometTailLength = 40;

//declare a new ArrayList for the comets
ArrayList<Comet> comets = new ArrayList<Comet>();

//create a PVector for the origin and more to move it
PVector origin;
PVector originVel;
PVector originAcc;


public void setup() {
  size(500, 500);
  noStroke();
  colorMode(HSB, 360, 100, 100, 100);
  if (startMode <= 1) {
    origin = new PVector(width/2, height/2);
    originVel = new PVector(0, 0);
    originAcc = new PVector(0, 0);
  }
  if (startMode == 2) {
    origin = new PVector(random(width), random(height));
    originVel = new PVector(0, 0);
    originAcc = new PVector(random(-.2f, .2f), random(-.2f, .2f));
  }
}

public void draw() {
  //Move the origin if appropriate
  originVel.add(originAcc);
  origin.add(originVel);
  if (startMode == 2) {
    originAcc = new PVector(random(-.2f, .2f), random(-.2f, .2f));
  }

  //Make sure the origin doesn't run away
  if (origin.x > width) {
    originVel.x = -abs(originVel.x);
  }
  if (origin.x < 0) {
    originVel.x = abs(originVel.x);
  }
  if (origin.y > height) {
    originVel.y = -abs(originVel.y);
  }
  if (origin.y < 0) {
    originVel.y = abs(originVel.y);
  }

  //print the size of the ArrayList to make sure it doesn't grow unreasonably large
  println(comets.size());
  //each frame, add a new comet to the ArrayList
  comets.add(new Comet(cometTailLength));
  background(0);
  //Iterate backwards through the ArrayList
  for (int i = comets.size()-1; i >= 0; i --) {
    //c will act as a placeholder for the comet currently being worked with
    Comet c = comets.get(i);
    c.display();
    c.update();
    c.edgeCheck();
    //check the value of the stillAlive variable, and remove if comet is dead
    if (!c.stillAlive) {
      println("DIE!");
      comets.remove(i);
    }
  }
}

class Comet {
  int tailLength;
  PVector loc;
  PVector vel;
  PVector acc;
  PVector[] locs;
  int hue;
  int sat;
  int bright;
  int alpha;
  boolean stillAlive;

  Comet(int _tailLength) {
    tailLength = _tailLength;
    stillAlive = true;
    if (coloration == 0 || coloration == 1) {
      hue = frameCount%360;
    }
    if (coloration == 2) {
      hue = makeItThisHue;
    }
    sat = makeItThisSat;
    bright = makeItThisBright;
    alpha = makeItThisAlpha;

    if (startMode == 0) {
      loc = new PVector(random(width), random(height));
    }
    else {
      loc = new PVector(origin.x,origin.y);
    }
    vel = new PVector(0, 0);
    acc = new PVector(random(-.5f, .5f), random(-.5f, .5f));
    locs = new PVector[tailLength];
    for (int i = 0; i < locs.length; i++) {
      locs[i] = new PVector(loc.x, loc.y);
    }
  }

  public void display() {

    for (int i = 0; i < locs.length; i++) {

      fill(hue, sat, bright, alpha);

      ellipse(locs[i].x, locs[i].y, i, i);
    }
  }

  public void update() {
    if (coloration == 0) {
      hue = frameCount%360;
    }
    vel.add(acc);
    loc.add(vel);
    acc = new PVector(random(-.5f, .5f), random(-.5f, .5f));
    for (int i = 0; i < locs.length-1; i++) {
      locs[i] = locs[i+1];
    }
    locs[locs.length-1] = new PVector(loc.x, loc.y);
  }
  public void edgeCheck() {
    if ((loc.x > width*2 || loc.x < -width || loc.y > height * 2 || loc.y < -height)) {
      stillAlive = false;
    }
  }
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "comets" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
