1. The role of variable `sideLength` is to determine whether the bug should call method `turn()`

   For example, in `boxBug`:

   ```java
   if (steps < sideLength && canMove())
           {
               move();
               steps++;
           }
           else
           {
               turn();
               turn();
               steps = 0;
           }
   ```

   This shows that if the number of the accumulated steps of the bug reaches `sideLength`, the bug will turn unconditionally.

2. The role of variable `steps` is to record the number of steps the bug has moved since the last time the bug called `turn()`. It increments every time the bug moves and is set to 0 every time the bug turns.

   ```java
   {
       move();
   	steps++;
   }

   {
       turn();
       turn();
       steps = 0;
   }
   ```

   ​