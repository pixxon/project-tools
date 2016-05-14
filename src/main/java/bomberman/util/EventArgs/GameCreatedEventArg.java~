package bomberman.util.EventArgs;

import bomberman.persistance.Actor;


/**
 * Created by Attila on 2016. 03. 14..
 */

/**
 * GameCreated event argumentum
 *
 */
public class GameCreatedEventArg {
	
    private Actor[][] mtx;
	private int height;
	private int width;
	
	public GameCreatedEventArg(int height, int width){
		this.height = height;
		this.width = width;
		
		mtx = new Actor[height][width];
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public void setField(int x, int y, Actor type){
		mtx[x][y] = type;
	}
	
	public Actor getField(int x, int y){
		return mtx[x][y];
	}
}
