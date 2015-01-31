import java.io.Serializable;

import org.newdawn.slick.Color;


public class FloatingText implements Serializable{

	
	String text = "errordmg";
	int cooldown = 15;
	Color colour = Color.magenta;
	
	int x = 300;
	int y = 300;
	int speedUp = 3; //3 per tick
	
	
	public FloatingText(String t, int c, int x, int y, int su, Color co){
		text = t;
		cooldown = c;
		this.x = x;
		this.y = y;
		speedUp = su;
		colour = co;
	}
	
	public FloatingText(String t, int x, int y, Color co){
		text = t;
		
		this.x = x;
		this.y = y;
		
		colour = co;
		
	}
	
	public FloatingText(String t, int x, int y, int c, Color co){
		text = t;
		cooldown = c;
		this.x = x;
		this.y = y;
		
		colour = co;
	}
}
