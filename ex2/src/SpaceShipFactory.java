/**
* This class has a single static method (createSpaceships(String[])),
* which is currently empty. It is used by the supplied driver to create all the spaceship objects
* according to the command line arguments. You will implement this static method. See the
* API of this class. E.g., if the supplied array contains the strings "a" and "b", the method
* will return an array containing an Aggressive ship and a Basher ship.
 */
public class SpaceShipFactory {
    SpaceShip[] objects;

    /**
     * @param args this get few Arguments (at less 2) and every one is:
     * h - Human controlled ship (controlled by the user).
     * r - Runner: a ship that tries to avoid all other ships.
     * b - Basher: a ship that deliberately tries to collide with other ships.
     * a - Aggressive: a ship that tries to pursue other ships and fire at them.
     * d - Drunkard: a ship with a drunken pilot.
    • s - Special: a ship with some interesting behavior
     * @return a array of spaceShips that the user choose.
     */
    public static SpaceShip[] createSpaceShips(String[] args) {
        String HUMAN = "h";    String RUNNER = "r";
        String BASHER = "b";   String AGGRESSIVE = "a";
        String DRUNKARD = "d";  String SPECIAL = "s";
        SpaceShip[] spaceShipsArray = new SpaceShip[args.length];
        spaceShipsArray[0] = new Runner();
        spaceShipsArray[1] = new Aggressive();
        for (int i = 0; i < args.length; i++){
            if (args[i].equals(HUMAN))
                spaceShipsArray[i] = new Human();

            else if (args[i].equals(BASHER))
                spaceShipsArray[i] = new Basher();

            else if (args[i].equals(DRUNKARD))
                spaceShipsArray[i] = new Drunkard();

            else if (args[i].equals(RUNNER))
                spaceShipsArray[i] = new Runner();

            else if (args[i].equals(AGGRESSIVE))
                spaceShipsArray[i] = new Aggressive();

            else if (args[i].equals(SPECIAL))
                spaceShipsArray[i] = new Special();
        }
        return spaceShipsArray;
    }
}
