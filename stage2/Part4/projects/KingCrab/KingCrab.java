import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.awt.Color;

import java.util.ArrayList;

/**
 * A <code>KingCrab</code> gets the actors to be processed in
 * the same way a CrabCritter does. A KingCrab causes each actor
 * that it processes to move one location further away from the
 * KingCrab. If the actor cannot move away, the KingCrab removes
 * it from the grid. When the KingCrab has completed processing the
 * actors, it moves like a CrabCritter.
 */
public class KingCrab extends CrabCritter
{


    /**
    * Get the distance between two location object
    */
    private static int getDistance(Location first, Location second) {
        return (int)Math.sqrt(Math.pow(first.getRow() - second.getRow(), 2) + 
                         Math.pow(first.getCol() - second.getCol(), 2));
    }

    /**
    * Move the target actor away for one cell,
    * if it cannot be moved, return false
    */
    private boolean moveAway(Actor target) {
        Location targetLocation = target.getLocation();
        ArrayList<Location> locations = getGrid().getEmptyAdjacentLocations(targetLocation);
        for (Location location : locations) {
            if (getDistance(location, getLocation()) - 
                getDistance(location, targetLocation) >= 1) {
                target.moveTo(location);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Processes the elements of <code>actors</code>. Move each
     * actor one cell away from the crab, if the actor cannot be moved,
     * it will be removed from the grid.
     * @param actors the actors to be processed
     */
    public void processActors(ArrayList<Actor> actors)
    {
        for (Actor a : actors)
        {
            if (!moveAway(a)) {
                a.removeSelfFromGrid();
            }
        }
    }
}
