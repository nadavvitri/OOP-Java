/**
 * Created by nadav vitri on 10/04/2017.
 */
import oop.ex2.GameGUI;
import java.awt.*;

/**
 * this class represent the Aggressive SpaceShip - This ship pursues other ships and tries to fire at them.
 * It will always accelerate, and turn towards the nearest ship. When its angle to the nearest ship is less
 * than 0.21 radians, it will try to fire.
 */
public class Aggressive extends SpaceShip {

    private static final double ANGLE_FROM_SHIP = 0.21;

    /**
     * the constructor of A Aggressive SpaceShip - all the basic features define here they from the class
     * SpaceShip by using the "super()" method.
     */
    protected Aggressive() {
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
        this.tryToShot(game);
        this.chargingEnergy();
        this.setCounterToFire();

    }

    /**
     * this method make a move for Aggressive SpaceShip - the ship always accelerate and make move towards the
     * close ship. the int for move return by using the math sign func
     * (that return 1 if it's positive, -1 if it's negetive and 0 if it's zero).
     * @param game the game object to which this ship belongs.
     * @return int this parameter indicates where the space ship is turning.
     * -1 indicates a right turn, 1 indicates a left turn and 0 indicates no turn.
     * Any other value results in an error
     */
    private int move(SpaceWars game) {
        int turnTo = (int) Math.signum(this.angleFromCloseShip(game));
        return turnTo;
    }

    /**
     * this method try to shot - When its angle to the nearest ship is less
     * than 0.21 radians, it will try to fire.
     * @param game the game object to which this ship belongs.
     */
    private void tryToShot(SpaceWars game) {
        if (this.angleFromCloseShip(game) < ANGLE_FROM_SHIP)
            this.fire(game);
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship. This will be displayed on the GUI at the end of the round.
     * @return the image of this ship.
     */
    @Override
    public Image getImage() {
        return GameGUI.ENEMY_SPACESHIP_IMAGE;
    }
}
