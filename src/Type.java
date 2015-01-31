
public enum Type {
	UNKNOWN,
	
	JOIN, LEAVE, //client's
	MOVEMENT, PFIRE, SFIRE, //client's
	
	MAP, ENTITIES, //server's
	REJECTED, ACCEPTED, INGAME, //server's
	
	NEWCHAT, GETCHAT, //chat system
	
	RAINBOW, WHITE, //colour
	YELLOW, BLUE, CYAN, RED
	
}
