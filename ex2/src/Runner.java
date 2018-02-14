/**
 * Created by nadav vitri on 13/04/2017.
 */
import oop.ex2.GameGUI;
import java.awt.Image;
import java.lang.*;

/**
 * this class represent the Aggressive SpaceShip - This spaceship attempts to run away from the fight.
 * It will always accelerate, and will constantly turn away from the closest ship. If the nearest ship is
 * closer than 0.25 units, and if its angle to the Runner is less than 0.23 radians, the Runner feels
 * threatened and will attempt to teleport.
 */
public class Runner extends SpaceShip {

    private int TURN_LEFT = 1;
    private int TURN_RIGHT = -1;
    private int NO_TURN = 0;
    private static final double CLOSE_DISTANCE = 0.25;
    private static final double ANGLE_FROM_SHIP = 0.23;

    /**
     * the constructor of A Runner SpaceShip - all the basic features define here they from the class
     * SpaceShip by using the "super()" method.
     */
    protected Runner() {
        super();
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        this.tryTeleport(game);
        this.getPhysics().move(true, move(game));
        this.chargingEnergy();
    }
    /**
     * this method make a move for Runner SpaceShip - the ship always accelerate and make move to the
     * opposite angle of the close ship.
     * @param game the game object to which this ship belongs.
     * @return int this parameter indicates where the space ship is turning.
     * -1 indicates a right turn, 1 indicates a left turn and 0 indicates no turn.
     * Any other value results in an error
     */
    private int move(SpaceWars game){
        if (this.angleFromCloseShip(game) > 0)
            return TURN_RIGHT;
        else if (this.angleFromCloseShip(game) < 0)
            return TURN_LEFT;
        return NO_TURN;
    }

    /**
     * this method try to teleport - If the nearest ship is closer than 0.25 units, and if its angle to the
     * Runner is less than 0.23 radians, the Runner feels threatened and will attempt to teleport.
     * @param game the game object to which this ship belongs.
     */
    private void tryTeleport(SpaceWars game){
        SpaceShip closeShip = game.getClosestShipTo(this);
        double distanceFromShip = this.distanceFromCloseShip(game);
        double distanceAngle = Math.abs(closeShip.spaceShipPhysics.getAngle() -
                this.spaceShipPhysics.getAngle());
        if ((distanceFromShip < CLOSE_DISTANCE) && (distanceAngle < ANGLE_FROM_SHIP))
            this.teleport();
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
