1. Use `loc1.getRow()`.

   ```java
   //@file: info/gridworld/grid/location.java
   //@line: 110~113
       public int getRow()
       {
           return row;
       }
   ```

   ​

2. The value of b would be false, since loc1 and loc2 have different col and row value.

   ```java
   //@file: info/gridworld/grid/location.java
   //@line: 205~212
   public boolean equals(Object other)
       {
           if (!(other instanceof Location))
               return false;

           Location otherLoc = (Location) other;
           return getRow() == otherLoc.getRow() && getCol() == otherLoc.getCol();
       }
   ```

3. The value of loc3 would be a Location object with row 5 and col 3:

   ```java
   //@file: info/gridworld/grid/location.java
   //@line: 130~169
   public Location getAdjacentLocation(int direction)
       {
   		...
           int dc = 0;
           int dr = 0;
           ...
           else if (adjustedDirection == SOUTH)
               dr = 1;
           ...
           return new Location(getRow() + dr, getCol() + dc);
       }
   ```

4. The value of dir would be 135.

   ```java
   //@file: info/gridworld/grid/location.java
   //@line: 178~195
   public int getDirectionToward(Location target)
       {
           int dx = target.getCol() - getCol();
           int dy = target.getRow() - getRow();
           int angle = (int) Math.toDegrees(Math.atan2(-dy, dx));

           int compassAngle = RIGHT - angle;
           compassAngle += HALF_RIGHT / 2;
           if (compassAngle < 0)
               compassAngle += FULL_CIRCLE;
           return (compassAngle / HALF_RIGHT) * HALF_RIGHT;
       }
   ```

5. It will first calculate a adjusted direction which is rounded to closest multiple of 45 based on the input :

   ```java
   //@file: info/gridworld/grid/location.java
   //@line: 133~137
   int adjustedDirection = (direction + HALF_RIGHT / 2) % FULL_CIRCLE;
           if (adjustedDirection < 0)
               adjustedDirection += FULL_CIRCLE;

           adjustedDirection = (adjustedDirection / HALF_RIGHT) * HALF_RIGHT;
   ```

   Then, based on the adjusted direction, it will choose whether to increment or decrement the value of the row and col and create a new Location object:

   ```java
     //@file: info/gridworld/grid/location.java
   //@line: 140~168
   if (adjustedDirection == EAST)
               dc = 1;
           else if (adjustedDirection == SOUTHEAST)
           {
               dc = 1;
               dr = 1;
           }
           else if (adjustedDirection == SOUTH)
               dr = 1;
           else if (adjustedDirection == SOUTHWEST)...
           else if (adjustedDirection == WEST)...
           else if (adjustedDirection == NORTHWEST)...
           else if (adjustedDirection == NORTH)...
           else if (adjustedDirection == NORTHEAST)...
           return new Location(getRow() + dr, getCol() + dc);
   ```

6. Call the `ArrayList<Location> getOccupiedLocations()`, the size of the returned ArrayList is the count of the objects in a grid, since only one object can be put on a location.

   ```java
   //@file: info/gridworld/grid/BoundedGrid.java
   //@line: 31
   private Object[][] occupantArray; // the array storing the grid elements
   ```

   Since each location is either containing or not containing an object, by multiplying the two value returned from `int getNumRows()` method and `int getNumCols()` method (to calculate the total area of the bounded grid) and minus the count of objects, the count of the empty locations in a bounded grid can be obtained.

7. call `grid.isValid(new Location(10, 10))` 

   ```java
   //@file: info/gridworld/grid/Grid.java
   //@line: 43~50
   /**
        * Checks whether a location is valid in this grid. <br />
        * Precondition: <code>loc</code> is not <code>null</code>
        * @param loc the location to check
        * @return <code>true</code> if <code>loc</code> is valid in this grid,
        * <code>false</code> otherwise
        */
       boolean isValid(Location loc);
   ```

8. Because Grid is an interface. An interface is used for other classes that inherit from it to implement the method in it. Any class inheriting from an interface must implement all methods in it. So no code is needed in methods in an interface. The implementation of the methods in Grid can be found in abstract class AbstractGrid and other classes that inherit from AbstractGrid.

   ```java
   //@file: info/gridworld/grid/Grid.java
   //@line: 29
   public interface Grid<E>
   //@file: info/gridworld/grid/AbstractGrid.java
   //@line: 26
   public abstract class AbstractGrid<E> implements Grid<E>

   ```

9. No, because from the perspective of implementing these methods, since there is no variable in Grid that records the number of objects, the number of the objects must be count when creating an array, which is not required by using an ArrayList. So using array may not be a better design.

10. `location` is the current location of the actor in current grid.

   `direction` is the direction that the actor is facing.

   `color` is the color of the actor, which will be shown in GUI.

   ```java
   //@file: info/gridworld/actor/Actor.java
   //@line: 32~34   
   private Location location;
       private int direction;
       private Color color;
   ```

11. An actor would be blue and be facing north when it is constructed.

    ```java
    //@file: info/gridworld/actor/Actor.java
    //@line: 39~45
    public Actor()
        {
            color = Color.BLUE;
            direction = Location.NORTH;
            grid = null;
            location = null;
        }
    ```

12. Since an actor already has its own method implementation and data member, it can not be created as a interface.

13. An actor cannot put itself into a grid twice without first removing itself, since an exception would be thrown that way:

    ```java
    //@file: info/gridworld/actor/Actor.java
    //@line: 115~127
    public void putSelfInGrid(Grid<Actor> gr, Location loc)
        {
            if (grid != null)
                throw new IllegalStateException(
                        "This actor is already contained in a grid.");
    		...
        }
    ```

    It also cannot remove itself from a grid twice:

    ```java
    //@file: info/gridworld/actor/Actor.java
    //@line: 133~146
    public void removeSelfFromGrid()
        {
            if (grid == null)
                throw new IllegalStateException(
                        "This actor is not contained in a grid.");
    		...
        }
    ```

    An actor can be placed into a grid, remove itself, and then put itself back. If so, In the end the actor will be placed in the location specified in the last `putSelfInGrid(Grid<Actor> gr, Location loc)` call.

14. An actor can turn 90 degrees to the right  by calling `setDirection(getDirection() + 90)` , this will take the current direction of the actor, adds it by 90 degrees, and  set it back to the actor.

15. ```java
    //@file: info/gridworld/actor/Bug.java
    //@line: 98~99   
    if (!gr.isValid(next))
                return false;
    ```

    This will check if the new location is valid in the grid containing the bug.

16. ```java
    //@file: info/gridworld/actor/Bug.java
    //@line: 100~101
    Actor neighbor = gr.get(next);
    return (neighbor == null) || (neighbor instanceof Flower);
    ```

    This will check if the neighbor object exists. If it exists and is not flower, then `canMove()` will return false, thus preventing the bug from walking into  a rock.

17. ```java
    //@file: info/gridworld/actor/Bug.java
    //@line: 98~100
    if (!gr.isValid(next))
                return false;
    Actor neighbor = gr.get(next);
    ```

    Methods `isValid(Location)` and `get(Location)` are called. The first is called to check whether the new location if out of the grid, the second is called to obtain the neighbor object so that it can be checked whether the neighbor is a rock or not.

18. ```java
    //@file: info/gridworld/actor/Bug.java
    //@line: 97
    Location next = loc.getAdjacentLocation(getDirection());
    ```

    Method `getAdjacentLocation(Direction)` is called to get the new location that the the bug is gong to occupy in order to check whether the new location is valid.

19. ```java
    //@file: info/gridworld/actor/Bug.java
    //@line: 93
    Grid<Actor> gr = getGrid();
    //@file: info/gridworld/actor/Bug.java
    //@line: 96
    Location loc = getLocation();
    //@file: info/gridworld/actor/Bug.java
    //@line: 97
    Location next = loc.getAdjacentLocation(getDirection());
    ```

    Methods `getGrid()`, `getDirection()` and `getLocation()` are invoked. The first one is invoked to get the current grid that contains the bug, so it can be used to check whether the new location is valid and check whether there is a neighbor on the new location. The second one is invoked to get the current location of the bug in order to get the new location. The last one is invoked to get the current direction of the bug in order to get the new location based on the current location and direction.

20. ```java
    //@file: info/gridworld/actor/Bug.java
    //@line: 78~81   
    		if (gr.isValid(next))
                moveTo(next);
            else
                removeSelfFromGrid();
    ```

    The bug will be removed from the grid.

21. Yes the variable loc is needed. It is used to record the original location of the bug, after the bug moves, a flower will be put in the original position using loc.

    ```java
    //@file: info/gridworld/actor/Bug.java
    //@line: 83
    flower.putSelfInGrid(gr, loc);
    ```

    The  loc can be replaced by calling `getLocation()` multiple times, but that also means that when putting the flower the original location must be calculated but using the new location and the opposite of current direction.

22. ```java
    //@file: info/gridworld/actor/Bug.java
    //@line: 82
    Flower flower = new Flower(getColor());

    //@file: info/gridworld/actor/Flower.java
    //@line: 46~49
    public Flower(Color initialColor)
        {
            setColor(initialColor);
        }
    ```

    Because when dropping the flower the flower is created by passing a color parameter to its constructor which sets the color of the flower.

23. ```java
    //@file: info/gridworld/actor/Actor.java
    //@line: 133~146
    public void removeSelfFromGrid()
        {
           ...
            grid.remove(location);
            grid = null;
            location = null;
        }
    ```

    No, when a bug removes itself from the grid, it calls the `removeSelfFromGrid()` method in Actor, which will not place a flower into the previous location.

24. ```java
    //@file: info/gridworld/actor/Bug.java
    //@line: 82~83
    Flower flower = new Flower(getColor());
    flower.putSelfInGrid(gr, loc);
    ```

25. ```java
    //@file: info/gridworld/actor/Bug.java
    //@line: 62~65
    public void turn()
        {
            setDirection(getDirection() + Location.HALF_RIGHT);
        }
    ```

    Four times, since each turn will turn the bug 45 degrees to the right