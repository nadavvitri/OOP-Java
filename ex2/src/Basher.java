/**
 * Created by nadav vitri on 10/04/2017.
 */
import oop.ex2.GameGUI;
import java.awt.*;

/**
 * this class represent the Basher SpaceShip - This ship attempts to collide with other ships.
 * It will always accelerate, and will constantly turn towards the closest ship. If it gets within a distance
 * of 0.19 units (inclusive) from another ship, it will attempt to turn on its shields.
 */
public class Basher extends SpaceShip {

    private static final double DISTANCE_FROM_SHIP = 0.19;

    /**
     * the constructor of A Basher SpaceShip - all the basic features define here they from the class
     * SpaceShip by using the "super()" method.
     */
    protected Basher() {
        super();
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        this.getPhysics().move(true, move(game));
        this.putShield(game);
        this.chargingEnergy();
    }

    /**
     * this method make a move for Basher SpaceShip - the ship always accelerate and make move towards the
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

}
