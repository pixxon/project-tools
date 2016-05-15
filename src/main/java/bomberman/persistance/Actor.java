package bomberman.persistance;

/**
 *
 * @author Imre
 */
public abstract class  Actor {
    
	
	
    protected int posX;
    protected int posY;
    
    protected boolean destroyable;

	/**
	*
	*  Abstract Actor Constructor
	*
	* @param posX the X position of the table where the object will be created 
	* @param posY the Y position of the table where the object will be created
	*
	*/
	
    public Actor(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }
    

	/**
	*
	* @return the X position in the table
	*/
    public int getPosX() {
        return posX;
    }

	
	/**
	*
	* @return the Y position in the table
	*/
    public int getPosY() {
        return posY;
    }

	
	/**
	*
	* @param posX set the position X in the table
	*/
	
    public void setPosX(int posX) {
        this.posX = posX;
    }

	/**
	*
	* @param posY set the position Y in the table
	*/
    public void setPosY(int posY) {
        this.posY = posY;
    }

	/**
	*
	* @return does the object destuctable
	*/
    public boolean isDestroyable() {
        return destroyable;
    }

    @Override
    public String toString(){
		return "Actor";
    }
}
