import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.awt.Color;

import java.util.ArrayList;

/**
 * A <code>BlusterCritter</code> looks at all of 
 * the neighbors within two steps of its current location. 
 * (For a BlusterCritter not near an edge, this includes 24 locations).
 * It counts the number of critters in those locations. 
 * If there are fewer than c critters, the BlusterCritter’s
 * color gets brighter (color values increase).
 * If there are c or more critters, the BlusterCritter’s
 * color darkens (color values decrease).
 */
public class BlusterCritter extends Critter
{
    private int courage;
    private static final double DARKENING_FACTOR = 0.05;

    /**
    * Constructor of BlusterCritter
    * @param - c indicates the courage of the bluster critter
    */
    public BlusterCritter(int c) {
        super();
        courage = c;
    }

    /**
    * Override the processActor() method to count the actor number
    * if fewer than courage, the critter brightens, otherwise darkens
    */
    public void processActors(ArrayList<Actor> actors)
    {
        int count = actors.size();
        if (count < courage) {
            brightenColor();
        }
        else {
            darkenColor();
        }
    }


    /**
    * make the color of the critter brighter
    */
    public void brightenColor() {
        Color c = getColor();
        int red = (int) ((c.getRed() + 1) * (1 + DARKENING_FACTOR));
        int green = (int) ((c.getGreen() + 1) * (1 + DARKENING_FACTOR));
        int blue = (int) ((c.getBlue() + 1) * (1 + DARKENING_FACTOR));

        if (red > 255) {
            red = 255;
        }

        if (green > 255) {
            green = 255;
        }

        if (blue > 255) {
            blue = 255;
        }

        setColor(new Color(red, green, blue));
    }

    /**
    * make the color of the critter darker
    */
    public void darkenColor() {
        Color c = getColor();
        int red = (int) (c.getRed() * (1 - DARKENING_FACTOR));
        int green = (int) (c.getGreen() * (1 - DARKENING_FACTOR));
        int blue = (int) (c.getBlue() * (1 - DARKENING_FACTOR));

        setColor(new Color(red, green, blue));
    }

    /**
    * override the getActors() method to get neighbors within two steps
    * of the current location
    */
    public ArrayList<Actor> getActors()
    {
        ArrayList<Actor> result = new ArrayList<Actor>();
        Location currentLoc = getLocation();

        Grid currentGrid = getGrid();

        for (int row = currentLoc.getRow() - 2; row <= currentLoc.getRow() + 2; row++) {
            for (int col = currentLoc.getCol() - 2; col <= currentLoc.getCol() + 2; col++) {
                if (col == currentLoc.getCol() && row == currentLoc.getRow()) {
                    continue;
                }
                Location temp = new Location(row, col);
                if (currentGrid.isValid(temp)) {
                    Actor actor = (Actor)currentGrid.get(temp);
                    if (actor != null) {
                        result.add(actor);
                    }
                }
            }
        }
        return result;
    }
}
