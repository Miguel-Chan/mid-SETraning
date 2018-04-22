1. `isValid` is specified in `Grid` and is implemented in `BoundedGrid` and `UnboundedGrid`

   ```java
   //@file: info/gridworld/grid/Grid.java
   //@line: 50
   boolean isValid(Location loc);
   //@file: info/gridworld/grid/BoundedGrid.java
   //@line: 60~64
   public boolean isValid(Location loc)
       {
           return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                   && 0 <= loc.getCol() && loc.getCol() < getNumCols();
       }
   //@file: info/gridworld/grid/UnBoundedGrid.java
   //@line: 53~56
   public boolean isValid(Location loc)
       {
           return true;
       }
   ```

2. `getValidAdjacentLocations` calls the `isValid` method, other methods don't call `isValid` because they would call `getValidAdjacentLocations` which calls `isValid`.

   ```java

   //@file: info/gridworld/grid/AbstractGrid.java
   //@line: 36~49
   public ArrayList<Location> getValidAdjacentLocations(Location loc)
       {
           ArrayList<Location> locs = new ArrayList<Location>();

           int d = Location.NORTH;
           for (int i = 0; i < Location.FULL_CIRCLE / Location.HALF_RIGHT; i++)
           {
               Location neighborLoc = loc.getAdjacentLocation(d);
               if (isValid(neighborLoc))
                   locs.add(neighborLoc);
               d = d + Location.HALF_RIGHT;
           }
           return locs;
       }
   ```

3. The `get` method. `get` is implemented in `BoundedGrid` and `UnboundedGrid`.

   ```java
   //@file: info/gridworld/grid/AbstractGrid.java
   //@line: 28~34
   public ArrayList<E> getNeighbors(Location loc)
       {
           ArrayList<E> neighbors = new ArrayList<E>();
           for (Location neighborLoc : getOccupiedAdjacentLocations(loc))
               neighbors.add(get(neighborLoc));
           return neighbors;
       }
   //@file: info/gridworld/grid/UnboundedGrid.java
   //@line: 66~71
   public E get(Location loc)
       {
   		...
       }
   //@file: info/gridworld/grid/BoundedGrid.java
   //@line: 85~91
       public E get(Location loc)
       {
           ...
       }
   ```

4. Because `getEmptyAdjacentLocations` needs to check whether there is an object occupying in that location. And `get` will return null if there is no object.

5. Only four instead of eight locations will be checked that way, which are North, South, East and West.

   ```java
   //@file: info/gridworld/grid/AbstractGrid.java
   //@line: 41~47
   for (int i = 0; i < Location.FULL_CIRCLE / Location.HALF_RIGHT; i++)
           {
               Location neighborLoc = loc.getAdjacentLocation(d);
               if (isValid(neighborLoc))
                   locs.add(neighborLoc);
               d = d + Location.HALF_RIGHT;
           }
   ```

6. An `IllegalArgumentException` will be thrown in the constructor for `BoundedGrid` if the arguments value are not larger than 0.

   ```java
   //@file: info/gridworld/grid/BoundedGrid.java
   //@line: 41~44
   		if (rows <= 0)
               throw new IllegalArgumentException("rows <= 0");
           if (cols <= 0)
               throw new IllegalArgumentException("cols <= 0");
   ```

7. By using the length of each row. The assumption that `numRow` > 0  so that `occupantArray[0]` absolutely exists.

   ```java
   //@file: info/gridworld/grid/BoundedGrid.java
   //@line: 53~58
   public int getNumCols()
       {
           // Note: according to the constructor precondition, numRows() > 0, so
           // theGrid[0] is non-null.
           return occupantArray[0].length;
       }
   ```

8. The row and column of the location must be equal to or larger than 0 and must be smaller than the `numRow` and `numCol` of the grid respectively.

   ```java
   //@file: info/gridworld/grid/BoundedGrid.java
   //@line: 60~64
   public boolean isValid(Location loc)
       {
           return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                   && 0 <= loc.getCol() && loc.getCol() < getNumCols();
       }
   ```

9. An `arrayList` of Location, the time complexity is O(c * r)

   ```java
   //@file: info/gridworld/grid/BoundedGrid.java
   //@line: 66~83
   public ArrayList<Location> getOccupiedLocations()
       {
           ArrayList<Location> theLocations = new ArrayList<Location>();
           // Look at all grid locations.
           for (int r = 0; r < getNumRows(); r++)
           {
               for (int c = 0; c < getNumCols(); c++)
               {
                   // If there's an object at this location, put it in the array.
                   Location loc = new Location(r, c);
                   if (get(loc) != null)
                       theLocations.add(loc);
               }
           }
           return theLocations;
       }
   ```

10. The return type of `get` is Object E. The time complexity is O(1).

  ```java
  //@file: info/gridworld/grid/BoundedGrid.java
  //@line: 85~91
  public E get(Location loc)
      {
          if (!isValid(loc))
              throw new IllegalArgumentException("Location " + loc
                      + " is not valid");
          return (E) occupantArray[loc.getRow()][loc.getCol()]; // unavoidable warning
      }
  ```

11. If the input location is invalid or the object E is null an exception will be thrown.The time complexity is O(1).

    ```java
    //@file: info/gridworld/grid/BoundedGrid.java
    //@line: 93~105
    public E put(Location loc, E obj)
        {
            if (!isValid(loc))
                throw new IllegalArgumentException("Location " + loc
                        + " is not valid");
            if (obj == null)
                throw new NullPointerException("obj == null");

            // Add the object to the grid.
            E oldOccupant = get(loc);
            occupantArray[loc.getRow()][loc.getCol()] = obj;
            return oldOccupant;
        }
    ```

12. The return  type of remove is object E, nothing will happen when trying to remove an item from an empty location, the time complexity is O(1).

    ```java
    //@file: info/gridworld/grid/BoundedGrid.java
    //@line: 107~119
    public E remove(Location loc)
        {
            if (!isValid(loc))
                throw new IllegalArgumentException("Location " + loc
                        + " is not valid");
            
            // Remove the object from the grid.
            E r = get(loc);
            occupantArray[loc.getRow()][loc.getCol()] = null;
            return r;
        }
    ```

13. Most of the method is efficient enough other than `getOccupiedLocations` which can be optimized to O(n) by using an Map implementation.

14. `hashCode` and `equals` methods must be implemented in order to use `HashMap`. `compareTo` must be implemented to use `TreeMap`. Location satisfies all those requirements.

    ```java
    //@file: info/gridworld/grid/Location.java
    //@line: 234
    public int compareTo(Object other)
    //@line: 218
    public int hashCode()  
    //@line: 205
    public boolean equals(Object other)
    ```

15. Because every location except null value is valid in an unbounded grid. Since the `isValid` method in unbounded grid always return true, the input location must be checked for null value. In a `BoundedGrid`, a null Location will cause `NullPointerException` when calling its `getRow` and `getCol` methods so no null checking is needed.

    ```java
    //@file: info/gridworld/grid/UnboundedGrid.java
    //@line: 53~56
    public boolean isValid(Location loc)
        {
            return true;
        }
    ```

16. The time complexity of all three methods are O(1), it would be $O(log_2n)$ if a `TreeMap` is used.

    ```java
    //@file: info/gridworld/grid/UnboundedGrid.java
    //@line: 66~87
    public E get(Location loc)
        {
            if (loc == null)
                throw new NullPointerException("loc == null");
            return occupantMap.get(loc);
        }

        public E put(Location loc, E obj)
        {
            if (loc == null)
                throw new NullPointerException("loc == null");
            if (obj == null)
                throw new NullPointerException("obj == null");
            return occupantMap.put(loc, obj);
        }

        public E remove(Location loc)
        {
            if (loc == null)
                throw new NullPointerException("loc == null");
            return occupantMap.remove(loc);
        }
    ```

    ​

17. The order of the list returned from `getOccupiedLocations` will be different, since `HashMap` uses `hashCode` of Location, while `TreeMap` will use Location's `compareTo` , the order of the returned list may be different with locations that have large value of col and row.

    ```java
    //@file: info/gridworld/grid/UnboundedGrid.java
    //@line: 58~64
    public ArrayList<Location> getOccupiedLocations()
        {
            ArrayList<Location> a = new ArrayList<Location>();
            for (Location loc : occupantMap.keySet())
                a.add(loc);
            return a;
        }
    //@file: info/gridworld/grid/Location.java
    //@line: 218~221
    public int hashCode()
        {
            return getRow() * 3737 + getCol();
        }
    //@line: 234~246
    public int compareTo(Object other)
        {
            Location otherLoc = (Location) other;
            if (getRow() < otherLoc.getRow())
                return -1;
            if (getRow() > otherLoc.getRow())
                return 1;
            if (getCol() < otherLoc.getCol())
                return -1;
            if (getCol() > otherLoc.getCol())
                return 1;
            return 0;
        }
    ```

18. Yes it could. Usually a map uses more memory than an array when storing the same amount of items, so if the array is almost filled with items, the memory usage would be smaller than a map implementation.

19. Many methods in `UnboundedGrid` can be reused since they would have the same design and those method share the same functionalities. Methods `getOccupiedLocations`, `remove` and `get` in `UnboundedGrid` can be used without change. `put` can not be used without change since in `BoundedGrid` `put` requires another `isValid` check.

Efficiency chart:

|            Methods             | `SparseGridNode` version | `LinkedList<OccupantInCol>` version | `HashMap` version | `TreeMap` version |
| :----------------------------: | :----------------------: | :---------------------------------: | :---------------: | :---------------: |
|        ` getNeighbors`         |           O(c)           |                O(c)                 |       O(1)        |   $O(log_2n)$   |
|  `getEmptyAdjacentLocations`   |           O(c)           |                O(c)                 |       O(1)        |   $O(log_2n)$   |
| `getOccupiedAdjacentLocations` |           O(c)           |                O(c)                 |       O(1)        |   $O(log_2n)$   |
|    ` getOccupiedLocations`     |         O(r + n)         |              O(r + n)               |       O(n)        |       O(n)        |
|             `get`              |           O(c)           |                O(c)                 |       O(1)        |   $O(log_2n)$   |
|             `put`              |           O(c)           |                O(c)                 |       O(1)        |   $O(log_2n)$   |
|           ` remove`            |           O(c)           |                O(c)                 |       O(1)        |   $O(log_2n)$   |

20. The Big-Oh efficiency of the `get` method is O(1). The efficiency of the `put` method when the row and column index values are within the current array bounds is O(1). The efficiency when the array needs to be resized is O(c*r).

