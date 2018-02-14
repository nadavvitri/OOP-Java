/**
 * Created by nadav vitri on 14/04/2017.
 */
import oop.ex2.GameGUI;
import java.awt.*;
import java.util.Random;

/**
 * this class represent the Special SpaceShip - Its pilot trying get the closet ship and act like a "leech"
 * but every 100 rounds he randomally try to teleport.
 */
public class Special extends SpaceShip{

    private Random random = new Random();
    private static final double DISTANCE_FROM_SHIP = 0.19;
    private static final int COUNTER_FOR_CHANGE_BEHAVE = 100;
    private int counterForTryingTeleport;

    /**
     * the constructor of A Special SpaceShip - all the basic features define here they from the class
     * SpaceShip by using the "super()" method.
     * the special SpaceShip has counter for teleport and for fire and for shield (like describe before).
     */
    protected Special(){
        super();
        this.counterForTryingTeleport = COUNTER_FOR_CHANGE_BEHAVE;
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        this.tryTeleport();
        this.getPhysics().move(true, move(game));
        this.putShield(game);
        this.chargingEnergy();
        this.setCounterForTryingTeleport();
    }

    /**
     * this method make a move for Special SpaceShip - the ship always accelerate and make move towards the
     * close ship. the int for move return by using the math sign func
     * (that return 1 if it's positive, -1 if it's negetive and 0 if it's zero).
     * @param game the game object to which this ship belongs.
     * @return int this parameter indicates where the space ship is turning.
     * -1 indicates a right turn, 1 indicates a left turn and 0 indicates no turn.
     * Any other value results in an error
     */
    private int move(SpaceWars game){
        int turnTo = (int) Math.signum(this.angleFromCloseShip(game));
        return turnTo;
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     * @return the image of this ship.
     */
    @Override
    public Image getImage() {
        if (this.hasShield)
            return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
        else
            return GameGUI.ENEMY_SPACESHIP_IMAGE;
    }

    /**
     * this method put shield to the SpaceShip or not - If it gets within a distance
     * of 0.19 units (inclusive) from another ship, it will attempt to turn on its shields.
     * @param game the game object to which this ship belongs.
     */
    private void putShield(SpaceWars game) {
        if (this.distanceFromCloseShip(game) <= DISTANCE_FROM_SHIP)
            this.shieldOn();
        else
            this.hasShield = false;
    }
    /**
     * this method try to teleport - every 100 rounds the pilot try to teleport and if the random boolean
     * is true he will.
     */
    private void tryTeleport(){
        if ((random.nextBoolean()) && (this.counterForTryingTeleport <= 0))
            this.counterForTryingTeleport = COUNTER_FOR_CHANGE_BEHAVE;
            this.teleport();
    }

    /**
     * reduce one from the counter to teleport.
     */
    private void setCounterForTryingTeleport(){
        this.counterForTryingTeleport-- ;
    }
}
