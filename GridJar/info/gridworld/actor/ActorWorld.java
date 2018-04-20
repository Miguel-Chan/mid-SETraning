package info.gridworld.actor;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;
import java.util.ArrayList;































public class ActorWorld
  extends World<Actor>
{
  private static final String DEFAULT_MESSAGE = "Click on a grid location to construct or manipulate an actor.";
  
  public ActorWorld() {}
  
  public ActorWorld(Grid<Actor> grid)
  {
    super(grid);
  }
  
  public void show()
  {
    if (getMessage() == null)
      setMessage("Click on a grid location to construct or manipulate an actor.");
    super.show();
  }
  
  public void step()
  {
    Grid<Actor> gr = getGrid();
    ArrayList<Actor> actors = new ArrayList();
    for (Location loc : gr.getOccupiedLocations()) {
      actors.add(gr.get(loc));
    }
    for (Actor a : actors)
    {

      if (a.getGrid() == gr) {
        a.act();
      }
    }
  }
  




  public void add(Location loc, Actor occupant)
  {
    occupant.putSelfInGrid(getGrid(), loc);
  }
  




  public void add(Actor occupant)
  {
    Location loc = getRandomEmptyLocation();
    if (loc != null) {
      add(loc, occupant);
    }
  }
  





  public Actor remove(Location loc)
  {
    Actor occupant = (Actor)getGrid().get(loc);
    if (occupant == null)
      return null;
    occupant.removeSelfFromGrid();
    return occupant;
  }
}
