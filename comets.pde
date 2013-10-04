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


void setup() {
  size(500, 500);
  noStroke();
  colorMode(HSB, 360, 100, 100, 100);
  if (startMode <= 1) {
    origin = new PVector(width/2, height/2);
    originVel = new PVector(0, 0);
    originAcc = new PVector(0, 0);
  }
  if (startMode == 2) {
    origin = new PVector(width/2,height/2);
    originVel = new PVector(0, 0);
    originAcc = new PVector(random(-.2, .2), random(-.2, .2));
  }
}

void draw() {
  originVel.limit(1);
  //Move the origin if appropriate
  originVel.add(originAcc);
  origin.add(originVel);
  if (startMode == 2) {
    originAcc = new PVector(random(-.2, .2), random(-.2, .2));
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

