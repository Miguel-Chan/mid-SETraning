import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.*;

import java.awt.Color;

/**
 * This class runs a unbounded world that contains Spiral bugs. <br />
 */
public final class SpiralBugRunner
{
    private SpiralBugRunner() {}

    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        world.setGrid(new UnboundedGrid());
        SpiralBug alice = new SpiralBug(3);
        alice.setColor(Color.ORANGE);
        SpiralBug bob = new SpiralBug(1);
        world.add(new Location(7, 8), alice);
        world.add(new Location(5, 5), bob);
        world.show();
    }
}