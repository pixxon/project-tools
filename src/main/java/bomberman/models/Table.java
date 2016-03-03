package bomberman.models;

/**
 *
 * @author Imre
 */
public class Table {
    private Actor playField[][];
    private int defaultValue = 15;
    
    public Table() {
        playField = new Actor[defaultValue][defaultValue];
        generateField(defaultValue);
    }

    
    public Table(int size) {
        if(size % 2 != 1)
            size++;
        playField = new Actor[size][size];
        generateField(size);
    }
    
    private void generateField(int size){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                playField[i][j] = new Floor();
            }
        }
        
        for (int i = 0; i < size; i++) {
            playField[0][i] = new NonDWall();
            playField[size-1][i] = new NonDWall();
        }
        
        for (int i = 1; i < size-1; i++) {
            playField[i][0] = new NonDWall();
            playField[i][size-1] = new NonDWall();
        }
        
        for (int i = 2; i < size-2; i+= 2) {
            for (int j = 2; j < size; j+= 2) {
                playField[i][j] = new NonDWall();
            }
        }
    }

    /**
     *
     * @return 
     */
    public Actor[][] getField(){
        return playField;
    }
}
