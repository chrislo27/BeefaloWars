import java.util.ArrayList;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;


public class ServerListener extends Listener{

	ServerLogic world;
	public ServerListener(ServerLogic world){
		this.world = world;

	}

	public void received(Connection connection, Object obj){
		if(!(obj instanceof Packet)) return;
		Packet p = (Packet) obj;
		Packet send = new Packet();
		if(p.type == Type.JOIN){
			Main.print(connection.getRemoteAddressTCP().getHostString() + ":" + connection.getRemoteAddressTCP().getPort() + " is attempting to connect...");
			if(world.findViaUsername(p.username) != null){
				Main.print(connection.getRemoteAddressTCP().getHostString() + " kicked for same name as online player");
				send.type = Type.REJECTED;
				send.username = "Kicked: someone else has the same name as you!";
				connection.sendTCP(send);
				connection.close();
			}else{
				connection.setName(p.username);
				send.type = Type.ACCEPTED;
				connection.sendTCP(send);
				world.entities.add(new Entity(0, 0, 200, "Player", p.username));

				
				send.type = Type.MAP;
				send.username = world.currentMap;
				connection.sendTCP(send);
				send.username = null;
				send.type = Type.ENTITIES;
				send.entities = world.entities;
				send.hits = world.hits;

				connection.sendTCP(send);
				
				send.type = Type.INGAME;
				send.entities = null;
				send.hits = null;
				connection.sendTCP(send);
				sendChat(p.username + " has joined the game.", Type.YELLOW);
			}
		}else if(p.type == Type.LEAVE){
			connection.close();
		}else if(p.type == Type.MOVEMENT){
			world.findViaUsername(p.username).move = p.movement;
		}else if(p.type == Type.NEWCHAT){
			sendChat(p.username, p.textColour);
		}
	}

	public void disconnected(Connection connection){
		world.disconnectPlayer(connection.toString());
		sendChat(connection.toString() + " has left the game.", Type.YELLOW);
	}
	
	public void sendChat(String s, Type colour){
		Main.print(s);
		if(world.server.getConnections().length > 0){
			Packet data = new Packet();
			data.type = Type.GETCHAT;
			data.username = s;
			data.textColour = colour;
			for(Connection c : world.server.getConnections()){
				c.sendTCP(data);
			}
		}
	}

}
