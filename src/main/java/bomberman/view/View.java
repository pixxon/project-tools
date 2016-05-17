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
import bomberman.util.Direction;
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
import javafx.scene.layout.Pane;
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
import javafx.scene.input.KeyEvent;

public class View extends Application implements IView{
	private Label labelGrid[][];
	private GameEventHandler playerID;
	private GameEventHandler gameOver;
	private GameEventHandler gameAdvanced;
	private GameEventHandler gameCreated;

	private IModel model;
	private int iD;
	private Scene scene;
	
	class Updater implements Runnable{
		private Scene scene;
		private Pane root;
		
		public Updater(Scene scene, Pane root){
			this.scene = scene;
			this.root = root;
		}
		
		@Override
		public void run(){  
			scene.setRoot(root);
		}
	}
	
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
				System.out.println("a");
				if(tmpGameOver.getWinnerID() == iD){
					
					Platform.runLater(new Runnable(){
						@Override
						public void run(){
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Game Over");
							alert.setHeaderText("Congratulations! You Won!");
							
							alert.showAndWait();
							System.exit(0);
						}
					});
					
				}else{
					
					Platform.runLater(new Runnable(){
						@Override
						public void run(){
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Game Over");
							alert.setHeaderText("Maybe next time :(");
							
							alert.showAndWait();
							System.exit(0);
						}
					});
				}
				
				
			}
		};
		
		gameAdvanced = new GameEventHandler(){
			@Override
			public void actionPerformed(Object sender, Object eventargs){
				GameAdvancedEventArg tmpAdvanced = (GameAdvancedEventArg)eventargs;
				if(tmpAdvanced.getX() < labelGrid.length && tmpAdvanced.getY() < labelGrid[tmpAdvanced.getX()].length)
					labelGrid[tmpAdvanced.getX()][tmpAdvanced.getY()].setBackground(getBackground(tmpAdvanced.getType()));
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

						labelGrid[i][j].setBackground(getBackground(data.getField(i, j)));
						root.add(labelGrid[i][j], i, j);
					}
				}
				
				Platform.runLater(new Updater(scene, root));
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
		this.scene = new Scene(new GridPane(), 600, 600);
	
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getText()) {
                    case "w":
			model.movePlayer(iD, Direction.UP);
		break;
                    case "s":
			model.movePlayer(iD, Direction.DOWN);
		break;
                    case "a":
			model.movePlayer(iD, Direction.LEFT);
		break;
                    case "d":
			model.movePlayer(iD, Direction.RIGHT);
		break;
                    case "b":
			model.placeBomb(iD);
		break;
                }
            }
        });

        primaryStage.setTitle("Bomberman");
		primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
          public void handle(WindowEvent we) {
              model.leavePlayer(iD);
          }
      });  
    }

	private Background getBackground(String type){

		Image image = null;

		if("Bomb".equals(type)){
			image = new Image(getClass().getResource("bomb.png").toString());
		}
		if("Flor".equals(type)){
			image = new Image(getClass().getResource("floorGrass.png").toString());
		}
		if("Obst".equals(type)){
			image = new Image(getClass().getResource("breakble.png").toString());
		}
		if("Player".equals(type.split(" ")[0])){
			if(Integer.toString(iD).equals(type.split(" ")[1])){
				image = new Image(getClass().getResource("bomberangel.png").toString());
			}else{
				image = new Image(getClass().getResource("bomberangel_blue.png").toString());
			}
		}
		if("Wall".equals(type)){
			image = new Image(getClass().getResource("brickWall.png").toString());
		}

		return new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
	}
}
