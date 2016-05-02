package bomberman.util.EventArgs;

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
	private Actor type;
	
	public GameAdvancedEventArg(int x, int y, Actor type){
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
	
	public Actor getType(){
		return type;
	}
	
	
}
