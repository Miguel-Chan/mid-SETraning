import static org.junit.Assert.*;
import org.junit.Test;
import info.gridworld.grid.Location;
import info.gridworld.actor.*;



/**
 * JumperTest
 */
public class JumperTest {

    /**
    * Test:What will a jumper do if the location in front of it is
    * empty, but the location two cells in front contains a flower or a rock?
    * Expect: Jumper will move to the cell in front of it in the current step.
    */
    @Test
    public void testOne() {
        ActorWorld world = new ActorWorld();
        Jumper jude = new Jumper();
        jude.setDirection(Location.NORTH);
        world.add(new Location(5, 5), jude);
        Rock rock = new Rock();
        world.add(new Location(3, 5), rock);
        jude.act();
        assertEquals(4, jude.getLocation().getRow());
        assertEquals(5, jude.getLocation().getCol());
        jude.act();
        assertEquals(2, jude.getLocation().getRow());
        assertEquals(5, jude.getLocation().getCol());
    }

    /**
    * Test:What will a jumper do if the location two cells in front of the 
    * jumper is out of the grid?
    * Expect: 1. The jumper will move to the cell in front of it in the current step. 
    * Then turn in the next step.
    */
    @Test
    public void testTwo() {
        ActorWorld world = new ActorWorld();
        Jumper jude = new Jumper();
        jude.setDirection(Location.NORTH);
        world.add(new Location(1, 5), jude);
        jude.act();
        assertEquals(0, jude.getLocation().getRow());
        assertEquals(5, jude.getLocation().getCol());
        jude.act();
        assertEquals(0, jude.getLocation().getRow());
        assertEquals(5, jude.getLocation().getCol());
        assertEquals(Location.NORTHEAST, jude.getDirection());
    }


    /**
    * Test:What will a jumper do if it is facing an edge of the grid?
    * Expect: It will turn.
    */
    @Test
    public void testThree() {
        ActorWorld world = new ActorWorld();
        Jumper jude = new Jumper();
        jude.setDirection(Location.NORTH);
        world.add(new Location(0, 5), jude);
        jude.act();
        assertEquals(0, jude.getLocation().getRow());
        assertEquals(5, jude.getLocation().getCol());
        assertEquals(Location.NORTHEAST, jude.getDirection());
    }

    /**
    * Test:What will a jumper do if another actor (not a flower or a rock)
    * is in the cell that is two cells in front of the jumper?
    * Expect: The jumper will move to the cell in front of it in the current step. 
    * Then for the next step if the cell two cells in front of its 
    * current location is available then it will jump to that cell.
    */
    @Test
    public void testFour() {
        ActorWorld world = new ActorWorld();
        Jumper jude = new Jumper();
        jude.setDirection(Location.NORTH);
        world.add(new Location(5, 5), jude);
        Actor actor = new Actor();
        world.add(new Location(3, 5), actor);
        jude.act();
        assertEquals(4, jude.getLocation().getRow());
        assertEquals(5, jude.getLocation().getCol());
        jude.act();
        assertEquals(2, jude.getLocation().getRow());
        assertEquals(5, jude.getLocation().getCol());
    }

    /**
    * Test: What will a jumper do if it encounters another jumper in its path?
    * 
    * Expect: One will move 2 cells ahead and the other one cell ahead.
    */
    @Test
    public void testFive() {
        ActorWorld world = new ActorWorld();
        Jumper jude = new Jumper();
        jude.setDirection(Location.NORTH);
        world.add(new Location(5, 5), jude);
        Jumper paul = new Jumper();
        paul.setDirection(Location.SOUTH);
        world.add(new Location(1, 5), paul);
        world.step();
        assertEquals(4, jude.getLocation().getRow());
        assertEquals(5, jude.getLocation().getCol());
        assertEquals(3, paul.getLocation().getRow());
        assertEquals(5, paul.getLocation().getCol());
    }


    /**
    * Test:If a jumper has an actor(not a flower or a rock) 
    * in front will it jump over? 
    * Expect: it will jump over just like facing a flower or a rock.
    */
    @Test
    public void testSix() {
        ActorWorld world = new ActorWorld();
        Jumper jude = new Jumper();
        jude.setDirection(Location.NORTH);
        world.add(new Location(5, 5), jude);
        Actor actor = new Actor();
        world.add(new Location(4, 5), actor);
        jude.act();
        assertEquals(3, jude.getLocation().getRow());
        assertEquals(5, jude.getLocation().getCol());
    }
}