import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class MessageState extends BasicGameState{
	int stateID = -1;
	public MessageState(int id){
		stateID = id;
	}
		
	public void init(GameContainer gc, StateBasedGame stb)
			throws SlickException {
		
	}

	
	public void render(GameContainer gc, StateBasedGame stb, Graphics g)
			throws SlickException {
		g.setColor(Color.white);
		g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
		
		g.setColor(Color.black);
		GameState.centerText(message, gc.getWidth()/2, gc.getHeight()/2 - 75, gc);
		GameState.centerText("Press ENTER to return to main menu", gc.getWidth()/2, gc.getHeight()/2 + 100, gc);
		
	}

	public static String message = "";
	
	public void update(GameContainer gc, StateBasedGame stb, int delta)
			throws SlickException {
		
		if(gc.getInput().isKeyPressed(Input.KEY_ENTER)){
			stb.enterState(Main.menustate);
		}
	}

	
	public int getID() {
		
		return stateID;
	}
	
	

}
