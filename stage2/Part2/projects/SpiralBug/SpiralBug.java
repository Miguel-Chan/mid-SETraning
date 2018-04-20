import info.gridworld.actor.Bug;

/**
 * A <code>SpiralBug</code> drops flowers in a spiral pattern <br />
 */
public class SpiralBug extends Bug
{
    private int steps;
    private int sideLength;

    /**
     * Constructs a circle bug that drops flowers in a spiral pattern
     * @param length the side length
     */
    public SpiralBug(int length)
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
            turn();
            turn();
            steps = 0;

            //Every time the bug reaches the end 
            //sideLength incremented so it will go in spiral
            sideLength += 1;
        }
    }
}
