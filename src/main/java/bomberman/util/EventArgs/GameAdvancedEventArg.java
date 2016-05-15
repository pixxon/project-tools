package bomberman.util.EventArgs;

import bomberman.persistance.Actor;

/**
 * Created by Attila on 2016. 03. 14..
 */


/**
 * GameAdvanced event argumentum
 *
 */
public class GameAdvancedEventArg {
	
    private int x;
	private int y;
	private String type;
	
	public GameAdvancedEventArg(int x, int y, String type){
		this.x = x;
		this.y = y;
		this.type = type;		
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public String getType(){
		return type;
	}
	
	
}
