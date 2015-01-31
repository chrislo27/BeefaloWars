import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


public class Entity {
	
	float x = 0;
	float y = 0;
	float speed = 4;
	int hp = 100;
	int age = 0;
	boolean constSpeed = false;
	float velox = 0;
	float veloy = 0;
	String username = "";
	boolean facingRight = true;
	String entityName = "errorname";
	Type team = Type.UNKNOWN;
	
	MovementData move = new MovementData();
	
	public Entity(){
		//serialization purposes
	}
	
	public Entity(int posx, int posy, int h, String name){
		x = posx;
		y = posy;
		hp = h;
		entityName = name;
	}
	public Entity(int posx, int posy, int h, String name, String user){
		x = posx;
		y = posy;
		hp = h;
		entityName = name;
		username = user;
	}
	

	
	public void update(){
		if(age < 0) age = 0;
		age++;
		if(move.up){
			y -= speed;
		}else if(move.down){
			y += speed;
		}
		if(move.left){
			facingRight = false;
			x -= speed;
		}else if(move.right){
			facingRight = true;
			x += speed;
		}
		x += velox;
		y += veloy;
		if(!constSpeed){
			if(velox > 0) velox--;
			if(velox < 0) velox++;
			if(veloy > 0) veloy--;
			if(veloy < 0) veloy++;
		}
		
	}
	public void render(GameContainer gc, Graphics g){
		if(!username.equals("")){
			if(team == Type.BLUE){
				g.setColor(Color.cyan);
			}else if(team == Type.RED){
				g.setColor(Color.red);
			}else if(team == Type.UNKNOWN){
				g.setColor(Color.white);
			}
			g.drawString(username, x, y-25);
		}
		if(facingRight){
			Main.resource.defaultSprite.draw(x, y);
		}else{
			Main.resource.defaultSprite.getFlippedCopy(true, false).draw(x, y);
		}
	}
}
