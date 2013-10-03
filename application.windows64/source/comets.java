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
For all comets to spawn at center, set startMode to 1.
 For comets to spawn randomly, set startMode to 0.
 */

int startMode = 1;

/*
For rainbow(all comets simultaneously), set coloration to 0
 For rainbow(individual comets), set coloration to 1
 To set color, set coloration to 2 and set your values
 */

int coloration = 1;
int makeItThisHue = 20;
int makeItThisSat = 30;
int makeItThisBright = 100;
int makeItThisAlpha = 3;

/*
Set the length of the comet tail here
 */
int cometTailLength = 40;

//declare a new ArrayList for the comets
ArrayList<Comet> comets = new ArrayList<Comet>();

public void setup() {
  size(500, 500);
  noStroke();
  colorMode(HSB, 360, 100, 100, 100);
}

public void draw() {
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
      sat = makeItThisSat;
      bright = makeItThisBright;
      alpha = makeItThisAlpha;
    }
    if (startMode == 0) {
      loc = new PVector(random(width), random(height));
    }
    else {
      loc = new PVector(width/2, height/2);
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
      if (coloration != 2) {
        fill(hue, 50, 100, 5);
      }
      if (coloration == 2) {
        fill(hue, sat, bright, alpha);
      }
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
    if ((loc.x > width*1.25f || loc.x < -width*.25f || loc.y > height * 1.25f || loc.y < -height*.25f)) {
      stillAlive = false;
    }
  }
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "comets" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
