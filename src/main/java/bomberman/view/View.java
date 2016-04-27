package bomberman.view;

import bomberman.util.Event;
import bomberman.util.EventHandler;
import bomberman.util.IView;

import javafx.application.Application;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;

public class View extends Application implements IView{
	
	private Label labelGrid[][];
	private EventHandler playerID;
	private EventHandler gameOver;
	private EventHandler gameAdvanced;
	private EventHandler gameCreated;
	private int iD;
	private Stage primaryStage;
	
	public View(){
		playerID = new EventHandler(){
			@Override
			public void actionPerformed(Object sender, Object eventargs){
				PlayerIDEventArgs tmPlayerID = (PlayerIDEventArgs)eventargs;
				iD = tmPlayerID.getID();
			}
		};
		
		gameOver = new EventHandler(){
			@Override
			public void actionPerformed(Object sender, Object eventargs){
				GameOverEventArg tmpGameOver = (PlayerIDEventArgs)eventargs;
				
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
		
		gameAdvanced = new EventHandler(){
			@Override
			public void actionPerformed(Object sender, Object eventargs){
				
				GameAdvancedEventArg tmpAdvanced = (GameAdvancedEventArg)eventargs;
				
				if(tmpAdvanced.getType() instanceof Bomb){
					labelgrid[tmpAdvanced.getX()][tmpAdvanced.getY()].setBackground(new Background("resources/bomb.png"));
				}
				if(tmpAdvanced.getType() instanceof Flor){
					labelgrid[tmpAdvanced.getX()][tmpAdvanced.getY()].setBackground(new Background("resources/floorGrass.png"));
				}
				if(tmpAdvanced.getType() instanceof Obst){
					labelgrid[tmpAdvanced.getX()][tmpAdvanced.getY()].setBackground(new Background("resources/breakable.png"));
				}
				if(tmpAdvanced.getType() instanceof Player){
					labelgrid[tmpAdvanced.getX()][tmpAdvanced.getY()].setBackground(new Background("resources/bomberangel.png"));
				}
				if(tmpAdvanced.getType() instanceof Wall){
					labelgrid[tmpAdvanced.getX()][tmpAdvanced.getY()].setBackground(new Background("resources/brickWall.png"));
				}
				
				//TODO: Draw Pictures.
				
			}
		};
		
		gameCreated = new EventHandler(){
			@Override
			public void actionPerformed(Object sender, Object eventargs){
				GameCreatedEventArg data = (GameCreatedEventArg)eventargs;
				
				labelGrid = new Label(data.getHeight(), data.getWidth());
				GridPane root = new GridPane();
				
				for(int i = 0; i < data.getHeight(), ++j){
					for(int j = 0; j < data.getWidth(), ++j){
						labelGrid[i][j] = new Label();
						if(data.getField(i, j).getType() instanceof Bomb){
							labelgrid[i][j].setBackground(new Background("resources/bomb.png"));
							root.add(labelgrid[i][j], i, j);
						}
						if(data.getField(i, j).getType() instanceof Flor){
							labelgrid[i][j].setBackground(new Background("resources/floorGrass.png"));
							root.add(labelgrid[i][j], i, j);
						}
						if(data.getField(i, j).getType() instanceof Obst){
							labelgrid[i][j].setBackground(new Background("resources/breakable.png"));
							root.add(labelgrid[i][j], i, j);
						}
						if(data.getField(i, j).getType() instanceof Player){
							labelgrid[i][j].setBackground(new Background("resources/bomberangel.png"));
							root.add(labelgrid[i][j], i, j);
						}
						if(data.getField(i, j).getType() instanceof Wall){
							labelgrid[i][j].setBackground(new Background("resources/brickWall.png"));
							root.add(labelgrid[i][j], i, j);
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
	public EventHandler getPlayerIDHandler(){
		return playerID;
	}
	
	@Override
	public EventHandler getGameOverHandler(){
		return gameOver;
	}
	
	@Override
	public EventHandler getGameAdvancedHandler(){
		return gameAdvanced;
	}
	
	@Override
	public EventHandler getGameCreatedHandler(){
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