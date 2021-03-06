package bomberman.persistance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Imre
 */
public class Table {
    private Actor playField[][];
    private int defaultValue = 15;
    private int size;
    private int floorcount = 0;
    private final float fullPercent = 0.6f;
    
	/**
	*
	* Table constructor with the defalt paramteres which is 15
	*
	*/
	
    public Table() {
        playField = new Actor[defaultValue][defaultValue];
        size = defaultValue;
        generateField();

    }

	
	/**
	*
	* @return the size of the table
	*
	*/
	
    public int GetSize(){
        return this.size;
    }
    
	/**
	*
	* @param size Create a table with the given size parameter
	*
	*/
	
    public Table(int size) {
        if(size < 9)
            size = 9;
        if(size % 2 != 1)
            size++;
        this.size = size;
        playField = new Actor[this.size][this.size];
        generateField();

    }
	
	/**
	*
	* Generate the playfield
	*
	*/
    
    private void generateField(){
        /**
         * Generate floor, for all fields
         */
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                playField[i][j] = new Flor(i,j);
            }
        }
        /**
         * Generate Walls 
         */
        for (int i = 0; i < size; i++) {
            playField[0][i] = new Wall(0,i);
            playField[size-1][i] = new Wall(size-1,i);
        }
        
        for (int i = 1; i < size-1; i++) {
            playField[i][0] = new Wall(i,0);
            playField[i][size-1] = new Wall(i,size-1);
        }
        
        for (int i = 2; i < size-2; i+= 2) {
            for (int j = 2; j < size; j+= 2) {
                playField[i][j] = new Wall(i,j);
            }
        }
        /**
         * Generate obstacles
         */
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(playField[i][j] instanceof Flor){
                    floorcount +=1;
                }
            }
        }
        
        Random r = new Random();
        for (int i = 0; i < (int)(floorcount-20)*fullPercent; i++) {
            int x = r.nextInt(size-1);
            int y = r.nextInt(size-1);
            while(true){
                if(playField[x][y] instanceof Wall || playField[x][y] instanceof Obst)
                {
                    x = r.nextInt(size-1);
                    y = r.nextInt(size-1);
                    continue;
                }
                //jobb felso sarok
                if((x == 1 ) && (y == 1 || y == 2 || y == 3))
                {
                    x = r.nextInt(size-1);
                    y = r.nextInt(size-1);
                }
                else if((x == 2 || x == 3) && (y == 1))
                {
                    x = r.nextInt(size-1);
                    y = r.nextInt(size-1);
                }
                //jobb also sarok
                else if((x == size -2 ) && (y == 1 || y == 2 || y == 3))
                {
                    x = r.nextInt(size-1);
                    y = r.nextInt(size-1);
                }
                else if(((x == size - 3 )||(x == size - 4 )) && (y == 1))
                {
                    x = r.nextInt(size-1);
                    y = r.nextInt(size-1);
                }
                //bal felso sarok
                else if((x == 1 )&& ((y == size - 2) || (y == size - 3 ) || (y == size - 4 )))
                {
                    x = r.nextInt(size-1);
                    y = r.nextInt(size-1);
                }
                else if(((x == 2 ) || (x == 3)) && (y == size - 2))
                { 
                    x = r.nextInt(size-1);
                    y = r.nextInt(size-1);
                }
                //bal also sarok
                else if((x == size - 2 )&& ((y == size - 2) || (y == size - 3 ) || (y == size - 4 )))
                {
                    x = r.nextInt(size-1);
                    y = r.nextInt(size-1);
                }
                else if(((x == size - 3 ) || (x == size - 4 ) ) && (y == size - 2))
                { 
                    x = r.nextInt(size-1);
                    y = r.nextInt(size-1);
                }
                else
                    break;
            }
            playField[x][y] = new Obst(x, y);
        }
        /*
        System.out.println(floorcount*0.7f);
        System.out.println(size*size);
        */
    }

    /**
     *
     * @return the generated table
     */
    public Actor[][] getPlayField(){
        return playField;
    }
    
	
	/**
	*
	* @return a field from the table on the X, Y postition
	*
	*/
    public Actor getField(int x, int y)
    {
        return playField[x][y];
    }
    
	
	/**
	*
	* @param X set the Actor on the X position
	* @param Y set the Actor on the Y position
 	* @param replace the Actor by the actor on the given coordinates
	*/
    public void setField(int x, int y, Actor actor)
    {
        playField[x][y] = actor;
    }
}
