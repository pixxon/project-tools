package bomberman;

import bomberman.models.Actor;
import bomberman.models.Field;

public class Hello{
    public static void main(String[] args) {
        Field f = new Field();
        Actor[][] a = f.getField();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.print("[" + a[i][j].getClass() + "]");
            }
            System.out.println("\n");
        }
    }
}