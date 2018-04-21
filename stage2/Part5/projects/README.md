1. Enter `ant` in console to run the program.

   The linked-list method is a more time-efficient implementation than BoundedGrid because when `getOccupiedLocations` is called the BoundedGrid will have to scan through the whole array to find all the location, which involves a lot of null checking. With the linked-list method, it only needs to scan every linked-list, which contains only existing objects, so it doesn't need to check whether there is an object in a location.

2. Efficiency chart:

   |            Methods             | `SparseGridNode` version | `LinkedList<OccupantInCol>` version | `HashMap` version | `TreeMap` version |
   | :----------------------------: | :----------------------: | :---------------------------------: | :---------------: | :---------------: |
   |        ` getNeighbors`         |           O(c)           |                O(c)                 |       O(1)        |   $$O(log_2n)$$   |
   |  `getEmptyAdjacentLocations`   |           O(c)           |                O(c)                 |       O(1)        |   $$O(log_2n)$$   |
   | `getOccupiedAdjacentLocations` |           O(c)           |                O(c)                 |       O(1)        |   $$O(log_2n)$$   |
   |    ` getOccupiedLocations`     |         O(r + n)         |              O(r + n)               |       O(n)        |       O(n)        |
   |             `get`              |           O(c)           |                O(c)                 |       O(1)        |   $$O(log_2n)$$   |
   |             `put`              |           O(c)           |                O(c)                 |       O(1)        |   $$O(log_2n)$$   |
   |           ` remove`            |           O(c)           |                O(c)                 |       O(1)        |   $$O(log_2n)$$   |

3. The Big-Oh efficiency of the `get` method is O(1). The efficiency of the `put` method when the row and column index values are within the current array bounds is O(1). The efficiency when the array needs to be resized is O(c*r).