import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.*;
import java.awt.Color;

import java.util.ArrayList;

/**
 * A <code>ChameleonKid</code> takes on the color of one of the actors 
 * immediately in front of hehind as
 * it moves through the grid. If there is no actor to process, the color will darken <br />
 */
public class ChameleonKid extends ModifiedChameleonCritter
{


    /**
     * Finds the valid adjacent locations of this critter in different
     * directions.
     * @param directions - an array of directions (which are relative to the
     * current direction)
     * @return a set of valid locations that are neighbors of the current
     * location in the given directions
     */
    public ArrayList<Location> getLocationsInDirections(int[] directions)
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        Grid gr = getGrid();
        Location loc = getLocation();
    
        for (int d : directions)
        {
            Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);
            if (gr.isValid(neighborLoc)) {
                locs.add(neighborLoc);
            }
        }
        return locs;
    }    


    /**
     * A kid gets the actors in front or behind
     * @return a list of actors occupying these locations
     */
    public ArrayList<Actor> getActors()
    {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        int[] dirs =
            { Location.AHEAD, Location.HALF_CIRCLE };
        for (Location loc : getLocationsInDirections(dirs))
        {
            Actor a = getGrid().get(loc);
            if (a != null) {
                actors.add(a);
            }
        }

        return actors;
    }

}
