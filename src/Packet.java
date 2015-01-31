import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;


public class Packet {
	
	Type type = Type.UNKNOWN;
	Type textColour = Type.WHITE;
	String username = null;
	ArrayList<Entity> entities = null;
	ArrayList<FloatingText> hits = null;
	MovementData movement = null;
	
}
