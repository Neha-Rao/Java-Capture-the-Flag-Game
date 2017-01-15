import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CaptureTheFlag{


  public static void main(String[] args){

    Field f = new Field();
    f.START_FROM_BASE = false;
    System.out.println(f.minX + "," + f.minY);


    /* --------------------------------------------- */
    /* create players in the game                    */

    Player p,q,r,s;

    int NUM_OF_EACH_PLAYER = 4;
    
    for(int i=0; i<NUM_OF_EACH_PLAYER; i+=1){
    	// create blue players
    	p = new Catcher(f, 1, "Catcher", 12, "blues", 'b', Math.random()*400+10, Math.random()*500+10);
    	q = new Seeker(f, 1, "Seeker", 12, "blues", 'b', Math.random()*400+10, Math.random()*500+10);
    	// create red players
    	r = new Catcher(f, 2, "Catcher", 7, "reds", 'r', Math.random()*400+410, Math.random()*500+10);
    	s = new Seeker(f, 2, "Seeker", 7, "reds", 'r', Math.random()*400+410, Math.random()*500+10);
    }


    /* ------------------------------------------- */
    /* play the game                               */

    boolean gameRunning = true;

    long iterations = 0;
    while( gameRunning ){
      iterations += 1;

      /* allow players to think about what to do and change directions */
      f.play();

      /* move all players */
      f.update();

      /* redraw all the players in their new positions */
      f.draw();
      


      /* give some message to display on the field */
      if(iterations < 1000){
        f.view.message("game on!");
      }else if(iterations < 2000){
        f.view.message("keeps on going...");
      }else{
        f.view.message("and going...");
      }

      // uncomment this if game is moving too fast , sleep for 10 ms
      //try{ Thread.sleep(10); } catch(Exception e) {}

      gameRunning = f.gameStillRunning();
      
    }
    while(true){
    	f.draw();
    	f.view.message(f.getWinner() + " won the game!");
    }

  }

}
