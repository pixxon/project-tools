package bomberman.util.EventArgs;

/**
 * Created by Attila on 2016. 03. 14..
 */


/**
 * GameOver event argumentum
 *
 */
public class GameOverEventArg {
    
	private boolean winner;

	public GameOverEventArg(boolean winner){
		this.winner=winner;
	}
	
	public boolean isWinner(){
		return winner;
	}
	
}
