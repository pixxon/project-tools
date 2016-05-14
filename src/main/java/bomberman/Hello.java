package bomberman;

import bomberman.GameServer;
import bomberman.GameClient;

public class Hello{
    public static void main(String[] args) {
	if ("server".equals(args[0])){
		GameServer server = new GameServer();
		server.runServer(3000);
	}

	if ("client".equals(args[0])){
		GameClient client = new GameClient();
		client.runClient("ws://localhost:3000/bomberman/game");
	}
    }
}
