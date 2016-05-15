package bomberman.persistance;

/**
 *
 * @author Imre
 */
public class Obst extends Actor{
    /*RENAME$$*/
	/**
	*
	* Obsticle Consturctor
	*
	* @param posX the X position of the table where the object  will be created 
	* @param posY the Y position of the table where the object  will be created
	*/
    public Obst(int posX, int posY) {
        super(posX, posY);
        destroyable = true;
    }
    
    @Override
    public String toString(){
	return "Obst";
    }
}
