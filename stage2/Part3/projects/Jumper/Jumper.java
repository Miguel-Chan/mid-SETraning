import info.gridworld.actor.*;
import info.gridworld.grid.*;
/**
 * A <code>Jumper</code> moves forward two cells in each move <br />
 */
public class Jumper extends Actor
{

    /**
     * Constructs a jumper that moves forward two cells in each move
     */
    public Jumper()
    {
    }

    /**
     * If the jumper can move in its current position,
     * it will move forward, otherwise it will only turn
     * once in this step
     */
    public void act() {
        if (canMove()) {
          move();
        }
        else {
          turn();
        }
    }


    /**
    * The move of jumper. Jumper will first move forward once 
    * if the next cell is available,
    * after which if it still can move, it will move one more time,
    * thus making it move forward two cell in each step.
    * If the next cell is unavailable but the second cell is,
    * the jumper will directly jump to the second cell.
    */
    public void move() {
        Grid<Actor> grid = getGrid();
        if (grid == null) {
            return;
        }
        if (canMoveTwice()) {
            Location neighborLocation = getLocation().getAdjacentLocation(getDirection());
            Location targetLocation = neighborLocation.getAdjacentLocation(getDirection());
            moveTo(targetLocation);
            return;
        }
        if (canMoveOnce()) {
            Location targetLocation = getLocation().getAdjacentLocation(getDirection());
            moveTo(targetLocation);
        }
    }



    /**
    * Test if the cell ahead of the jumper is available
    * @return true if the cell ahead is available
    */
    private boolean canMoveOnce() {
        Grid<Actor> grid = getGrid();
        if (grid == null) {
            return false;
        }
        Location currentLocation = getLocation();
        Location nextLocation = currentLocation.getAdjacentLocation(getDirection());
        if (!grid.isValid(nextLocation)) {
            return false;
        }
        Actor neighbor = (Actor)grid.get(nextLocation);
        return neighbor == null;
    }


    /**
    * Test if the second cell ahead of the jumper is available
    * @return true if the second cell ahead is available
    */
    private boolean canMoveTwice() {
        Grid<Actor> grid = getGrid();
        if (grid == null) {
            return false;
        }
        Location nextLocation = getLocation().getAdjacentLocation(getDirection());
        Location secondLocation = nextLocation.getAdjacentLocation(getDirection());
        if (!grid.isValid(secondLocation)) {
            return false;
        }
        Actor neighbor = (Actor)grid.get(secondLocation);
        return neighbor == null;
    }


    /**
    * Right rotate 45 degrees.
    */
    public void turn() {
        setDirection(getDirection() + 45);
    }

    /**
    * Test if the jumper can move forward
    * * @return true if at least one cell ahead is available
    */
    public boolean canMove() {
        return canMoveOnce() || canMoveTwice();
    }
}
