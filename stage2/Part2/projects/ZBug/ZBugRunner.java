import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.*;

import java.awt.Color;

/**
 * This class runs a world that contains Z bugs. <br />
 */
public final class ZBugRunner
{
    private ZBugRunner() {}

    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        world.setGrid(new BoundedGrid(18, 18));
        ZBug alice = new ZBug(8);
        alice.setColor(Color.ORANGE);
        ZBug bob = new ZBug(2);
        world.add(new Location(0, 0), alice);
        world.add(new Location(5, 5), bob);
        world.show();
    }
}