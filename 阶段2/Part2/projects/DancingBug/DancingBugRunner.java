import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.*;

import java.awt.Color;

/**
 * This class runs a world that contains dancing bugs. <br />
 */
public final class DancingBugRunner
{

    private DancingBugRunner() {}

    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        DancingBug alice = new DancingBug(new int[]{1, 5, 2, 4, 1, 1, 3, 2});
        alice.setColor(Color.ORANGE);
        DancingBug bob = new DancingBug(new int[]{1, 2, 2, 4, 1, 2, 3, 2});
        world.add(new Location(5, 5), alice);
        world.add(new Location(2, 2), bob);
        world.show();
    }
}