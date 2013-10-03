ArrayList<Comet> comets = new ArrayList<Comet>();

void setup() {
  size(500, 500);
  noStroke();
  //  fill(255,30);
  colorMode(HSB, 360, 100, 100, 100);
}

void draw() {
  comets.add(new Comet());
  background(0);
  for (int i = comets.size()-1; i >= 0; i --) {
    Comet c = comets.get(i);
    c.display();
    c.update();
    if (!c.stillAlive) {
      comets.remove(i);
    }
  }
}

class Comet {
  PVector loc;
  PVector vel;
  PVector acc;
  PVector[] locs;
  int hue;
  boolean stillAlive;

  Comet() {
    stillAlive = true;
    hue = frameCount%360;
    loc = new PVector(width/2, height/2);
    vel = new PVector(0, 0);
    acc = new PVector(random(-.5, .5), random(-.5, .5));
    locs = new PVector[20];
    for (int i = 0; i < locs.length; i++) {
      locs[i] = new PVector(random(width), random(height));
    }
  }

  void display() {

    for (int i = 0; i < locs.length; i++) {
      fill(hue, 50, 100, 20);
      ellipse(locs[i].x, locs[i].y, i, i);
    }
  }

  void update() {
    vel.add(acc);
    loc.add(vel);
    acc = new PVector(random(-.5, .5), random(-.5, .5));
    for (int i = 0; i < locs.length-1; i++) {
      locs[i] = locs[i+1];
    }
    locs[locs.length-1] = new PVector(loc.x, loc.y);
  }
  void edgeCheck() {
    if ((loc.x > width*2 || loc.x < -width) && (loc.y > height * 2 || loc.y < -height)) {
      stillAlive = false;
    }
  }
}

