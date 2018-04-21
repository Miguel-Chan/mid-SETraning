import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.awt.Color;

import java.util.ArrayList;

/**
 * A <code>QuickCrab</code> processes actors the same way a CrabCritter does.
 * A QuickCrab moves to one of the two locations, randomly selected,
 * that are two spaces to its right or left, if that location and the
 * intervening location are both empty. Otherwise, a QuickCrab moves like a CrabCritter.
 */
public class QuickCrab extends CrabCritter
{
    /**
     * @return list of empty locations two spaces to the right and to the left
     */
    public ArrayList<Location> getMoveLocations()
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        int[] dirs =
            { Location.LEFT, Location.RIGHT };
        for (Location loc : getTwoSpaceLocationsInDirections(dirs)) {
            if (getGrid().get(loc) == null) {
                locs.add(loc);
            }
        }
        if (locs.size() == 0) {
            return super.getMoveLocations();
        }
        return locs;
    }

    /**
     * Finds the valid two-space adjacent locations of this critter in different
     * directions.
     * @param directions - an array of directions (which are relative to the
     * current direction)
     * @return a set of valid locations that are two-space neighbors of the current
     * location in the given directions
     */
    public ArrayList<Location> getTwoSpaceLocationsInDirections(int[] directions)
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        Grid gr = getGrid();
        Location loc = getLocation();
    
        for (int d : directions)
        {
            Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);
            Location twoSpaceNeighbor = neighborLoc.getAdjacentLocation(getDirection() + d);
            if (gr.isValid(twoSpaceNeighbor) && gr.isValid(neighborLoc)
             && gr.get(neighborLoc) == null) {
                locs.add(twoSpaceNeighbor);
            }
        }
        return locs;
    }    
    
}
