1. ```java
   //@file: info/gridworld/actor/Critter.java
   //@line: 38
   public void act();
   //@line: 56
   public ArrayList<Actor> getActors();
   //@line: 71
   public void processActors(ArrayList<Actor> actors);
   //@line: 88
   public ArrayList<Location> getMoveLocations();
   //@line: 104
   public Location selectMoveLocation(ArrayList<Location> locs);
   //@line: 125
   public void makeMove(Location loc)
   ```

2. A critter will first get the actors' list by calling `getActors()` , then process all the actors on the list with `processActors(ArrayList<Actor>)`, then it gets all the locations it can move to  by using `getMoveLocations()` , selects a location with `selectMoveLocation(ArrayList<Location> )` , and finally make a move to that direction.

   ```java
   //@file: info/gridworld/actor/Critter.java
   //@line: 42~46
   	    ArrayList<Actor> actors = getActors();
           processActors(actors);
           ArrayList<Location> moveLocs = getMoveLocations();
           Location loc = selectMoveLocation(moveLocs);
           makeMove(loc);
   ```

3. Depends, if the subclasses need to use a different way to collect all the actors, then they should override the `getActor()` method. Otherwise there is no need to do so. For example, `ChameleonCritter` uses the same actor list so it doesn't have the need to override that method while on the other hand, `CrabCritter` only collects actors that are in front of it so it have to override that method.

   ```java
   //@file: critters/CrabCritter.java
   //@line: 44~57
   public ArrayList<Actor> getActors()
       {
           ArrayList<Actor> actors = new ArrayList<Actor>();
           int[] dirs =
               { Location.AHEAD, Location.HALF_LEFT, Location.HALF_RIGHT };
           for (Location loc : getLocationsInDirections(dirs))
           {
               Actor a = getGrid().get(loc);
               if (a != null)
                   actors.add(a);
           }
           return actors;
       }
   ```

4. For each of the actors in the actors list, if it is not a rock or a critter, then it will be removed from the grid.

   ```java
   //@file: info/gridworld/actor/Critter.java
   //@line: 71~78
   public void processActors(ArrayList<Actor> actors)
       {
           for (Actor a : actors)
           {
               if (!(a instanceof Rock) && !(a instanceof Critter))
                   a.removeSelfFromGrid();
           }
       }
   ```

5. `getMoveLocation`, `selectMoveLocation` and `makeMove`. The first one creates a list of possible locations that the critter can go to. The second one selects a location from the list and the last one moves the critter to the location selected.

   ```java
   //@file: info/gridworld/actor/Critter.java
   //@line: 44~46       
   ArrayList<Location> moveLocs = getMoveLocations();
           Location loc = selectMoveLocation(moveLocs);
           makeMove(loc);
   ```

6. The critter class itself don't have any data member that need initialization, and data members in its base class Actor will be initialized by the constructor of Actor which is called automatically by Java, so no constructor is needed.

7. Because `act` will call `processActors` and `makeMove` methods, which are overridden in `ChameleonCritter`, so `act` will perform differently.

   ```java
   //@file: critters/ChameleonCritter.java
   //@line: 36~45
   public void processActors(ArrayList<Actor> actors)
       {
           ...
       }
   //@file: critters/ChameleonCritter.java
   //@line: 50~54
   public void makeMove(Location loc)
       {
           ...
       }
   ```

8. Because the `makeMove` method is overridden to turn the critter to the direction of the new location as it moves, so after making that turn, the critter still has to move like a normal critter, which can be accomplished by calling `super.makeMove(Location)`.

   ```java
   //@file: critters/ChameleonCritter.java
   //@line: 50~54
   public void makeMove(Location loc)
       {
           setDirection(getLocation().getDirectionToward(loc));
           super.makeMove(loc);
       }
   ```

9. Add the following lines of code to the beginning of `makeMove` method:

   ```java
   Location oldLoc = getLocation();
   Grid<Actor> gr = getGrid();
   Flower flower = new Flower(getColor());
   flower.putSelfInGrid(gr, oldLoc);
   ```

10. Because `ChameleonCritter` doesn't use a different way to collect actors than a normal critter, thus the same actor list can be used and the `getActor` method doesn't need to be overridden.

11. ​