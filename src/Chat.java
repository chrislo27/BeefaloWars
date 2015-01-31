import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


public class Chat {
	int maxShown = 5;
	ArrayList<String> chat = new ArrayList(maxShown);
	ArrayList<Type> chatColour = new ArrayList(maxShown);
	int timer = 0;


	public void addMessage(String s, Type c){
		//standard time is 10 seconds, or 200 ticks
		if(chat.size() == maxShown){
			chat.remove(maxShown-1);
			chatColour.remove(maxShown-1);
		}
		if(chat.size() == 0){
			timer = 200;
		}
		chat.add(0, s);
		chatColour.add(0, c);


	}

	public void render(GameContainer gc, Graphics g){
		
		if(chat.size() > 0){
			for(int i = 0; i < chat.size(); i++){
				if(chatColour.get(i) == null){
					g.setColor(Main.rainbow);
				}else{
					g.setColor(Main.getColor(chatColour.get(i)));
				}
				g.drawString(chat.get(i), 25, gc.getHeight() - 50 - (i*20));
			}
		}
		
	}

	public void update(){
		if(timer == 0){
			
			if(chat.size() > 0){
				timer = 200;
				chat.remove(chat.size()-1);
			}
		}
		if(timer > 0) timer--;
	}

}
