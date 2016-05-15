package bomberman;

import bomberman.view.View;


import java.util.concurrent.CountDownLatch;

import javafx.application.Application;
/* Created by Attila on 2016. 03. 14..
 */


public class GameClient{

	public GameClient() {
		CountDownLatch latch = new CountDownLatch(1);
	    }

    public void runClient(String whereToConnect) {
	Application.launch(View.class, "");
    }
}
