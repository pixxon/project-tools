package bomberman.persistance;

/**
 *
 * @author Imre
 */
public class Player extends Actor{
    
    private static int PLAYER_ID = 0;
    
    private final int player_id;
    private boolean alive;
    private boolean bombInQue;
    private Bomb bomb;

	
	/**
	* Player constructor
	*
	* @param posX the X position of the table where the object will be created 
	* @param posY the Y position of the table where the object will be created
	*
	*/
	
    public Player(int posX, int posY) {
        super(posX, posY);
        this.player_id = PLAYER_ID;
        PLAYER_ID++;
        alive = true;
        destroyable = true;
    }

	
	/**
	*
	* @param set the Player alive status
	*
	*/
	
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

	/**
	*
	* @return teturns is the Player alive
	*
	*/
	
    public boolean isAlive() {
        return alive;
    }

		
	/**
	*
	* @return get the Player id
	*
	*/
	
    public int getPlayer_id() {
        return player_id;
    }
    
	/**
	*
	* Instantiate a bomb and place it in the queue
	*
	*/
	
    public void PlaceBomb(){
        bomb = new Bomb(posX, posY);
        bombInQue = true;
    }

	/**
	*
	* @param bombInQue set the status is any Bomb in the queue
	*
	*/
	
    public void setBombInQue(boolean bombInQue) {
        this.bombInQue = bombInQue;
    }
	
	
	/**
	*
	* @param set the Player bomb
	*
	*/
	
    public void setBomb(Bomb bomb) {
        this.bomb = bomb;
    }

	/**
	*
	* @return returns if any bomb in the queue
	*
	*/
	
    public boolean isBombInQue() {
        return bombInQue;
    }

	/**
	*
	* @return get the bomb reference
	*
	*/
    public Bomb getBomb() {
        return bomb;
    }

    @Override
    public String toString(){
		return "Player " + player_id;
    }
}
