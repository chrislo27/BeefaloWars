import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;


public class GameState extends BasicGameState {

	int stateID = -1;
	public GameState(int i){
		stateID = i;
	}

	public static void centerText(String text, float x, float y, GameContainer gc){
		Font myFont = gc.getGraphics().getFont();
		gc.getGraphics().drawString(text, x - (myFont.getWidth(text) / 2), y);
	}

	public void init(GameContainer gc, StateBasedGame stb)
			throws SlickException {
		Main.resource = new Resource();
		in = gc.getInput();
	}
	//everything is stored here
	static ArrayList<Entity> entities = null;
	static ArrayList<FloatingText> hits = null;

	TiledMap map;
	static boolean makemap = false;
	static String map_name;
	float camx = 0;
	float camy = 0;

	MovementData move = new MovementData();
	static Chat chat = new Chat();
	Input in;
	TextField chatbox = null;
	public void render(GameContainer gc, StateBasedGame stb, Graphics g)
			throws SlickException {
		if(entities != null){
			if(entities.size() > 0){
				for(Entity e : entities){
					if(e.username.equals(Main.username)){
						camx = e.x - (gc.getWidth()/2);
						camy = e.y - (gc.getHeight()/2);
					}
				}

			}
		}
		g.translate(-camx, -camy);
		if(map != null){
			map.render(0, 0);
		}


		if(entities != null){
			if(entities.size() > 0){
				for(Entity e : entities){
					e.render(gc, g);
				}

			}
		}

		g.translate(camx, camy);
		chat.render(gc, g);
		g.setColor(Color.magenta);
		if(chatbox != null) chatbox.render(gc, g);
	}


	public void update(GameContainer gc, StateBasedGame stb, int delta)
			throws SlickException {
		ConnectingState.info = "";
		if(makemap){
			map = new TiledMap("images/maps/" + map_name + "/map.tmx", "images/maps/" + map_name + "/tiles");
		}
		if(chat != null) chat.update();
		if(Main.client.isConnected()){

			if(chatbox == null){
				if(in.isKeyPressed(Input.KEY_T)){
					chatbox = new TextField(gc, gc.getDefaultFont(), 25, gc.getHeight() - 30, gc.getWidth() - 100, 25);
					chatbox.setFocus(true);
				}
				if(in.isKeyDown(Input.KEY_W) != move.up){
					Packet p = new Packet();
					p.type = Type.MOVEMENT;
					p.username = Main.username;
					move.up = !move.up;
					p.movement = move;
					Main.client.sendTCP(p);
				}
				if(in.isKeyDown(Input.KEY_S) != move.down){
					Packet p = new Packet();
					p.type = Type.MOVEMENT;
					p.username = Main.username;
					move.down = !move.down;
					p.movement = move;
					Main.client.sendTCP(p);
				}
				if(in.isKeyDown(Input.KEY_A) != move.left){
					Packet p = new Packet();
					p.type = Type.MOVEMENT;
					p.username = Main.username;
					move.left = !move.left;
					p.movement = move;
					Main.client.sendTCP(p);
				}
				if(in.isKeyDown(Input.KEY_D) != move.right){
					Packet p = new Packet();
					p.type = Type.MOVEMENT;
					p.username = Main.username;
					move.right = !move.right;
					p.movement = move;
					Main.client.sendTCP(p);
				}

				if(in.isKeyPressed(Input.KEY_ESCAPE)){
					Packet p = new Packet();
					p.username = Main.username;
					p.type = Type.LEAVE;
					Main.client.sendTCP(p);
					stb.enterState(Main.menustate);
				}
			}else{
				chatbox.setFocus(true);
				if(in.isKeyPressed(Input.KEY_ESCAPE)){
					chatbox = null;
				}
				if(in.isKeyPressed(Input.KEY_ENTER)){
					if(chatbox.getText().trim().equals("")){
						chatbox = null;
					}else{
						Packet p = new Packet();
						p.type = Type.NEWCHAT;
						p.textColour = Type.WHITE;
						if(chatbox.getText().startsWith("@")){
							if(chatbox.getText().substring(1, 2).equals("w")){
								p.textColour = Type.RAINBOW;
							}
							p.username = "<" + Main.username + ">: " + chatbox.getText().substring(2);
						}else{
							p.username = "<" + Main.username + ">: " + chatbox.getText();
						}
						Main.client.sendTCP(p);
						chatbox = null;
					}
				}
			}
		}
		if(!Main.client.isConnected()){
			MessageState.message = "Lost connection to server";
			stb.enterState(Main.messagestate);
		}
	}


	public int getID() {
		return stateID;
	}

}
