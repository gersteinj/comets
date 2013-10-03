class Comet {
  int tailLength;
  PVector loc;
  PVector vel;
  PVector acc;
  PVector[] locs;
  int hue;
  boolean stillAlive;

  Comet(int _tailLength) {
    tailLength = _tailLength;
    stillAlive = true;
    hue = frameCount%360;
    if (startMode == 0) {
      loc = new PVector(random(width), random(height));
    }
    else {
      loc = new PVector(width/2, height/2);
    }
    vel = new PVector(0, 0);
    acc = new PVector(random(-.5, .5), random(-.5, .5));
    locs = new PVector[tailLength];
    for (int i = 0; i < locs.length; i++) {
      locs[i] = new PVector(loc.x, loc.y);
    }
  }

  void display() {

    for (int i = 0; i < locs.length; i++) {
      fill(hue, 50, 100, 15);
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
    if ((loc.x > width*1.25 || loc.x < -width*.25 || loc.y > height * 1.25 || loc.y < -height*.25)) {
      stillAlive = false;
    }
  }
}

