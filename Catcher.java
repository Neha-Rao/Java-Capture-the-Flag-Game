/* Player that looks for players on the opposite team and tries to catch them
 * and bring them to its jail. After bringing an enemy player to jail, it respawns at its base
 * and tries to catch another player.
 */

import java.util.ArrayList;

public class Catcher extends Player{

  int side;
  int[] jail = new int[2];
  int[] base = new int[2];
  Player enemy;
  ArrayList<Entity> enemies;
  int counter=0;

  public Catcher(Field f, int side, String name, int number, String team, char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = 0.5;
    this.speedY = 0.5;
    this.side = side;
    if(this.side == 1){
    	jail[0] = f.getJail1Position()[0];
    	jail[1] = f.getJail1Position()[1];
    	base[0] = f.getBase1Position()[0];
    	base[1] = f.getBase1Position()[1];
    	this.enemies = f.getTeam2();
    } if(this.side == 2){
    	jail[0] = f.getJail2Position()[0];
    	jail[1] = f.getJail2Position()[1];
    	base[0] = f.getBase2Position()[0];
    	base[1] = f.getBase2Position()[1];
    	this.enemies = f.getTeam1();
    }
  }

  @Override
  public void play(Field field){
    
    if(enemies.size()==0){
    	return;
    } else if (counter<enemies.size()){
      enemy = (Player) enemies.get(counter);
    } else {
    	counter=0;
    	enemy = (Player) enemies.get(counter);
    }
    
    // don't try to catch this player if it's already in jail
    if(enemy.isInJail(jail[0], jail[1])){
    	counter+=1;
    	return;
    }
    
    // don't try to catch other catchers to keep behavior simple
    if(enemy.getName().equals("Catcher")){
    	counter+=1;
    	return;
    }
    
    double xDistance = enemy.getX() - this.getX();
    double yDistance = enemy.getY() - this.getY();
    double hyp = Math.hypot(xDistance, yDistance);

    // if the catcher caught the opponent, bring them to jail
    if(field.catchOpponent(this, enemy)){
    	enemy.setCaught(true);
    	xDistance = jail[0] - this.getX();
    	yDistance = jail[1] - this.getY();
    
	    hyp = Math.hypot(xDistance, yDistance);
	    this.x += Math.abs(this.speedX)*(xDistance/hyp);
	    this.y += Math.abs(this.speedY)*(yDistance/hyp);
	    enemy.x += Math.abs(this.speedX)*(xDistance/hyp);
	    enemy.y += Math.abs(this.speedY)*(yDistance/hyp);
	    if(enemy.isInJail(jail[0], jail[1])){
	    	enemy.speedX = 0;
	    	enemy.speedY = 0;
	    	enemy.x = jail[0];
	    	enemy.y = jail[1];
	    	this.x = base[0];
	    	this.y = base[1];
	    	counter+=1;
	    }
	    return;
    }
    // otherwise continue chasing
    this.x += Math.abs(this.speedX)*(xDistance/hyp);
    this.y += Math.abs(this.speedY)*(yDistance/hyp);
  }

  @Override
  public void update(Field field){}
}
