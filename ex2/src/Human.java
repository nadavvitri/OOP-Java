/**
 * Created by nadav vitri on 10/04/2017.
 */
import oop.ex2.GameGUI;
import java.awt.Image;

/**
 * this class represent the Human SpaceShip - SpaceShip that Controlled by the user.
 * The keys are: left-arrow and right-arrow to turn, up-arrow to accelerate, 'd' to fire a shot,
 * 's' to turn on the shield, 'a' to teleport.
 */
public class Human extends SpaceShip {

    private static final int TURN_LEFT = 1;
    private static final int TURN_RIGHT = -1;

    /**
     * the constructor of A Human SpaceShip - all the basic features define here they from the class
     * SpaceShip by using the "super()" method.
     */
    protected Human(){
        super();
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        if (game.getGUI().isTeleportPressed())
            this.teleport();
        this.move(game);
        this.putShield(game);
        if (game.getGUI().isShotPressed())
            this.fire(game);
        this.chargingEnergy();
        this.setCounterToFire();
    }

    /**
     * this method make a move for Human SpaceShip - The keys are: left-arrow and right-arrow to turn,
     * up-arrow to accelerate.
     * @param game the game object to which this ship belongs.
     */
    private void move(SpaceWars game){
        boolean accel = game.getGUI().isUpPressed();
        int turn = 0;
        if (game.getGUI().isLeftPressed())
            turn += TURN_LEFT;
        if (game.getGUI().isRightPressed())
            turn += TURN_RIGHT;
        this.getPhysics().move(accel, turn);
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
            return GameGUI.SPACESHIP_IMAGE_SHIELD;
        else
            return GameGUI.SPACESHIP_IMAGE;
    }

    /**
     * this method put shield to the SpaceShip or not - if the Human use the 's' key so this method try to
     * put shields on (and if he has enough energy for that), else the shield is off.
     * @param game the game object to which this ship belongs.
     */
    private void putShield(SpaceWars game) {
        if (game.getGUI().isShieldsPressed())
            this.shieldOn();
        else
            this.hasShield = false;
    }


}
