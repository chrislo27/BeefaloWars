import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;



public class MainMenuState extends BasicGameState {

	int stateID = -1;
	MainMenuState(int id){
		stateID = id;
	}
	Input in;
	static StateBasedGame stbased;
	public void init(GameContainer gc, StateBasedGame stb)
			throws SlickException {
		in = gc.getInput();
	}

	
	public void render(GameContainer gc, StateBasedGame stb, Graphics g)
			throws SlickException {
		g.setColor(Color.white);
		g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
		g.setColor(Color.black);
		GameState.centerText("TITLE HERE", gc.getWidth()/2, 50, gc);
		g.drawString("1) Connect to server", 100, 250);
		g.drawString("2) Change username", 100, 275);
		g.drawString("3) Exit", 100, 300);
	}

	
	public void update(GameContainer gc, StateBasedGame stb, int delta)
			throws SlickException {
		
		if(in.isKeyPressed(Input.KEY_1)){
			stb.enterState(Main.clientstate);
		}else if(in.isKeyPressed(Input.KEY_2)){
			stb.enterState(Main.namestate);
		}else if(in.isKeyPressed(Input.KEY_3)){
			System.exit(0);
		}
	}

	
	public int getID() {
		
		return stateID;
	}

}
