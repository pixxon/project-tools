package bomberman.view;

import bomberman.util.GameEvent;
import bomberman.util.GameEventHandler;
import bomberman.util.IView;
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

import javafx.application.Application;
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



public class View extends Application implements IView{
	
	private Label labelGrid[][];
	private GameEventHandler playerID;
	private GameEventHandler gameOver;
	private GameEventHandler gameAdvanced;
	private GameEventHandler gameCreated;
	private int iD;
	private Stage primaryStage;
	
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
				
				if(tmpGameOver.isWinner()){
					
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
				
				if(tmpAdvanced.getType() instanceof Bomb){
					labelGrid[tmpAdvanced.getX()][tmpAdvanced.getY()].setBackground(new Background(new BackgroundImage(new Image("resources/bomb.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
				}
				if(tmpAdvanced.getType() instanceof Flor){
					labelGrid[tmpAdvanced.getX()][tmpAdvanced.getY()].setBackground(new Background(new BackgroundImage(new Image("resources/floorGrass.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
				}
				if(tmpAdvanced.getType() instanceof Obst){
					labelGrid[tmpAdvanced.getX()][tmpAdvanced.getY()].setBackground(new Background(new BackgroundImage(new Image("resources/breakable.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
				}
				if(tmpAdvanced.getType() instanceof Player){
					labelGrid[tmpAdvanced.getX()][tmpAdvanced.getY()].setBackground(new Background(new BackgroundImage(new Image("resources/bomberangel.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
				}
				if(tmpAdvanced.getType() instanceof Wall){
					labelGrid[tmpAdvanced.getX()][tmpAdvanced.getY()].setBackground(new Background(new BackgroundImage(new Image("resources/brickWall.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
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
						if(data.getField(i, j) instanceof Bomb){
							labelGrid[i][j].setBackground(new Background(new BackgroundImage(new Image("resources/bomb.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
							root.add(labelGrid[i][j], i, j);
						}
						if(data.getField(i, j) instanceof Flor){
							labelGrid[i][j].setBackground(new Background(new BackgroundImage(new Image("resources/floorGrass.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
							root.add(labelGrid[i][j], i, j);
						}
						if(data.getField(i, j) instanceof Obst){
							labelGrid[i][j].setBackground(new Background(new BackgroundImage(new Image("resources/breakable.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
							root.add(labelGrid[i][j], i, j);
						}
						if(data.getField(i, j) instanceof Player){
							labelGrid[i][j].setBackground(new Background(new BackgroundImage(new Image("resources/bomberangel.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
							root.add(labelGrid[i][j], i, j);
						}
						if(data.getField(i, j) instanceof Wall){
							labelGrid[i][j].setBackground(new Background(new BackgroundImage(new Image("resources/brickWall.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
							root.add(labelGrid[i][j], i, j);
						}
					}
				}
				
				Scene scene = new Scene(root, 640, 480);
				primaryStage.setScene(scene);
				//TODO: Set BG here too.
			}
		};
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
		
        primaryStage.setTitle("Bomberman");
        primaryStage.show();
    }
	
	public static void main(String[] args) {
        launch(args);
    }
}