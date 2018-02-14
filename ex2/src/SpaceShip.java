/**
 * The API spaceships need to implement for the SpaceWars game. 
 * It is your decision whether SpaceShip.java will be an interface, an abstract class,
 *  a base class for the other spaceships or any other option you will choose.
 *  
 * @author oop
 */
import java.awt.Image;
import oop.ex2.*;

public abstract class SpaceShip{

    private static final int FIFTEEN_ENERGY_UNIT = 15;
    private static final int TWENTY_ENERGY_UNIT = 20;
    private static final int FIVE_ENERGY_UNIT = 5;
    private static final int TEN_ENERGY_UNIT = 10;
    private static final int THREE_ENERGY_UNIT = 3;
    private static final int TWO_ENERGY_UNIT = 2;
    private static final int HUNDRED_ENERGY_UNIT = 100;
    private static final int NO_MORE_LIFE = 0;
    private static final int WAIT_SEVEN_ROUNDS = 7;
    private static final int INITIATE_HEALTH = 25;
    private static final int INITIATE_MAX_ENERGY = 250;
    private static final int INITIATE_CURRERNT_ENERGY = 200;
    private static final int COUNTER_TO_FIRE_AGAIN = 0;
    private int health, currentEnergy, maxEnergy, counterToFire;
    protected SpaceShipPhysics spaceShipPhysics;
    boolean hasShield;

    /**
     * the constructor of A basic SpaceShip - all the basic features define here (and more specifically
     * features define in the other classes that define different types of a SpaceShip.
     */
    public SpaceShip() {
        this.health = INITIATE_HEALTH;
        this.maxEnergy = INITIATE_MAX_ENERGY;
        this.currentEnergy = INITIATE_CURRERNT_ENERGY;
        this.spaceShipPhysics = new SpaceShipPhysics();
        this.counterToFire = COUNTER_TO_FIRE_AGAIN;
        this.hasShield = false;
    }

    /**
     * Does the actions of this ship for this round. 
     * This is called once per round by the SpaceWars game driver.
     * 
     * @param game the game object to which this ship belongs.
     */
    public abstract void doAction(SpaceWars game); {
    }

    /**
     * This method is called every time a collision with this ship occurs 
     */
    public void collidedWithAnotherShip(){
        if ((this.hasShield) && (this.currentEnergy >= FIFTEEN_ENERGY_UNIT) &&
                (this.maxEnergy + TWENTY_ENERGY_UNIT < maxEnergy)){
            this.maxEnergy += TWENTY_ENERGY_UNIT;
            this.currentEnergy -= FIFTEEN_ENERGY_UNIT;
        }
        else if ((this.hasShield) && (this.currentEnergy >= TEN_ENERGY_UNIT) &&
                (this.maxEnergy >= FIVE_ENERGY_UNIT)){
            this.maxEnergy -= FIVE_ENERGY_UNIT;
            this.currentEnergy -= TEN_ENERGY_UNIT;
        }
        else if (!this.hasShield){
            this.health --;
            if ((this.currentEnergy >= TEN_ENERGY_UNIT) && (this.maxEnergy >= FIVE_ENERGY_UNIT))
                this.maxEnergy -= FIVE_ENERGY_UNIT;
                this.currentEnergy -= TEN_ENERGY_UNIT;
        }
    }

    /** 
     * This method is called whenever a ship has died. It resets the ship's 
     * attributes, and starts it at a new random position.
     */
    public void reset(){
        this.health = INITIATE_HEALTH;
        this.maxEnergy = INITIATE_MAX_ENERGY;
        this.currentEnergy = INITIATE_CURRERNT_ENERGY;
        this.spaceShipPhysics = new SpaceShipPhysics();
        this.counterToFire = COUNTER_TO_FIRE_AGAIN;
        this.hasShield = false;
    }

    /**
     * Checks if this ship is dead.
     * 
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        if (this.health <= NO_MORE_LIFE)
            return true;
        return false;
    }

    /**
     * Gets the physics object that controls this ship.
     * 
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return this.spaceShipPhysics;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets shot at (with or without a shield).
     */
    public void gotShot() {
        if ((this.hasShield) && (this.currentEnergy >= TWO_ENERGY_UNIT)){
            this.currentEnergy -= TWO_ENERGY_UNIT;
        }
        else if ((this.maxEnergy >= FIVE_ENERGY_UNIT) && (this.currentEnergy >= TEN_ENERGY_UNIT))
            this.maxEnergy -= FIVE_ENERGY_UNIT;
            this.currentEnergy -= TEN_ENERGY_UNIT;
            this.health--;
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     * @return the image of this ship.
     */
    public abstract Image getImage(); {
    }

    /**
     * Attempts to fire a shot.
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
       if ((currentEnergy >= FIFTEEN_ENERGY_UNIT) && (counterToFire <= 0)){
           game.addShot(this.spaceShipPhysics);
           this.currentEnergy -= FIFTEEN_ENERGY_UNIT;
           counterToFire = WAIT_SEVEN_ROUNDS;
       }
    }

    /**
     * reduce one from the counter to fire (use every rounds for some of the SpaceShip)
     */
    public void setCounterToFire(){
        counterToFire --;
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if (currentEnergy >= THREE_ENERGY_UNIT){
            this.hasShield = true;
            this.currentEnergy -= THREE_ENERGY_UNIT;
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
       if (currentEnergy >= HUNDRED_ENERGY_UNIT){
           this.spaceShipPhysics = new SpaceShipPhysics();
           this.currentEnergy -= HUNDRED_ENERGY_UNIT;
       }
    }
    /**
     * this method charging the Energy of a SpaceShip - The current energy level is constantly charging:
     * it goes up by 2*(CurrentVelocity/MaximalVelocity) + 1 every round, up to the ship's current maximal
     * energy level. In other words, the charge is +1 when velocity below half the maximal velocity, +2 when
     * above half and +3 when at maximal velocity.
     */
    protected void chargingEnergy(){
        double calculateCharging = 2 * ((this.spaceShipPhysics.getVelocity()) /
                (this.spaceShipPhysics.getMaxVelocity())) + 1;
        int chargeEnergy = (int) calculateCharging;
        if (this.currentEnergy + chargeEnergy <= this.maxEnergy)
            this.currentEnergy += chargeEnergy;
    }

    /**
     * @param game the game object to which this ship belongs.
     * @return Computes the distance from another position. This computation takes the toroidal
     * structure of space into account.
     */
    protected double distanceFromCloseShip(SpaceWars game){
        return this.getPhysics().distanceFrom(game.getClosestShipTo(this).getPhysics());
    }

    /**
     * @param game the game object to which this ship belongs.
     * @return the relative angle to turn.
     */
    protected double angleFromCloseShip(SpaceWars game){
        return this.getPhysics().angleTo(game.getClosestShipTo(this).getPhysics());
    }


    
}
