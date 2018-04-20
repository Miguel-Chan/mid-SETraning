import info.gridworld.actor.Bug;

/**
 * A <code>DancingBug</code> dances by making different turns before each move. <br />
 */
public class DancingBug extends Bug
{
    private int steps;

    private int[] moves;

    /**
     * Constructs a dancing bug that dances by making different turns before each move.
     * @param An array that represents how many times the bug turns before each move
     */
    public DancingBug(int[] times)
    {
        steps = 0;
        moves = times.clone();
    }

    /**
     * Moves to the next location of the square.
     */
    public void act()
    {
        //Turns based on the array before action.
        int turningIndex = steps % moves.length;
        for (int i = 0; i < moves[turningIndex]; i++) {
            turn();
        }

        //Act like a bug
        if (canMove())
        {
            move();
            steps++;
        }
        else
        {
            turn();
            steps = 0;
        }
    }
}
