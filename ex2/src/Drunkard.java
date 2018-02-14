/**
 * Created by nadav vitri on 10/04/2017.
 */
import oop.ex2.GameGUI;
import java.awt.*;
import java.util.Random;

/**
 * this class represent the Drunkard SpaceShip - Its pilot had a tad too much to drink.
 * is move and fire and teleporting are random.
 */
public class Drunkard extends SpaceShip {

    private int TURN_LEFT = 1;
    private int TURN_RIGHT = -1;
    private static final int COUNTER_FOR_TELEPORTING = 20;
    private static final  int COUNTER_FOR_FIRE = 10;
    private static final  int COUNTER_FOR_SHIELD = 5;
    private int counterForFire,  counterForTeleporting, counterForShield;
    private Random randomMove = new Random();

    /**
     * the constructor of A Drunkard SpaceShip - all the basic features define here they from the class
     * SpaceShip by using the "super()" method.
     * the drunkard SpaceShip has counter for teleport and for fire and for shield (like describe before).
     */
    protected Drunkard(){
        super();
        this.counterForTeleporting = COUNTER_FOR_TELEPORTING;
        this.counterForFire = COUNTER_FOR_FIRE;
        this.counterForShield = COUNTER_FOR_SHIELD;
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * every round the counters reduce by one.
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        this.tryTeleport();
        this.getPhysics().move(randomMove.nextBoolean(), move());
        this.tryFire(game);
        this.tryPutShield();
        chargingEnergy();
        this.setCounterForTeleporting();
        this.setCounterForFiring();
        this.setCounterForShield();

    }

    /**
     * this method choose random move (between 1 and -1) for move.
     * @return int this parameter indicates where the space ship is turning.
     * -1 indicates a right turn, 1 indicates a left turn and 0 indicates no turn.
     * Any other value results in an error
     */
    private int move(){
        return this.randomMove.nextInt(TURN_LEFT) + (TURN_RIGHT);
    }

    /**
     * this method try to teleport - every 20 rounds the pilot try to teleport and if the random boolean
     * is true he will.
     */
    private void tryTeleport(){
        if ((randomMove.nextBoolean()) && (this.counterForTeleporting <= 0))
            this.counterForTeleporting = COUNTER_FOR_TELEPORTING;
            this.teleport();
    }

    /**
     * this method try to fire - every 10 rounds the pilot try to fire and if the random boolean
     * is true he will.
     */
    private void tryFire(SpaceWars game){
        if ((randomMove.nextBoolean()) && (this.counterForFire <= 0))
            this.counterForFire = COUNTER_FOR_FIRE;
            this.fire(game);
    }
    /**
     * this method try to put shield on - every 5 rounds the pilot try to put shield on and if the random
     * boolean is true he will.
     */
    private void tryPutShield(){
        if ((randomMove.nextBoolean()) && (this.counterForShield <= 0)) {
            this.counterForShield = COUNTER_FOR_SHIELD;
            this.shieldOn();
        }else
            this.hasShield = false;
    }

    /**
     * reduce one from the counter to teleport.
     */
    private void setCounterForTeleporting(){
        this.counterForTeleporting --;
    }

    /**
     * reduce one from the counter to fire.
     */
    private void setCounterForFiring(){
        this.counterForFire --;
    }

    /**
     * reduce one from the counter to fire.
     */
    private void setCounterForShield(){
        this.counterForShield --;
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


}
