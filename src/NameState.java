import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


public class NameState extends BasicGameState{

	int stateID = -1;
	public NameState(int id){
		stateID = id;
	}
	TextField text = null;
	public void init(GameContainer gc, StateBasedGame stb)
			throws SlickException {
		text = new TextField(gc, gc.getDefaultFont(), 256, 275, 256, 25);
		text.setText(Main.username);
		text.setFocus(false);
	}

	
	public void render(GameContainer gc, StateBasedGame stb, Graphics g)
			throws SlickException {
		g.setColor(Color.white);
		g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
		
		g.setColor(Color.black);
		GameState.centerText("Username:", gc.getWidth()/2, 250, gc);
		if(text.getText().trim().equals("")){
			g.setColor(Color.gray);
		}
		GameState.centerText("ESC - BACK TO MAIN MENU", gc.getWidth()/2, 325, gc);
		GameState.centerText("PRESS ENTER TO CONTINUE", gc.getWidth()/2, 350, gc);
		text.render(gc, g);
	}

	
	public void update(GameContainer gc, StateBasedGame stb, int delta)
			throws SlickException {
		
		
		if((gc.getInput().isKeyPressed(Input.KEY_ENTER) && !text.getText().trim().equals(""))){
			Main.username = text.getText().trim();
			
			stb.enterState(Main.menustate);
		}else if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			stb.enterState(Main.menustate);
			text.setFocus(false);
		}
	}

	
	public int getID() {
		
		return stateID;
	}

}
