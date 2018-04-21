|            Methods             | `SparseGridNode` version | `LinkedList<OccupantInCol>` version | `HashMap` version | `TreeMap` version |
| :----------------------------: | :----------------------: | :---------------------------------: | :---------------: | :---------------: |
|        ` getNeighbors`         |           O(c)           |                O(c)                 |       O(1)        |   $$O(log_2n)$$   |
|  `getEmptyAdjacentLocations`   |           O(c)           |                O(c)                 |       O(1)        |   $$O(log_2n)$$   |
| `getOccupiedAdjacentLocations` |           O(c)           |                O(c)                 |       O(1)        |   $$O(log_2n)$$   |
|    ` getOccupiedLocations`     |         O(r + n)         |              O(r + n)               |       O(n)        |       O(n)        |
|             `get`              |           O(c)           |                O(c)                 |       O(1)        |   $$O(log_2n)$$   |
|             `put`              |           O(c)           |                O(c)                 |       O(1)        |   $$O(log_2n)$$   |
|           ` remove`            |           O(c)           |                O(c)                 |       O(1)        |   $$O(log_2n)$$   |

