
/**
 * Class extending sprite, with 
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Box extends Sprite{
    //public final int CENTERX, CENTERY;
    public final int x, y;
    private BoxStatus status;
    /**
     * Constructor for objects of class cell
     * @param centerX the center X of box
     * @param centerY the center Y of box
     * @param x value of box x side
     * @param y value of box y side
     */
    public Box(int centerX, int centerY, int x, int y){
        this(centerX, centerY, x, y, BoxStatus.EMPTY);
    }
    /**
     * Constructor for objects of Box
     * @param centerX
     */
    public Box(int centerX, int centerY, int x, int y, BoxStatus status){
        //this.CENTERX = centerX;
        //this.CENTERY = centerY;
        super(centerX,centerY);
        this.x = x;
        this.y = y;
        this.status = status;
    }
    /**
     * <pre>
     * getter for BoxStatus
     * @return BoxStatus
     * </pre>
     */
    public BoxStatus getStatus() {
        return status;
    }
    /**
     * <pre>
     * setter for BoxStatus
     * </pre>
     */
    public void setStatus(BoxStatus status) {
        this.status = status;
    }
    /**
     * <pre>
     * Draws sprite of a box
     * @param t a turtle object
     * </pre>
     */
    public void drawSprite(Turtle t){
        final int STARTINGX = CENTERX - x/2;
        final int STARTINGY = CENTERY + y/2;
        if(this.status == BoxStatus.POINT){
        t.penColor("Gold");
        }
        else if(this.status == BoxStatus.DANGER){
        t.penColor("Red");
        }
        else if(this.status == BoxStatus.PLAYER){
        t.penColor("steelblue");
        }
        else {
        t.penColor("Black");
        }
        for(int count = 0; count < x/2; count++){
            t.up();
            t.setPosition(STARTINGX + count,STARTINGY,-90);
            t.down();
            t.forward(y/2);
            //System.out.println("x = " + (STARTINGX + count) + " y = " + STARTINGY);
        }
    }
    /**
     * <pre>
     * Overridden toString for Box
     * @return string containing CENTERX,CENTERY,x,y, and BoxStatus
     * </pre>
     */
    @Override
    public String toString() { 
        String result = "CENTERX: " + CENTERX + ",CENTERY: " + CENTERY + ",x: " + x + ",y: " + y + ",BoxStatus: " + getStatus();
        return result;
    } 
}
