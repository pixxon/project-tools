package bomberman;

import bomberman.GameServer;
import bomberman.GameClient;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Hello{
    public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Server or client?");
		String mode = br.readLine();

	if ("server".equals(mode)){
		GameServer server = new GameServer();
		server.runServer(3000);
		System.in.read();
		server.stopServer();
	}

	if ("client".equals(mode)){
		GameClient client = new GameClient();
		client.runClient("ws://localhost:3000/bomberman/game");
	}
    }
}
