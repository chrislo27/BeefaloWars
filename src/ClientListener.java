import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;


public class ClientListener extends Listener {
	
	
	
	public void received(Connection connection, Object obj){
		if(!(obj instanceof Packet)) return;
		Packet p = (Packet) obj;
		Packet send = new Packet();
		
		if(p.type == Type.ACCEPTED){
			ConnectingState.info = "Server recieved request";
		}else if(p.type == Type.INGAME){
			ConnectingState.statebg.enterState(Main.gamestate);
		}else if(p.type == Type.REJECTED){
			ConnectingState.info = p.username;
		}else if(p.type == Type.MAP){
			ConnectingState.info = "Getting map...";
			GameState.map_name = p.username;
			GameState.makemap = true;
		}else if(p.type == Type.ENTITIES){
			GameState.entities = p.entities;
			GameState.hits = p.hits;
		}else if(p.type == Type.GETCHAT){
			GameState.chat.addMessage(p.username, p.textColour);
		}
	}
}
