import java.io.IOException;
import java.net.URL;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class ConnectingState extends BasicGameState{
	int stateID = -1;
	public ConnectingState(int id){
		stateID = id;
	}
		
	public void init(GameContainer gc, StateBasedGame stb)
			throws SlickException {
		statebg = stb;
		text = new TextField(gc, gc.getDefaultFont(), 256, 300, 256, 25);
		text.setText("localhost");
		port = new TextField(gc, gc.getDefaultFont(), 256, 350, 256, 25);
		port.setText("31337");
	}
	
	static StateBasedGame statebg;
	
	TextField text = null;
	TextField port = null;
	static String info = "";
	public void render(GameContainer gc, StateBasedGame stb, Graphics g)
			throws SlickException {
		g.setColor(Color.white);
		g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
		g.setColor(Color.black);
		GameState.centerText("WORK IN PROGRESS MULTIPLAYER", gc.getWidth()/2, 100, gc);
		GameState.centerText("Press ESC to stop the client and exit to main menu", gc.getWidth()/2, 150, gc);
		GameState.centerText("Please enter the IP and port", gc.getWidth()/2, 175, gc);
		GameState.centerText(info, gc.getWidth()/2, 200, gc);
		text.render(gc, g);
		port.render(gc, g);
		
	}
	int tick = 0;
	
	public void enter(GameContainer gc, StateBasedGame stb){
		GameState.chat = new Chat();
	}
	
	public void update(GameContainer gc, StateBasedGame stb, int delta)
			throws SlickException {
		if(tick == 0 && !info.equals("")){
			info = "";
		}
		if(tick > 0) tick--;
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			text.setFocus(false);
			port.setFocus(false);
			info = "";
			stb.enterState(Main.menustate);
		}else if(gc.getInput().isKeyPressed(Input.KEY_ENTER)){
			text.setFocus(false);
			port.setFocus(false);
			info = "Attempting to connect to the server...";
			tick = 100;
			render(gc, stb, gc.getGraphics());
			try {
				
				//System.out.println(text.getText() + ", " + Integer.parseInt(port.getText()));
				Main.client.connect(5000, text.getText().trim(), Integer.parseInt(port.getText()), Integer.parseInt(port.getText()));
				Main.client.sendTCP(new Object());
				Packet p = new Packet();
				p.type = Type.JOIN;
				p.username = Main.username;
				Main.client.sendTCP(p);
				info = "Sending client info...";
				tick = 100;
			} catch (Exception e) {
				e.printStackTrace();
				info = "Failed to connect to the server!";
				tick = 200;
			}
		}
		
	}

	
	public int getID() {
		
		return stateID;
	}
	
	

}
