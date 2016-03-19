package bomberman.connection.demo;

import bomberman.connection.ServerEndPoint;
import org.glassfish.tyrus.server.Server;

/**
 * Created by Attila on 2016. 03. 14..
 */
public class DemoServer {

    private Server server;

    public void runServer(int port) {
        server = new Server("localhost", port, "/bomberman",ServerEndPoint.class);

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopServer(){
        server.stop();
    }
}
