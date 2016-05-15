package bomberman.view;

import bomberman.util.GameEvent;
import bomberman.util.GameEventHandler;
import bomberman.util.IView;
import bomberman.util.IModel;
import bomberman.connection.ClientEndPoint;
import bomberman.persistance.Actor;
import bomberman.persistance.Flor;
import bomberman.persistance.Bomb;
import bomberman.persistance.Obst;
import bomberman.persistance.Wall;
import bomberman.persistance.Player;
import bomberman.util.EventArgs.PlayerIDEventArg;
import bomberman.util.EventArgs.GameCreatedEventArg;
import bomberman.util.EventArgs.GameOverEventArg;
import bomberman.util.EventArgs.GameAdvancedEventArg;

import org.glassfish.tyrus.client.ClientManager;
import javax.websocket.DeploymentException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage; 
import javafx.scene.layout.BackgroundRepeat; 
import javafx.scene.layout.BackgroundPosition; 
import javafx.scene.layout.BackgroundSize; 
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;


public class View extends Application implements IView{
	private Label labelGrid[][];
	private GameEventHandler playerID;
	private GameEventHandler gameOver;
	private GameEventHandler gameAdvanced;
	private GameEventHandler gameCreated;

	private IModel model;
	private int iD;
	private Scene scene;
	
	public View(){
		playerID = new GameEventHandler(){
			@Override
			public void actionPerformed(Object sender, Object eventargs){
				PlayerIDEventArg tmPlayerID = (PlayerIDEventArg)eventargs;
				iD = tmPlayerID.getID();
			}
		};
		
		gameOver = new GameEventHandler(){
			@Override
			public void actionPerformed(Object sender, Object eventargs){
				GameOverEventArg tmpGameOver = (GameOverEventArg)eventargs;
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Game Over");
				
				if(tmpGameOver.getWinnerID() == iD){
					
					alert.setHeaderText("Congratulations! You Won!");
					
				}else{
					
					alert.setHeaderText("Maybe next time :(");
					
				}
				
				alert.showAndWait();
				
				//TODO: Close application.
				
			}
		};
		
		gameAdvanced = new GameEventHandler(){
			@Override
			public void actionPerformed(Object sender, Object eventargs){
				
				GameAdvancedEventArg tmpAdvanced = (GameAdvancedEventArg)eventargs;
				
				if("Bomb".equals(tmpAdvanced.getType())){
					labelGrid[tmpAdvanced.getX()][tmpAdvanced.getY()].setBackground(new Background(new BackgroundImage(new Image(getClass().getResource("bomb.png").toString()), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
				}
				if("Flor".equals(tmpAdvanced.getType())){
					labelGrid[tmpAdvanced.getX()][tmpAdvanced.getY()].setBackground(new Background(new BackgroundImage(new Image(getClass().getResource("floorGrass.png").toString()), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
				}
				if("Obst".equals(tmpAdvanced.getType())){
					labelGrid[tmpAdvanced.getX()][tmpAdvanced.getY()].setBackground(new Background(new BackgroundImage(new Image(getClass().getResource("breakble.png").toString()), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
				}
				if("Player".equals(tmpAdvanced.getType())){
					labelGrid[tmpAdvanced.getX()][tmpAdvanced.getY()].setBackground(new Background(new BackgroundImage(new Image(getClass().getResource("bomberangel.png").toString()), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
				}
				if("Wall".equals(tmpAdvanced.getType().split(" ")[0])){
					labelGrid[tmpAdvanced.getX()][tmpAdvanced.getY()].setBackground(new Background(new BackgroundImage(new Image(getClass().getResource("brickWall.png").toString())
, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
				}
				
				//TODO: Draw Pictures.
				
			}
		};
		
		gameCreated = new GameEventHandler(){
			@Override
			public void actionPerformed(Object sender, Object eventargs){

				GameCreatedEventArg data = (GameCreatedEventArg)eventargs;
				
				labelGrid = new Label[data.getHeight()][data.getWidth()];
				GridPane root = new GridPane();
				
				for(int i = 0; i < data.getHeight(); ++i){
					for(int j = 0; j < data.getWidth(); ++j){
						labelGrid[i][j] = new Label();
						labelGrid[i][j].setMinWidth(40);
						labelGrid[i][j].setMaxWidth(40);
						labelGrid[i][j].setMinHeight(40);
						labelGrid[i][j].setMaxHeight(40);

						if("Bomb".equals(data.getField(i, j))){
							labelGrid[i][j].setBackground(new Background(new BackgroundImage(new Image(getClass().getResource("bomb.png").toString()), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
							root.add(labelGrid[i][j], i, j);
						}
						if("Flor".equals(data.getField(i, j))){
							labelGrid[i][j].setBackground(new Background(new BackgroundImage(new Image(getClass().getResource("floorGrass.png").toString()), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
							root.add(labelGrid[i][j], i, j);
						}
						if("Obst".equals(data.getField(i, j))){
							labelGrid[i][j].setBackground(new Background(new BackgroundImage(new Image(getClass().getResource("breakble.png").toString()), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
							root.add(labelGrid[i][j], i, j);
						}
						if("Player".equals(data.getField(i, j).split(" ")[0])){
							labelGrid[i][j].setBackground(new Background(new BackgroundImage(new Image(getClass().getResource("bomberangel.png").toString()), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
							root.add(labelGrid[i][j], i, j);
						}
						if("Wall".equals(data.getField(i, j))){
							labelGrid[i][j].setBackground(new Background(new BackgroundImage(new Image(getClass().getResource("brickWall.png").toString()), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
							root.add(labelGrid[i][j], i, j);
						}
					}
				}

				scene.setRoot(root);
				//TODO: Set BG here too.
			}
		};

		
		model = new ClientEndPoint();
        	model.getGameAdvanced().addListener(getGameAdvancedHandler());
        	model.getGameCreated().addListener(getGameCreatedHandler());
        	model.getGameOver().addListener(getGameOverHandler());
        	model.getPlayerID().addListener(getPlayerIDHandler());
		

		ClientManager client = ClientManager.createClient();
		try {
		    client.connectToServer(model, new URI("ws://localhost:3000/bomberman/game"));

		} catch (DeploymentException | URISyntaxException  e) {
		    throw new RuntimeException(e);
		}

		model.newPlayer();
	}
	
	@Override
	public GameEventHandler getPlayerIDHandler(){
		return playerID;
	}
	
	@Override
	public GameEventHandler getGameOverHandler(){
		return gameOver;
	}
	
	@Override
	public GameEventHandler getGameAdvancedHandler(){
		return gameAdvanced;
	}
	
	@Override
	public GameEventHandler getGameCreatedHandler(){
		return gameCreated;
	}
	
	@Override
    public void start(Stage primaryStage) {
	this.scene = new Scene(new GridPane(), 640, 480);

        primaryStage.setTitle("Bomberman");
	primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
          public void handle(WindowEvent we) {
              model.leavePlayer(iD);
          }
      });  
    }
}
