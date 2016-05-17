package bomberman;

import bomberman.view.View;

import java.util.concurrent.CountDownLatch;
import javafx.application.Application;

/**
 * GameClient class provides a user interface and connection with the server.
 */
public class GameClient{

	/**
	 * Creates a new instance.
	 */
	public GameClient() {
	    }

	/**
	 *
	 * @param whereToConnect
     */
    public void runClient(String whereToConnect) {
		View.connectionAddress = whereToConnect;
		Application.launch(View.class, "");
    }
}
