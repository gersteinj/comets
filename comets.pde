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

void setup() {
  size(500, 500);
  noStroke();
  colorMode(HSB, 360, 100, 100, 100);
}

void draw() {
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

