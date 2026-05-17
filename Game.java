
import java.util.Random;

/**
 * <pre>
 * Constructs a board which updates vertically in real time
 * Intakes user inputs to move a virtual user
 * Tracks collection of points and danger which randomly spawn in
 * @author Gryphon Lee
 * </pre>
 */
public class Game {
    public static void main(String[] args) {
        //MAKE SURE TO TEST PUSHING TO GIT 
        final int BOARDWIDTH = 3; 
        final int BOARDLENGTH = 7;
        final int BOXWIDTH = 100;
        final int BOXLENGTH = 100; 
        int points = 0;
        int objectiveMaxxing = 0;
        boolean alive = true;
        Turtle drawer = new Turtle();
        drawer.speed(0);
        drawer.hide();
        Turtle turtleSwitch = new Turtle();
        turtleSwitch.up();
        turtleSwitch.onKey("holdingKeyRight", "d");
        turtleSwitch.onKey("holdingKeyLeft", "a");
        turtleSwitch.setPosition(25,0);
        BoxStatus[] boardstate = {
            BoxStatus.EMPTY,BoxStatus.POINT,BoxStatus.EMPTY,
            BoxStatus.EMPTY,BoxStatus.EMPTY,BoxStatus.EMPTY,
            BoxStatus.EMPTY,BoxStatus.EMPTY,BoxStatus.EMPTY,
            BoxStatus.EMPTY,BoxStatus.EMPTY,BoxStatus.EMPTY,
            BoxStatus.EMPTY,BoxStatus.EMPTY,BoxStatus.EMPTY,
            BoxStatus.EMPTY,BoxStatus.EMPTY,BoxStatus.EMPTY,
            BoxStatus.EMPTY,BoxStatus.PLAYER,BoxStatus.EMPTY,};
        
        while (alive){
            turtleSwitch.hide();
            if(turtleSwitch.getSpeed() == 100.0){
                playerMovement(boardstate, BOARDWIDTH, "d");
                turtleSwitch.speed(0);
            }   
            if(turtleSwitch.getSpeed() == -100.0){
                playerMovement(boardstate, BOARDWIDTH, "a");
                turtleSwitch.speed(0);
            }   
            
            if (objectiveMaxxing == -1){
                alive = false;
            } 
            else if (objectiveMaxxing == 1){
                points++;
                System.out.println("Points: " + points);
                objectiveMaxxing = 0;
            } 
            objectiveMaxxing = statusInteraction(boardstate, BOARDWIDTH,points);
            
            drawBoard(boardstate, BOARDWIDTH,BOARDLENGTH,BOXWIDTH,BOXLENGTH, drawer);
            drawer.zoomFit();
            tickGravity(boardstate,BOARDWIDTH);
            tickRNG(boardstate,BOARDWIDTH, true);
            
            turtleWait(drawer,1.0);

        }
        System.out.println("GAME OVER");
    } 
    /**
    * <pre>
    * sets a turtle's speed to 100, and makes it visably pointing right
    * @param t a turtle object
    * </pre>
    */
    public static void holdingKeyRight(Turtle t){
        t.setDirection(0);
        t.speed(100.0);
        t.show();
    }
    /**
    * <pre>
    * sets a turtle's speed to -100, and makes it visably pointing left
    * @param t a turtle object
    * </pre>
    */
    public static void holdingKeyLeft(Turtle t){
        t.setDirection(180);
        t.speed(-100.0);
        t.show();
    }
    /**
    * <pre>
    * moves the player value in an array to the left or right depending on 
    * recived string. Cannot move past the bottom row of the array
    * @param og boardstate array
    * @param boardWidth width of array
    * @param s string to take in
    * </pre>
    */
    public static void playerMovement(BoxStatus[] og, int boardWidth, String s){
        int botRow= og.length - boardWidth - 1;
        int playerPos = 0;
        for (int i = botRow ; i < og.length; i++){
            if(og[i] == BoxStatus.PLAYER){
                playerPos = i;
            }
        }
        if (playerPos > 0 && s.equals("d") && og[botRow + boardWidth] != BoxStatus.PLAYER){
            og[playerPos + 1] = BoxStatus.PLAYER;
            og[playerPos] = BoxStatus.EMPTY;
        }
        else if (playerPos > 0 && s.equals("a") && og[botRow + 1] != BoxStatus.PLAYER){
            og[playerPos - 1] = BoxStatus.PLAYER;
            og[playerPos] = BoxStatus.EMPTY;
        }
    }
    /**
    * <pre>
    * Constructs a board by drawing a box for each value in an array
    * @param og boardstate array
    * @param boardWidth width of array
    * @param boardLength length of array
    * @param boxX x value of box side
    * @param boxY y value of box side
    * @param t a turtle object
    * </pre>
    */
    public static void drawBoard(BoxStatus[] og, int boardWidth,int boardLength, int boxX, int boxY, Turtle t){
        int currentSet = 0;
        for (int y = boardLength; y > 0; y--) {
            for (int x = 0; x < boardWidth; x++) {
                    Box b = new Box(x*boxX/2,y*boxY/2, boxX-10,boxY-10, og[currentSet]);
                    b.drawSprite(t);
                    currentSet++;
            }
        }

    }
    /**
    * <pre>
    * Shifts all values in an array down by the width of the array
    * @param og boardstate array
    * @param boardWidth width of array
    * </pre>
    */
    public static void tickGravity(BoxStatus[] og, int boardWidth){
        for (int i = og.length -1; i >= boardWidth; i--) {
            if(!(og[i] == BoxStatus.PLAYER && og[i-boardWidth] != BoxStatus.DANGER)){
                og[i] = og[i-boardWidth];
            }
        }       
        
    }
    /**
    * <pre>
    * Randomly returns status
    * Weights are determined by inputted array
    * @param statuses array used to determine weight of statuses
    * @return POINT, DANGER, or EMPTY
    * </pre>
    */
    public static BoxStatus randomStatus (int[] statuses){
        Random rng = new Random();
        int stored = rng.nextInt(statuses.length);
        switch(statuses[stored]){
            case 1:
                return BoxStatus.POINT;
            case -1: 
                return BoxStatus.DANGER;
            default:
                return BoxStatus.EMPTY;
        }
    }
    /**
    *<pre>
    * Randomly sets status of the top row
    * When true cannot create 2+ dangers
    * @param og boardstate array
    * @param boardWidth width of array
    * @param fair if true, only 1 danger can ever spawn
    * </pre>
    */
    public static void tickRNG(BoxStatus[] og,int boardWidth,boolean fair){
        //way simplier in hindsight to fix
        Random rng = new Random();
        int[] randOptions = {1,-1,0,0,0,0};
        int dangerSpot = -1;
        BoxStatus holder;
        for (int i = 0; i < boardWidth; i++) {
            og[i] = randomStatus(randOptions);
            if (og[i] == BoxStatus.DANGER && fair){
                randOptions[1] = 0;
                dangerSpot = rng.nextInt(boardWidth);
                og[i] = randomStatus(randOptions);
            }
        }      
        if (dangerSpot != -1){
            og[dangerSpot] = BoxStatus.DANGER;
        }
    }
    /**
    * Detects interaction between player and unique statuses
    * @param og boardstate array
    * @param boardWidth Width of array
    * @param p current player score
    * @return
    *   1 if player collected point
    *   -1 if player hit danger
    *   0 else
    */
    public static int statusInteraction(BoxStatus[] og, int boardWidth, int p){
        for (int i = og.length - boardWidth*2 + 1; i < og.length - boardWidth; i++) {
            if(og[i+boardWidth] == BoxStatus.PLAYER){
                switch(og[i]){
                    case BoxStatus.POINT:
                        return 1;
                    case BoxStatus.DANGER: 
                        return -1;
                }
            }
        }
        return 0;
    }
    /**
    * Causes a delay using turtle movement
    * @param t turtle used for movement
    * @param time wait duration in seconds
    */
    public static void turtleWait(Turtle t, double time){
        double ogSpeed = t.getSpeed();
        double seconds = time*5;
        t.speed(100);
        t.up();
        t.setPosition(0,0);
        for (int i = 0; i < seconds; i++) {
            t.forward(10);
        }   
        for (int i = 0; i < seconds; i++) {
            t.forward(-10);
        }       
        t.speed(ogSpeed);
        t.down();
    }
    //turtle.onKey("playerMove", turtle)
}