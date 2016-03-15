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

public class View extends Application implements IView{
	
	private List<List<Label>> labelGrid;
	private EventHandler playerID;
	private EventHandler gameOver;
	private EventHandler gameAdvanced;
	private EventHandler gameCreated;
	private int iD;
	
	public View(){
		playerID = new EventHandler(){
			@Override
			public void actionPerformed(Object sender, Object eventargs){
				iD = (Integer)eventargs;
			}
		};
		
		gameOver = new EventHandler(){
			@Override
			public void actionPerformed(Object sender, Object eventargs){
				
			}
		};
		
		gameAdvanced = new EventHandler(){
			@Override
			public void actionPerformed(Object sender, Object eventargs){
				
			}
		};
		
		gameCreated = new EventHandler(){
			@Override
			public void actionPerformed(Object sender, Object eventargs){
				List<List<FieldType>> data = (List<List<FieldType>>)eventargs;
				
				labelGrid = new List<List<Label>>(data.length);
				
				for(int i = 0; i < data.length, i++){
					labelGrid.at(i) = new List<Label>(data.at(i).length);
					for(int j = 0; j < data.at(i).length){
						labelGrid.at(i).at(j) = new Label();
						switch (data.at(i).at(j)){
							case Bomb: labelgrid.at(i).at(j).setBackground(new Background());
							break;
							case Wall: labelgrid.at(i).at(j).setBackground(new Background());
							break;
							case Player: labelgrid.at(i).at(j).setBackground(new Background());
							break;
							case Empty: labelgrid.at(i).at(j).setBackground(new Background());
							break;
							case Breakable: labelgrid.at(i).at(j).setBackground(new Background());
						}
					}
				}
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
		Label label = new Label("a label");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        
        GridPane root = new GridPane();
        root.add(btn, 1, 1);
		root.add(label, 2, 1);

		Scene scene = new Scene(root, 640, 480);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	
	public static void main(String[] args) {
        launch(args);
    }
}