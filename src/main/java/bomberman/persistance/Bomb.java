package bomberman.persistance;

/**
 *
 * @author Imre
 */
public class Bomb extends Actor{
    
    private static int BOMB_ID = 0;
    
    private final int local_bomb_id;
    
	/**
	* Bomb constructor
	*
	* @param posX the X position of the table where the object will be created 
	* @param posY the Y position of the table where the object will be created
	*
	*/
	
    public Bomb(int posX, int posY) {
        super(posX, posY);
        destroyable = true;
        local_bomb_id = BOMB_ID;
        BOMB_ID++;
    }

	/**
	*
	* @return the bomb id
	*
	*/
	
    public int getLocal_bomb_id() {
        return local_bomb_id;
    }
    
    @Override
    public String toString(){
		return "Bomb";
    }
}
