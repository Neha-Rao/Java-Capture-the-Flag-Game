/* Player that looks for the enemy team's flag and tries to capture and 
 * bring back to its base.
 */

public class Seeker extends Player{

  int side;
  int[] flag = new int[2];
  int[] base = new int[2];

  public Seeker(Field f, int side, String name, int number, String team, char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = 0.2;
    this.speedY = 0.2;
    this.side = side;
    if(side == 1){
    	base[0] = f.getBase1Position()[0];
    	base[1] = f.getBase1Position()[1];
    } if(side == 2){
    	base[0] = f.getBase2Position()[0];
    	base[1] = f.getBase2Position()[1];
    }
  }

  @Override
  public void play(Field field){
	
	// if caught by a Catcher player, stop moving
	if(this.getCaught() == true){
		// drop the flag if we are carrying it
		field.dropFlag(this);
		return;
	}
	
    if(side == 1){
    	flag[0] = field.getFlag2Position()[0];
    	flag[1] = field.getFlag2Position()[1];
    } if(side == 2){
    	flag[0] = field.getFlag1Position()[0];
    	flag[1] = field.getFlag1Position()[1];
    }
	
    double xDistance=0;
    double yDistance=0;
	
    // check if we won the game
    if (field.winGame(this)) {
    	return;
	} else {
		xDistance = flag[0] - this.getX();
	    yDistance = flag[1] - this.getY();
	}
	double hyp = Math.hypot(xDistance, yDistance);
    this.x += Math.abs(this.speedX)*(xDistance/hyp);
    this.y += Math.abs(this.speedY)*(yDistance/hyp);
  }

  @Override
  public void update(Field field){}

}
