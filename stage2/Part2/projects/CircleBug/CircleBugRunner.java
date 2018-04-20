import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;

import java.awt.Color;

/**
 * This class runs a world that contains circle bugs. <br />
 */
public final class CircleBugRunner
{
    private CircleBugRunner() {}

    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        CircleBug alice = new CircleBug(5);
        alice.setColor(Color.ORANGE);
        CircleBug bob = new CircleBug(2);
        world.add(new Location(7, 8), alice);
        world.add(new Location(5, 5), bob);
        world.show();
    }
}