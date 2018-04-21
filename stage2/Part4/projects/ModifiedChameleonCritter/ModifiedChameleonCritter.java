import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;
import java.awt.Color;

import java.util.ArrayList;

/**
 * A <code>ModifiedChameleonCritter</code> takes on the color of neighboring actors as
 * it moves through the grid. If there is no actor to process, the color will darken <br />
 */
public class ModifiedChameleonCritter extends ChameleonCritter
{

    private static final double DARKENING_FACTOR = 0.05;
    /**
     * Randomly selects a neighbor and changes this critter's color to be the
     * same as that neighbor's. If there are no neighbors, 
     * The color will darken.
     */
    public void processActors(ArrayList<Actor> actors)
    {
        int n = actors.size();
        if (n == 0) {
            //Darken the color when there is no actor to process
            Color c = getColor();
            int red = (int) (c.getRed() * (1 - DARKENING_FACTOR));
            int green = (int) (c.getGreen() * (1 - DARKENING_FACTOR));
            int blue = (int) (c.getBlue() * (1 - DARKENING_FACTOR));

            setColor(new Color(red, green, blue));
            return;
        }
        super.processActors(actors);
    }

}
