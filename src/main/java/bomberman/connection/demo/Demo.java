package bomberman.connection.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Attila on 2016. 03. 14..
 */
public class Demo {

    public static void main(String[] args){

        //Server
        DemoServer demoServer = new DemoServer();
        //@param port where to be tun
        demoServer.runServer(3000);


        //Clients
        DemoClient demoClient = new DemoClient();
        DemoClient demoClient2 = new DemoClient();

        //@param where to be connected
        demoClient.runClient("ws://localhost:3000/bomberman/game");
        demoClient2.runClient("ws://localhost:3000/bomberman/game");



        demoClient.newPlayer();
        demoClient.movePlayer();
        demoClient.leavePlayer();
        demoClient.placeBomb();

        demoClient2.newPlayer();

        System.out.println("Please press a key to stop the demo.");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
