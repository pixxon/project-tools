package bomberman.persistance;

/**
 *
 * @author Imre
 */
public class Wall extends Actor{
	/**
	* Wall constuctor
	*
	* @param posX the X position of the table where the object will be created 
	* @param posY the Y position of the table where the object will be created
	*/
    public Wall(int posX, int posY) {
        super(posX, posY);
        destroyable = false; 
    }
    
    @Override
    public String toString(){
		return "Wall";
    }
}
