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

/**
 * The main interface between the user and the program.
 *
 * <p>
 *     Using JavaFX the class creates a graphic user interface which is updated on movements.
 *     Handles keyboard events so they can be transmitted to the {@link bomberman.model.Model}.
 * </p>
 */
public class View extends Application implements IView{
	/**
	 * The address of the server.
	 */
	public static String connectionAddress;

	/**
	 * A grid containing the shown {@link javafx.}
	 */
	private Label labelGrid[][];

	/**
	 * Contains the ID given by the server.
	 */
	private GameEventHandler playerID;

	/**
	 * Signals that the game ended.
	 */
	private GameEventHandler gameOver;

	/**
	 * Signals to refresh the scene.
	 */
	private GameEventHandler gameAdvanced;

	/**
	 * Signal that the game begun.
	 */
	private GameEventHandler gameCreated;

	/**
	 * Model where the user input is transmitted to.
	 */
	private IModel model;

	/**
	 * ID of the player, given by the server.
	 */
	private int iD;

	/**
	 * The visible scene.
	 */
	private Scene scene;

	/**
	 * Inner class to update the scene on JavaFX thread.
	 *
	 * <p>
	 *     Implements {@link Runnable} so it can be executed on another thread.
	 * </p>
	 */
	class Updater implements Runnable{
		/**
		 * The visible scene
		 */
		private Scene scene;
		/**
		 * The new layout.
		 */
		private Pane root;

		/**
		 * Sets the variables to the given ones.
		 *
		 * @param scene The visible scene
         * @param root The new layout
         */
		public Updater(Scene scene, Pane root){
			this.scene = scene;
			this.root = root;
		}

		/**
		 * Overrides run method, so it can be executed on a different thread.
		 */
		@Override
		public void run(){  
			scene.setRoot(root);
		}
	}

	/**
	 * Sets up the needed {@link GameEventHandler}s and connects them to the {@link IModel}.
	 *
	 * <p>
	 *     The EventHandlers are anonymous inner classes.
	 *     They just override the actionPerformed method.
	 * </p>
	 */
	public View(){
		/**
		 * Sets the iD to the given one.
		 */
		playerID = new GameEventHandler(){
			@Override
			public void actionPerformed(Object sender, Object eventargs){
				PlayerIDEventArg tmPlayerID = (PlayerIDEventArg)eventargs;
				iD = tmPlayerID.getID();
			}
		};

		/**
		 * Shows the endgame result in a pop-up window.
		 */
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

		/**
		 * Changes the given field to the new one.
		 */
		gameAdvanced = new GameEventHandler(){
			@Override
			public void actionPerformed(Object sender, Object eventargs){
				GameAdvancedEventArg tmpAdvanced = (GameAdvancedEventArg)eventargs;
				if(tmpAdvanced.getX() < labelGrid.length && tmpAdvanced.getY() < labelGrid[tmpAdvanced.getX()].length)
					labelGrid[tmpAdvanced.getX()][tmpAdvanced.getY()].setBackground(getBackground(tmpAdvanced.getType()));
			}
		};

		/**
		 * Creates a new grid from the given matrix.
		 */
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
	}

	/**
	 * Getter for playerIDHandler.
	 * @return Reference to the playerIDHandler.
     */
	@Override
	public GameEventHandler getPlayerIDHandler(){
		return playerID;
	}

	/**
	 * Getter for gameOverHandler.
	 * @return Reference to the gameOverHandler.
     */
	@Override
	public GameEventHandler getGameOverHandler(){
		return gameOver;
	}

	/**
	 * Getter for gameAdvancedHandler.
	 * @return Reference to the gameOverHandler.
     */
	@Override
	public GameEventHandler getGameAdvancedHandler(){
		return gameAdvanced;
	}

	/**
	 * Getter for gameCreatedHandler.
	 * @return Reference to the gameCreatedHandler.
     */
	@Override
	public GameEventHandler getGameCreatedHandler(){
		return gameCreated;
	}

	/**
	 * Main enty point of the JavaFX application.
	 *
	 * <p>
	 *     Creates a new scene and
	 * </p>
	 *
	 * @param primaryStage The stage where it can draw.
     */
	@Override
    public void start(Stage primaryStage) {
		model = new ClientEndPoint();
		model.getGameAdvanced().addListener(getGameAdvancedHandler());
		model.getGameCreated().addListener(getGameCreatedHandler());
		model.getGameOver().addListener(getGameOverHandler());
		model.getPlayerID().addListener(getPlayerIDHandler());

		ClientManager client = ClientManager.createClient();
		try {
			client.connectToServer(model, new URI(connectionAddress));

		} catch (DeploymentException | URISyntaxException  e) {
			throw new RuntimeException(e);
		}

		model.newPlayer();

		/**
		 * Handles KeyEvents and transmits it to the model.
		 */
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

		this.scene = new Scene(new GridPane(), 600, 600);
        primaryStage.setTitle("Bomberman");
		primaryStage.setScene(scene);
        primaryStage.show();

		/**
		 * Handles application close.
		 */
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
          	public void handle(WindowEvent we){
              	model.leavePlayer(iD);
          	}
      	});
    }

	/**
	 * Private method to get the right image from resources.
	 *
	 * <p>
	 *     The resources must be next to the class.
	 * </p>
	 *
	 * @param type Name of the resource.
	 * @return Background containing the needed image.
     */
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
