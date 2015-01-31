import java.util.ArrayList;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;


public class ServerLogic {

	
	//NOTE: tickrate is 20 times a second!!!
	public void update(){
		for(FloatingText t : hits){
			t.cooldown--;
			t.y -= t.speedUp;
		}
		ArrayList<FloatingText> toreee = new ArrayList();
		for(FloatingText r: hits){
			if(r.cooldown < 1){
				toreee.add(r);
			}
		}
		for(FloatingText r: toreee){
			hits.remove(r);
		}
		
		if(entities.size() > 0){
			for(Entity e : entities){
				e.update();
			}
		}
		
		if(server.getConnections().length > 0){
			Packet data = new Packet();
			data.type = Type.ENTITIES;
			data.entities = (ArrayList<Entity>) entities.clone();
			for(Connection c : server.getConnections()){
				c.sendUDP(data);
			}
		
		}
		
	}
	
	public ServerLogic(String map, Server s){
		currentMap = map;
		server = s;
	}
	
	public void disconnectPlayer(String username){
		entities.remove(findViaUsername(username));
	}

	Server server;
	ArrayList<Entity> entities = new ArrayList();
	ArrayList<FloatingText> hits = new ArrayList();
	String currentMap;
	
	public Entity findViaUsername(String user){
		if(entities.size() == 0) return null;
		for(Entity e : entities){
			if(e.username.equals(user)) return e;
		}
		return null;
	}

}
