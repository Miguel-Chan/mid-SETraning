import info.gridworld.actor.Bug;

/**
 * A <code>ZBug</code> moves in a 'Z' pattern <br />
 */
public class ZBug extends Bug
{
    private int steps;
    private int sideLength;
    private int totalSteps;

    /**
     * Constructs a Z bug that moves in a 'Z' pattern
     * @param length the side length
     */
    public ZBug(int length)
    {
        steps = 0;
        sideLength = length;
        setDirection(90);
    }

    /**
     * Moves to the next location of the square.
     */
    public void act()
    {
        if (totalSteps < sideLength * 3) {
            if (steps < sideLength && canMove())
            {
                move();
                steps++;
                totalSteps++;
            }
            else if (totalSteps % sideLength == 0)
            {
                steps = 0;

                //First Rotation in 'Z'
                if (totalSteps / sideLength == 1) {
                    setDirection(225);
                }
                //Second Rotation in 'Z'
                else {
                    setDirection(90);
                }
            }
        }
    }
}
