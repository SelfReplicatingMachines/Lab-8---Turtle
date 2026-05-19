//make sure if this is really all thats needed
/**
 * Abstract class repersenting sprites with a center x and center y
 * @author Gryphon Lee
 * @version 1
 */
public abstract class Sprite{
    public final int CENTERX, CENTERY;
    /**
     * <pre>
     * Constructer for Sprite
     * @param centerX the center X of sprite
     * @param centerY the center Y of sprite
     * </pre>
     */
    public Sprite (int centerX, int centerY){
        CENTERX = centerX;
        CENTERY = centerY;
    }
    public abstract void drawSprite(Turtle t);
    /**
     * <pre>
     * Overridden toString for Sprite
     * @return string containing CENTERX, CENTER Y
     * </pre>
     */
    @Override
    public String toString() { 
    String result = CENTERX + "," + CENTERY;
    return result;
    } 
    /**
     * <pre>
     * compares two sprites based off x and y
     * @param x x value
     * @param y y value
     * @return boolean if both centerX and Y are equal to given params
     * </pre>
     */
    public boolean equals(int x, int y) {
    return (CENTERX == x && CENTERY == y) ;
    }
    //equals
}
