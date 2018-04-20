import info.gridworld.actor.Bug;

/**
 * A <code>CircleBug</code> traces out a circle of a given size. <br />
 */
public class CircleBug extends Bug
{
    private int steps;
    private int sideLength;

    /**
     * Constructs a circle bug that traces a circle of a given side length
     * @param length the side length
     */
    public CircleBug(int length)
    {
        steps = 0;
        sideLength = length;
    }

    /**
     * Moves to the next location of the square.
     */
    public void act()
    {
        if (steps < sideLength && canMove())
        {
            move();
            steps++;
        }
        else
        {
            //One turn so it seems like it's going in circle
            turn();
            steps = 0;
        }
    }
}
