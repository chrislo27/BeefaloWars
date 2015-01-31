

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


import org.newdawn.slick.AppGameContainer;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;



public class Main extends StateBasedGame {

	public static final String name = "Beefalo Wars";
	public static final String VERSION = "1.0";



	public static final int gamestate = 1;
	public static final int menustate = 2;
	public static final int loadingstate = 3;
	public static final int creditsstate = 4;
	public static final int messagestate = 5;
	public static final int controlstate = 6;
	public static final int namestate = 7;
	public static final int serverstate = 8;
	public static final int clientstate = 9;

	public static String username = "Player";


	public static boolean flash = false;
	public static float volume = 0.35f;

	static Color rainbow = new Color(0, 0, 0);

	public static final int STATE_ID = 0;
	public static int seed = (int) (System.currentTimeMillis()/1000);
	public static Random random = new Random(seed);
	public static int octave = 20;

	public static Server server = null;
	public static Client client = null;
	


	public Main() throws IOException, SlickException {
		super(name);
		addState(new GameState(gamestate));
		addState(new ConnectingState(clientstate));
		addState(new MainMenuState(menustate));
		addState(new NameState(namestate));
		addState(new MessageState(messagestate));
		enterState(menustate);
	}


	public static DecimalFormat toNearest100th = new DecimalFormat("0.##");



	public static int random(int x, int y){
		if(x == y){
			return x;
		}
		return random.nextInt(y - (x - 1)) + x;
	}
	public static void print(Object o){
		System.out.println(o);
	}
	public static void print(){
		System.out.println();
	}

	public static String getIp() throws Exception{
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(
					whatismyip.openStream()));
			String ip = in.readLine();
			return ip;
		} finally {
			if (in != null) {

				in.close();

			}
		}
	}
	public static AppGameContainer app;

	public static Kryo register(Kryo kryo){
		//kryo.register(.class);
		kryo.register(Type.class);
		kryo.register(Packet.class);
		kryo.register(Entity.class);
		kryo.register(ArrayList.class);
		kryo.register(FloatingText.class);
		kryo.register(Object.class);
		kryo.register(MovementData.class);
		return kryo;
	}
	
	public static Color getColor(Type t){
		if(t == null) return null;
		if(t == Type.RAINBOW){
			return Main.rainbow;
		}else if(t == Type.WHITE){
			return Color.white;
		}else if(t == Type.YELLOW){
			return Color.yellow;
		}else if(t == Type.BLUE){
			return Color.blue;
		}else if(t == Type.CYAN){
			return Color.cyan;
		}else if(t == Type.RED){
			return Color.red;
		}
		return null;
	}
	
	public static ServerLogic logic = null;
	static boolean running = true;
	public static Resource resource;
	
	public static void main(String[] args) throws SlickException, IOException {
		
		if(System.getProperty("server") == null){
			client = new Client();
			new Thread(client).start();
			register(client.getKryo());
			client.addListener(new ClientListener());
			
			app = new AppGameContainer(new Main());
			app.setResizable(false);
			app.setDisplayMode(768, 600, false);
			app.start();
		}else{
			int port = 1337;
			print("Starting server...");
			print("What port should to use: ");
			Scanner portCheck = new Scanner(System.in);
			
			while(portCheck.hasNext()){
				
				String s = portCheck.nextLine();
				
				try{
					port = Integer.parseInt(s);
					break;
				}catch(NumberFormatException e){
					print("Your port specified is not a number!");
				}
			}
			
			print("Autoplaying dm_goodlake");

			server = new Server();
			logic = new ServerLogic("dm_goodlake", server);

			register(server.getKryo());
			ServerListener listener = new ServerListener(logic);
			server.addListener(listener);

			server.bind(port, port);
			print("Server bound to port " + port + " successfully");




			server.start();
			Thread logicUpdater = new Thread(){
				public void run(){
					while(running){
						
						
						try {
							this.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						logic.update();
					}
				}
			};
			logicUpdater.start();
			Scanner scan = new Scanner(System.in);
			while(scan.hasNext()){

				String s = scan.nextLine();
				s = s.toLowerCase();
				System.out.println(s);
				if(s.equals("exit") || s.equals("stop")){
					print("Stopping server...");
					running = false;
					server.close();
					server.stop();
					print("Server stopped; exiting");
					System.exit(0);
				}else if(s.equals("info")){
					String ip = null;
					try{
						ip = getIp();
					}catch(Exception e){}
					if(ip != null){
						print("If you want others to connect to your server, give them this ip: " + ip + " and this port " + port);
					}
				}else if(s.startsWith("say")){
					listener.sendChat("<CONSOLE>: " + s.substring(4), Type.YELLOW);
				}else{
					print("Invalid command.");
				}

			}
		}




	}

	public void initStatesList(final GameContainer gc) throws SlickException {
		gc.setTargetFrameRate(120);
		gc.setMaximumLogicUpdateInterval(50);
		gc.setMinimumLogicUpdateInterval(50);
		gc.setUpdateOnlyWhenVisible(false);
		gc.setAlwaysRender(true);
		gc.setShowFPS(true);
		gc.setSoundVolume(0.4f);


		//rainbow
		Thread rainbowT = new Thread() {
			public void run(){
				final float freq = 0.3f;
				int counter = 0;
				boolean backwards = false;
				while (true){
					try{
						this.sleep(100);

						if(backwards == true){
							counter--;
						}else counter++;

						flash = !flash;

						rainbow.r = (float) Math.sin(freq*counter + 0);
						rainbow.g = (float) Math.sin(freq*counter + (2*Math.PI)/3);
						rainbow.b = (float) Math.sin(freq*counter + (4*Math.PI)/3);

						if(counter == 32){
							backwards = true;
						}else if(counter == 0){
							backwards = false;
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		};
		rainbowT.start();


	}

}
