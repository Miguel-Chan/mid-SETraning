package info.gridworld.maze;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;

/**
 * A <code>MazeBug</code> can find its way in a maze. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class MazeBug extends Bug {
	public Location next;
	public Location last;
	public boolean isEnd = false;
	public Stack<Location> crossLocation = new Stack<Location>();
	public Integer stepCount = 0;
	boolean hasShown = false;//final message has been shown

	/**
	 * Constructs a maze bug that can go out of a maze
	 *
	 */
	public MazeBug() {
		setColor(Color.GREEN);
		last = new Location(0, 0);
	}

	/**
	 * Moves to the next location of the square.
	 */
	public void act() {
		boolean willMove = canMove();
		isEnd = hasReached();
		if (isEnd == true) {
		//to show step count when reach the goal		
			if (hasShown == false) {
				String msg = stepCount.toString() + " steps";
				JOptionPane.showMessageDialog(null, msg);
				hasShown = true;
			}
		} else if (willMove) {
            crossLocation.push(getLocation());
            next = getNewLocation(getDirectionsCount());
            move();

        } else {
            goBack();
        }
        //increase step count when move
        stepCount++;
	}

    /**
     * called when no where else to go,
     * go back to the previous location and search for
     * other route
     */
    private void goBack() {
	    next = crossLocation.pop();
	    move();
    }

    /**
     * @return The direction count for the four directions of maze bug.
     */
    private int[] getDirectionsCount() {
	    int[] result = {0, 0, 0, 0};
	    for (int i = 0; i < crossLocation.size() - 1; i++) {
	        result[crossLocation.get(i).getDirectionToward(
	                crossLocation.get(i + 1)) / Location.RIGHT]++;
        }
        result[crossLocation.get(crossLocation.size() - 1).getDirectionToward(
                getLocation()
        ) / Location.RIGHT]++;
	    return result;
    }


    /**
     * @return a new available location to go to next.
     */
    private Location getNewLocation(int[] directionsCount) {
        ArrayList<Location> locs = getAvailableLocations();
        if (locs.size() == 1) {
            return locs.get(0);
        }
        else {
            ArrayList<Location> bestChoices = new ArrayList<Location>();
            bestChoices.add(locs.get(0));
            for (Location loc : locs) {
                if (directionsCount[getLocation().getDirectionToward(loc) / Location.RIGHT] ==
                    directionsCount[getLocation().getDirectionToward(bestChoices.get(0))
                            / Location.RIGHT]) {
                    bestChoices.add(loc);
                }
                else if (directionsCount[getLocation().getDirectionToward(loc) / Location.RIGHT] >
                        directionsCount[getLocation().getDirectionToward(bestChoices.get(0))
                                / Location.RIGHT]) {
                    bestChoices.clear();
                    bestChoices.add(loc);
                }
            }
            int index = 0;
            if (bestChoices.size() > 1) {
                index = (int)(Math.random() * bestChoices.size());
            }
            return bestChoices.get(index);
        }
    }


    /**
     * @return Whether the bug has reached the destination of
     * the maze.
     */
    public boolean hasReached() {
	    ArrayList<Location> locs = getValid(getLocation());
	    if (locs.size() == 0) {
	        return false;
        }
	    Grid<Actor> gr = getGrid();
        for (Location loc : locs) {
            if (gr.get(loc) instanceof Rock && gr.get(loc).getColor().equals(Color.RED)) {
                return true;
            }
        }
        return false;
    }

	/**
	 * Find all positions that can be move to.
	 * 
	 * @param loc
	 *            the location to detect.
	 * @return List of positions.
	 */
	public ArrayList<Location> getValid(Location loc) {
        ArrayList<Location> locs = new ArrayList<Location>();
        Grid<Actor> gr = getGrid();
        for (int i = 0; i < Location.FULL_CIRCLE / Location.RIGHT; i++)
        {
            Location neighborLoc = loc.getAdjacentLocation(i * Location.RIGHT);
            if (gr.isValid(neighborLoc))
                locs.add(neighborLoc);
        }
        return locs;
	}

	/**
	 * Tests whether this bug can move forward into a location that is empty or
	 * contains a flower.
	 * 
	 * @return true if this bug can move.
	 */
	public boolean canMove() {
		return getAvailableLocations().size() != 0;
	}

    /**
     * @return a list of locations with no rock or
     * flower on.
     */
    private ArrayList<Location> getAvailableLocations() {
	    ArrayList<Location> locs = getValid(getLocation());
        ArrayList<Location> result = new ArrayList<Location>();
        if (locs.size() == 0) {
            return result;
        }
        Grid gr = getGrid();
        for (Location loc : locs) {
            if (!(gr.get(loc) instanceof Rock) && !(gr.get(loc) instanceof Flower)) {
                result.add(loc);
            }
        }
        return result;
    }


	/**
	 * Moves the bug forward, putting a flower into the location it previously
	 * occupied.
	 */
	public void move() {
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return;
		Location loc = getLocation();
		if (gr.isValid(next)) {
			setDirection(getLocation().getDirectionToward(next));
			moveTo(next);
		} else
			removeSelfFromGrid();
		Flower flower = new Flower(getColor());
		flower.putSelfInGrid(gr, loc);
	}
}
