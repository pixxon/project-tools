package bomberman.connection.util;



/**
 * Created by Attila on 2016. 03. 14..
 */

/**
 * Class to wrap the message object
 */
public class MessageWrapper {
    /**
     * @name The name of the class which is wrapped
     * @object The object which is wrapped
     */
    private String name;
    private String object;

    /**
     * Consructor
     * @param name The name of the wrapped class
     * @param object The string of the wrapped class
     */
    public MessageWrapper(String name,String object) {
        this.object = object;
        this.name = name;
    }


    /**
     * Getter
     * @return Returns the name of the wrapped object
     */
    public String getName() {
        return name;
    }

    /**
     * Getter
     * @return Returns the string of the object
     */
    public String getObject() {
        return object;
    }
}
