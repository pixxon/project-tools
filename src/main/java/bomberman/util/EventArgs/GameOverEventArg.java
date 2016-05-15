package bomberman.util.EventArgs;

/**
 * Created by Attila on 2016. 03. 14..
 */


/**
 * GameOver event argumentum
 *
 */
public class GameOverEventArg {
    
	private int winnerID;

	public GameOverEventArg(int winnerID){
		this.winnerID=winnerID;
	}
	
	public int getWinnerID(){
		return winnerID;
	}
	
}
