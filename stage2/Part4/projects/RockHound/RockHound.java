import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.awt.Color;

import java.util.ArrayList;

/**
 * A <code>RockHound</code> moves like a critter and
 * removes any rocks in the actors list
 */
public class RockHound extends Critter
{
    /**
    * process the actors the same way as a Critter
    * and remove any rock on the list from grid
    * @param actors the actors to be processed
    */
    public void processActors(ArrayList<Actor> actors)
    {
        for (Actor a : actors)
        {
            if (a instanceof Rock) {
                a.removeSelfFromGrid();
            }
        }
        super.processActors(actors);
    }

}
