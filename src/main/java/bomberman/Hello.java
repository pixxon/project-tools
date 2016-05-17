package bomberman;

import bomberman.GameServer;
import bomberman.GameClient;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
 * Hello class is the main class for Bomberman game.
 *
 * <p>
 *     Contains both the server and the client.
 *     After running it the user can decide what to do.
 * </p>
 */
public class Hello{
	/**
	 * Main entry point for the Bomberman game.
	 *
	 * <p>
	 *     The user can decide to start a server or a client.
	 *     After that the IP address and the port is needed to start.
	 * </p>
	 *
	 * @throws IOException When can not open System.in.
     */
    public static void main(String[]) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Server or client?");
		String mode = br.readLine();

		if ("server".equals(mode)){
			GameServer server = new GameServer();

			System.out.println("IP address?");
			String ip = br.readLine();
			System.out.println("Port?");
			Integer port = Integer.parseInt(br.readLine());

			server.runServer(ip, port);

			System.out.println("Press button to stop.");
			System.in.read();

			server.stopServer();
		}

		if ("client".equals(mode)){
			GameClient client = new GameClient();

			System.out.println("IP address?");
			String ip = br.readLine();
			System.out.println("Port?");
			Integer port = Integer.parseInt(br.readLine());

			client.runClient("ws://" + ip.toString() ":" + port.toString() + "/bomberman/game");
		}
    }
}
